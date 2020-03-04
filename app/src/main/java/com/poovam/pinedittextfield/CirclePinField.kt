package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by poovam-5255 on 5/23/2018.
 *
 * Can be used for password
 * Doesn't show the entered characters in view
 */
class CirclePinField: PinField{

    companion object {
        private val DEFAULT_FILLER_RADIUS = -1f
    }

    var fillerColor =  ContextCompat.getColor(context,R.color.pinFieldLibraryAccent)
        set(value){
            field = value
            fillerPaint.color = fillerColor
            invalidate()
        }

    var fillerRadius = DEFAULT_FILLER_RADIUS
        set(value){
            field = value
            actuallyUsedFillerRadius = field
            invalidate()
        }

    private var actuallyUsedFillerRadius = fillerRadius
        set(value){
            field = if(this.fillerRadius == DEFAULT_FILLER_RADIUS)
                circleRadiusDp-highLightThickness
            else
                this.fillerRadius
        }

    var circleRadiusDp = Util.dpToPx(10f)
        set(value){
            field = value
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
            fillerRadius = a.getDimension(R.styleable.CirclePinField_fillerRadius,fillerRadius)
            if(distanceInBetween == DEFAULT_DISTANCE_IN_BETWEEN) distanceInBetween = Util.dpToPx(30f)
        } finally {
            a.recycle()
        }
    }

    init {
        fillerPaint.style = Paint.Style.FILL
    }

    override fun getViewWidth(desiredWidth:Int, widthMeasureSpec: Int): Int {

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val viewWidth = getCircleDiameterWithPadding() * numberOfFields
        //Measure Width
        return when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(viewWidth, widthSize)
            MeasureSpec.UNSPECIFIED -> viewWidth
            else -> viewWidth
        }
    }

    private fun getCircleDiameterWithPadding():Int{
        val diameter = circleRadiusDp*2
        return Math.round(diameter + getThickness() + getPadding())
    }

    /**
     * This will return thickness based on the chosen mode
     * there will be a small padding even if distanceInBetween = 0dp if there is a highlight
     * this is to make sure when highlighting there is no overlap
     */
    private fun getThickness(): Int{
        return Math.round(if(!highlightNoFields())highLightThickness else lineThickness)
    }

    private fun getPadding(): Float{
        return if (distanceInBetween!= DEFAULT_DISTANCE_IN_BETWEEN) distanceInBetween else getDefaultDistanceInBetween()/2
    }

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){
            var startingX = (width - (getCircleDiameterWithPadding()*numberOfFields))/2

            startingX = if(startingX > 0) startingX else 0

            val x1 = (getCircleDiameterWithPadding().toFloat() * i) + getCircleDiameterWithPadding()/2 + startingX

            val character:Char? = text?.getOrNull(i)

            val y1 = getViewHeight(height)

            canvas?.drawCircle(x1,y1,circleRadiusDp, fieldBgPaint)
            if(highlightAllFields() && hasFocus()){
                canvas?.drawCircle(x1,y1,circleRadiusDp, highlightPaint)
            }else{
                canvas?.drawCircle(x1,y1,circleRadiusDp, fieldPaint)
            }

            if(character!=null) {
                canvas?.drawCircle(x1,y1,actuallyUsedFillerRadius, fillerPaint)
            }

            highlightLogic(i, text?.length){
                canvas?.drawCircle(x1,y1,circleRadiusDp, highlightPaint)
            }
        }
    }

    /**
     * This is a dirty hack since the height provided by Android framework seems same
     * but drawing it on canvas seems to draw out of frame
     * Need to look into this more
     */
    private fun getViewHeight(height: Int): Float{
        if(!isCustomBackground && this.layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT
                && getPadding() < circleRadiusDp){
            return (height - getPadding()*1.5).toFloat()
        }
        return height/2.toFloat()
    }

}