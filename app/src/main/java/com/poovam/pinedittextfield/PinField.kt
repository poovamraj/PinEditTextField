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

    private val defaultWidth = Util.dpToPx(60f).toInt()

    protected val defDistanceInBetweenValue = -1f

    protected var distanceInBetween:Float = defDistanceInBetweenValue
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    var numberOfFields = 4
    set(value){
        field = value
        limitCharsToNoOfFields()
        invalidate()
    }

    protected var singleFieldWidth = 0

    var lineThickness = Util.dpToPx(1.5f)
        set(value){
            field = value
            fieldPaint.strokeWidth  = field
            highlightPaint.strokeWidth = highLightThickness
            invalidate()
        }

    var fieldColor = ContextCompat.getColor(context,android.R.color.black)
        set(value){
            field = value
            fieldPaint.color = field
            invalidate()
        }

    var highlightPaintColor = ContextCompat.getColor(context,R.color.colorAccent)
        set(value){
            field = value
            highlightPaint.color = field
            invalidate()
        }

    protected var fieldPaint = Paint()

    protected var textPaint = Paint()

    protected var highlightPaint = Paint()

    protected var yPadding = Util.dpToPx(10f)

    protected var isHighlightEnabled = true

    var highLightThickness = lineThickness
    get(){
        return lineThickness + lineThickness*0.5f
    }

    var onTextCompleteListener: OnTextCompleteListener? = null

    init {
        limitCharsToNoOfFields()
        setWillNotDraw(false)
        maxLines = 1

        fieldPaint.color = fieldColor
        fieldPaint.isAntiAlias = true
        fieldPaint.style = Paint.Style.STROKE
        fieldPaint.strokeWidth = lineThickness

        textPaint.color = currentTextColor
        textPaint.isAntiAlias = true
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.style = Paint.Style.FILL

        highlightPaint = Paint(fieldPaint)
        highlightPaint.color = highlightPaintColor
        highlightPaint.strokeWidth =  highLightThickness
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet) : super(context,attr){
        initParams(attr)
    }

    constructor(context: Context,attr: AttributeSet,defStyle: Int) : super(context,attr,defStyle){
        initParams(attr)
    }

    private fun initParams(attr: AttributeSet){
        val a = context.theme.obtainStyledAttributes(attr, R.styleable.PinField, 0,0)

        try {
            numberOfFields = a.getInt(R.styleable.PinField_noOfFields, numberOfFields)
            lineThickness = a.getDimension(R.styleable.PinField_lineThickness, lineThickness)
            distanceInBetween = a.getDimension(R.styleable.PinField_distanceInBetween, defDistanceInBetweenValue)
            fieldColor = a.getColor(R.styleable.PinField_fieldColor,fieldColor)
            highlightPaintColor = a.getColor(R.styleable.PinField_highlightColor,highlightPaintColor)
            isHighlightEnabled = a.getBoolean(R.styleable.PinField_highlightEnabled,isHighlightEnabled)
        } finally {
            a.recycle()
        }
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

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }

    protected fun getDefaultDistanceInBetween(): Float{
        return (singleFieldWidth/(numberOfFields-1)).toFloat()
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