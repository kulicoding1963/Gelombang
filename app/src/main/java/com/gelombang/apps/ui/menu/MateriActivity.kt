package com.gelombang.apps.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityMateriBinding
import com.gelombang.apps.ui.HomeActivity

class MateriActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMateriBinding = ActivityMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = resources.getString(R.string.materi)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.VISIBLE

        binding.include.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.include.btnMenu.setOnClickListener { onBackPressed() }

        binding.btnPengertian.setOnClickListener {
            val intent = Intent(this, ContentMateriActivity::class.java)
            intent.putExtra(
                ContentMateriActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.pengertian_gelombang)
            )
            intent.putExtra(ContentMateriActivity.EXTRA_PDF, "1.pdf")
            startActivity(intent)
        }
        binding.btnGejala.setOnClickListener {
            val intent = Intent(this, ContentMateriActivity::class.java)
            intent.putExtra(
                ContentMateriActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.gejala_gelombang)
            )
            intent.putExtra(ContentMateriActivity.EXTRA_PDF, "2.pdf")
            startActivity(intent)
        }
        binding.btnStasioner.setOnClickListener {
            val intent = Intent(this, ContentMateriActivity::class.java)
            intent.putExtra(
                ContentMateriActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.gelombang_stasioner)
            )
            intent.putExtra(ContentMateriActivity.EXTRA_PDF, "3.pdf")
            startActivity(intent)
        }
        binding.btnSifat.setOnClickListener {
            val intent = Intent(this, ContentMateriActivity::class.java)
            intent.putExtra(
                ContentMateriActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.sifat_sifat_gelombang)
            )
            intent.putExtra(ContentMateriActivity.EXTRA_PDF, "4.pdf")
            startActivity(intent)
        }
        binding.btnGempa.setOnClickListener {
            val intent = Intent(this, ContentMateriActivity::class.java)
            intent.putExtra(
                ContentMateriActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.kesiagapan)
            )
            intent.putExtra(ContentMateriActivity.EXTRA_PDF, "5.pdf")
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
