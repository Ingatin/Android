package id.co.brainy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.co.brainy.data.network.response.TasksItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


@Composable
fun CardMyTask(
    tasks: TasksItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary.copy(
                alpha = 0.6f
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = tasks.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                )
                Text(
                    text = tasks.category,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = MaterialTheme.colorScheme.primary.copy(
                        ))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                )
            }

            Text(
                text = tasks.desc,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = getTimeRemainingText(tasks.dueDate),
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                ),
            )



        }

    }
}

fun getTimeRemainingText(targetDateTime: String): String {
    val format = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

    return try {
        val now = Calendar.getInstance().time
        val target = format.parse(targetDateTime)

        if (target != null) {
            val diffInMillis = target.time - now.time

            if (diffInMillis <= 0) {
                "the time is over"
            } else {
                val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
                val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60

                when {
                    days > 0 -> "$days day ago"
                    hours > 0 -> "$hours hours ago"
                    minutes > 0 -> "$minutes minutes ago"
                    else -> "is time"
                }
            }
        } else {
            "Incorrect date format"
        }
    } catch (e: Exception) {
        "Failed to calculate time"
    }
}


@Preview(showBackground = true)
@Composable
fun CardMyTaskPreview() {
//    CardMyTask(
//        title = "Task",
//        category = "Category",
//        time = "this time",
//        desc = "this is description"
//    )
}