package opensource.poovam.com.pinfield.pin_field

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import opensource.poovam.com.pinfield.R


/**
 * Created by poovam-5255 on 3/3/2018.
 * View where all the magic happens
 */
class PinField(context: Context) : View(context){


    var  mPoints= arrayOf(
        arrayOf(arrayOf(44.5f, 100f), arrayOf(100f, 18f), arrayOf(156f, 100f), arrayOf(100f, 180f), arrayOf(44.5f, 100f)), // 0
        arrayOf(arrayOf(77f, 20.5f), arrayOf(104.5f, 20.5f), arrayOf(104.5f, 181f), arrayOf(104.5f, 181f), arrayOf(104.5f, 181f)), //1
        arrayOf(arrayOf(56f, 60f), arrayOf(144.5f, 61f), arrayOf(108f, 122f), arrayOf(57f, 177f), arrayOf(147f, 177f)), // 2
        arrayOf(arrayOf(63.25f, 54f), arrayOf(99.5f, 18f), arrayOf(99.5f, 96f), arrayOf(100f, 180f), arrayOf(56.5f, 143f)), // 3
        arrayOf(arrayOf(155f, 146f), arrayOf(43f, 146f), arrayOf(129f, 25f), arrayOf(129f, 146f), arrayOf(129f, 179f)), //4
        arrayOf(arrayOf(146f, 20f), arrayOf(91f, 20f), arrayOf(72f, 78f), arrayOf(145f, 129f), arrayOf(45f, 154f)), // 5
        arrayOf(arrayOf(110f, 20f), arrayOf(110f, 20f), arrayOf(46f, 126f), arrayOf(153f, 126f), arrayOf(53.5f, 100f)), // 6
        arrayOf(arrayOf(47f, 21f), arrayOf(158f, 21f), arrayOf(120.67f, 73.34f), arrayOf(83.34f, 126.67f), arrayOf(46f, 181f)),  // 7
        arrayOf(arrayOf(101f, 96f), arrayOf(101f, 19f), arrayOf(101f, 96f), arrayOf(101f, 179f), arrayOf(101f, 96f)), // 8
        arrayOf(arrayOf(146.5f, 100f), arrayOf(47f, 74f), arrayOf(154f, 74f), arrayOf(90f, 180f), arrayOf(90f, 180f)) // 9
    )
    
    var mControlPoint1 = arrayOf(
        arrayOf(arrayOf(44.5f, 60f), arrayOf(133f, 18f), arrayOf(156f , 140f), arrayOf(67f, 180f)), // 0
        arrayOf(arrayOf(77f, 20.5f), arrayOf(104.5f, 20.5f), arrayOf(104.5f, 181f), arrayOf(104.5f, 181f)), // 1
        arrayOf(arrayOf(59f, 2f), arrayOf(144.5f, 78f), arrayOf(94f, 138f), arrayOf(57f, 177f)), // 2
        arrayOf(arrayOf(63f, 27f), arrayOf(156f, 18f), arrayOf(158f, 96f), arrayOf(54f, 180f)), // 3
        arrayOf(arrayOf(155f, 146f), arrayOf(43f, 146f), arrayOf(129f, 25f), arrayOf(129f, 146f)), // 4
        arrayOf(arrayOf(91f, 20f), arrayOf(72f, 78f), arrayOf(97f, 66f), arrayOf(140f, 183f)), // 5
        arrayOf(arrayOf(110f, 20f), arrayOf(71f, 79f), arrayOf(52f, 208f), arrayOf(146f, 66f)), // 6
        arrayOf(arrayOf(47f, 21f), arrayOf(158f, 21f), arrayOf(120.67f, 73.34f), arrayOf(83.34f, 126.67f)), // 7
        arrayOf(arrayOf(44f, 95f), arrayOf(154f, 19f), arrayOf(44f, 96f), arrayOf(154f, 179f)), // 8
        arrayOf(arrayOf(124f, 136f), arrayOf(42f, 8f), arrayOf(152f, 108f), arrayOf(90f, 180f)) // 9
    );

    var mControlPoint2 = arrayOf(
        arrayOf(arrayOf(67f, 18f), arrayOf(156f, 60f), arrayOf(133f, 180f), arrayOf(44.5f, 140f)), // 0
        arrayOf(arrayOf(104.5f, 20.5f), arrayOf(104.5f, 181f), arrayOf(104.5f, 181f), arrayOf(104.5f, 181f)), // 1
        arrayOf(arrayOf(143f, 4f), arrayOf(130f, 98f), arrayOf(74f, 155f), arrayOf(147f, 177f)), // 2
        arrayOf(arrayOf(86f, 18f), arrayOf(146f, 96f), arrayOf(150f, 180f), arrayOf(56f, 150f)), // 3
        arrayOf(arrayOf(43f, 146f), arrayOf(129f, 25f), arrayOf(129f, 146f), arrayOf(129f, 179f)), // 4
        arrayOf(arrayOf(91f, 20f), arrayOf(72f, 78f), arrayOf(145f, 85f), arrayOf(68f, 198f)), // 5
        arrayOf(arrayOf(110f, 20f), arrayOf(48f, 92f), arrayOf(158f, 192f), arrayOf(76f, 64f)), // 6
        arrayOf(arrayOf(158f, 21f), arrayOf(120.67f, 73.34f), arrayOf(83.34f, 126.67f), arrayOf(46f, 181f)), // 7
        arrayOf(arrayOf(44f, 19f), arrayOf(154f, 96f), arrayOf(36f, 179f), arrayOf(154f, 96f)), // 8
        arrayOf(arrayOf(54f, 134f), arrayOf(148f, -8f), arrayOf(129f, 121f), arrayOf(90f, 180f)) // 9
    );

