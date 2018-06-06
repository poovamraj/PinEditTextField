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
            val squareHeight = paddedX2-paddedX1
            val paddedY1 = (height/2)-(squareHeight/2)
            val paddedY2 = (height/2)+(squareHeight/2)
            val textX = ((paddedX2-paddedX1)/2)+paddedX1
            val textY = ((paddedY2-paddedY1)/2+paddedY1)+lineThicknessDp+(textColor.textSize/4)
            val character:Char? = text?.getOrNull(i)

            canvas?.drawRect(paddedX1,paddedY1,paddedX2,paddedY2, fieldColor)
            if(character!=null) {
                canvas?.drawText(character.toString(),textX,textY, textColor)
                if(isHighlightEnabled){
                    canvas?.drawRect(paddedX1,paddedY1,paddedX2,paddedY2, highlightColor)
                }
            }
        }
    }
}