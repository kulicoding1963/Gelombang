package com.gelombang.apps.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityMainMenuBinding
import com.gelombang.apps.databinding.DialogLoginSiswaBinding
import com.gelombang.apps.model.User
import com.gelombang.apps.ui.menu.DiskusiActivity
import com.gelombang.apps.ui.menu.LatihanActivity
import com.gelombang.apps.ui.menu.MateriActivity
import com.gelombang.apps.ui.menu.PendahuluanActivity

class MainMenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainMenuBinding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = resources.getText(R.string.menu)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.GONE

        val user: User? = intent.getParcelableExtra(EXTRA_DATA)

        binding.include.btnHome.setOnClickListener { onBackPressed() }

        binding.btnPendahuluan.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    PendahuluanActivity::class.java
                )
            )
        }
        binding.btnMateri.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MateriActivity::class.java
                )
            )
        }
        binding.btnLatihan.setOnClickListener {
            println(user?.type)
            if (user?.type == null || user.type == "") {
                showLoginDialogSiswa( LatihanActivity::class.java)
            } else {
                val intent = Intent(this, LatihanActivity::class.java)
                intent.putExtra(LatihanActivity.NAME, user.name)
                intent.putExtra(LatihanActivity.TYPE, user.type)
                startActivity(intent)
            }

        }
        binding.btnDiskusi.setOnClickListener {
            if (user?.type == null || user.type == "") {
                showLoginDialogSiswa( DiskusiActivity::class.java)
            } else {
                val intent = Intent(this, DiskusiActivity::class.java)
                intent.putExtra(DiskusiActivity.NAME, user.name)
                intent.putExtra(DiskusiActivity.TYPE, user.type)
                startActivity(intent)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoginDialogSiswa(cls:Class<*>) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding: DialogLoginSiswaBinding = DialogLoginSiswaBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)

        binding.btnLoginSiswa.setOnClickListener {
            if (binding.edUsername.text.isNotEmpty()) {
                val intent = Intent(this, cls)
                intent.putExtra(DiskusiActivity.NAME, binding.edUsername.text.toString())
                intent.putExtra(DiskusiActivity.TYPE, "siswa")
                startActivity(intent)
                dialog.dismiss()
            } else {
                Toast.makeText(
                    baseContext,
                    "Nama siswa/kelompok tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp
        dialog.show()
    }

}
