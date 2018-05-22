package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.widget.TextView


/**
 * Created by poovam-5255 on 3/3/2018.
 * View where all the magic happens
 */
class PinField : TextView {

    enum class Shape{
        CIRCLE,LINE,SQUARE
    }

    private val defaultWidth = Util.dpToPx(60).toInt()

    var shape = Shape.LINE

    var circleRadiusDp = Util.dpToPx(10)

    var isCircleFilled = false

    var distanceInBetweenDp = Util.dpToPx(15).toInt()

    var numberOfFields = 4

    private var singleFieldWidth = 0

    var lineThicknessDp = Util.dpToPx(1)

    var mArcPaint = Paint()

    var mTextPaint = Paint()

    var mTextSize = Util.dpToPx(30)

    var mTextPaddingFromBottom = Util.dpToPx(3)

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        limitCharsToNoOfFields()
        setWillNotDraw(false)

        mArcPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mArcPaint.isAntiAlias = true
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = lineThicknessDp

        mTextPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mTextSize
        mTextPaint.style = Paint.Style.FILL
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet) : super(context,attr)

    constructor(context: Context,attr: AttributeSet,defStyle: Int) : super(context,attr,defStyle)

    constructor(context: Context,attr: AttributeSet,defStyle: Int,defStyleRes:Int) : super(context, attr, defStyle, defStyleRes)

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
            val character:Char? = text?.getOrNull(i)

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

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }


    private fun limitCharsToNoOfFields(){
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(numberOfFields)
        filters = filterArray
    }
}