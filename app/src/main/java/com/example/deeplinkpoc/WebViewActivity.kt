package com.example.deeplinkpoc

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.deeplinkpoc.ui.theme.DeepLinkPOCTheme

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeepLinkPOCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Top App Bar")
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        finish()
                                    }) {
                                        Icon(Icons.Filled.ArrowBack, "backIcon")
                                    }
                                },
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                                elevation = 10.dp
                            )
                        },
                        content = {
                            OpenWebView()
                        }
                    )
                }
            }
        }
    }


}

@Composable
private fun OpenWebView() {
    val context = LocalContext.current
    val activity = context.findActivity()
    val mUrl = activity?.intent?.extras?.getString("URLCONTENT") ?: ""
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = object :WebViewClient(){
                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                   Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    Toast.makeText(context,"Finished",Toast.LENGTH_SHORT).show()
                }
            }

            //to enable JS
            //settings.javaScriptEnabled = true
            //settings.userAgentString = System.getProperty("http.agent")

            loadUrl(mUrl)
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
