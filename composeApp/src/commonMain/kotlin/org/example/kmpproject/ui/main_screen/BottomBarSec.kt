package org.example.kmpproject.ui.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.example.kmpproject.ui.home.MainScrViewModel

@Composable
fun BottomBarSec(){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .size(220.dp, 60.dp)
            .background(Color(228, 228, 228))
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ){
            BottomItem(0,
                Icons.Outlined.Home,"home")
            BottomItem(1,
                Icons.Outlined.ShoppingCart,"product")
            BottomItem(2,
                Icons.Outlined.FavoriteBorder,"cart")
            BottomItem(3,
                Icons.Outlined.AccountCircle,"profile")
        }
    }
}

@Composable
fun BottomItem(
    tabNum: Int,
    icon: ImageVector,
    //navController: NavHostController,
    naviName : String,
    viewModel: MainScrViewModel = MainScrViewModel()
){
    val boxColor by viewModel.boxColor.collectAsState()
    val iconColor by viewModel.iconColor.collectAsState()

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(boxColor)
            .clickable {
                //navController.navigate(naviName)
                viewModel.changeColor() // حلها بعدييين .. اللون بيرجع للأول تاني ...
            },
        contentAlignment = Alignment.Center
    ){
        Icon(imageVector = icon, contentDescription = "", tint = iconColor)
    }
}