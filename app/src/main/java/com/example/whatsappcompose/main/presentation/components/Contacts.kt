package com.example.whatsappcompose.main.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.whatsappcompose.R
import com.example.whatsappcompose.core.domain.User
import com.example.whatsappcompose.main.presentation.main.MainEvents

@Composable
fun Contacts(
    users: List<User>,
    onEvent: (MainEvents.OnContactClick) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        users.forEach { user ->
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.photo)
                    .size(200)
                    .build()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        onEvent(
                            MainEvents.OnContactClick(
                                user.name,
                                user.photo.ifEmpty { null }
                            )
                        )
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(60.dp),
                    painter = if (user.photo != "") painter else painterResource(id = R.drawable.undraw_pic_profile_empty),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = user.name, fontWeight = FontWeight.Bold)
            }
        }
    }
}
