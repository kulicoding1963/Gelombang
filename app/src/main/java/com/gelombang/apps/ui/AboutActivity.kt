package com.gelombang.apps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityAboutBinding
import com.github.barteksc.pdfviewer.util.FitPolicy

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAboutBinding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = resources.getText(R.string.tentang_aplikasi)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.GONE
        binding.include.btnMenu.visibility = View.GONE

        binding.pdf.fromAsset("tentang.pdf")
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
