package com.yxkj.loradeviceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.yxkj.loradeviceapp.R
import com.yxkj.loradeviceapp.adapter.FragmentPageAdapter
import com.yxkj.loradeviceapp.databinding.FragmentMainBinding
import java.util.*
import kotlin.jvm.internal.Intrinsics


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private var devicesFragmentlist: List<BaseFragment>? = null

    /* access modifiers changed from: private */
    val binding get() = _binding!!


    // androidx.fragment.app.Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Intrinsics.checkNotNullParameter(inflater, "inflater")
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.refreshLayout.setColorSchemeResources(
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
        )
        binding.refreshLayout.setOnRefreshListener {
            sendHttp(binding.viewPager.currentItem)
        }
        initView()
        return binding.root
    }

    /* JADX INFO: Multiple debug info for r0v0 java.util.ArrayList: [D('$this$initView_u24lambda_u2d1' java.util.ArrayList), D('tabStr' java.util.ArrayList)] */ /* JADX INFO: Multiple debug info for r5v2 'index$iv'  int: [D('index' int), D('index$iv' int)] */
    private fun initView() {
        val tabStr: ArrayList<String> = ArrayList<String>()
        tabStr.add("所有设备")
        tabStr.add("房间")
        binding.viewPager.removeAllViews()
        if (devicesFragmentlist == null) {
            devicesFragmentlist =
                listOf(DevicesFragment(), OtherDevicesFragment())
        }
        val viewPager = binding.viewPager
        val list = devicesFragmentlist
        if (list != null) {
            val childFragmentManager = childFragmentManager
            viewPager.adapter = FragmentPageAdapter(list, childFragmentManager, 1)
            binding.tablayout.removeAllTabs()
            var index = 0
            for (i in tabStr) {
                val tab = binding.tablayout.newTab()
                tab.text = i
                binding.tablayout.addTab(tab, index == 0)
                index += 1
            }
            binding.comboSetting.setOnClickListener{

            }

            binding.tablayout.addOnTabSelectedListener(object:OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.viewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
            binding.viewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    binding.tablayout.getTabAt(position)?.select()
                    binding.refreshLayout.isRefreshing = true
                    sendHttp(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    when (state) {
                        1 -> {
                            binding.refreshLayout.isEnabled = false
                            return
                        }
                        2 -> {
                            binding.refreshLayout.isEnabled = true
                            return
                        }
                        else -> return
                    }
                }
            })
            binding.havedeviceLayout.visibility = View.VISIBLE
            binding.nodevicesLayout.visibility = View.GONE
            binding.refreshLayout.isRefreshing = false
            sendHttp(0)
            return
        }
        throw NullPointerException("null cannot be cast to non-null type kotlin.collections.List<androidx.fragment.app.Fragment>")
    }

    /* access modifiers changed from: private */
    fun sendHttp(position: Int) {
        if (devicesFragmentlist != null) {
            devicesFragmentlist!![position].refresh{
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    // androidx.fragment.app.Fragment
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment.newInstance()
        }
    }
}