package com.gelombang.apps.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gelombang.apps.R
import com.gelombang.apps.adapter.SectionsPagerAdapter
import com.gelombang.apps.databinding.ActivityContentDiskusiBinding
import com.gelombang.apps.model.HasilEvaluasi
import com.gelombang.apps.ui.HomeActivity
import com.gelombang.apps.ui.MainMenuActivity

class ContentDiskusiActivity : AppCompatActivity() {

    companion object {
        const val URL = "URL"
        const val TYPE = "TYPE"
        const val NAME = "NAME"
        const val DATA = "DATA"
        const val PERTEMUAN = "PERTEMUAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContentDiskusiBinding =
            ActivityContentDiskusiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url:String? = intent.getStringExtra(URL)
        val pertemuan:Int? = intent.getIntExtra(PERTEMUAN,0)
        val hash:String? = intent.getStringExtra(TYPE)
        val name:String? = intent.getStringExtra(NAME)
        val hasilEvaluasi: HasilEvaluasi? = intent.getParcelableExtra(DATA)

        binding.include.titleBar.text = String.format(resources.getString(R.string.pertemuan), pertemuan)
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

        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager,
            url,
            pertemuan,
            hash,
            name,
            hasilEvaluasi
        )
        binding.viewpager.adapter = sectionsPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
