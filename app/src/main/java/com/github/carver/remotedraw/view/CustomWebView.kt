package com.github.carver.remotedraw.view

import android.content.Context
import android.os.Process
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.carver.remotedraw.R

class CustomWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var view: View
    private var webView: WebView? = null

    init {
        view = LayoutInflater.from(context)
            .inflate(R.layout.view_sub_process, this)
        initView()
    }


    private fun initView() {
        webView = view.findViewById<WebView>(R.id.webView)
        webView?.loadUrl("https://xw.qq.com/?f=qqcom")
    }

    fun canGoBack() = webView?.canGoBack()

    fun goBack() = webView?.goBack()
}