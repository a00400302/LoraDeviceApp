package com.yxkj.loradeviceapp.fragment

import androidx.fragment.app.Fragment
import kotlin.jvm.internal.Intrinsics

abstract class BaseFragment : Fragment() {
    abstract fun sendHttp(callback:()->Unit)
    fun refresh(callback:()->Unit) {
        sendHttp(callback)
    }
}