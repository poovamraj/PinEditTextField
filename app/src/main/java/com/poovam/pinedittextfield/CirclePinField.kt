package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

/**
 * Created by poovam-5255 on 5/23/2018.
 */
class CirclePinField: PinField{

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet) : super(context,attr)

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context,attr,defStyle)

    constructor(context: Context, attr: AttributeSet, defStyle: Int, defStyleRes:Int) : super(context, attr, defStyle, defStyleRes)

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){

            val x1 = (i*singleFieldWidth)
            val character:Char? = text?.getOrNull(i)

            canvas?.drawCircle(x1+(singleFieldWidth/2).toFloat(),(height/2).toFloat(),circleRadiusDp,mArcPaint)
        }
    }

}