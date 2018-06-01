package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.text.InputType
import android.util.AttributeSet

/**
 * Created by poovam-5255 on 5/23/2018.
 *
 * Can be used for password
 * Doesn't show the entered characters in view
 */
class CirclePinField: PinField{

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet) : super(context,attr)

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context,attr,defStyle)

    init {
        filledPaint.strokeWidth = circleRadiusDp
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){

            val x1 = (i*singleFieldWidth)
            val character:Char? = text?.getOrNull(i)

            canvas?.drawCircle(x1+(singleFieldWidth/2).toFloat(),(height/2).toFloat(),circleRadiusDp,mArcPaint)

            if(character!=null) {
                canvas?.drawCircle(x1+(singleFieldWidth/2).toFloat(),(height/2).toFloat(),circleRadiusDp/2,filledPaint)
            }
        }
    }

}