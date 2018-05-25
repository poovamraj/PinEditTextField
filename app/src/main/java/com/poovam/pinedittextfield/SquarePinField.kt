package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

/**
 * Created by poovam-5255 on 5/23/2018.
 *
 * Pin field represented with squares
 */

class SquarePinField : PinField{

    constructor(context: Context): super(context)

    constructor(context: Context, attr: AttributeSet) : super(context,attr)

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context,attr,defStyle)


    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){

            val x1 = (i*singleFieldWidth)
            val padding = (distanceInBetweenDp/2)
            val paddedX1 = (x1 + padding).toFloat()
            val paddedX2 = ((x1+singleFieldWidth)-padding).toFloat()
            val character:Char? = text?.getOrNull(i)

            val squareHeight = paddedX2-paddedX1
            val y1 = (height/2)-(squareHeight/2)
            val y2 = (height/2)+(squareHeight/2)
            canvas?.drawRect(paddedX1,y1,paddedX2,y2,mArcPaint)

        }
    }
}