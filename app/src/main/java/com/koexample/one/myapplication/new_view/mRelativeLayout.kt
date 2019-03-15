package com.koexample.one.myapplication.new_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout


class mRelativeLayout : RelativeLayout {
    var cur_rad: Float = 0f
    var r: Float = 0f

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
//        this.setClipToOutline(true);
//        this.outlineProvider = object : ViewOutlineProvider() {
//            override fun getOutline(view: View, outline: Outline) {
//                println(2333333);
//                // outline.setRoundRect(width,width,height,height, this@mRelativeLayout.cur_rad);
//                var margin = Math.min(view.getWidth(), view.getHeight()) / 10;
//                //view是match_parent的
//                outline.setRoundRect(
//                    0,
//                    0,
//                    view.getWidth(),
//                    view.getHeight(),
//                    this@mRelativeLayout.cur_rad
//                );
//            }
//        }
        this.isDrawingCacheEnabled = true
        this.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_LOW
    }

    /*
    offset为最大弧度 scale为当前弧度比例
     */
    fun setRadius(offset: Float, scale: Float, color: Int = Color.WHITE) {
        /*
        圆角矩形方案一：
        缺点：重复创建drawable 重复设置背景
         */

//        var rad = offset * scale;
//        var outerRadian = floatArrayOf(rad, rad, rad, rad, rad, rad, rad, rad);
//        /**
//         * 内部矩形与外部矩形的距离
//         */
//        //var insetDistance: RectF = RectF(100f, 100f, 50f, 50f);
//        /**
//         * 内部矩形弧度
//         * */
//        //var insideRadian = floatArrayOf(20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f);
//        var roundRectShape = RoundRectShape(outerRadian, null, null);
//        var drawable = ShapeDrawable(roundRectShape);
//        drawable.setShape(roundRectShape);
//        drawable.getPaint().setStyle(Paint.Style.FILL);
//        drawable.getPaint().setColor(color);
//        this.setBackground(drawable);
        /*
        圆角矩形方案二：
        调用outline回调接口
        outline有待了解
         */
        this.cur_rad = offset * scale
        this.r = 25f * scale
        println("2333  " + cur_rad)
        //this.invalidateOutline();
        //this.postInvalidate()


    }

    override fun onDraw(canvas: Canvas) {
        println("233 hahaha")
        val path = Path()
        path.addRoundRect(
            RectF(0f, 0f, width * 1f, height * 1f),
            floatArrayOf(cur_rad, cur_rad, cur_rad, cur_rad, cur_rad, cur_rad, cur_rad, cur_rad),
            Path.Direction.CW
        )
        canvas.clipPath(path)
        canvas.drawColor(Color.WHITE)
        super.onDraw(canvas)
    }

}