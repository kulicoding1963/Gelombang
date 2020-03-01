package com.gelombang.apps.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gelombang.apps.R
import com.gelombang.apps.model.HasilEvaluasi
import com.gelombang.apps.ui.menu.diskusi.DiskusiFragment
import com.gelombang.apps.ui.menu.diskusi.EvaluasiDuaFragment
import com.gelombang.apps.ui.menu.diskusi.EvaluasiSatuFragment
import com.gelombang.apps.ui.menu.diskusi.EvaluasiTigaFragment

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val url: String?,
    private val type: Int?,
    private val hash: String?,
    private val name: String?,
    private val hasilEvaluasi: HasilEvaluasi?
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    @StringRes
    private val tabTiles = intArrayOf(
        R.string.diskusi,
        R.string.jawaban
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        when (position) {
            0 -> {
                bundle.putString(DiskusiFragment.PDF, url)
                fragment = DiskusiFragment()
                fragment.arguments = bundle
            }
            1 -> {
                bundle.putString("TYPE", hash)
                bundle.putString("NAME", name)
                bundle.putParcelable("DATA", hasilEvaluasi)
                fragment = when(type){
                    1 -> EvaluasiSatuFragment()
                    2 -> EvaluasiDuaFragment()
                    3 -> EvaluasiTigaFragment()
                    else -> throw IllegalStateException("Unexpected value: $position")
                }
                fragment.arguments = bundle
            }
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTiles[position])
    }

}