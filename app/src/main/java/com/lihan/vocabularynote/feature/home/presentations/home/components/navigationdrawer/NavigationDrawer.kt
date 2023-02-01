package com.lihan.vocabularynote.feature.home.presentations.home.components.navigationdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.core.domain.model.User
import com.lihan.vocabularynote.feature.storage.presentations.edit.StorageEditEvent


@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier ,
    imageSize : Dp = 70.dp,
    onSaveName : (String) -> Unit = {},
    onSelectedImage : (Int) -> Unit ={},
    userName : String = "",
    userIcon : Int = User.icons[0],
    textStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
) {
    val spacer = LocalSpacing.current
    val focusManager = LocalFocusManager.current
    var mUserName by remember{
        mutableStateOf(userName)
    }
    var isEditMode by remember {
        mutableStateOf(false)
    }
    Box (
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacer.spaceLarge, bottom = spacer.spaceLarge)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(imageSize)
                    .clickable {
                        onSelectedImage(userIcon)
                    }
                ,
                painter = painterResource(id = userIcon),
                contentDescription = "UserIcon"
            )
            Spacer(modifier = Modifier.height(spacer.spaceMedium))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                    isEditMode = !isEditMode
                },
                contentAlignment = Alignment.Center
            ){
                if (isEditMode){
                    TextField(
                        modifier = Modifier.onFocusChanged {
                            if(!it.hasFocus){
                                onSaveName(mUserName)
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = textStyle,
                        value = mUserName,
                        onValueChange = {
                            mUserName = it
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                isEditMode = false
                                onSaveName(mUserName)
                                focusManager.clearFocus()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                    )

                }else{
                    Text(
                        text = mUserName,
                        style = textStyle
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacer.spaceLarge))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black
            )
        }


    }

}


@Composable
fun DrawerBody(
    items : List<DrawerItem>,
    onItemClick : (DrawerItem) -> Unit
) {
    val spacer = LocalSpacing.current
    LazyColumn{
        items(items){ drawerItem->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(drawerItem)
                    }
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacer.spaceMedium)
                ){
                    Image(
                        modifier = Modifier.weight(2f),
                        imageVector = drawerItem.imageVector,
                        contentDescription = drawerItem.name
                    )
                    Spacer(modifier = Modifier.width(LocalSpacing.current.spaceMedium))
                    Text(
                        modifier = Modifier.weight(8f),
                        text = drawerItem.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Divider(color = Color.LightGray)
            }
            Spacer(modifier = Modifier.height(spacer.spaceSmall))

        }
    }

}
