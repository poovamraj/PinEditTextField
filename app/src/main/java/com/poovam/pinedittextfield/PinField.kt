package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager



/**
 * Created by poovam-5255 on 3/3/2018.
 * View where all the magic happens
 */
open class PinField : AppCompatEditText {

    private val defaultWidth = Util.dpToPx(60).toInt()

    var circleRadiusDp = Util.dpToPx(10)

    var isCircleFilled = false

    var distanceInBetweenDp = Util.dpToPx(15).toInt()

    var numberOfFields = 4

    protected var singleFieldWidth = 0

    var lineThicknessDp = Util.dpToPx(1)

    var mArcPaint = Paint()

    var mTextPaint = Paint()

    var mTextSize = Util.dpToPx(30)

    var mTextPaddingFromBottom = Util.dpToPx(3)

    var yPadding = Util.dpToPx(10)

    /**
     * Highlight works only for square and line field
     */
    var isHighlightEnabled = true

    var filledPaint = Paint()

    var highLightThickness = 2
        set(value) {
            field = value
            filledPaint.strokeWidth = lineThicknessDp + value
            invalidate()
        }

    var onTextCompleteListener: OnTextCompleteListener? = null

    init {
        limitCharsToNoOfFields()
        setWillNotDraw(false)
        maxLines = 1

        mArcPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mArcPaint.isAntiAlias = true
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = lineThicknessDp

        mTextPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mTextSize
        mTextPaint.style = Paint.Style.FILL

        filledPaint = Paint(mArcPaint)
        filledPaint.strokeWidth = lineThicknessDp+highLightThickness
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet) : super(context,attr)

    constructor(context: Context,attr: AttributeSet,defStyle: Int) : super(context,attr,defStyle)

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


    final override fun setWillNotDraw(willNotDraw: Boolean) {
        super.setWillNotDraw(willNotDraw)
    }

    fun getApproxXToCenterText(text: String, widthToFitStringInto: Int): Int {
        val p = Paint()
        p.typeface = typeface
        p.textSize = mTextSize
        val textWidth = p.measureText(text)
        return ((widthToFitStringInto - textWidth) / 2f).toInt() - (mTextSize / 2f).toInt()
    }

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }


    private fun limitCharsToNoOfFields(){
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(numberOfFields)
        filters = filterArray
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (text != null && text.length == numberOfFields){
            onTextCompleteListener?.onTextComplete(text.toString())
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    interface OnTextCompleteListener {
        fun onTextComplete(enteredText: String)
    }
}