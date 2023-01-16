package com.example.deeplinkpoc

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.deeplinkpoc.ui.theme.DeepLinkPOCTheme

class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeepLinkPOCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    View()
                }
            }
        }
    }
}

@Composable
fun View() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Top App Bar")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        },
        content = {
            //OpenWebViewOutsideTheApp()
            OpenTheWebViewInsideTheApp()
        }
    )
}

@Composable
fun OpenTheWebViewInsideTheApp() {
    DefineView()
    OpenWebView()
}

@Composable
fun DefineView() {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 16.dp),
        content = {
            Text(text = "Click Me", color = Color.Yellow)
        },
        onClick = {
            isClicked.value = true
        })
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun OpenWebView() {
    if (isClicked.value) {
        val mUrl = "https://www.google.com/"
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()

                //to enable JS
                settings.javaScriptEnabled = true

                //settings.userAgentString = System.getProperty("http.agent")
                loadUrl(mUrl)
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }
}

@Composable
fun OpenWebViewOutsideTheApp() {
    val uriHandler = LocalUriHandler.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 16.dp),
        content = {
            Text(text = "Click Me", color = Color.Yellow)
        },
        onClick = {
            uriHandler.openUri("https://www.google.com/")
        })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeepLinkPOCTheme {
        OpenTheWebViewInsideTheApp()
    }
}

private var isClicked = mutableStateOf<Boolean>(false)