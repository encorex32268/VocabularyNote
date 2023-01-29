package com.lihan.vocabularynote.feature.home.presentations.home.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class MultipleActionItem(
    val name : String ,
    val icon : ImageVector
){
    companion object{
        const val ACTION_ITEM_ADD = "add"
        const val ACTION_ITEM_EDIT= "edit"
        const val ACTION_ITEM_DELETE= "delete"
        const val ACTION_ITEM_TOOL= "tool"
    }
}



@Composable
fun MultipleFloatingActionButton(
    multipleActionItems : List<MultipleActionItem>,
    onFloatingButtonClick : (MultipleActionItem) -> Unit,
    floatingActionButtonIcon : ImageVector = Icons.Default.Add
) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    val transition  = updateTransition(targetState = isClicked, label = "")
    val rotation by transition.animateFloat(label = "") {
        if (it) 45f else 0f
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isClicked){
            val alpha : Float by transition.animateFloat(
                transitionSpec = {
                    tween(durationMillis = 500)
                }, label = ""
            ){
                if (it) 1f else 0f
            }
            multipleActionItems.forEach {
                MiniFloatingActionButton(
                    onClick = {
                        onFloatingButtonClick(it)
                    },
                    multipleActionItem = it,
                    buttonAlpha = alpha
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
        FloatingActionButton(
            onClick = { isClicked = !isClicked }
        ){
            Icon(
                modifier = Modifier.rotate(rotation),
                imageVector = floatingActionButtonIcon,
                contentDescription = "FloatingMenuButton",
                tint = Color.Black
            )

        }
    }


}


@Composable
fun MiniFloatingActionButton(
    onClick : () -> Unit,
    multipleActionItem: MultipleActionItem,
    buttonAlpha : Float
) {
    val fabSize = 56.dp
    Box(
        modifier = Modifier
            .size(fabSize)
            .clip(CircleShape)
            .background(MaterialTheme.colors.secondary)
            .clickable {
                onClick()
            }
            .alpha(buttonAlpha)
        ,
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = multipleActionItem.icon,
            contentDescription = multipleActionItem.name,
            tint = Color.Black
        )
    }

}