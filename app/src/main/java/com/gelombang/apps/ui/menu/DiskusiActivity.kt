package com.gelombang.apps.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityDiskusiBinding
import com.gelombang.apps.ui.HomeActivity

class DiskusiActivity : AppCompatActivity() {

    companion object {
        const val TYPE = "TYPE"
        const val NAME = "NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDiskusiBinding = ActivityDiskusiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = resources.getString(R.string.diskusi)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.VISIBLE

        binding.include.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.include.btnMenu.setOnClickListener { onBackPressed() }

        val type:String? = intent.getStringExtra(TYPE)
        val name:String? = intent.getStringExtra(NAME)

        binding.btnPertemuan1.setOnClickListener {
            if(type.equals("guru")){
                val intent = Intent(this,HasilEvalusiActivity::class.java)
                intent.putExtra(HasilEvalusiActivity.EXTRA_PERTEMUAN,1)
                intent.putExtra(HasilEvalusiActivity.EVALUSI,"Diskusi")
                intent.putExtra(HasilEvalusiActivity.URL, "lembar_diskusi_1.pdf")
                startActivity(intent)
            }else{
                val intent = Intent(this,ContentDiskusiActivity::class.java)
                intent.putExtra(ContentDiskusiActivity.NAME, name)
                intent.putExtra(ContentDiskusiActivity.TYPE, type)
                intent.putExtra(ContentDiskusiActivity.PERTEMUAN, 1)
                intent.putExtra(ContentDiskusiActivity.URL, "lembar_diskusi_1.pdf")
                startActivity(intent)
            }
        }
        binding.btnPertemuan2.setOnClickListener {
            if(type.equals("guru")){
                val intent = Intent(this,HasilEvalusiActivity::class.java)
                intent.putExtra(HasilEvalusiActivity.EXTRA_PERTEMUAN,2)
                intent.putExtra(HasilEvalusiActivity.EVALUSI,"Diskusi")
                intent.putExtra(HasilEvalusiActivity.URL, "lembar_diskusi_2.pdf")
                startActivity(intent)
            }else{
                val intent = Intent(this,ContentDiskusiActivity::class.java)
                intent.putExtra(ContentDiskusiActivity.NAME, name)
                intent.putExtra(ContentDiskusiActivity.TYPE, type)
                intent.putExtra(ContentDiskusiActivity.PERTEMUAN, 2)
                intent.putExtra(ContentDiskusiActivity.URL, "lembar_diskusi_2.pdf")
                startActivity(intent)
            }
        }
        binding.btnPertemuan3.setOnClickListener {
            if(type.equals("guru")){
                val intent = Intent(this,HasilEvalusiActivity::class.java)
                intent.putExtra(HasilEvalusiActivity.EXTRA_PERTEMUAN,3)
                intent.putExtra(HasilEvalusiActivity.EVALUSI,"Diskusi")
                intent.putExtra(HasilEvalusiActivity.URL, "lembar_diskusi_3.pdf")
                startActivity(intent)
            }else{
                val intent = Intent(this,ContentDiskusiActivity::class.java)
                intent.putExtra(ContentDiskusiActivity.NAME, name)
                intent.putExtra(ContentDiskusiActivity.TYPE, type)
                intent.putExtra(ContentDiskusiActivity.PERTEMUAN, 3)
                intent.putExtra(ContentDiskusiActivity.URL, "lembar_diskusi_3.pdf")
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
