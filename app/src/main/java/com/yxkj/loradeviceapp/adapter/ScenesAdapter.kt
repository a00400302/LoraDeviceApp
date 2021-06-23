package com.yxkj.loradeviceapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxkj.loradeviceapp.bean.SceneBean
import com.yxkj.loradeviceapp.databinding.ScenesItemBinding


class ScenesAdapter(var list: List<SceneBean>) : RecyclerView.Adapter<ScenesAdapter.ViewHolder?>() {



    inner class ViewHolder(binding: ScenesItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ScenesItemBinding = ScenesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }
}