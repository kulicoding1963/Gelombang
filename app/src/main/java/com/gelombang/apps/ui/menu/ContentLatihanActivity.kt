package com.gelombang.apps.ui.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ContentLatihanPertemuanDuaBinding
import com.gelombang.apps.databinding.ContentLatihanPertemuanSatuBinding
import com.gelombang.apps.databinding.ContentLatihanPertemuanTigaBinding
import com.gelombang.apps.model.HasilEvaluasi
import com.gelombang.apps.ui.HomeActivity
import com.gelombang.apps.utils.*
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
import java.util.*

class ContentLatihanActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PERTEMUAN = "PERTEMUAN"
        const val TYPE = "TYPE"
        const val DATA = "DATA"
        const val NAME = "NAME"
    }

    private var uri = arrayOfNulls<Uri>(0)
    private var images = arrayOfNulls<ImageView>(0)
    private var storageRef: StorageReference? = null
    private var mDatabase: DatabaseReference? = null
    private var type: String? = null
    private var name: String? = ""
    private var hasilEvaluasi: HasilEvaluasi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance().getReference("Latihan")
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://gelombang-f1a38.appspot.com")

        type = intent.getStringExtra(TYPE)
        name = intent.getStringExtra(NAME)
        hasilEvaluasi = intent.getParcelableExtra(DATA)

        when (intent.getIntExtra(EXTRA_PERTEMUAN, 0)) {
            1 -> pertemuanSatu(
                ContentLatihanPertemuanSatuBinding.inflate(layoutInflater)
            )
            2 -> pertemuanDua(
                ContentLatihanPertemuanDuaBinding.inflate(layoutInflater)
            )
            3 -> pertemuanTiga(
                ContentLatihanPertemuanTigaBinding.inflate(layoutInflater)
            )
            else -> Toast.makeText(this, "Tidak ada layout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pertemuanSatu(
        binding: ContentLatihanPertemuanSatuBinding
    ) {
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = String.format(resources.getString(R.string.pertemuan), 1)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.VISIBLE

        binding.include.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.include.btnMenu.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(HomeActivity.BACK, true)
            startActivity(intent)
        }
        binding.pdf1.fromAsset("latihan_11.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()
        binding.pdf2.fromAsset("latihan_12.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()

        uri = arrayOfNulls(2)
        images = arrayOfNulls(2)

        images[0] = binding.upload1
        images[1] = binding.upload2

        if (type.equals("guru")) {
            binding.submit.visibility = View.GONE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = false
            }
            showDialog(this)
            val pathReference:StorageReference = storageRef!!.child("Latihan").child("PertemuanSatu").child(hasilEvaluasi?.key + "0.jpg")
            pathReference.downloadUrl.addOnSuccessListener { uri ->
                displayImageOriginal(
                    this,
                    images[0],
                    uri,
                    images[0]!!.width,
                    images[0]!!.width * 2
                )
                val pathReference1:StorageReference = storageRef!!.child("Latihan").child("PertemuanSatu").child(hasilEvaluasi?.key + "1.jpg")
                pathReference1.downloadUrl.addOnSuccessListener { uri1 ->
                    displayImageOriginal(
                        this,
                        images[1],
                        uri1,
                        images[1]!!.width,
                        images[1]!!.width * 2
                    )
                    hideDialog()
                }.addOnFailureListener { e ->
                    hideDialog()
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                hideDialog()
                Toast.makeText(this ,e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.submit.visibility = View.VISIBLE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = true
            }
        }

        images[0]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(0)
            } else {
                requestPermission(this)
            }
        }
        images[1]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(1)
            } else {
                requestPermission(this)
            }
        }
        binding.submit.setOnClickListener {
            var result = false
            for (x: Int in images.indices) {
                if (uri[x] == null) {
                    result = true
                    break
                }
            }
            if (result) {
                Toast.makeText(this, "Isi jawaban yang kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                sendAnswer(uri, "PertemuanSatu")
            }
        }
    }

    private fun pertemuanDua(
        binding: ContentLatihanPertemuanDuaBinding
    ) {
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = String.format(resources.getString(R.string.pertemuan), 2)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.VISIBLE

        binding.include.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.include.btnMenu.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(HomeActivity.BACK, true)
            startActivity(intent)
        }
        binding.pdf1.fromAsset("latihan_21.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()
        binding.pdf2.fromAsset("latihan_22.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()
        binding.pdf3.fromAsset("latihan_23.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()

        uri = arrayOfNulls(3)
        images = arrayOfNulls(3)

        images[0] = binding.upload1
        images[1] = binding.upload2
        images[2] = binding.upload3

        if (type.equals("guru")) {
            binding.submit.visibility = View.GONE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = false
            }
            showDialog(this)
            val pathReference:StorageReference = storageRef!!.child("Latihan").child("PertemuanDua").child(hasilEvaluasi?.key + "0.jpg")
            pathReference.downloadUrl.addOnSuccessListener { uri ->
                displayImageOriginal(
                    this,
                    images[0],
                    uri,
                    images[0]!!.width,
                    images[0]!!.width * 2
                )
                val pathReference1:StorageReference = storageRef!!.child("Latihan").child("PertemuanDua").child(hasilEvaluasi?.key + "1.jpg")
                pathReference1.downloadUrl.addOnSuccessListener { uri1 ->
                    displayImageOriginal(
                        this,
                        images[1],
                        uri1,
                        images[1]!!.width,
                        images[1]!!.width * 2
                    )
                    val pathReference2:StorageReference = storageRef!!.child("Latihan").child("PertemuanDua").child(hasilEvaluasi?.key + "2.jpg")
                    pathReference2.downloadUrl.addOnSuccessListener { uri2 ->
                        displayImageOriginal(
                            this,
                            images[2],
                            uri2,
                            images[2]!!.width,
                            images[2]!!.width * 2
                        )
                        hideDialog()
                    }.addOnFailureListener { e ->
                        hideDialog()
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { e ->
                    hideDialog()
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                hideDialog()
                Toast.makeText(this ,e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.submit.visibility = View.VISIBLE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = true
            }
        }

        images[0]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(0)
            } else {
                requestPermission(this)
            }
        }
        images[1]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(1)
            } else {
                requestPermission(this)
            }
        }
        images[2]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(2)
            } else {
                requestPermission(this)
            }
        }
        binding.submit.setOnClickListener {
            var result = false
            for (x: Int in images.indices) {
                if (uri[x] == null) {
                    result = true
                    break
                }
            }
            if (result) {
                Toast.makeText(this, "Isi jawaban yang kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                sendAnswer(uri, "PertemuanDua")
            }
        }
    }

    private fun pertemuanTiga(
        binding: ContentLatihanPertemuanTigaBinding
    ) {
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = String.format(resources.getString(R.string.pertemuan), 3)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.VISIBLE

        binding.include.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.include.btnMenu.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(HomeActivity.BACK, true)
            startActivity(intent)
        }

        binding.pdf1.fromAsset("latihan_31.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()
        binding.pdf2.fromAsset("latihan_32.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()
        binding.pdf3.fromAsset("latihan_33.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()
        binding.pdf4.fromAsset("latihan_34.pdf")
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .swipeHorizontal(false)
            .load()

        uri = arrayOfNulls(4)
        images = arrayOfNulls(4)

        images[0] = binding.upload1
        images[1] = binding.upload2
        images[2] = binding.upload3
        images[3] = binding.upload4

        if (type.equals("guru")) {
            binding.submit.visibility = View.GONE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = false
            }
            showDialog(this)
            val pathReference:StorageReference = storageRef!!.child("Latihan").child("PertemuanTiga").child(hasilEvaluasi?.key + "0.jpg")
            pathReference.downloadUrl.addOnSuccessListener { uri ->
                displayImageOriginal(
                    this,
                    images[0],
                    uri,
                    images[0]!!.width,
                    images[0]!!.width * 2
                )
                val pathReference1:StorageReference = storageRef!!.child("Latihan").child("PertemuanTiga").child(hasilEvaluasi?.key + "1.jpg")
                pathReference1.downloadUrl.addOnSuccessListener { uri1 ->
                    displayImageOriginal(
                        this,
                        images[1],
                        uri1,
                        images[1]!!.width,
                        images[1]!!.width * 2
                    )
                    val pathReference2:StorageReference = storageRef!!.child("Latihan").child("PertemuanTiga").child(hasilEvaluasi?.key + "2.jpg")
                    pathReference2.downloadUrl.addOnSuccessListener { uri2 ->
                        displayImageOriginal(
                            this,
                            images[2],
                            uri2,
                            images[2]!!.width,
                            images[2]!!.width * 2
                        )
                        val pathReference3:StorageReference = storageRef!!.child("Latihan").child("PertemuanTiga").child(hasilEvaluasi?.key + "3.jpg")
                        pathReference3.downloadUrl.addOnSuccessListener { uri3 ->
                            displayImageOriginal(
                                this,
                                images[3],
                                uri3,
                                images[3]!!.width,
                                images[3]!!.width * 2
                            )
                            hideDialog()
                        }.addOnFailureListener { e ->
                            hideDialog()
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { e ->
                        hideDialog()
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { e ->
                    hideDialog()
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                hideDialog()
                Toast.makeText(this ,e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.submit.visibility = View.VISIBLE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = true
            }
        }

        images[0]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(0)
            } else {
                requestPermission(this)
            }
        }
        images[1]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(1)
            } else {
                requestPermission(this)
            }
        }
        images[2]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(2)
            } else {
                requestPermission(this)
            }
        }
        images[3]?.setOnClickListener {
            if (checkPermission(this)) {
                showFileChooser(3)
            } else {
                requestPermission(this)
            }
        }
        binding.submit.setOnClickListener {
            var result = false
            for (x: Int in images.indices) {
                if (uri[x] == null) {
                    result = true
                    break
                }
            }
            if (result) {
                Toast.makeText(this, "Isi jawaban yang kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                sendAnswer(uri, "PertemuanTiga")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showFileChooser(position: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1 && data != null && data.data != null) {
            val convert: Uri? = data.data
            val filePath: String? = getPathFromUri(this, convert!!)
            uri[requestCode] = Uri.fromFile(saveBitmapToFile(File(filePath!!)))
            displayImageOriginal(
                this,
                images[requestCode],
                uri[requestCode],
                images[requestCode]!!.width,
                images[requestCode]!!.width * 2
            )
        }
    }

    private fun sendAnswer(
        imageUri: Array<Uri?>,
        pertemuan: String
    ) {
        showDialog(this)
        val key: String? = mDatabase!!.child(pertemuan).push().key
        for (x: Int in imageUri.indices) {
            val path: StorageReference = storageRef!!.child("Latihan").child(pertemuan).child("$key$x.jpg")
            val uploadTask: UploadTask = path.putFile(imageUri[x]!!)
            uploadTask.addOnSuccessListener {
                if (x == imageUri.size - 1) {
                    val maps = HashMap<String, String>()
                    maps["user"] = name!!
                    maps["status"] = "0"
                    maps["nilai"] = ""
                    mDatabase!!.child(pertemuan).child(key!!).setValue(maps)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Berhasil mengirim data",
                                Toast.LENGTH_SHORT
                            ).show()
                            hideDialog()
                            onBackPressed()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT)
                                .show()
                            hideDialog()
                        }
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                hideDialog()
            }
        }
    }
}
