package com.gelombang.apps.ui.menu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityContentMateriBinding
import com.gelombang.apps.ui.HomeActivity
import com.gelombang.apps.ui.MainMenuActivity
import com.gelombang.apps.utils.dialogVideo
import com.github.barteksc.pdfviewer.util.FitPolicy

class ContentMateriActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TITLE_BAR = "TITLE"
        const val EXTRA_PDF = "PDF"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContentMateriBinding =
            ActivityContentMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title: String? = intent.getStringExtra(EXTRA_TITLE_BAR)
        val pdf: String? = intent.getStringExtra(EXTRA_PDF)

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
            intent.putExtra(HomeActivity.BACK,true)
            startActivity(intent)
        }
        binding.pdf.fromAsset(pdf)
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .linkHandler { event ->
                when (event.link.uri) {
                    "materi_5" -> dialogVideo(R.raw.materi_5, this)
                }
            }
            .swipeHorizontal(false)
            .load()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
