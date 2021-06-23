package com.yxkj.loradeviceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yxkj.loradeviceapp.databinding.FragmentMySettingBinding
import kotlin.jvm.internal.Intrinsics

class MySettingFragment : Fragment() {
    private var _binding: FragmentMySettingBinding? = null
    val binding: FragmentMySettingBinding?
        get() {
            val fragmentMySettingBinding = _binding
            Intrinsics.checkNotNull(fragmentMySettingBinding)
            return fragmentMySettingBinding
        }

    // androidx.fragment.app.Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Intrinsics.checkNotNullParameter(inflater, "inflater")
        _binding = FragmentMySettingBinding.inflate(inflater, container, false)
        val root = binding!!.root
        Intrinsics.checkNotNullExpressionValue(root, "binding.root")
        return root
    }
}