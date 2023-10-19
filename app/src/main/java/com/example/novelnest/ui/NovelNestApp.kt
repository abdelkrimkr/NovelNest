package com.example.novelnest.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.novelnest.ui.screens.HomeScreen
import com.example.novelnest.ui.screens.NovelViewModel

@OptIn(ExperimentalMaterial3Api::class , ExperimentalComposeUiApi::class)
@Composable
fun NovelApp(){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) ,
    topBar = { NovelTopBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
        ) {
            val novelViewModel : NovelViewModel = viewModel(factory = NovelViewModel.Factory)
            val keyboardController = LocalSoftwareKeyboardController.current
            val searchName = novelViewModel.searchNameUiState
            val context = LocalContext.current
            Column(
                    modifier = Modifier. padding(start = 8.dp, end = 8.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                        value = searchName ,
                        label = { Text(text = "Search Book")},
                        onValueChange ={ name -> novelViewModel.setSearchName(name)},
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                                onDone = {
                                    if (searchName.isBlank()){
                                        Toast.makeText(context, "No text were entered", Toast.LENGTH_SHORT).show()
                                    }else {
                                        novelViewModel.getNovel(searchName)
                                    }
                                    keyboardController?.hide()}),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )
                Spacer(modifier = Modifier.height(10.dp))
                HomeScreen(
                        novelUiState = novelViewModel.novelUiState,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NovelTopBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
            title = {
                    Text(
                            text = "NovelNest",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.displaySmall)
            },
            scrollBehavior = scrollBehavior,
            modifier = modifier
    )
}
