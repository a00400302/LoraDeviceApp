package com.yxkj.loradeviceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yxkj.loradeviceapp.adapter.ScenesAdapter
import com.yxkj.loradeviceapp.bean.SceneBean
import com.yxkj.loradeviceapp.databinding.FragmentScenesBinding
import kotlin.jvm.internal.Intrinsics


class ScenesFragment : Fragment() {
    private var _binding: FragmentScenesBinding? = null
    val binding: FragmentScenesBinding?
        get() {
            val fragmentScenesBinding = _binding
            Intrinsics.checkNotNull(fragmentScenesBinding)
            return fragmentScenesBinding
        }

    // androidx.fragment.app.Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // androidx.fragment.app.Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Intrinsics.checkNotNullParameter(inflater, "inflater")
        _binding = FragmentScenesBinding.inflate(inflater, container, false)
        binding!!.sceneslist.layoutManager = GridLayoutManager(context, 2)
        binding!!.sceneslist.adapter = ScenesAdapter(listOf(SceneBean(), SceneBean()))
        val root = binding!!.root
        Intrinsics.checkNotNullExpressionValue(root, "binding.root")
        return root
    }
}