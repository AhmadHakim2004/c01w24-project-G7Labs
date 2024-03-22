package com.example.androidassist.sharedComponents.utilities

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.example.androidassist.sharedComponents.utilities.LocaleUtils.Companion.setAppLocale
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Locale

class LocalUtilsUnitTests {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockResources: Resources

    @Mock
    lateinit var mockConfiguration: Configuration

    @Mock
    lateinit var mockDisplayMetrics: android.util.DisplayMetrics

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        `when`(mockContext.resources).thenReturn(mockResources)
        `when`(mockResources.configuration).thenReturn(mockConfiguration)
        `when`(mockResources.displayMetrics).thenReturn(mockDisplayMetrics)
    }

    @Test
    fun `test setting app locale`() {
        val localeCode = "en_US"
        setAppLocale(mockContext, localeCode)

        val expectedLocale = Locale(localeCode)
        val sdkInt = Build.VERSION.SDK_INT

        if (sdkInt >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            assertEquals(expectedLocale, mockConfiguration.locales[0])
        } else {
            assertEquals(expectedLocale, mockConfiguration.locale)
        }
    }

}