package com.halalrishtey

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView
    private lateinit var toolbar: Toolbar
    private lateinit var reloadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myWebView = findViewById(R.id.webview)
        toolbar = findViewById(R.id.my_toolbar)
        reloadButton = findViewById(R.id.reload_button)
        setSupportActionBar(toolbar)

        toolbar.title = "Halal Rishtey"
        toolbar.setBackgroundColor(Color.rgb(219, 120, 120))
        toolbar.setTitleTextColor(Color.WHITE)
        myWebView.settings.allowFileAccess = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        myWebView.settings.allowFileAccessFromFileURLs = true
        myWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.databaseEnabled = true
        myWebView.settings.setAppCacheEnabled(true)

        myWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        myWebView.loadUrl("https://halalrishtey.com/")

        reloadButton.setOnClickListener {
            myWebView.reload()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        }
    }
}
