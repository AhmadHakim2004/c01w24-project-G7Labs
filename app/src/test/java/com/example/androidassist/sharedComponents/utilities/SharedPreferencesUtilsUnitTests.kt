package com.example.androidassist.sharedComponents.utilities

import android.content.Context
import android.content.SharedPreferences
import com.example.androidassist.sharedComponents.utilities.SharedPreferenceUtils.Companion.addStringToDefaultSharedPrefFile
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesUtilsUnitTests {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    lateinit var mockEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
    }

    @Test
    fun `test getting default shared pref file`() {
        val defaultSharedPreferences = SharedPreferenceUtils.getDefaultSharedPrefFile(mockContext)
        assertEquals(mockSharedPreferences, defaultSharedPreferences)
    }

    @Test
    fun `test adding string to default shared pref file`() {
        val key = "testKey"
        val value = "testValue"
        SharedPreferenceUtils.addStringToDefaultSharedPrefFile(mockContext, key, value)
        verify(mockEditor).putString(key, value)
        verify(mockEditor).apply()
    }

    @Test
    fun `test getting string from default shared pref file`() {
        val key = "testKey"
        val defaultValue = "defaultValue"
        val expectedValue = "testValue"
        `when`(mockSharedPreferences.getString(key, defaultValue)).thenReturn(expectedValue)
        val result = SharedPreferenceUtils.getStringFromDefaultSharedPrefFile(mockContext, key, defaultValue)
        assertEquals(expectedValue, result)
    }


}