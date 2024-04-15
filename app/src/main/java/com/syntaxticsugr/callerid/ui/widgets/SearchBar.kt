package com.syntaxticsugr.callerid.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.slaviboy.composeunits.dw
import com.syntaxticsugr.callerid.R
import com.syntaxticsugr.callerid.enums.ImeState
import com.syntaxticsugr.callerid.utils.ImeStateListener
import com.syntaxticsugr.callerid.viewmodel.SearchBarViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchBar(
    searchBarViewModel: SearchBarViewModel = koinViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    ImeStateListener { state ->
        when (state) {
            ImeState.HIDDEN -> focusManager.clearFocus()
            else -> {}
        }
    }

    if (searchBarViewModel.showPhoneNumberInfoDialog) {
        PhoneNumberInfoDialog(
            phoneNumber = remember { searchBarViewModel.formattedPhoneNumber() },
            onDismissRequest = {
                searchBarViewModel.showPhoneNumberInfoDialog = false
            }
        )
    }

    fun onSearchButtonClicked() {
        if (!isFocused) {
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
            searchBarViewModel.searchPhoneNumber()
        }
    }

    Card(
        modifier = Modifier
            .width(0.75.dw),
        shape = RoundedCornerShape(0.25.dw)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 0.02.dw),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
                    .weight(1f),
                value = if (isFocused) {
                    searchBarViewModel.phoneNumber
                } else {
                    ""
                },
                onValueChange = { value ->
                    searchBarViewModel.phoneNumber = value
                    if (searchBarViewModel.phoneNumberError) {
                        searchBarViewModel.phoneNumberError = false
                    }
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Search Phone Number"
                    )
                },
                prefix = if (isFocused) {
                    {
                        Text(
                            text = "+",
                            color = if (searchBarViewModel.phoneNumberError) {
                                MaterialTheme.colorScheme.error
                            } else {
                                Color.Unspecified
                            }
                        )
                    }
                } else {
                    null
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchButtonClicked()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = if (searchBarViewModel.phoneNumberError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        Color.Unspecified
                    },
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            IconButton(
                onClick = {
                    onSearchButtonClicked()
                }
            ) {
                val size = 0.06.dw

                if (searchBarViewModel.searching) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(size),
                        strokeWidth = size / 10
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
