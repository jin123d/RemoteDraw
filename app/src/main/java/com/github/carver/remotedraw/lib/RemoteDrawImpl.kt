package com.github.carver.remotedraw.lib

import android.app.Presentation
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.hardware.display.DisplayManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.github.carver.remotedraw.IRemoteDraw
import com.github.carver.remotedraw.R
import com.github.carver.remotedraw.view.CustomWebView

/**
 * 在子进程实现渲染。
 */
class RemoteDrawImpl(private val service: Service) : IRemoteDraw.Stub() {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var contentView: CustomWebView? = null

    override fun setSurface(surface: Surface?) {
        if (surface == null) {
            Log.e(TAG, "setSurface: surface is null.")
            return
        }
        mainHandler.post {
            draw(surface)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return contentView?.dispatchTouchEvent(event) ?: false
    }

    override fun back() {
        mainHandler.post {
            if (contentView?.canGoBack() == true) {
                contentView?.goBack()
            }
        }
    }

    private fun draw(surface: Surface) {
        val displayManager = service.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val dm = service.resources.displayMetrics
        val virtualDisplay = displayManager.createVirtualDisplay(
            "",
            dm.widthPixels,
            dm.heightPixels,
            dm.densityDpi,
            surface,
            0
        )
        val presentation = Presentation(service, virtualDisplay.display)
        if (contentView != null) {
            (contentView!!.parent as ViewGroup).removeView(contentView)
        }
        if (contentView == null) {
            createView()
        }
        presentation.setContentView(contentView!!)
        presentation.show()
    }

    private fun createView() {
        contentView = CustomWebView(service)
    }

    companion object {
        private const val TAG = "RemoteDrawImpl"
    }
}