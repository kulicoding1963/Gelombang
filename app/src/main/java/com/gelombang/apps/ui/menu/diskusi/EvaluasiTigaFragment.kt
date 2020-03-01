package com.gelombang.apps.ui.menu.diskusi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import com.gelombang.apps.databinding.FragmentEvaluasiTigaBinding
import com.gelombang.apps.model.HasilEvaluasi
import com.gelombang.apps.utils.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class EvaluasiTigaFragment : Fragment() {
    companion object {
        const val TYPE = "TYPE"
        const val DATA = "DATA"
        const val NAME = "NAME"
    }
    private var uri = arrayOfNulls<Uri>(7)
    private var images = arrayOfNulls<ImageView>(7)
    private var storageRef: StorageReference? = null
    private var mDatabase: DatabaseReference? = null
    private var type: String? = null
    private var name: String? = ""
    private var hasilEvaluasi: HasilEvaluasi? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEvaluasiTigaBinding = FragmentEvaluasiTigaBinding.inflate(layoutInflater)
        mDatabase = FirebaseDatabase.getInstance().getReference("Diskusi")
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://gelombang-f1a38.appspot.com")
        type = arguments?.getString(TYPE)
        name = arguments?.getString(NAME)
        hasilEvaluasi = arguments?.getParcelable(DATA)

        images[0] = binding.upload1
        images[1] = binding.upload2
        images[2] = binding.upload3
        images[3] = binding.upload4
        images[4] = binding.upload5
        images[5] = binding.upload6
        images[6] = binding.upload7

        if (type.equals("guru")) {
            binding.submit.visibility = View.GONE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = false
            }
            showDialog(activity!!)
            val pathReference:StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child(hasilEvaluasi?.key + "0.jpg")
            pathReference.downloadUrl.addOnSuccessListener { uri ->
                displayImageOriginal(
                    activity!!,
                    images[0],
                    uri,
                    images[0]!!.width,
                    images[0]!!.width * 2
                )
                val pathReference1:StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child(hasilEvaluasi?.key + "1.jpg")
                pathReference1.downloadUrl.addOnSuccessListener { uri1 ->
                    displayImageOriginal(
                        activity!!,
                        images[1],
                        uri1,
                        images[1]!!.width,
                        images[1]!!.width * 2
                    )
                    val pathReference2:StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child(hasilEvaluasi?.key + "2.jpg")
                    pathReference2.downloadUrl.addOnSuccessListener { uri2 ->
                        displayImageOriginal(
                            activity!!,
                            images[2],
                            uri2,
                            images[2]!!.width,
                            images[2]!!.width * 2
                        )
                        val pathReference3:StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child(hasilEvaluasi?.key + "3.jpg")
                        pathReference3.downloadUrl.addOnSuccessListener { uri3 ->
                            displayImageOriginal(
                                activity!!,
                                images[3],
                                uri3,
                                images[3]!!.width,
                                images[3]!!.width * 2
                            )
                            val pathReference4:StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child(hasilEvaluasi?.key + "4.jpg")
                            pathReference4.downloadUrl.addOnSuccessListener { uri4 ->
                                displayImageOriginal(
                                    activity!!,
                                    images[4],
                                    uri4,
                                    images[4]!!.width,
                                    images[4]!!.width * 2
                                )
                                val pathReference5:StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child(hasilEvaluasi?.key + "5.jpg")
                                pathReference5.downloadUrl.addOnSuccessListener { uri5 ->
                                    displayImageOriginal(
                                        activity!!,
                                        images[5],
                                        uri5,
                                        images[5]!!.width,
                                        images[5]!!.width * 2
                                    )
                                    val pathReference6:StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child(hasilEvaluasi?.key + "6.jpg")
                                    pathReference6.downloadUrl.addOnSuccessListener { uri6 ->
                                        displayImageOriginal(
                                            activity!!,
                                            images[6],
                                            uri6,
                                            images[6]!!.width,
                                            images[6]!!.width * 2
                                        )
                                        hideDialog()
                                    }.addOnFailureListener { e ->
                                        hideDialog()
                                        Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                                    }
                                }.addOnFailureListener { e ->
                                    hideDialog()
                                    Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                                }
                            }.addOnFailureListener { e ->
                                hideDialog()
                                Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener { e ->
                            hideDialog()
                            Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { e ->
                        hideDialog()
                        Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { e ->
                    hideDialog()
                    Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                hideDialog()
                Toast.makeText(activity!! ,e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.submit.visibility = View.VISIBLE
            for (x: Int in images.indices) {
                images[x]!!.isEnabled = true
            }
        }

        images[0]?.setOnClickListener {
            if (checkPermission(activity!!)) {
                showFileChooser(0)
            } else {
                requestPermission(activity!!)
            }
        }
        images[1]?.setOnClickListener {
            if (checkPermission(activity!!)) {
                showFileChooser(1)
            } else {
                requestPermission(activity!!)
            }
        }
        images[2]?.setOnClickListener {
            if (checkPermission(activity!!)) {
                showFileChooser(2)
            } else {
                requestPermission(activity!!)
            }
        }
        images[3]?.setOnClickListener {
            if (checkPermission(activity!!)) {
                showFileChooser(3)
            } else {
                requestPermission(activity!!)
            }
        }
        images[4]?.setOnClickListener {
            if (checkPermission(activity!!)) {
                showFileChooser(4)
            } else {
                requestPermission(activity!!)
            }
        }
        images[5]?.setOnClickListener {
            if (checkPermission(activity!!)) {
                showFileChooser(5)
            } else {
                requestPermission(activity!!)
            }
        }
        images[6]?.setOnClickListener {
            if (checkPermission(activity!!)) {
                showFileChooser(6)
            } else {
                requestPermission(activity!!)
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
                Toast.makeText(activity, "Isi jawaban yang kosong", Toast.LENGTH_SHORT).show()
            } else {
                sendAnswer(uri)
            }
        }

        return binding.root
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
            val filePath: String? = getPathFromUri(activity!!, convert!!)
            uri[requestCode] = Uri.fromFile(saveBitmapToFile(File(filePath!!)))
            displayImageOriginal(
                activity,
                images[requestCode],
                uri[requestCode],
                images[requestCode]!!.width,
                images[requestCode]!!.width * 2
            )
        }
    }

    private fun sendAnswer(
        imageUri: Array<Uri?>
    ) {
        showDialog(activity!!)
        val key: String? = mDatabase!!.child("PertemuanTiga").push().key
        for (x: Int in imageUri.indices) {
            val path: StorageReference = storageRef!!.child("Diskusi").child("PertemuanTiga").child("$key$x.jpg")
            val uploadTask: UploadTask = path.putFile(imageUri[x]!!)
            uploadTask.addOnSuccessListener {
                if (x == imageUri.size - 1) {
                    val maps = HashMap<String, String>()
                    maps["user"] = name!!
                    maps["status"] = "0"
                    maps["nilai"] = ""
                    mDatabase!!.child("PertemuanTiga").child(key!!).setValue(maps)
                        .addOnSuccessListener {
                            Toast.makeText(
                                activity,
                                "Berhasil mengirim data",
                                Toast.LENGTH_SHORT
                            ).show()
                            hideDialog()
                            activity?.finish()
                        }.addOnFailureListener { e ->
                            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                                .show()
                            hideDialog()
                        }
                }
            }.addOnFailureListener { e ->
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                hideDialog()
            }
        }
    }

}
