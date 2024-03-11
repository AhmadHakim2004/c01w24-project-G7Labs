package com.example.androidassist.sharedComponents.utilities

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.GridView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LayoutUtilsUnitTests {
    private val viewMock = mockk<View>()
    private val textViewMock = mockk<TextView>()
    private val gridViewMock = mockk<GridView>()
    private val cardViewMock = mockk<CardView>()
    private var layoutParamsMock = mockk<LayoutParams>()
    private var marginLayoutParamsMock = mockk<MarginLayoutParams>()

    private val initialHeight = 100
    private val initialWidth = 100
    private val heightMock = 1920
    private val widthMock = 1080

    private val percentage = 0.25f
    private val percentageLeft = 0.1f
    private val percentageTop = 0.2f
    private val percentageRight = 0.3f
    private val percentageBottom = 0.4f

    private val expectedHeightRelatedPercentage = heightMock * percentage
    private val expectedWidthRelatedPercentage = widthMock * percentage
    private val expectedHeightRelatedPercentageInt = expectedHeightRelatedPercentage.toInt()
    private val expectedWidthRelatedPercentageInt = expectedWidthRelatedPercentage.toInt()

    private val expectedLeftPercentage = (widthMock * percentageLeft).toInt()
    private val expectedTopPercentage = (heightMock * percentageTop).toInt()
    private val expectedRightPercentage = (widthMock * percentageRight).toInt()
    private val expectedBottomPercentage = (heightMock * percentageBottom).toInt()

    private var leftMarginResult = 0
    private var topMarginResult = 0
    private var rightMarginResult = 0
    private var bottomMarginResult = 0

    private var leftPaddingResult = 0
    private var topPaddingResult = 0
    private var rightPaddingResult = 0
    private var bottomPaddingResult = 0

    private var elevationResult = 0f

    private var textSizeResult = 0f

    private var horizontalSpacingResult = 0
    private var verticalSpacingResult = 0

    @Before
    fun setup() {
        mockkObject(AndroidAssistApplication)

        val contextMock = mockk<Context>()
        every { AndroidAssistApplication.getAppContext() } returns contextMock

        val resourcesMock = mockk<Resources>()
        every { contextMock.resources } returns resourcesMock

        val displayMetricsMock = mockk<DisplayMetrics>()
        displayMetricsMock.widthPixels = widthMock
        displayMetricsMock.heightPixels = heightMock
        every { resourcesMock.displayMetrics } returns displayMetricsMock

        layoutParamsMock.height = initialHeight
        layoutParamsMock.width = initialWidth

        every { viewMock.layoutParams } returns layoutParamsMock
        every { viewMock.layoutParams = any() } answers {
            val params = firstArg<LayoutParams>()
            layoutParamsMock = params
        }

        every { cardViewMock.layoutParams } returns marginLayoutParamsMock
        every { cardViewMock.layoutParams = any() } answers {
            val params = firstArg<MarginLayoutParams>()
            marginLayoutParamsMock = params
        }

        every { marginLayoutParamsMock.setMargins(any<Int>(), any<Int>(), any<Int>(), any<Int>()) } answers {
            val params =  args as List<Int>
            marginLayoutParamsMock.leftMargin = params[0]
            marginLayoutParamsMock.topMargin  = params[1]
            marginLayoutParamsMock.rightMargin  = params[2]
            marginLayoutParamsMock.bottomMargin  = params[3]
        }

        every { marginLayoutParamsMock.setMargins(any<Int>()) } answers {
            val params =  firstArg<Int>()
            marginLayoutParamsMock.leftMargin = params
            marginLayoutParamsMock.topMargin  = params
            marginLayoutParamsMock.rightMargin  = params
            marginLayoutParamsMock.bottomMargin  = params
        }

        mockkConstructor(MarginLayoutParams::class)

        every { anyConstructed<MarginLayoutParams>().setMargins(any<Int>(), any<Int>(), any<Int>(), any<Int>()) } answers {
            val params =  args as List<Int>
            leftMarginResult = params[0]
            topMarginResult = params[1]
            rightMarginResult = params[2]
            bottomMarginResult = params[3]
        }

        every { anyConstructed<MarginLayoutParams>().setMargins(any<Int>()) } answers {
            val params =  firstArg<Int>()
            leftMarginResult = params
            topMarginResult = params
            rightMarginResult = params
            bottomMarginResult = params
        }

        every { viewMock.setPadding(any<Int>(), any<Int>(), any<Int>(), any<Int>()) } answers {
            val params =  args as List<Int>
            leftPaddingResult = params[0]
            topPaddingResult = params[1]
            rightPaddingResult = params[2]
            bottomPaddingResult = params[3]
        }

        every { viewMock.setPadding(any()) } answers {
            val params = firstArg<Int>()
            leftPaddingResult = params
            topPaddingResult = params
            rightPaddingResult = params
            bottomPaddingResult = params
        }

        every { viewMock.elevation = any() } answers {
            val params = firstArg<Float>()
            elevationResult = params
        }

        every { textViewMock.textSize = any() } answers {
            val params = firstArg<Float>()
            textSizeResult = params
        }

        every { gridViewMock.horizontalSpacing = any() } answers {
            val params = firstArg<Int>()
            horizontalSpacingResult = params
        }
        every { gridViewMock.verticalSpacing = any() } answers {
            val params = firstArg<Int>()
            verticalSpacingResult = params
        }
    }

    @Test
    fun testSetHeight() {
        LayoutUtils.setHeight(viewMock, percentage)
        assertEquals(expectedHeightRelatedPercentageInt, layoutParamsMock.height)
        assertEquals(initialWidth, layoutParamsMock.width)
    }

    @Test
    fun testSetWidth() {
        LayoutUtils.setWidth(viewMock, percentage)
        assertEquals(expectedWidthRelatedPercentageInt, layoutParamsMock.width)
        assertEquals(initialHeight, layoutParamsMock.height)
    }

    @Test
    fun testSetMarginsWithMarginLayoutParamsAndFourArg() {
        LayoutUtils.setMargins(cardViewMock, percentageLeft, percentageTop, percentageRight, percentageBottom)
        assertEquals(expectedLeftPercentage, marginLayoutParamsMock.leftMargin)
        assertEquals(expectedTopPercentage, marginLayoutParamsMock.topMargin)
        assertEquals(expectedRightPercentage, marginLayoutParamsMock.rightMargin)
        assertEquals(expectedBottomPercentage, marginLayoutParamsMock.bottomMargin)
    }

    @Test
    fun testSetMarginsWithMarginLayoutParamsAndOneArgComparedToHeight() {
        LayoutUtils.setMargins(cardViewMock, percentage)
        assertEquals(expectedHeightRelatedPercentageInt, marginLayoutParamsMock.leftMargin)
        assertEquals(expectedHeightRelatedPercentageInt, marginLayoutParamsMock.topMargin)
        assertEquals(expectedHeightRelatedPercentageInt, marginLayoutParamsMock.rightMargin)
        assertEquals(expectedHeightRelatedPercentageInt, marginLayoutParamsMock.bottomMargin)
    }

    @Test
    fun testSetMarginsWithMarginLayoutParamsAndOneArgComparedToWidth() {
        LayoutUtils.setMargins(cardViewMock, percentage, false)
        assertEquals(expectedWidthRelatedPercentageInt, marginLayoutParamsMock.leftMargin)
        assertEquals(expectedWidthRelatedPercentageInt, marginLayoutParamsMock.topMargin)
        assertEquals(expectedWidthRelatedPercentageInt, marginLayoutParamsMock.rightMargin)
        assertEquals(expectedWidthRelatedPercentageInt, marginLayoutParamsMock.bottomMargin)
    }

    @Test
    fun testSetMarginsWithLayoutParamsAndFourArg() {
        LayoutUtils.setMargins(viewMock, percentageLeft, percentageTop, percentageRight, percentageBottom)
        assertEquals(expectedLeftPercentage, leftMarginResult)
        assertEquals(expectedTopPercentage, topMarginResult)
        assertEquals(expectedRightPercentage, rightMarginResult)
        assertEquals(expectedBottomPercentage, bottomMarginResult)
    }

    @Test
    fun testSetMarginsWithLayoutParamsAndOneArgComparedToHeight() {
        LayoutUtils.setMargins(viewMock, percentage)
        assertEquals(expectedHeightRelatedPercentageInt, leftMarginResult)
        assertEquals(expectedHeightRelatedPercentageInt, topMarginResult)
        assertEquals(expectedHeightRelatedPercentageInt, rightMarginResult)
        assertEquals(expectedHeightRelatedPercentageInt, bottomMarginResult)
    }

    @Test
    fun testSetMarginsWithLayoutParamsAndOneArgComparedToWidth() {
        LayoutUtils.setMargins(viewMock, percentage, false)
        assertEquals(expectedWidthRelatedPercentageInt, leftMarginResult)
        assertEquals(expectedWidthRelatedPercentageInt, topMarginResult)
        assertEquals(expectedWidthRelatedPercentageInt, rightMarginResult)
        assertEquals(expectedWidthRelatedPercentageInt, bottomMarginResult)
    }

    @Test
    fun testSetPaddingFourArg() {
        LayoutUtils.setPadding(viewMock, percentageLeft, percentageTop, percentageRight, percentageBottom)
        assertEquals(expectedLeftPercentage, leftPaddingResult)
        assertEquals(expectedTopPercentage, topPaddingResult)
        assertEquals(expectedRightPercentage, rightPaddingResult)
        assertEquals(expectedBottomPercentage, bottomPaddingResult)
    }

    @Test
    fun testSetPaddingOneArgComparedToHeight() {
        LayoutUtils.setPadding(viewMock, percentage)
        assertEquals(expectedHeightRelatedPercentageInt, leftPaddingResult)
        assertEquals(expectedHeightRelatedPercentageInt, topPaddingResult)
        assertEquals(expectedHeightRelatedPercentageInt, rightPaddingResult)
        assertEquals(expectedHeightRelatedPercentageInt, bottomPaddingResult)
    }

    @Test
    fun testSetPaddingOneArgComparedToWidth() {
        LayoutUtils.setPadding(viewMock, percentage, false)
        assertEquals(expectedWidthRelatedPercentageInt, leftPaddingResult)
        assertEquals(expectedWidthRelatedPercentageInt, topPaddingResult)
        assertEquals(expectedWidthRelatedPercentageInt, rightPaddingResult)
        assertEquals(expectedWidthRelatedPercentageInt, bottomPaddingResult)
    }

    @Test
    fun testSetElevationComparedToHeight() {
        LayoutUtils.setElevation(viewMock, percentage)
        assertEquals(expectedHeightRelatedPercentage, elevationResult)
    }

    @Test
    fun testSetElevationComparedToWidth() {
        LayoutUtils.setElevation(viewMock, percentage, false)
        assertEquals(expectedWidthRelatedPercentage, elevationResult)
    }

    @Test
    fun testSetTextSizeComparedToHeight() {
        LayoutUtils.setTextSize(textViewMock, percentage)
        assertEquals(expectedHeightRelatedPercentage, textSizeResult)
    }

    @Test
    fun testSetTextSizeComparedToWidth() {
        LayoutUtils.setTextSize(textViewMock, percentage, false)
        assertEquals(expectedWidthRelatedPercentage, textSizeResult)
    }

    @Test
    fun testSetVerticalSpacing() {
        LayoutUtils.setVerticalSpacing(gridViewMock, percentage)
        assertEquals(expectedHeightRelatedPercentageInt, verticalSpacingResult)
    }

    @Test
    fun testSetHorizontalSpacing() {
        LayoutUtils.setHorizontalSpacing(gridViewMock, percentage)
        assertEquals(expectedWidthRelatedPercentageInt, horizontalSpacingResult)
    }
}