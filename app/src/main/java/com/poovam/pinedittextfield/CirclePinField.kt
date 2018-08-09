package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet

/**
 * Created by poovam-5255 on 5/23/2018.
 *
 * Can be used for password
 * Doesn't show the entered characters in view
 */
class CirclePinField: PinField{

    var fillerColor =  ContextCompat.getColor(context,R.color.accent)
        set(value){
            field = value
            fillerPaint.color = fillerColor
            invalidate()
        }

    var circleRadiusDp = Util.dpToPx(10f)
        set(value){
            field = value
            fillerPaint.strokeWidth = field
            invalidate()
        }

    var fillerPaint = Paint(fieldPaint)

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet) : super(context,attr){
        initParams(attr)
    }

    constructor(context: Context,attr: AttributeSet,defStyle: Int) : super(context,attr,defStyle){
        initParams(attr)
    }

    private fun initParams(attr: AttributeSet){
        val a = context.theme.obtainStyledAttributes(attr, R.styleable.CirclePinField, 0,0)

        try {
            circleRadiusDp = a.getDimension(R.styleable.CirclePinField_circleRadius, circleRadiusDp)
            fillerColor = a.getColor(R.styleable.CirclePinField_fillerColor, fillerColor)
        } finally {
            a.recycle()
        }
    }

    init {
        fillerPaint.strokeWidth = circleRadiusDp
    }

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){

            val x1 = (i*singleFieldWidth)
            val character:Char? = text?.getOrNull(i)

            if(isHighlightEnabled && hasFocus()){
                canvas?.drawCircle(x1+(singleFieldWidth/2).toFloat(),(height/2).toFloat(),circleRadiusDp, highlightPaint)
            }else{
                canvas?.drawCircle(x1+(singleFieldWidth/2).toFloat(),(height/2).toFloat(),circleRadiusDp, fieldPaint)
            }

            if(character!=null) {
                canvas?.drawCircle(x1+(singleFieldWidth/2).toFloat(),(height/2).toFloat(),(circleRadiusDp/2)-highLightThickness, fillerPaint)
            }
        }
    }

}