package id.co.brainy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTime(
    selectedDate: String,
    onDateSelected: (Long) -> Unit,
    timePickerState: TimePickerState
) {



    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TimeInput(state = timePickerState)
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            placeholder = { Text("dd-MMM-yyyy") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .padding(bottom = 22.dp, top = 4.dp)
                .border(
                    width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(14.dp)
                ),
            shape = RoundedCornerShape(14.dp),
        )
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton( onClick ={
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateSelected(millis)
                        }
                        showDatePicker = false
                    }
                    ){
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }

}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}


@Preview(showBackground = true)
@Composable
fun DateTimePreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}