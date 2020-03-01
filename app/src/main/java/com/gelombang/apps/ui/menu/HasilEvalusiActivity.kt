package com.gelombang.apps.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gelombang.apps.adapter.HasilEvaluasiAdapter
import com.gelombang.apps.databinding.ActivityHasilEvalusiBinding
import com.gelombang.apps.model.HasilEvaluasi
import com.gelombang.apps.ui.HomeActivity
import com.gelombang.apps.utils.hideDialog
import com.gelombang.apps.utils.showDialog
import com.google.firebase.database.*

class HasilEvalusiActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PERTEMUAN = "PERTEMUAN"
        const val EVALUSI = "EVALUASI"
        const val URL = "URL"
    }

    private var adapterHasil: HasilEvaluasiAdapter? = null
    private var dataHasils = mutableListOf<HasilEvaluasi>()
    private var mDatabase: DatabaseReference? = null
    private var pdf: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHasilEvalusiBinding =
            ActivityHasilEvalusiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pertemuan: Int = intent.getIntExtra(EXTRA_PERTEMUAN, 0)
        pdf = intent?.getStringExtra(URL)
        val title: String? = "Hasil "+intent?.getStringExtra(EVALUSI) + " " + pertemuan

        binding.include.titleBar.text = title
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
        adapterHasil = HasilEvaluasiAdapter(dataHasils)
        binding.rvEvaluasi.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvEvaluasi.adapter = adapterHasil

        mDatabase = FirebaseDatabase.getInstance().reference

        if (intent?.getStringExtra(EVALUSI).equals("Latihan")) {
            when (intent.getIntExtra(EXTRA_PERTEMUAN, 0)) {
                1 -> requestData("Latihan", "PertemuanSatu")
                2 -> requestData("Latihan", "PertemuanDua")
                3 -> requestData("Latihan", "PertemuanTiga")
            }
        } else {
            when (intent.getIntExtra(EXTRA_PERTEMUAN, 0)) {
                1 -> requestData("Diskusi", "PertemuanSatu")
                2 -> requestData("Diskusi", "PertemuanDua")
                3 -> requestData("Diskusi", "PertemuanTiga")
            }
        }

        adapterHasil!!.ItemClick(object : HasilEvaluasiAdapter.OnItemClick {
            override fun onItemClicked(dataHasil: HasilEvaluasi?) {
                if (intent?.getStringExtra(EVALUSI).equals("Latihan")) {
                    val intent = Intent(baseContext, ContentLatihanActivity::class.java)
                    intent.putExtra(ContentLatihanActivity.TYPE, "guru")
                    intent.putExtra(ContentLatihanActivity.DATA, dataHasil)
                    intent.putExtra(ContentLatihanActivity.EXTRA_PERTEMUAN, pertemuan)
                    startActivity(intent)
                } else {
                    val intent = Intent(baseContext,ContentDiskusiActivity::class.java)
                    intent.putExtra(ContentDiskusiActivity.TYPE, "guru")
                    intent.putExtra(ContentDiskusiActivity.PERTEMUAN, pertemuan)
                    intent.putExtra(ContentDiskusiActivity.DATA, dataHasil)
                    intent.putExtra(ContentDiskusiActivity.URL, "lembar_diskusi_1.pdf")
                    startActivity(intent)
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun requestData(kegiatan: String, aktivitas: String) {
        showDialog(this)
        mDatabase!!.child(kegiatan).child(aktivitas).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (x: DataSnapshot in dataSnapshot.children) {
                        val dataHasil = HasilEvaluasi(
                            x.child("user").value.toString(),
                            x.key.toString(),
                            x.child("nilai").value.toString(),
                            x.child("status").value.toString()
                        )
                        dataHasils.add(dataHasil)
                    }
                    adapterHasil!!.notifyDataSetChanged()
                    hideDialog()
                } else {
                    Toast.makeText(baseContext, "Data Kosong", Toast.LENGTH_SHORT).show()
                    hideDialog()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, databaseError.message, Toast.LENGTH_SHORT)
                    .show()
                hideDialog()
            }
        })
    }
}
