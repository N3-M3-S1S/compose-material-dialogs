package com.vanpra.composematerialdialogs.datetime.test.functional

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.test.utils.DialogWithContent
import com.vanpra.composematerialdialogs.test.utils.defaultButtons
import com.vanpra.composematerialdialogs.test.utils.extensions.assertDialogDoesNotExist
import com.vanpra.composematerialdialogs.test.utils.extensions.onDialogDateSelector
import com.vanpra.composematerialdialogs.test.utils.extensions.onPositiveButton
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatePickerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testDate = LocalDate(year = 2021, monthNumber = 1, dayOfMonth = 1)

    @Test
    fun datePickerDialogWaitForPositiveButton() {
        var selectedDate: LocalDate? = null

        composeTestRule.setContent {
            DialogWithContent(buttons = { defaultButtons() }) {
                datepicker(initialDate = testDate, waitForPositiveButton = true) {
                    selectedDate = it
                }
            }
        }

        composeTestRule.onDialogDateSelector(20).performClick()
        assertEquals(null, selectedDate)
        composeTestRule.onPositiveButton().performClick()
        /* Need this line or else tests don't wait for dialog to close */
        composeTestRule.assertDialogDoesNotExist()
        assertEquals(LocalDate(year = 2021, monthNumber = 1, dayOfMonth = 20), selectedDate)
    }

    @Test
    fun datePickerDialogDontWaitForPositiveButton() {
        var selectedDate: LocalDate? = null

        composeTestRule.setContent {
            DialogWithContent(buttons = { defaultButtons() }) {
                datepicker(initialDate = testDate, waitForPositiveButton = false) {
                    selectedDate = it
                }
            }
        }

        composeTestRule.onDialogDateSelector(20).performClick()
        composeTestRule.waitForIdle()
        assertEquals(LocalDate(year = 2021, monthNumber = 1, dayOfMonth = 20), selectedDate)
        selectedDate = null
        composeTestRule.onPositiveButton().performClick()
        /* Need this line or else tests don't wait for dialog to close */
        composeTestRule.assertDialogDoesNotExist()
        assertEquals(null, selectedDate)
    }

    @Test
    fun datePickerCustomTitle() {
        val title = "Custom Title"
        composeTestRule.setContent {
            DialogWithContent(buttons = { defaultButtons() }) {
                datepicker(title = title)
            }
        }

        composeTestRule.onNodeWithText(title).assertExists()
    }
}
