package com.example.tripdrop.navigator


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChipDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tripdrop.R
import com.example.tripdrop.ui.theme.TripDropTheme


@Composable
fun BottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClick: (Int) -> Unit
) {

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = Color.White,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClick(index) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(text = item.text, style = MaterialTheme.typography.labelSmall)

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.black),
                    unselectedTextColor = colorResource(id = R.color.white),
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )

        }
    }

}


data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomNavigationPreview() {
    TripDropTheme {
        BottomNavigation(
            items = listOf(
                BottomNavigationItem(icon = R.drawable.home, text = "HOME"),
                BottomNavigationItem(icon = R.drawable.add, text = "POST"),
                BottomNavigationItem(icon = R.drawable.account, text = "PROFILE")
            ),
            selected = 0,
            onItemClick = {}
        )
    }
}