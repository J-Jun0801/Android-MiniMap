package com.jun.minimap

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jrummyapps.android.widget.TwoDScrollView
import com.jun.minimap.module.MiniMapView
import com.jun.minimap.utils.Define.*
import com.jun.minimap.utils.Util
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private var gridLayout: GridLayout? = null
    private var miniMapView: MiniMapView? = null
    private var scrollView: TwoDScrollView? = null
    private var left = 0
    private var top = 0
    private var right = 0
    private var bottom = 0
    private val onClick = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_ok -> {
                if ((findViewById<View>(R.id.et_row) as EditText).text.toString().isEmpty()) {
                    Toast.makeText(this@MainActivity, "input row", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                if ((findViewById<View>(R.id.et_col) as EditText).text.toString().isEmpty()) {
                    Toast.makeText(this@MainActivity, "input col", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                ROW_COUNT = (findViewById<View>(R.id.et_row) as EditText).text.toString().toInt()
                COLUMN_COUNT = (findViewById<View>(R.id.et_col) as EditText).text.toString().toInt()
                TOTAL_COUNT = ROW_COUNT * COLUMN_COUNT
                gridLayout!!.removeAllViews()
                setView()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        setGridLayout()
        setMiniMap()
        setScrollView()
        findViewById<View>(R.id.btn_ok).setOnClickListener(onClick)
    }

    private fun setGridLayout() {
        gridLayout = findViewById(R.id.grid_layout)
        gridLayout?.columnCount = COLUMN_COUNT
        gridLayout?.rowCount = ROW_COUNT
        val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                Math.round(Util.dpToPixel(this@MainActivity, BUTTON_SIZE)),
                Math.round(Util.dpToPixel(this@MainActivity, BUTTON_SIZE))
        )
        for (index in 0 until TOTAL_COUNT) {
            val button = Button(this)
            gridLayout?.addView(button, params)
        }
    }

    private fun setMiniMap() {
        miniMapView = findViewById(R.id.mini_map)
        val bm = BitmapFactory.decodeResource(resources, R.drawable.sample)
        miniMapView?.setBackgroundBitmap(bm)
        gridLayout!!.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                gridLayout!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                        gridLayout!!.width / MINMAP_RATE, gridLayout!!.height / MINMAP_RATE)
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1)
                params.rightMargin = Math.round(Util.dpToPixel(this@MainActivity, 10f))
                params.bottomMargin = Math.round(Util.dpToPixel(this@MainActivity, 10f))
                miniMapView?.layoutParams = params
                Log.e("TAG ", "============== v = " + scrollView?.getHeight())
                right = (Util.getScreenWidth(this@MainActivity) / MINMAP_RATE).toDouble().roundToInt()
                bottom = (scrollView?.height!! / MINMAP_RATE).toDouble().roundToInt()
                miniMapView?.invalidRect(left, top, right, bottom)
            }
        })
    }

    private fun setScrollView() {
        scrollView = findViewById(R.id.scroll_view)
        scrollView?.setScrollChangeListner { view, x, y, oldx, oldy ->
            left = (x / 10.toFloat()).roundToInt()
            top = (y / 10.toFloat()).roundToInt()
            right = ((Util.getScreenWidth(this@MainActivity) + x) / MINMAP_RATE).toDouble().roundToInt()
            bottom = ((scrollView?.height!! + y) / MINMAP_RATE).toDouble().roundToInt()
            miniMapView?.invalidRect(left, top, right, bottom)
        }
    }
}