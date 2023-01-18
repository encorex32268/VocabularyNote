package com.lihan.vocabularynote.feature.tag.presentations.add

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.presentations.componets.DialogColorPicker
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.tag.presentations.TagEvent
import com.lihan.vocabularynote.feature.tag.presentations.TagViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun TagAddScreen(
    modifier: Modifier = Modifier,
    onCloseButtonClicked : () -> Unit,
    viewModel : TagAddViewModel = hiltViewModel(),
    tag: Tag
) {
    val spacer = LocalSpacing.current
    var dialogShow by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true){
        viewModel.onEvent(TagAddEvent.InitTag(tag))
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TagAddEvent.InertTag)
                onCloseButtonClicked()
            }) {
                Icon(imageVector = Icons.Default.Done  , contentDescription ="TagAddDone")
            }
        }
    ){
        Column (
            modifier = Modifier.fillMaxSize(),
        ) {
            IconButton(
                modifier = Modifier
                    .size(64.dp)
                    .padding(spacer.spaceSmall)
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
                text = Route.TAG_ADD_EDIT
            )
            Spacer(modifier = Modifier.height(spacer.spaceExtraLarge))
            Spacer(modifier = Modifier.height(spacer.spaceExtraLarge))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(Color(viewModel.tagAddState.color))
                        .clickable {
                            dialogShow = true
                        },
                )
                Spacer(modifier = Modifier.height(spacer.spaceMedium))
                if(dialogShow){
                    DialogColorPicker(
                        modifier = Modifier.fillMaxWidth(),
                        onColorSend = {
                            viewModel.onEvent(TagAddEvent.ColorPicked(it.color.toArgb()))
                            dialogShow = false
                        },
                        isShowDialog = dialogShow,
                        defaultColor = viewModel.tagAddState.color
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacer.spaceExtraSmall))
            val bringIntoViewRequester = remember {
                BringIntoViewRequester()
            }
            val scope = rememberCoroutineScope()
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current
            BasicTextField(
                textStyle = TextStyle(textAlign = TextAlign.Center),
                value = viewModel.tagAddState.name,
                onValueChange = {
                    viewModel.onEvent(TagAddEvent.InputTagName(it))
                },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        defaultKeyboardAction(ImeAction.Done)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .padding(
                        top = spacer.spaceMedium,
                        start = spacer.spaceExtraLarge,
                        end = spacer.spaceExtraLarge
                    )
                    .clip(RoundedCornerShape(5.dp))
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(MaterialTheme.colors.surface)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .border(
                        width = 1.dp,
                        color = Color(viewModel.tagAddState.color),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(spacer.spaceMedium)
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            scope.launch {
                                delay(200)
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                    .navigationBarsPadding()
                    .imePadding()


            )

        }


        }

    }

}