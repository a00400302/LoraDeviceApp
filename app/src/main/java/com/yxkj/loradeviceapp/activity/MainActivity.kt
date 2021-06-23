package com.yxkj.loradeviceapp.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.Utils
import com.orhanobut.hawk.Hawk
import com.yxkj.loradeviceapp.Constant
import com.yxkj.loradeviceapp.R
import com.yxkj.loradeviceapp.adapter.FragmentPageAdapter
import com.yxkj.loradeviceapp.databinding.ActivityMainBinding
import com.yxkj.loradeviceapp.fragment.MainFragment
import com.yxkj.loradeviceapp.fragment.MySettingFragment
import com.yxkj.loradeviceapp.fragment.ScenesFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fragments: List<Fragment>? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Hawk.put(Constant.SERVER_IP, "47.93.235.26")
        Hawk.put(Constant.SERVER_PORT, 8083)
        setContentView(binding.root)
        initView()
        changeStatusBarTextImgColor(true)

    }

    private fun changeStatusBarTextImgColor(isBlack: Boolean) {
        if (isBlack) {
            window.decorView.systemUiVisibility = 8192
        } else {
            window.decorView.systemUiVisibility = 0
        }
    }

    private fun initView() {
        fragments = listOf(
            MainFragment(),
            ScenesFragment(),
            MySettingFragment()
        )

        binding.viewpager.adapter = FragmentPageAdapter(fragments!!, supportFragmentManager, 1)
        binding.tablayout.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.devices -> {
                    binding.viewpager.currentItem = 0
                }
                R.id.mysetting -> {
                    binding.viewpager.currentItem = 2
                }
                R.id.scenes -> {
                    binding.viewpager.currentItem = 1
                }
            }
            false
        }
        binding.viewpager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                    binding.tablayout.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }



}