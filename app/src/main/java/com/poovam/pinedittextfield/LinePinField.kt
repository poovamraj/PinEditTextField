package com.poovam.pinedittextfield

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

/**
 * Created by poovam-5255 on 5/23/2018.
 * Normal pin field with bottom lines
 */

class LinePinField : PinField {

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

            val y = height - yPadding
            canvas?.drawLine(paddedX1,y,paddedX2,y,mArcPaint)
            if(character!=null) canvas?.drawText(character.toString(),((paddedX2-paddedX1)-mTextPaint.textSize)+paddedX1,(y-lineThicknessDp)-mTextPaddingFromBottom,mTextPaint)

        }

    }
}