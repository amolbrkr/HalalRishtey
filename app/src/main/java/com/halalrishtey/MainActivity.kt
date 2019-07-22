package com.halalrishtey

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView
    private lateinit var toolbar: Toolbar
    private lateinit var reloadButton: Button
    private lateinit var mFilePathCallback: ValueCallback<Array<Uri>>

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
        myWebView.settings.loadWithOverviewMode = true
        myWebView.settings.allowFileAccess = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        myWebView.settings.allowFileAccessFromFileURLs = true
        myWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        myWebView.settings.mediaPlaybackRequiresUserGesture = false
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.databaseEnabled = true
        myWebView.settings.setAppCacheEnabled(false)
        myWebView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        myWebView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                super.onPermissionRequest(request)
                request?.grant(request.resources)
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                mFilePathCallback = filePathCallback
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                val PICKFILE_REQUEST_CODE = 100
                startActivityForResult(intent, PICKFILE_REQUEST_CODE)
                return true
            }
        }

        myWebView.loadUrl("https://halalrishtey.com/")

        reloadButton.setOnClickListener {
            Toast.makeText(applicationContext, "Reloading Content! Please wait...", Toast.LENGTH_LONG).show()
            myWebView.reload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && resultCode == Activity.RESULT_OK) {
            val resultsArray = arrayOfNulls<Uri>(1)
            resultsArray[0] = data.data
            mFilePathCallback.onReceiveValue(resultsArray as Array<Uri>)
            Log.d("ACTIVITY RESULT", data.data.toString())
        } else {
            Log.d("ACTIVITY RESULT", "Cannot get file path.")
        }
    }

    override fun onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
