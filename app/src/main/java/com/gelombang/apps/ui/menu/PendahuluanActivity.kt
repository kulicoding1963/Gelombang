package com.gelombang.apps.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityPendahuluanBinding
import com.gelombang.apps.ui.HomeActivity


class PendahuluanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPendahuluanBinding = ActivityPendahuluanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = resources.getString(R.string.pendahuluan)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.VISIBLE

        binding.include.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.include.btnMenu.setOnClickListener { onBackPressed() }
        binding.btnKi.setOnClickListener {
            val intent = Intent(this, ContentPendahuluanActivity::class.java)
            intent.putExtra(
                ContentPendahuluanActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.kompetensi_inti_ki)
            )
            intent.putExtra(ContentPendahuluanActivity.EXTRA_PDF, "kompetensi_inti.pdf")
            startActivity(intent)
        }
        binding.btnKd.setOnClickListener {
            val intent = Intent(this, ContentPendahuluanActivity::class.java)
            intent.putExtra(
                ContentPendahuluanActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.kompetensi_dasar_kd)
            )
            intent.putExtra(ContentPendahuluanActivity.EXTRA_PDF, "kompetensi_dasar.pdf")
            startActivity(intent)
        }
        binding.btnIndikator.setOnClickListener {
            val intent = Intent(this, ContentPendahuluanActivity::class.java)
            intent.putExtra(
                ContentPendahuluanActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.indikator)
            )
            intent.putExtra(ContentPendahuluanActivity.EXTRA_PDF, "indikator.pdf")
            startActivity(intent)
        }
        binding.btnTujuan.setOnClickListener {
            val intent = Intent(this, ContentPendahuluanActivity::class.java)
            intent.putExtra(
                ContentPendahuluanActivity.EXTRA_TITLE_BAR,
                resources.getString(R.string.tujuan)
            )
            intent.putExtra(ContentPendahuluanActivity.EXTRA_PDF, "tujuan.pdf")
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
