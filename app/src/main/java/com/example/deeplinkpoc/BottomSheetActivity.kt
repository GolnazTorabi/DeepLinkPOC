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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.deeplinkpoc.ui.theme.ui.theme.DeepLinkPOCTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class BottomSheetActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeepLinkPOCTheme {
                val modalBottomSheetState =
                    rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
                val coroutineScope = rememberCoroutineScope()
                // A surface container using the 'background' color from the theme
                ModalBottomSheetLayout(
                    sheetState = modalBottomSheetState,
                    sheetContent = {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            OpenWebView()
                        }
                    },
                    content = {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(all = 16.dp),
                            content = {
                                Text(text = "Click Me To Show WebView", color = Color.Green)
                            },
                            onClick = {
                                coroutineScope.launch {
                                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            })
                    })
            }
        }
    }
}

@Composable
private fun OpenWebView() {
    val context = LocalContext.current
    val activity = context.findBottomActivity()
    val mUrl = activity?.intent?.extras?.getString("URLCONTENTBottom") ?: ""
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show()
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

fun Context.findBottomActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


