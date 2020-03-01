package com.gelombang.apps.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityHomeBinding
import com.gelombang.apps.databinding.DialogLoginGuruBinding
import com.gelombang.apps.model.User


class HomeActivity : AppCompatActivity() {

    private var isLogged = false
    private var user = User()
    companion object {
        const val BACK = "BACK"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null

        binding.include.titleBar.text = resources.getString(R.string.beranda)
        binding.include.btnGuru.visibility = View.VISIBLE
        binding.include.btnHome.visibility = View.GONE
        binding.include.btnMenu.visibility = View.GONE
        binding.include.btnExit.visibility = View.VISIBLE

        val back:Boolean? = intent?.getBooleanExtra(BACK,false)
        if(back!!){
            startActivity(
                Intent(
                    this,
                    MainMenuActivity::class.java
                )
            )
        }

        binding.include.btnGuru.setOnClickListener {
            if (isLogged) {
                Toast.makeText(
                    baseContext,
                    "Kamu login sebagai ${user.name}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showLoginDialogGuru()
            }
        }

        binding.btnProfil.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }
        binding.btnTentang.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AboutActivity::class.java
                )
            )
        }
        binding.btnMulai.setOnClickListener {
            val intent = Intent(this,MainMenuActivity::class.java)
            intent.putExtra(MainMenuActivity.EXTRA_DATA,user)
            startActivity(intent)
        }
        binding.include.btnExit.setOnClickListener { exitDialog()}
    }

    private fun showLoginDialogGuru() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding: DialogLoginGuruBinding = DialogLoginGuruBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)

        binding.btnLoginGuru.setOnClickListener {
            if (binding.edUsername.text.toString() != "" || binding.edPassword.text.toString() != "") {
                if (binding.edUsername.text.toString() == "admin" && binding.edPassword.text.toString() == "gel2020") {
                    dialog.dismiss()
                    user = User("admin", "guru")
                    isLogged = true
                    val intent = Intent(this, MainMenuActivity::class.java)
                    intent.putExtra(MainMenuActivity.EXTRA_DATA, user)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        baseContext,
                        "Username atau password salah!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    baseContext,
                    "Username atau Password tidak boleh kosong!",
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

    private fun exitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informasi")
        builder.setMessage("Apakah anda ingin keluar dari aplikasi?")
        builder.setPositiveButton("YA") { _, _ ->
            super.onBackPressed()
        }
        builder.setNegativeButton("TIDAK") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        exitDialog()
    }
}
