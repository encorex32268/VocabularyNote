package com.lihan.vocabularynote.presentations.storage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterBAndW
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lihan.vocabularynote.core.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.core.componets.SearchBar

@Composable
fun StorageScreen(
    onCloseButtonClicked : () -> Unit,
    onAddNewStorageClicked : () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacer = LocalSpacing.current
    Column (
        modifier = Modifier.fillMaxSize()
      ){
        IconButton(
            modifier =Modifier
                .size(64.dp)
                .padding(spacer.spaceMedium)
                .align(Alignment.End),
            onClick = {
            onCloseButtonClicked()
        }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close"
            )
        }
        TitleText(
            modifier = Modifier.padding(spacer.spaceMedium),
            text = Route.STORAGE
        )
        Spacer(modifier = Modifier.height(spacer.spaceSmall))
        Row (
            modifier = Modifier.fillMaxWidth()
        ){

            SearchBar(
                modifier = Modifier.weight(3f),
                onValueChange = {

                },
                onSearch = {

                },
                text = "Search....",
                onFocusChanged ={

                }
            )
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Default.FilterBAndW,
                contentDescription = "FilterBAndW"
            )
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Default.Filter,
                contentDescription = "Filter"
            )
        }
        Spacer(modifier = Modifier.height(spacer.spaceSmall))
        LazyColumn{
            // storage card
        }



    }

}