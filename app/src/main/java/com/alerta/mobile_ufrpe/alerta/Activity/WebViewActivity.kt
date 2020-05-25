package com.alerta.mobile_ufrpe.alerta.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import com.alerta.mobile_ufrpe.alerta.R

class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webView = findViewById<View>(R.id.webviewClima) as WebView
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl("https://www.climatempo.com.br/")

    }

}
