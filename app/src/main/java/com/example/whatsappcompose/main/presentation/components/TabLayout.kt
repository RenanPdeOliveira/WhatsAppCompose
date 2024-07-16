package com.example.whatsappcompose.main.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.presentation.main.MainEvents
import kotlinx.coroutines.launch

@Composable
fun TabLayout(
    users: List<User>,
    onEvent: (MainEvents.OnContactClick) -> Unit
) {
    val state = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    var tabIndex by remember {
        mutableIntStateOf(0)
    }
    val tabs = listOf(
        TabItem(Icons.AutoMirrored.Rounded.Chat, "Chats"),
        TabItem(Icons.Rounded.Contacts, "Contacts")
    )
    TabRow(
        selectedTabIndex = tabIndex
    ) {
        tabs.forEachIndexed { index, tab ->
            LeadingIconTab(
                selected = tabIndex == index,
                onClick = {
                    tabIndex = index
                    scope.launch {
                        state.animateScrollToPage(tabIndex)
                    }
                },
                icon = {
                    Icon(imageVector = tab.icon, contentDescription = tab.title)
                },
                text = {
                    Text(text = tab.title)
                }
            )
        }
    }
    HorizontalPager(
        state = state,
        modifier = Modifier.fillMaxWidth()
    ) { index ->
        when (index) {
            0 -> {
                tabIndex = 0
                Contacts(users, onEvent)
            }

            1 -> {
                tabIndex = 1
                Contacts(users, onEvent)
            }
        }
    }
}

data class TabItem(
    val icon: ImageVector,
    val title: String
)