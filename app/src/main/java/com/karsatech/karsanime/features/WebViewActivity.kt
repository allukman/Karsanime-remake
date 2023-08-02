package com.karsatech.karsanime.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.karsatech.karsanime.R
import com.karsatech.karsanime.databinding.ActivityWebViewBinding
import com.karsatech.karsanime.features.anime.full.FullDetailAnimeActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webviewUrl = intent.getStringExtra(WEBVIEW_URL)

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                Toast.makeText(this@WebViewActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }

        binding.webView.loadUrl(webviewUrl.toString())
    }

    companion object {
        const val WEBVIEW_URL = "WEBVIEW_URL"
    }
}