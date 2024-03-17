package com.syntaxticsugr.callerid.ui.widgets

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R

@Composable
fun SearchBar(context: Context) {
    Card(
        modifier = Modifier
            .width(0.75.dw),
        shape = RoundedCornerShape(0.25.dw)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 0.04.dw, top = 0.01.dw, end = 0.02.dw, bottom = 0.01.dw),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Search Phone Number")

            Spacer(modifier = Modifier.weight(0.1f))

            IconButton(
                onClick = {
                    /* TODO */
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        }
    }
}
