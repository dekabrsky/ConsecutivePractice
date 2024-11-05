package ru.urfu.consecutivepractice.presentation.drivers.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.urfu.consecutivepractice.presentation.drivers.model.DriverUiModel
import ru.urfu.consecutivepractice.ui.theme.Typography

@Composable
fun DriverItem(
    item: DriverUiModel,
    onFavoriteClicked: (DriverUiModel) -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = item.tag, style = Typography.titleMedium)

        Text(
            text = item.name,
            style = Typography.titleMedium,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        AsyncImage(
            model = item.flagUrl,
            contentDescription = null,
        )

        Icon(
            Icons.Default.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.clickable { onFavoriteClicked.invoke(item) }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun DriverItemPreview() {
    DriverItem(item = DriverUiModel("Lewis Hamilton", "HAM", ""))
}