    private val mInterpolator: Interpolator

    private val mPath: Path = Path()


    // Numbers currently shown.
    private var mCurrent = 0
    private var mNext = 1

    // Frame of transition between current and next frames.
    private var mFrame = 0

    enum class Shape{
        CIRCLE,LINE
    }

    private var fieldBackground: Drawable? = null

    var shape = Shape.CIRCLE

    var circleRadiusDp = Util.dpToPx(10)

    var isCircleFilled = false

    var distanceInBetweenDp = -1
    set(value) {
        field = value
        isCustomDistanceInBetween = true
    }

    var numberOfFields = 4

    var singleFieldWidth = 0

    var lineThicknessDp = Util.dpToPx(5)

    var mArcPaint = Paint()


    private val mPaint: Paint = mArcPaint

    private var isCustomDistanceInBetween = false

    init {

        setWillNotDraw(false)
        mInterpolator = AccelerateDecelerateInterpolator()

        distanceInBetweenDp = Util.dpToPx(10).toInt()

        mArcPaint.color = ContextCompat.getColor(context,R.color.colorAccent)
        mArcPaint.isAntiAlias = true
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = lineThicknessDp

    }
    constructor(context: Context, attr: AttributeSet) : this(context)

    constructor(context: Context,attr: AttributeSet,defStyle: Int) : this(context,attr)

    fun setFieldBackground(@DrawableRes drawableRes: Int ){
        fieldBackground = ContextCompat.getDrawable(context,drawableRes)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        singleFieldWidth = measuredWidth/numberOfFields
        if(!isCustomDistanceInBetween){
            distanceInBetweenDp = singleFieldWidth/(numberOfFields-1)
        }
    }

    override fun onDraw(canvas: Canvas?) {

        for (i in 0 until numberOfFields){
            if(shape == Shape.LINE){
                canvas?.drawLine(((i*singleFieldWidth)+(distanceInBetweenDp/2)).toFloat(),100f,(((i*singleFieldWidth)+singleFieldWidth)-(distanceInBetweenDp/2)).toFloat(),100f,mArcPaint)
            }else{
                canvas?.drawCircle(((i*singleFieldWidth)+(distanceInBetweenDp/2))+(singleFieldWidth/2).toFloat(),100f,circleRadiusDp,mArcPaint)
            }
        }


        val currentFrame: Int = when {
            mFrame < 2 -> 0
            mFrame > 8 -> 6
            else -> mFrame - 2
        }

        val factor = mInterpolator.getInterpolation(currentFrame / 6.0f)

        // Reset the path.
        mPath.reset()

        val current = mPoints[mCurrent]
        val next = mPoints[mNext]

        val curr1 = mControlPoint1[mCurrent]
        val next1 = mControlPoint1[mNext]

        val curr2 = mControlPoint2[mCurrent]
        val next2 = mControlPoint2[mNext]

        // First point.
        mPath.moveTo(current[0][0] + (next[0][0] - current[0][0]) * factor,
                current[0][1] + (next[0][1] - current[0][1]) * factor)

        // Rest of the points connected as bezier curve.
        for (i in 1..4) {
            mPath.cubicTo(curr1[i - 1][0] + (next1[i - 1][0] - curr1[i - 1][0]) * factor,
                    curr1[i - 1][1] + (next1[i - 1][1] - curr1[i - 1][1]) * factor,
                    curr2[i - 1][0] + (next2[i - 1][0] - curr2[i - 1][0]) * factor,
                    curr2[i - 1][1] + (next2[i - 1][1] - curr2[i - 1][1]) * factor,
                    current[i][0] + (next[i][0] - current[i][0]) * factor,
                    current[i][1] + (next[i][1] - current[i][1]) * factor)
        }

        // Draw the path.
        canvas?.drawPath(mPath, mPaint)

        // Next frame.
        mFrame++

        // Each number change has 10 frames. Reset.
        if (mFrame == 10) {
            // Reset to zarro.
            mFrame = 0

            mCurrent = mNext
            mNext++

            // Reset to zarro.
            if (mNext == 10) {
                mNext = 0
            }
        }

        // Callback for the next frame.
        postInvalidateDelayed(100)

        super.onDraw(canvas)
    }
}