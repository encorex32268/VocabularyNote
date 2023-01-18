package com.lihan.vocabularynote.feature.home.presentations.home.components.navigationdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.core.domain.model.User


@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
    user : User?=null,
    imageSize : Dp = 70.dp
) {
    val spacer = LocalSpacing.current
    Box (
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacer.spaceLarge , bottom = spacer.spaceLarge)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp, color = Color.Black, CircleShape
                    )
                ,
                imageVector = ImageVector.vectorResource(id = user?.image?: R.drawable.ic_launcher_background),
                contentDescription = "${user?.name} icon"
            )
            Spacer(modifier = Modifier.height(spacer.spaceSmall))
            Text(
                text = "Lee",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(spacer.spaceSmall))
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
