package com.gelombang.apps.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityProfileBinding
import com.github.barteksc.pdfviewer.util.FitPolicy

class ProfileActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = resources.getText(R.string.profil)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.GONE
        binding.include.btnMenu.visibility = View.GONE

        binding.pdf.fromAsset("profil.pdf")
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .enableDoubletap(false)
            .enableSwipe(false)
            .load()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
