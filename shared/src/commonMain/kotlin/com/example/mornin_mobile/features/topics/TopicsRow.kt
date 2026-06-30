package com.example.mornin_mobile.features.topics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mornin_mobile.MorninWarm
import com.example.mornin_mobile.domain.entity.TopicEntity

@Composable
fun TopicsRow(viewModel: TopicsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.topics) { topic ->
            TopicPill(topic = topic, onDeleteClicked = { viewModel.onDeleteClicked(topic) })
        }
        item {
            AddTopicPill(onClick = { viewModel.onAddClicked() })
        }
    }

    if (state.showAddDialog) {
        AddTopicDialog(
            onConfirm = { viewModel.onAddConfirmed(it) },
            onDismiss = { viewModel.onAddDismissed() }
        )
    }

    state.topicToDelete?.let { topic ->
        DeleteTopicDialog(
            topicName = topic.name,
            onConfirm = { viewModel.onDeleteConfirmed() },
            onDismiss = { viewModel.onDeleteDismissed() }
        )
    }
}

@Composable
private fun TopicPill(topic: TopicEntity, onDeleteClicked: () -> Unit) {
    Surface(
        color = MorninWarm.SurfaceAlt,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp, end = 8.dp, top = 6.dp, bottom = 6.dp)
        ) {
            Text(
                text = topic.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MorninWarm.Text
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "✕",
                fontSize = 11.sp,
                color = MorninWarm.TextMuted,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDeleteClicked
                )
            )
        }
    }
}

@Composable
private fun AddTopicPill(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = MorninWarm.Accent.copy(alpha = 0.12f),
        shape = RoundedCornerShape(50)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "+ Add topic",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MorninWarm.Accent
            )
        }
    }
}

@Composable
private fun AddTopicDialog(onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add topic") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Topic name") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (text.isNotBlank()) onConfirm(text.trim()) },
                enabled = text.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun DeleteTopicDialog(topicName: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Remove topic") },
        text = { Text("Remove \"$topicName\" from your topics?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Remove", color = MorninWarm.Accent)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
