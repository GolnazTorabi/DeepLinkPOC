package com.example.deeplinkpoc

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.deeplinkpoc.ui.theme.DeepLinkPOCTheme


class MainActivity : ComponentActivity() {
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
                OpenTheWebViewInsideTheApp()
            }
        )
    }

    @Composable
    fun OpenTheWebViewInsideTheApp() {
        DefineView()
    }

    @Composable
    fun DefineView() {
        val activity = LocalContext.current as Activity
        val mUrl = "https://www.google.com/"
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = 16.dp),
            content = {
                Text(text = "Click Me", color = Color.Yellow)
            },
            onClick = {
                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra("URLCONTENT",mUrl)
                activity.startActivity(intent)

                //viewModel.updateClicked()
            })
    }
}




