package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

/*
aşağıdaki fonskiyonu yazma amacımız bir resource'la işlem yaparken
aşağıdaki kod bloğunu yazmaya uğraşmamak.
composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.my_string)

Onun yerine bu şekilde bir resource kaynağını kontrol edebilir olacağız.
composeTestRule.onNodeWithStringId(R.string.my_string)
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))