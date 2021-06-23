package com.yxkj.loradeviceapp.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.orhanobut.logger.Logger
import com.yxkj.loradeviceapp.adapter.DevicesListAdapter
import com.yxkj.loradeviceapp.bean.AgentBean
import com.yxkj.loradeviceapp.bean.ModelsBean
import com.yxkj.loradeviceapp.databinding.FragmentDevicesBinding
import com.yxkj.loradeviceapp.lightApi
import com.yxkj.loradeviceapp.util.RetrofitUtil
import java.util.*


class DevicesFragment : BaseFragment() {
    private var _binding: FragmentDevicesBinding? = null
    private var agentBean: AgentBean? = null
    private var comboId: Int? = null


    override fun sendHttp(callback: () -> Unit) {
        val agents = RetrofitUtil.getRetrofit(lightApi::class.java).agents
        RetrofitUtil.sendHttp(agents, {
            callback()
            agentBean = null
            agentBean = it
            changeView(it)
        }, {
            callback()
            Toast.makeText(context, "访问失败请刷新重试", Toast.LENGTH_SHORT).show()
            Logger.e("http failure", it.message)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val it: Bundle? = arguments
        if (it != null) {
            comboId = Integer.valueOf(it.getInt("ComboId", 0))
            agentBean = it.getSerializable("agentBean") as AgentBean
        }
    }

    private val binding get() = _binding!!


    private fun changeView(agentBean: AgentBean?) {
        val arrayList: ArrayList<ModelsBean> = ArrayList<ModelsBean>()
        if (agentBean != null) {
            for (data in agentBean.data!!) {
                for (t in data.models) {
                    t.AgentName = data.name
                    t.AgentId = data.agentId
                    arrayList.add(t)
                }
            }
        }
        binding.devicesList.layoutManager = GridLayoutManager(context, 2)
        val devicesListAdapter = DevicesListAdapter(arrayList)
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
        val screenWidth: Int = ScreenUtils.getScreenWidth()
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


    /* compiled from: DevicesFragment.kt */
    inner class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
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