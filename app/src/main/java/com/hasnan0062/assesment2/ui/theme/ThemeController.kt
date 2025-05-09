package com.hasnan0062.assesment2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.compose.GreenTheme
import com.hasnan0062.assesment2.ui.theme.defaults.DefaultTheme
import com.hasnan0062.assesment2.util.SettingsDataStore

@Composable
fun Theme(function: @Composable () -> Unit) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val isTheme by dataStore.themeFlow.collectAsState(true)

    if (isTheme) {
        DefaultTheme {
            function()
        }
    } else {
        GreenTheme {
            function()
        }
    }
}