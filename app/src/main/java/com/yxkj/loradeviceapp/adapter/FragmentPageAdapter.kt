package com.yxkj.loradeviceapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlin.jvm.internal.Intrinsics

class FragmentPageAdapter(list2: List<Fragment>, fm: FragmentManager?, behavior: Int) :
    FragmentPagerAdapter(
        fm!!, behavior
    ) {
    val list: List<Fragment>

    // androidx.viewpager.widget.PagerAdapter
    override fun getCount(): Int {
        return list.size
    }

    // androidx.fragment.app.FragmentPagerAdapter
    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: java.util.List<? extends androidx.fragment.app.Fragment> */ /* JADX WARN: Multi-variable type inference failed */ /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    init {
        Intrinsics.checkNotNullParameter(list2, "list")
        Intrinsics.checkNotNullParameter(fm, "fm")
        list = list2
    }
}