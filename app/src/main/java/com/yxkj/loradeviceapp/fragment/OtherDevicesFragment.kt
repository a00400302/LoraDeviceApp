package com.yxkj.loradeviceapp.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.yxkj.loradeviceapp.adapter.OtherAdapter
import com.yxkj.loradeviceapp.bean.DataBean
import com.yxkj.loradeviceapp.bean.OtherBean
import com.yxkj.loradeviceapp.databinding.FragmentDevicesBinding

import com.yxkj.loradeviceapp.lightApi
import com.yxkj.loradeviceapp.util.RetrofitUtil
import java.util.*

class OtherDevicesFragment : BaseFragment() {
    private var agentBean: OtherBean? = null
    private var _binding: FragmentDevicesBinding? = null
    private val binding get() = _binding!!
    private var comboId: Int? = null
    override fun sendHttp(callback: () -> Unit) {
        val retrofitLoraLight = RetrofitUtil.getRetrofitLoraLight(lightApi::class.java)
        RetrofitUtil.sendHttp(retrofitLoraLight.lighList, {
            callback()
            changeView(it)
        }, {
            callback()
        })
    }


    // androidx.fragment.app.Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val it = arguments
        if (it != null) {
            comboId = Integer.valueOf(it.getInt("ComboId", 0))
            agentBean = it.getSerializable("agentBean") as OtherBean?
        }
    }

    /* access modifiers changed from: private */
    private fun changeView(otherBean: OtherBean?) {
        val arrayList: ArrayList<DataBean> = ArrayList<DataBean>()
        if (otherBean != null) {
            for (t in otherBean.data) {
                arrayList.add(t)
            }
        }
        binding.devicesList.layoutManager = GridLayoutManager(context, 2)
        val devicesListAdapter = OtherAdapter(arrayList)
        devicesListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT
        binding.devicesList.adapter = devicesListAdapter
    }

    // androidx.fragment.app.Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemWidth: Int
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        val screenWidth = ScreenUtils.getScreenWidth()
        itemWidth = if (SizeUtils.px2dp(screenWidth.toFloat()) <= 400) {
            SizeUtils.dp2px(160.0f)
        } else {
            SizeUtils.dp2px(175.0f)
        }

        binding.devicesList.addItemDecoration(
            SpaceItemDecoration(
                (screenWidth - itemWidth * 2) / 4
            )
        )

        changeView(agentBean)

        return binding.root
    }


    inner class SpaceItemDecoration(var space: Int) : ItemDecoration() {


        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.getChildAdapterPosition(view) % 2 == 0) {
                outRect.left = SizeUtils.dp2px(15.0f)
            } else if (parent.getChildAdapterPosition(view) % 2 == 1) {
                outRect.left = space * 2 - SizeUtils.dp2px(15.0f)
                outRect.right = SizeUtils.dp2px(15.0f)
            }
        }


    }


}