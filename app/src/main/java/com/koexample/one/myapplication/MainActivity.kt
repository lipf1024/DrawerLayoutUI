package com.koexample.one.myapplication

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.koexample.one.myapplication.new_view.mRelativeLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        init()
    }

    fun init() {
        var mm: mRelativeLayout = findViewById(R.id.Content)
        mm.setBackgroundColor(Color.TRANSPARENT)
        var drawer: DrawerLayout = findViewById(R.id.drawer)
        drawer.setScrimColor(Color.TRANSPARENT)//消除抽屉阴影

        drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            var tag = false//抽屉打开事件
            var viewGroup: ViewGroup? = null
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                if (!tag) {
                    tag = true
                    onDrawerStart(drawerView)
                }
                var mContent: View = drawer.getChildAt(0)
                var mMenu: View = drawerView
                var scale: Float = 1 - slideOffset
                var rightScale: Float = 0.8f + scale * 0.2f
                when (drawerView.tag) {
                    "LEFT" -> {
                        val leftScale: Float = 1 - 0.3f * scale
                        mMenu.scaleX = leftScale
                        mMenu.scaleY = leftScale
                        //mContent.setAlpha(0.6f + 0.4f * (1 - scale));
                        mContent.translationX = (mMenu.measuredWidth + 20) * (1 - scale)
                        mContent.pivotX = 0f
                        mContent.pivotY = mContent.measuredHeight / 2f
                        mContent.invalidate()
                        mContent.scaleX = rightScale
                        mContent.scaleY = rightScale
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT)
                    }
                    else -> {
                        mContent.translationX = -(mMenu.measuredWidth + 20) * slideOffset
                        mContent.pivotX = mContent.measuredWidth * 1f
                        mContent.pivotY = mContent.measuredHeight / 2f
                        mContent.invalidate()
                        mContent.scaleX = rightScale
                        mContent.scaleY = rightScale
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT)
                    }


                }
                mm.setRadius(40f, slideOffset, Color.WHITE)
                if (slideOffset == 0f) {
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    tag = false
                    onDrawerEnd(drawerView)
                }


            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            fun onDrawerStart(drawerView: View) {

            }

            fun onDrawerEnd(drawerView: View) {
            }

            fun gaosimohu(background: Bitmap): Bitmap {
                var renderScript = RenderScript.create(this@MainActivity)
                var input = Allocation.createFromBitmap(renderScript, background)
                var output = Allocation.createTyped(renderScript, input.type)
                var scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
                scriptIntrinsicBlur.setInput(input)
                scriptIntrinsicBlur.setRadius(10f)
                scriptIntrinsicBlur.forEach(output)
                output.copyTo(background)
                renderScript.destroy()
                return background
            }
        })
    }
}