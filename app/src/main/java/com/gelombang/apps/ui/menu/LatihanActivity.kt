package com.gelombang.apps.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gelombang.apps.R
import com.gelombang.apps.databinding.ActivityLatihanBinding
import com.gelombang.apps.ui.HomeActivity

class LatihanActivity : AppCompatActivity() {

    companion object {
        const val TYPE = "TYPE"
        const val NAME = "NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLatihanBinding = ActivityLatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)

        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.include.titleBar.text = resources.getString(R.string.latihan)
        binding.include.btnGuru.visibility = View.GONE
        binding.include.btnHome.visibility = View.VISIBLE
        binding.include.btnMenu.visibility = View.VISIBLE

        binding.include.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.include.btnMenu.setOnClickListener { onBackPressed() }

        val type:String? = intent.getStringExtra(ContentLatihanActivity.TYPE)
        val name:String? = intent.getStringExtra(ContentLatihanActivity.NAME)

        binding.btnPertemuan1.setOnClickListener {
            if(type.equals("guru")){
                val intent = Intent(this,HasilEvalusiActivity::class.java)
                intent.putExtra(HasilEvalusiActivity.EXTRA_PERTEMUAN,1)
                intent.putExtra(HasilEvalusiActivity.EVALUSI,"Latihan")
                startActivity(intent)
            }else{
                val intent = Intent(this,ContentLatihanActivity::class.java)
                intent.putExtra(ContentLatihanActivity.EXTRA_PERTEMUAN,1)
                intent.putExtra(ContentLatihanActivity.NAME,name)
                intent.putExtra(ContentLatihanActivity.TYPE,type)
                startActivity(intent)
            }
        }
        binding.btnPertemuan2.setOnClickListener {
            if(type.equals("guru")){
                val intent = Intent(this,HasilEvalusiActivity::class.java)
                intent.putExtra(HasilEvalusiActivity.EXTRA_PERTEMUAN,2)
                intent.putExtra(HasilEvalusiActivity.EVALUSI,"Latihan")
                startActivity(intent)
            }else{
                val intent = Intent(this,ContentLatihanActivity::class.java)
                intent.putExtra(ContentLatihanActivity.EXTRA_PERTEMUAN,2)
                intent.putExtra(ContentLatihanActivity.NAME,name)
                intent.putExtra(ContentLatihanActivity.TYPE,type)
                startActivity(intent)
            }
        }
        binding.btnPertemuan3.setOnClickListener {
            if(type.equals("guru")){
                val intent = Intent(this,HasilEvalusiActivity::class.java)
                intent.putExtra(HasilEvalusiActivity.EXTRA_PERTEMUAN,3)
                intent.putExtra(HasilEvalusiActivity.EVALUSI,"Latihan")
                startActivity(intent)
            }else{
                val intent = Intent(this,ContentLatihanActivity::class.java)
                intent.putExtra(ContentLatihanActivity.EXTRA_PERTEMUAN,3)
                intent.putExtra(ContentLatihanActivity.NAME,name)
                intent.putExtra(ContentLatihanActivity.TYPE,type)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
