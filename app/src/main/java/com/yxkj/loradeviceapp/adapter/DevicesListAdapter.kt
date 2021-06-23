package com.yxkj.loradeviceapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yxkj.loradeviceapp.R
import com.yxkj.loradeviceapp.bean.ModelsBean
import com.yxkj.loradeviceapp.databinding.DevicesItemBinding
import com.yxkj.loradeviceapp.lightApi
import com.yxkj.loradeviceapp.util.RetrofitUtil
import java.util.*


class DevicesListAdapter(var items: ArrayList<ModelsBean>) : RecyclerView.Adapter<DevicesListAdapter.ViewHolder?>() {


    /* compiled from: DevicesListAdapter.kt */
    inner class ViewHolder(itemView: DevicesItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val cardView: CardView
        val imageView: ImageView
        private val name: TextView

        /* renamed from: switch  reason: not valid java name */
        private val f0switch: SwitchCompat
        private val type: TextView
        fun getName(): TextView {
            return name
        }

        fun getType(): TextView {
            return type
        }

        val switch: SwitchCompat
            get() = f0switch

        fun bind(context: Context?) {
            f0switch.setOnClickListener{
                val modelsBean: ModelsBean = items[layoutPosition]
                val ledControl = RetrofitUtil.getRetrofit(lightApi::class.java)
                    .ledControl(modelsBean.modelId, !modelsBean.status)
                RetrofitUtil.sendHttp(ledControl,{
                        if (it.code == 1) {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            switch.isChecked = modelsBean.status
                            return@sendHttp
                        }
                        switch.isChecked = !modelsBean.status
                        imageView.setImageResource(if (modelsBean.status) R.mipmap.lvdeng else R.mipmap.hongdeng)
                },{
                    Toast.makeText(context, "网络请求失败请稍后重试", Toast.LENGTH_SHORT).show()
                })
            }

        }


        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        init {

            val cardView2: CardView = itemView.cardView

            cardView = cardView2
            val textView: TextView = itemView.deviceName

            name = textView
            val imageView2: ImageView = itemView.deviceIcon

            imageView = imageView2
            val textView2: TextView = itemView.deviceType
            type = textView2
            val switchCompat: SwitchCompat = itemView.deviceControl
            f0switch = switchCompat
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DevicesItemBinding =
            DevicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ViewHolder(binding)
        val context: Context = parent.context
        viewHolder.bind(context)
        return viewHolder
    }

    override  fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelsBean: ModelsBean = items[position]
        val modelsBean2: ModelsBean = modelsBean
        holder.getName().text = modelsBean2.name
        holder.switch.setOnCheckedChangeListener(null)
        val switchCompat: SwitchCompat = holder.switch
        switchCompat.isChecked = modelsBean2.status
        val imageView = holder.imageView
        val bool2: Boolean = modelsBean2.status
        imageView.setImageResource(if (modelsBean2.status) R.mipmap.lvdeng else R.mipmap.hongdeng)
        holder.getType().text = "灯"
    }


    override fun getItemViewType(position: Int): Int {
        return items[position].devicetype
    }

    override fun getItemCount(): Int {
        return items.size
    }
}