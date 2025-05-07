package com.hasnan0062.assesment2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hasnan0062.assesment2.navigation.SetupNavGraph
import com.hasnan0062.assesment2.ui.theme.Assesment2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assesment2Theme {
                SetupNavGraph()
            }
        }
    }
}
