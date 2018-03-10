package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager






/**
 * Created by poovam-5255 on 3/3/2018.
 * View where all the magic happens
 */
class PinField(context: Context) : View(context), View.OnClickListener{

    enum class Shape{
        CIRCLE,LINE
    }

    private var fieldBackground: Drawable? = null

    var shape = Shape.CIRCLE

    var circleRadiusDp = Util.dpToPx(10)

    var isCircleFilled = false

    var mText: SpannableStringBuilder = SpannableStringBuilder()

    val inputType = InputType.TYPE_CLASS_TEXT

    var distanceInBetweenDp = -1
        set(value) {
            field = value
            isCustomDistanceInBetween = true
        }

    var numberOfFields = 4

    var singleFieldWidth = 0

    var lineThicknessDp = Util.dpToPx(5)

    var mArcPaint = Paint()

    private var isCustomDistanceInBetween = false

    init {
        isFocusableInTouchMode = true


        setWillNotDraw(false)

        distanceInBetweenDp = Util.dpToPx(10).toInt()

        mArcPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mArcPaint.isAntiAlias = true
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = lineThicknessDp

        setOnClickListener(this)

        setOnKeyListener(OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {

                if (event.unicodeChar == 0) {

                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        mText.delete(mText.length - 1, mText.length)
                        return@OnKeyListener true
                    }

                } else {
                    if(event.keyCode == KeyEvent.KEYCODE_ENTER){
                        return@OnKeyListener false
                    }
                    mText.append(event.unicodeChar.toChar())
                    return@OnKeyListener true
                }
            }
            false
        })

    }
    constructor(context: Context, attr: AttributeSet) : this(context)

    constructor(context: Context,attr: AttributeSet,defStyle: Int) : this(context,attr)

    fun setFieldBackground(@DrawableRes drawableRes: Int ){
        fieldBackground = ContextCompat.getDrawable(context,drawableRes)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        singleFieldWidth = measuredWidth/numberOfFields
        if(!isCustomDistanceInBetween){
            distanceInBetweenDp = singleFieldWidth/(numberOfFields-1)
        }
    }

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){
            if(shape == Shape.LINE){
                canvas?.drawLine(((i*singleFieldWidth)+(distanceInBetweenDp/2)).toFloat(),100f,(((i*singleFieldWidth)+singleFieldWidth)-(distanceInBetweenDp/2)).toFloat(),100f,mArcPaint)
            }else{
                canvas?.drawCircle(((i*singleFieldWidth)+(distanceInBetweenDp/2))+(singleFieldWidth/2).toFloat(),100f,circleRadiusDp,mArcPaint)
            }
        }

        super.onDraw(canvas)
    }

    override fun onClick(v: View?) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE
        outAttrs.inputType = inputType
        return MyInputConnection(this,true)
    }

    inner class MyInputConnection internal constructor(targetView: View, fullEditor: Boolean) : BaseInputConnection(targetView, fullEditor) {

        private val mEditable: SpannableStringBuilder

        init {
            val customView = targetView as PinField
            mEditable = customView.mText
        }

        override fun getEditable(): Editable {
            return mEditable
        }
    }
}