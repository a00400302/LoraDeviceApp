package com.yxkj.loradeviceapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.google.gson.Gson
import com.king.view.arcseekbar.ArcSeekBar
import com.yxkj.loradeviceapp.R
import com.yxkj.loradeviceapp.bean.DataBean
import com.yxkj.loradeviceapp.databinding.ActivityDeviceInfoBinding
import com.yxkj.loradeviceapp.lightApi
import com.yxkj.loradeviceapp.util.RetrofitUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import kotlin.jvm.internal.Intrinsics





/* compiled from: DeviceInfoActivity.kt */
class DeviceInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceInfoBinding

    private var controlMode = 1
    private lateinit var dataBean: DataBean
    var direction = 0
    private var mCurPosX = 0f
    private var mCurPosY = 0f
    private var mPosX = 0f
    private var mPosY = 0f
    private var progressNow = 0


    /* access modifiers changed from: protected */
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity

    @SuppressLint("ClickableViewAccessibility")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(binding.titleView)


        dataBean = intent.getSerializableExtra("model") as DataBean
        setItemStatus()
        checkMode()
        binding.titleView.setleftOnClickListener {
            finish()
        }
        binding.buttonBrightness.setOnClickListener {
            controlMode = 1
            checkMode()
        }
        binding.buttonTemperature.setOnClickListener {
            controlMode = 2
            checkMode()
        }
        binding.arcSeekBar.setOnChangeListener(object : ArcSeekBar.OnChangeListener {
            override fun onStartTrackingTouch(isCanDrag: Boolean) {

            }

            override fun onProgressChanged(progress: Float, max: Float, fromUser: Boolean) {
                if (fromUser) {
                    if (controlMode == 1) {
                        progressNow = progress.toInt() + 10
                    } else if (progress.toInt() <= 4) {
                        progressNow = progress.toInt()
                    } else {
                        progressNow = progress.toInt() + 13
                    }
                }
                Log.e(NotificationCompat.CATEGORY_PROGRESS, progress.toString())
            }

            override fun onStopTrackingTouch(isCanDrag: Boolean) {
                if (controlMode == 1) {
                    sendHttp(dataBean.id,1,progressNow,1)
                } else {
                    sendHttp(dataBean.id,16,dataBean.luminance,progressNow)
                }

            }

            override fun onSingleTapUp() {
            }
        })

        binding.swipeLayout.setOnTouchListener { _, event ->
            Log.e("TAG", event.action.toString())
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mPosX = event.x
                    mPosY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    if (dataBean.isOpen == 255) {
                        if (direction == 2) {
                            direction = 0
                            sendHttp(dataBean.id, 1, dataBean.luminance, 1)
                        } else if (direction == 0 || direction == 1) {
                            val f = mCurPosX
                            val f2 = mPosX
                            if (f - f2 <= 0.0f || Math.abs(f - f2) <= 300.0f) {
                                val f3 = mCurPosX
                                val f4 = mPosX
                                if (f3 - f4 < 0.0f && Math.abs(f3 - f4) > 300.0f) {
                                    Log.e("TAG", "wang向左")
                                    direction = 1
                                    controlMode = 2
                                    checkMode()
                                    sendHttp(dataBean.id, 16, dataBean.luminance, getLight(false))
                                }
                            } else {
                                Log.e("TAG", "wang向右")
                                direction = 1
                                controlMode = 2
                                checkMode()

                                sendHttp(
                                    dataBean.id,
                                    16,
                                    dataBean.luminance,
                                    getLight(true)
                                )
                            }
                        }
                    }
                    direction = 0

                }
                MotionEvent.ACTION_MOVE -> {
                    mCurPosX = event.x
                    mCurPosY = event.y
                    if (dataBean.isOpen != 255) {
                        return@setOnTouchListener false
                    }
                    if (direction == 0 || direction == 2) {
                        val f5 = mCurPosY
                        val f6 = mPosY
                        if (f5 - f6 <= 0.0f || Math.abs(f5 - f6) <= 300.0f) {
                            val f7 = mCurPosY
                            val f8 = mPosY
                            if (f7 - f8 < 0.0f && Math.abs(f7 - f8) > 300.0f) {
                                Log.e("TAG", "wang向上")
                                direction = 2
                                controlMode = 1
                                if (dataBean.luminance >= 95) {
                                    dataBean.luminance = 95
                                } else {
                                    dataBean.luminance = dataBean.luminance + 1
                                }
                                checkMode()
                            }
                        } else {
                            Log.e("TAG", "wang向下")
                            direction = 2
                            controlMode = 1
                            if (dataBean.luminance <= 10) {
                                dataBean.luminance = 10
                            } else {
                                dataBean.luminance = dataBean.luminance - 1

                            }
                            checkMode()
                        }
                    }

                }

            }
            return@setOnTouchListener false
        }
        binding.minusLight.setOnClickListener {
            if (controlMode == 1) {
                    sendHttp(dataBean.id, 1, 239, 1)
            }else{
                sendHttp(dataBean.id, 16, dataBean.luminance, getLight(false))
            }
        }
        binding.addLight.setOnClickListener {
            if (controlMode == 1) {
                sendHttp(dataBean.id, 1, 238, dataBean.light)

            }else{
                sendHttp(dataBean.id, 16, dataBean.luminance, getLight(true))
            }
        }
        binding.buttonSwitch.setOnClickListener {
                if (dataBean.isOpen == 255) {
                    sendHttp(dataBean.id, 1, 0, dataBean.light)
                }else{
                    sendHttp(dataBean.id, 1, 255, dataBean.light)
                }
        }
    }

    private fun sendHttp(id: Int, type: Int, luminance: Int, light: Int) {
        val requestBody = RequestBody.create(
            MediaType.parse("application/json"), Gson().toJson(
                hashMapOf(
                    Pair<String, Any?>("id", Integer.valueOf(id)),
                    Pair<String, Any?>("type", Integer.valueOf(type)),
                    Pair<String, Any?>("luminance", Integer.valueOf(luminance)),
                    Pair<String, Any?>("light", Integer.valueOf(light))
                ), HashMap::class.java
            )
        )
        RetrofitUtil.sendHttp(
            RetrofitUtil.getRetrofitLoraLight(lightApi::class.java).setParams(requestBody),
            {
                if (it.code == 100) {
                    dataBean = it.data
                    checkMode()
                } else {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }
                setItemStatus()
            }, {
                Toast.makeText(this, "网络请求失败请稍后重试", Toast.LENGTH_SHORT).show()

            })

    }

    /* access modifiers changed from: private */
    private fun setItemStatus() {
        if (dataBean.isOpen == 255) {
            binding.addLight.isEnabled = true
            binding.minusLight.isEnabled = true
            binding.arcSeekBar.isEnabledDrag = true
            binding.arcSeekBar.isEnabled = true
            binding.buttonSwitch.setCardBackgroundColor(2131034155)
        } else {
            binding.addLight.isEnabled = false
            binding.minusLight.isEnabled = false
            binding.arcSeekBar.isEnabledDrag = false
            binding.arcSeekBar.isEnabled = false
            binding.buttonSwitch.setCardBackgroundColor(ColorUtils.getColor(R.color.button_enable))
        }
    }

    /* access modifiers changed from: private */
    private fun checkMode() {
        if (controlMode == 1) {
            binding.arcSeekBar.max = 85
            binding.arcSeekBar.progress = dataBean.luminance.toInt() - 10
            binding.modeText.text = "亮度"
            binding.buttonBrightness.isEnabled = false
            binding.buttonTemperature.isEnabled = true
            binding.buttonBrightness.setCardBackgroundColor(2131034155)
            binding.buttonTemperature.setCardBackgroundColor(ColorUtils.getColor(R.color.button_enable))
        } else {
            binding.arcSeekBar.max = 8
            if (dataBean.light >= 17) {
                binding.arcSeekBar.progress = dataBean.light.toInt() - 17 + 4
            } else {
                binding.arcSeekBar.progress = dataBean.light
            }
            binding.modeText.text = "色温"
            binding.buttonBrightness.isEnabled = true
            binding.buttonTemperature.isEnabled = false
            binding.buttonTemperature.setCardBackgroundColor(2131034155)
            binding.buttonBrightness.setCardBackgroundColor(ColorUtils.getColor(R.color.button_enable))
        }
    }


    private fun getLight(isAdd: Boolean): Int {
        return when {
            isAdd -> {
                if (controlMode != 2) {
                    return 1
                }

                if (dataBean.light == 4) {
                    return 18
                }
                return dataBean.light + 1
            }
            controlMode != 2 -> {
                1
            }
            else -> {
                if (dataBean.light == 17) {
                    return 3
                }else {
                    return dataBean.light - 1
                }
            }
        }
    }


}