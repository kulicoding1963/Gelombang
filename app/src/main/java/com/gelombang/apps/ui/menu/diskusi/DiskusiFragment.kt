package com.gelombang.apps.ui.menu.diskusi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gelombang.apps.R
import com.gelombang.apps.databinding.FragmentDiskusiBinding
import com.gelombang.apps.utils.dialogVideo
import com.github.barteksc.pdfviewer.util.FitPolicy

/**
 * A simple [Fragment] subclass.
 */
class DiskusiFragment : Fragment() {
    companion object {
        const val PDF = "PDF"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentDiskusiBinding = FragmentDiskusiBinding.inflate(layoutInflater)
        val pdf:String? = arguments?.getString(PDF)
        binding.pdf.fromAsset(pdf)
            .enableSwipe(true)
            .pageFitPolicy(FitPolicy.WIDTH)
            .spacing(0)
            .linkHandler { event->
                when(event.link.uri){
                    "diskusi_1_1" -> dialogVideo(R.raw.diskusi_1_1, requireActivity())
                    "diskusi_1_2" -> dialogVideo(R.raw.diskusi_1_2,requireActivity())
                    "diskusi_1_3a" -> dialogVideo(R.raw.diskusi_1_3a,requireActivity())
                    "diskusi_1_3b" -> dialogVideo(R.raw.diskusi_1_3b,requireActivity())
                    "diskusi_1_3c" -> dialogVideo(R.raw.diskusi_1_3c,requireActivity())
                    "diskusi_1_3d" -> dialogVideo(R.raw.diskusi_1_3d,requireActivity())
                    "diskusi_1_4" -> dialogVideo(R.raw.diskusi_1_4,requireActivity())
                    "diskusi_3_1" -> dialogVideo(R.raw.diskusi_3_1,requireActivity())
                    "diskusi_3_2" -> dialogVideo(R.raw.diskusi_3_2,requireActivity())
                    "diskusi_3_3" -> dialogVideo(R.raw.diskusi_3_3,requireActivity())
                }
            }
            .swipeHorizontal(false)
            .load()
        return binding.root
    }

}
