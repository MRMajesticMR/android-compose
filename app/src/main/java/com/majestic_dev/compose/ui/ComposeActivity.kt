package com.majestic_dev.compose.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majestic_dev.compose.R
import com.majestic_dev.compose.ui.theme.ComposeTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var count by remember { mutableStateOf(0) }

            App {
                Column {
                    MessageCard(msg = SampleData.conversationSample.first())
                    Counter(
                        count = count,
                        countUpdate = { count = it }
                    )
                }
            }
        }

    }

}

@Composable
fun Counter(count: Int, countUpdate: (Int) -> Unit) {


    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        onClick = { countUpdate(count + 1) }
    ) {
        Text("I've been clicked $count times")
    }
}

@Composable
fun App(content: @Composable () -> Unit) {
    ComposeTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }

}

@Composable
fun MessageCard(msg: Message) {
    var isExpanded by remember { mutableStateOf(false) }
    var isAvatarExpanded by remember { mutableStateOf(false) }

    val surfaceColor: Color by animateColorAsState(
        if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Author avatar",
            modifier = Modifier
                .animateContentSize()
                .size(if (isAvatarExpanded) 120.dp else 40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                .clickable { isAvatarExpanded = !isAvatarExpanded }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
                    .clickable { isExpanded = !isExpanded },
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor
            ) {
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

//    @Preview()
@Composable
fun PreviewConversation() {
    ComposeTheme {
        Conversation(messages = SampleData.conversationSample)
    }
}

@Preview(
    name = "Light Mode"
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMessageCard() {
    ComposeTheme {
        MessageCard(
            msg = Message(
                author = "Arkadii",
                body = "Let's say hello to Android Compose Stable Version"
            )
        )
    }
}