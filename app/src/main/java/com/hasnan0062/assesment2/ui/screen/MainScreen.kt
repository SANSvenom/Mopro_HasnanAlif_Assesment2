package com.hasnan0062.assesment2.ui.screen


import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hasnan0062.assesment2.R
import com.hasnan0062.assesment2.database.BukuDb
import com.hasnan0062.assesment2.model.Buku
import com.hasnan0062.assesment2.navigation.Screen
import com.hasnan0062.assesment2.screen.MainViewModel
import com.hasnan0062.assesment2.ui.theme.Theme
import com.hasnan0062.assesment2.util.SettingsDataStore
import com.hasnan0062.assesment2.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)
    val isTheme by dataStore.themeFlow.collectAsState(true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_library),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                        )
                        Text(text = stringResource(id = R.string.app_name))
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveLayout(!showList)
                    }
                    }){
                        Icon(
                            painter = painterResource(
                                if(showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_list_24
                            ),
                            contentDescription = stringResource(
                                if(showList) R.string.grid
                                else R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveTheme(!isTheme)
                    }
                    }){
                        Icon(
                            painter = painterResource(
                                R.drawable.baseline_palette_24
                            ),
                            contentDescription = stringResource(
                                R.string.theme
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.tambah_buku),
                    tint = MaterialTheme.colorScheme.primary
                )

            }
        }
    ) { padding ->
        ScreenContent(showList, modifier = Modifier.padding(padding), navController)

    }
}


@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier = Modifier, navController: NavHostController){
    val context  = LocalContext.current
    val db = BukuDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if(data.isEmpty()){
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }

    }else {
        if(showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp),
            ) {
                items(data) {
                    ListItem(buku = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                    HorizontalDivider()
                }
            }
        }
        else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    GridItem(buku = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(buku: Buku, onClick: () -> Unit ){
    Column(
        modifier = Modifier.fillMaxWidth()
            .clickable {onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)


    ){
        Text(text = buku.judul,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(text = buku.isbn,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = buku.kategori)
    }
}

@Composable
fun GridItem(buku: Buku, onClick: () -> Unit ){
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, DividerDefaults.color)
    ){
           Column(
               modifier = Modifier.padding(8.dp),
               verticalArrangement = Arrangement.spacedBy(8.dp)
           ) {
               Text(text = buku.judul,
                   maxLines = 1,
                   overflow = TextOverflow.Ellipsis,
                   fontWeight = FontWeight.Bold
               )
               Text(
                   text = buku.isbn,
                   maxLines = 1,
                   overflow = TextOverflow.Ellipsis
               )
               Text(text = buku.kategori)
           }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Theme {
        MainScreen(rememberNavController())
    }
}


