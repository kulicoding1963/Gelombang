package com.gelombang.apps.utils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.gelombang.apps.R
import com.gelombang.apps.databinding.DialogProgressBinding
import com.gelombang.apps.databinding.DialogVideoBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun dialogVideo(source: Int, activity: Activity) {
    val videoPath: String? = "android.resource://" + activity.packageName + "/" + source
    val dialog = Dialog(activity, android.R.style.Theme_Material_Light)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val binding: DialogVideoBinding = DialogVideoBinding.inflate(activity.layoutInflater)
    dialog.setContentView(binding.root)
    dialog.setCanceledOnTouchOutside(true)

    val uri: Uri = Uri.parse(videoPath)
    binding.video.setVideoURI(uri)
    binding.video.start()

    binding.btnMulai.setOnClickListener {
        if (binding.video.currentPosition > 100) {
            binding.video.start()
        } else {
            binding.video.setVideoURI(uri)
            binding.video.start()
        }
    }
    binding.btnStop.setOnClickListener {
        binding.video.pause()
    }
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(dialog.window!!.attributes)
    lp.width = WindowManager.LayoutParams.MATCH_PARENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.window!!.attributes = lp
    dialog.show()
}

private var dialog: Dialog? = null
fun showDialog(activity: Activity) {
    dialog = Dialog(activity,android.R.style.Theme_Material_Light)
    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val binding: DialogProgressBinding = DialogProgressBinding.inflate(activity.layoutInflater)
    dialog!!.setContentView(binding.root)
    dialog!!.setCancelable(false)
    dialog!!.setCanceledOnTouchOutside(false)
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(dialog!!.window!!.attributes)
    lp.width = WindowManager.LayoutParams.WRAP_CONTENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog!!.window!!.attributes = lp
    dialog!!.show()
}

fun hideDialog() {
    if(dialog!!.isShowing){
        dialog!!.dismiss()
    }
}

fun requestPermission(activity: Activity) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    ) {
        Toast.makeText(
            activity,
            " Please allow this permission in App Settings.",
            Toast.LENGTH_LONG
        ).show()
    } else {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }
}

fun checkPermission(context: Context): Boolean {
    val result = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun displayImageOriginal(
    ctx: Context?,
    img: ImageView?,
    uri: Uri?,
    width: Int,
    height: Int
) {
    Glide.with(ctx!!)
        .load(uri)
        .error(R.drawable.ic_error)
        .centerInside()
        .transition(DrawableTransitionOptions().crossFade(2000))
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .override(width, height)
        .into(img!!)
}

fun saveBitmapToFile(file: File): File? {
    return try {
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        o.inSampleSize = 6
        var inputStream = FileInputStream(file)
        BitmapFactory.decodeStream(inputStream, null, o)
        inputStream.close()
        val REQUIRED_SIZE = 75
        var scale = 1
        while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
            o.outHeight / scale / 2 >= REQUIRED_SIZE
        ) {
            scale *= 2
        }
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        inputStream = FileInputStream(file)
        val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
        inputStream.close()
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        assert(selectedBitmap != null)
        selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
        file
    } catch (e: Exception) {
        null
    }
}

fun getPathFromUri(context: Context, uri: Uri): String? {
    if (DocumentsContract.isDocumentUri(context, uri)) {
        if (isExternalStorageDocument(uri)) {
            val docId:String = DocumentsContract.getDocumentId(uri)
            val split:Array<String> = docId.split(":").toTypedArray()
            val type:String = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getDataDirectory().toString() + "/" + split[1]
            }
        } else if (isDownloadsDocument(uri)) {
            val id:String = DocumentsContract.getDocumentId(uri)
            val contentUri:Uri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                java.lang.Long.valueOf(id)
            )
            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
            val docId:String = DocumentsContract.getDocumentId(uri)
            val split:Array<String> = docId.split(":").toTypedArray()
            val type:String = split[0]
            var contentUri: Uri? = null
            when (type) {
                "image" -> {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }
                "video" -> {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }
                "audio" -> {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
            }
            val selection = "_id=?"
            val selectionArgs:Array<String> = arrayOf(
                split[1]
            )
            return getDataColumn(context, contentUri, selection, selectionArgs)
        }
    } else if ("content".equals(uri.scheme, ignoreCase = true)) { // Return the remote address
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
            context,
            uri,
            null,
            null
        )
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null
}

fun getDataColumn(
    context: Context, uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection:Array<String> = arrayOf(
        column
    )
    try {
        cursor = context.contentResolver.query(
            uri!!, projection, selection, selectionArgs,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val index:Int = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}
fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}
fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}
fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}
fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}
