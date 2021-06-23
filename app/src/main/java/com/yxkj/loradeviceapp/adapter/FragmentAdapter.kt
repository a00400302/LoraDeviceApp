package com.yxkj.loradeviceapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.jvm.internal.Intrinsics

class FragmentAdapter(list2: List<Fragment>, activity: AppCompatActivity?) : FragmentStateAdapter(
    activity!!
) {
    private val list: List<Fragment>

    // androidx.recyclerview.widget.RecyclerView.Adapter
    override fun getItemCount(): Int {
        return list.size
    }

    // androidx.viewpager2.adapter.FragmentStateAdapter
    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: java.util.List<? extends androidx.fragment.app.Fragment> */ /* JADX WARN: Multi-variable type inference failed */ /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    init {
        Intrinsics.checkNotNullParameter(list2, "list")
        Intrinsics.checkNotNullParameter(activity, "activity")
        list = list2
    }
}