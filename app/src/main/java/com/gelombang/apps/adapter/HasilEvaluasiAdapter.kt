package com.gelombang.apps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gelombang.apps.R
import com.gelombang.apps.model.HasilEvaluasi

class HasilEvaluasiAdapter(private val data: List<HasilEvaluasi>) :
    RecyclerView.Adapter<HasilEvaluasiAdapter.ViewHolder>() {
    private var onItemClick: OnItemClick? = null

    fun ItemClick(onItemClick: OnItemClick?) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_name, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var tvNama: TextView = itemView.findViewById(R.id.tv_nama)
        fun bind(dataHasil: HasilEvaluasi) {
            tvNama.text = dataHasil.name
            itemView.setOnClickListener { onItemClick!!.onItemClicked(dataHasil) }
        }

    }

    interface OnItemClick {
        fun onItemClicked(dataHasil: HasilEvaluasi?)
    }

}
