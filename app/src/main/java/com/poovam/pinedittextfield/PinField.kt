package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
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
        CIRCLE,LINE,SQUARE
    }

    enum class KeyboardType(val inputType: Int){
        TEXT(InputType.TYPE_CLASS_TEXT),NUMBER(InputType.TYPE_CLASS_NUMBER)
    }

    private var fieldBackground: Drawable? = null

    private val defaultWidth = Util.dpToPx(60).toInt()

    var shape = Shape.LINE

    var circleRadiusDp = Util.dpToPx(10)

    var isCircleFilled = false

    var mText: SpannableStringBuilder = SpannableStringBuilder()

    val inputType = KeyboardType.TEXT

    var distanceInBetweenDp = Util.dpToPx(15).toInt()

    var numberOfFields = 4

    private var singleFieldWidth = 0

    var lineThicknessDp = Util.dpToPx(1)

    var mArcPaint = Paint()

    var mTextPaint = Paint()

    var mTextSize = Util.dpToPx(30)

    var mTextPaddingFromBottom = Util.dpToPx(3)

    init {
        isFocusableInTouchMode = true


        setWillNotDraw(false)

        mArcPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mArcPaint.isAntiAlias = true
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = lineThicknessDp

        mTextPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mTextSize
        mTextPaint.style = Paint.Style.FILL

        setOnClickListener(this)

    }

    constructor(context: Context, attr: AttributeSet) : this(context)

    constructor(context: Context,attr: AttributeSet,defStyle: Int) : this(context,attr)

    fun setFieldBackground(@DrawableRes drawableRes: Int ){
        fieldBackground = ContextCompat.getDrawable(context,drawableRes)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val desiredWidth = (defaultWidth * numberOfFields)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val width: Int

        //Measure Width
        width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> Math.min(desiredWidth, widthSize)
            View.MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> desiredWidth
        }
        singleFieldWidth = width/numberOfFields
        distanceInBetweenDp = singleFieldWidth/(numberOfFields-1)


        val desiredHeight = singleFieldWidth
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val height: Int

        //Measure Height
        height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> Math.min(desiredHeight, heightSize)
            View.MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){

            val x1 = (i*singleFieldWidth)
            val padding = (distanceInBetweenDp/2)
            val paddedX1 = (x1 + padding).toFloat()
            val paddedX2 = ((x1+singleFieldWidth)-padding).toFloat()
            val y = paddedX2-paddedX1
            val character:Char? = mText.getOrNull(i)

            when(shape){
                Shape.LINE -> {
                    canvas?.drawLine(paddedX1,y,paddedX2,y,mArcPaint)
                    if(character!=null) canvas?.drawText(character.toString(),((paddedX2-paddedX1)-mTextPaint.textSize)+paddedX1,(y-lineThicknessDp)-mTextPaddingFromBottom,mTextPaint)
                }
                Shape.CIRCLE -> {
                    canvas?.drawCircle(paddedX1+(singleFieldWidth/2).toFloat(),0f,circleRadiusDp,mArcPaint)
                }
                Shape.SQUARE -> {
                    canvas?.drawRect(paddedX1,0f,paddedX2,y,mArcPaint)
                }
            }

        }

        super.onDraw(canvas)
    }

    private fun drawChar(canvas: Canvas?,textPaint: Paint,text:String){
        val r = Rect()
        canvas?.getClipBounds(r)
        val cHeight = r.height()
        val cWidth = r.width()
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.getTextBounds(text, 0, text.length, r)
        val x = cWidth / 2f - r.width() / 2f - r.left
        val y = cHeight / 2f + r.height() / 2f - r.bottom
        canvas?.drawText(text, x, y, textPaint)
    }

    override fun onClick(v: View?) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE
        outAttrs.inputType = inputType.inputType
        return MyInputConnection(this,true)
    }

    inner class MyInputConnection internal constructor(private val targetView: PinField, fullEditor: Boolean) : BaseInputConnection(targetView, fullEditor) {

        private var mEditable: SpannableStringBuilder

        init {
            val customView = targetView
            mEditable = customView.mText
        }

        override fun getEditable(): Editable {
            targetView.invalidate()
            return mEditable
        }
    }
}