package com.yxkj.loradeviceapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.yxkj.loradeviceapp.databinding.CustomTitleViewBinding
import kotlin.jvm.internal.Intrinsics

class TitleView : RelativeLayout {
    private var _isShowSetting = true
    private var _textColor = Color.BLACK
    private var _title: String? = ""
    private var inflate: CustomTitleViewBinding? = null
    private var title: String?
        get() = _title
        set(value) {
            _title = value
            val customTitleViewBinding = inflate
            val textView = customTitleViewBinding?.titleText
            if (textView != null) {
                textView.text = value
            }
        }
    var textColor: Int
        get() = _textColor
        set(value) {
            _textColor = value
            val customTitleViewBinding = inflate
            customTitleViewBinding?.titleText?.setTextColor(value)
            customTitleViewBinding?.backButton?.setColorFilter(value)
            customTitleViewBinding?.settingButton?.setColorFilter(value)

        }
    var isShowSetting: Boolean
        get() = _isShowSetting
        set(value) {
            _isShowSetting = value
            val customTitleViewBinding = inflate
            val imageView = customTitleViewBinding?.settingButton
            if (imageView != null) {
                imageView.visibility = if (value) VISIBLE else GONE
            }
        }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    constructor(context: Context) : super(context) {
        Intrinsics.checkNotNullParameter(context, "context")
        init(context, null, 0)
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Intrinsics.checkNotNullParameter(context, "context")
        Intrinsics.checkNotNullParameter(attrs, "attrs")
        init(context, attrs, 0)
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        Intrinsics.checkNotNullParameter(context, "context")
        Intrinsics.checkNotNullParameter(attrs, "attrs")
        init(context, attrs, defStyle)
    }

    @SuppressLint("ResourceType")
    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TitleView, defStyle, 0)
        inflate = CustomTitleViewBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.custom_title_view, this as ViewGroup, true)
        )
        title = a.getString(R.styleable.TitleView_title)
        isShowSetting = a.getBoolean(R.styleable.TitleView_isShowSetting, false)
        textColor = a.getColor(R.styleable.TitleView_titleColor, ViewCompat.MEASURED_STATE_MASK)
        a.recycle()
    }

    fun setleftOnClickListener(onClickListener: OnClickListener?) {
        Intrinsics.checkNotNullParameter(onClickListener, "onClickListener")
        inflate?.backButton?.setOnClickListener(onClickListener)
    }

    fun setRightOnClickListener(onClickListener: OnClickListener?) {
        Intrinsics.checkNotNullParameter(onClickListener, "onClickListener")
        inflate?.settingButton?.setOnClickListener(onClickListener)

    }
}