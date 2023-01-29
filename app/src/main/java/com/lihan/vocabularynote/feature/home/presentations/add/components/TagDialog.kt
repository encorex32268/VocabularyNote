package com.lihan.vocabularynote.feature.home.presentations.add.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.tag.domain.model.Tag

@Composable
fun TagDialog(
    modifier: Modifier = Modifier,
    tags : List<Tag> = emptyList(),
    onTagItemClicked : (Tag) -> Unit,
    onAddTagClicked : () -> Unit,
    isShow : Boolean = false,
) {
    val spacer = LocalSpacing.current
    var isShowDialog by remember{
        mutableStateOf(isShow)
    }
    if(isShowDialog){
        Dialog(
            onDismissRequest = {
                isShowDialog = false
            }
        ) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ){
                LazyColumn(
                    modifier = modifier
                ){
                    items(tags){ tag ->
                        Row(
                            modifier = modifier
                                .padding(spacer.spaceExtraSmall)
                                .clickable {
                                    isShowDialog = false
                                    onTagItemClicked(tag)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = Color(tag.color),
                                        shape = CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(spacer.spaceExtraSmall))
                            Text(text = tag.name)
                        }
                    }
                    item {
                        Row(
                            modifier = modifier,
                            horizontalArrangement = Arrangement.Center
                        ){
                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                isShowDialog = false
                                onAddTagClicked()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "TagAdd"
                                )
                            }
                        }
                    }
                }

            }
        }

    }
}