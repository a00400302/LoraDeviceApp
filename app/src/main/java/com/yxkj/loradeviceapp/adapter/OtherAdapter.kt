package com.yxkj.loradeviceapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.yxkj.loradeviceapp.R
import com.yxkj.loradeviceapp.activity.DeviceInfoActivity
import com.yxkj.loradeviceapp.bean.DataBean
import com.yxkj.loradeviceapp.databinding.DevicesItemBinding
import com.yxkj.loradeviceapp.lightApi
import com.yxkj.loradeviceapp.util.RetrofitUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*



class OtherAdapter(arrayList: ArrayList<DataBean>) : RecyclerView.Adapter<OtherAdapter.ViewHolder>() {
    private val items: ArrayList<DataBean> = arrayList
    fun getItems(): ArrayList<DataBean> {
        return items
    }



    inner class ViewHolder(itemView: DevicesItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val cardView: CardView
        val imageView: ImageView
        val name: TextView


        val switch: SwitchCompat

        val type: TextView
        fun bind(context: Context) {
            cardView.setOnClickListener{
                val modelsBean: DataBean = getItems()[layoutPosition]
                val intent = Intent(context, DeviceInfoActivity::class.java)
                intent.putExtra("model", modelsBean)
                context.startActivity(intent)
            }

            switch.setOnClickListener{
                val dataBean: DataBean = getItems()[layoutPosition]
                val modelsBean: DataBean = dataBean
                val hashMap: HashMap<String, Any> = HashMap<String, Any>()
                val num: Int = modelsBean.id
                hashMap["id"] = num
                hashMap["type"] = 1
                val num2: Int = modelsBean.isOpen
                var i = 255
                if (num2 == 255) {
                    i = 0
                }
                hashMap["luminance"] = Integer.valueOf(i)
                hashMap["light"] = 1
                val requestBody = RequestBody.create(
                    MediaType.parse("application/json"),
                    Gson().toJson(hashMap, HashMap::class.java)
                )


                RetrofitUtil.sendHttp(RetrofitUtil.getRetrofitLoraLight(lightApi::class.java).setParams(requestBody),
                    {
                        if (it.code == 100) {
                            switch.isChecked = it.data.isOpen == 255
                            imageView.setImageResource(if (it.data.isOpen == 255) R.mipmap.lvdeng else R.mipmap.hongdeng)
                        }else{
                            Toast.makeText(context ,it.msg, Toast.LENGTH_SHORT).show()
                            switch.isChecked = modelsBean.isOpen == 255
                        }
                    },{
                        switch.isChecked = modelsBean.isOpen == 255
                        Toast.makeText(context ,"网络请求失败请稍后重试", Toast.LENGTH_SHORT).show()
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
            switch = switchCompat
        }
    }

    // androidx.recyclerview.widget.RecyclerView.Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DevicesItemBinding = DevicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder: ViewHolder = ViewHolder(binding)
        val context = parent.context
        viewHolder.bind(context)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataBean: DataBean = items[position]
        val modelsBean: DataBean = dataBean
        holder.name.text = modelsBean.ctrName
        holder.switch.setOnCheckedChangeListener(null)
        val switchCompat = holder.switch
        val num: Int = modelsBean.isOpen
        switchCompat.isChecked = true && num == 255
        val imageView = holder.imageView
        val num2: Int = modelsBean.isOpen
        imageView.setImageResource(if (num2 == 255) R.mipmap.lvdeng else R.mipmap.hongdeng)
        holder.type.text = "灯"
    }

    // androidx.recyclerview.widget.RecyclerView.Adapter
    override fun getItemCount(): Int {
        return items.size
    }

}