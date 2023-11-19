package com.lihan.vocabularynote.feature.tag.presentations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.ColorUtils
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.presentations.componets.VocabularyTextField
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.tag.presentations.components.TagCard
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import java.lang.String
import java.util.Date

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun TagScreen(
    tagState : TagState,
    onEvent : (TagEvent) -> Unit = {}
) {
    val spacer = LocalSpacing.current
    var isShowInsertDialog by remember {
        mutableStateOf(false)
    }
    var isShowEditDialog by remember {
        mutableStateOf(false)
    }
    var isShowDeleteDialog by remember {
        mutableStateOf(false)
    }
    var editTag by remember {
        mutableStateOf<Tag?>(null)
    }
    var deleteTag by remember {
        mutableStateOf<Tag?>(null)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isShowInsertDialog = !isShowInsertDialog
            }){
                Icon(imageVector = Icons.Default.Add, contentDescription ="TagAdd")
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TitleText(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = com.lihan.vocabularynote.R.string.tag)
                    )
                }
                Spacer(modifier = Modifier.height(spacer.spaceSmall))
                LazyVerticalGrid(columns = GridCells.Fixed(4)){
                    items(tagState.tags){ item->
                        TagCard(
                            tag = item,
                            onTagClicked = {
                                editTag = it
                                editTag?.let {
                                    isShowEditDialog = true
                                }
                            },
                            onLongClicked = {
                                deleteTag = it
                                deleteTag?.let {
                                    isShowDeleteDialog = true
                                }
                            }
                        )
                    }
                }


            }
            if(isShowInsertDialog){
                    Dialog(
                        onDismissRequest = { isShowInsertDialog = false  }
                    ) {
                        InsertTagDialog(
                            modifier = Modifier.fillMaxWidth(),
                            onEvent = onEvent,
                            onDismiss = { isShowInsertDialog = false }
                        )
                    }
            }
            if (isShowEditDialog){
                Dialog(
                    onDismissRequest = { isShowEditDialog = false }
                ) {
                    InsertTagDialog(
                        modifier = Modifier.fillMaxWidth(),
                        onEvent = onEvent,
                        tag = editTag,
                        onDismiss = {isShowEditDialog = false}
                    )
                }
            }
            if (isShowDeleteDialog){
                DeleteTagDialog(
                    tag = deleteTag,
                    onDismiss = {isShowDeleteDialog = false },
                    onOkClick = {
                        onEvent(TagEvent.DeleteTag(deleteTag))
                    }
                )
            }

        }

    }
}

@Composable
private fun DeleteTagDialog(
    tag : Tag?=null,
    onDismiss: () -> Unit = {},
    onOkClick : () -> Unit = {}
){
    tag?.let {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            title = {
                Text(text = stringResource(id = R.string.storage_edit_delete_dialog_title))
            },
            confirmButton = {
                TextButton(onClick = {
                    onOkClick()
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.storage_edit_delete_dialog_OK_button))
                }
            },
            text = {
                Text(text = stringResource(id = R.string.storage_edit_delete_dialog_message))
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.storage_edit_delete_dialog_cancel_button))
                }
            }
        )
    }
}


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
private fun InsertTagDialog(
    modifier: Modifier = Modifier,
    defaultColor : Int = 0,
    onEvent : (TagEvent) -> Unit = {},
    tag : Tag?=null,
    onDismiss : () -> Unit = {}
){
    val spacer = LocalSpacing.current
    val colorPickerController = rememberColorPickerController()
    val color = Color(defaultColor)
    val hexColor = String.format("#%06X", 0xFFFFFF and color.toArgb())
    var selectColor by remember {
        mutableStateOf(
            ColorEnvelope(
                color = Color(defaultColor),
                hexCode = hexColor,
                fromUser = true
            )
        )
    }
    var insertTagText by remember {
        mutableStateOf(tag?.name ?: "")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(spacer.spaceSmall),
        contentColor = Color.White,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HsvColorPicker(
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = spacer.spaceMedium),
                controller =colorPickerController,
                onColorChanged = {
                    selectColor = it
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = Color.Black
                    )
                    .background(
                        color = selectColor.color,
                        shape = CircleShape
                    )
                )
                Spacer(modifier = Modifier.width(LocalSpacing.current.spaceLarge))
                VocabularyTextField(
                    text =insertTagText,
                    onValueChange = {
                        insertTagText = it
                    },
                    onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus(true) }
                )

            }
            val isColorDark = ColorUtils.calculateLuminance(selectColor.color.toArgb()) < 0.25
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = spacer.spaceSmall),
                onClick = {
                    onEvent(TagEvent.InertTag(
                        Tag(
                            id = tag?.id,
                            name = insertTagText.ifEmpty { "Empty" },
                            color = selectColor.color.toArgb(),
                            createAt = Date().time
                        )
                    ))
                    onDismiss()
            }) {
                Text(
                    text = "Ok",
                    color = if (isColorDark){
                        Color.White
                    }else{
                        Color.Black
                    }
                )
            }
            Spacer(modifier = Modifier.height(spacer.spaceSmall))

        }

    }
}


@ExperimentalComposeUiApi
@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TagScreenPreView(){
    VocabularyNoteTheme {
        InsertTagDialog(
            modifier = Modifier.fillMaxWidth()
        )
//        TagScreen(
//            tagState = TagState(
//                listOf(
//                    Tag(
//                        name = "Test",
//                        color = Color.GREEN.toColor().toArgb(),
//                        createAt = 12312
//                    ),
//                    Tag(
//                        name = "Test2",
//                        color = Color.RED.toColor().toArgb(),
//                        createAt = 12312
//                    )
//                )
//            ),
//            onCloseButtonClicked = { },
//            onNavigationNewTag = { },
//            onNavigationEditTag = { tag -> }
//        )
    }

}
