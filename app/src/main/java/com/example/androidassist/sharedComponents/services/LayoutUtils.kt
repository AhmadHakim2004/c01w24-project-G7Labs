package com.example.androidassist.sharedComponents.services

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.view.setPadding

class LayoutUtils() {
    private var _width: Int = 0
    private var _height: Int = 0

    constructor(context: Context) : this() {
        setDimensions(context)
    }

    /**
     * Sets Margins of View
     *
     * @param view View to be Modified
     * @param leftPercentage Percentage of screen width to set the left margin
     * @param topPercentage Percentage of screen height to set the top margin
     * @param rightPercentage Percentage of screen width to set the right margin
     * @param bottomPercentage Percentage of screen height to set the bottom margin
     *
     * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
     */
    fun setMargins(
            view: View,
            leftPercentage: Float,
            topPercentage: Float,
            rightPercentage: Float,
            bottomPercentage: Float,
    ) {
        val layoutParams = view.layoutParams as MarginLayoutParams

        layoutParams.setMargins(
                (_width * leftPercentage).toInt(),
                (_height * topPercentage).toInt(),
                (_width * rightPercentage).toInt(),
                (_height * bottomPercentage).toInt()
        )
    }

    /**
     * Sets Margins of View
     *
     * @param view View to be Modified
     * @param marginPercentage Percentage of screen height/width to set the margins
     * @param comparedToHeight true by default to set the margins as a percentage of screen height,
     *                          set it to false to set margins as a percentage of screen width
     *
     * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
     */
    fun setMargins(view: View, marginPercentage: Float, comparedToHeight: Boolean = true)
    {
        val selectedDimensionSize = if(comparedToHeight) _height else _width
        val layoutParams = view.layoutParams as MarginLayoutParams

        layoutParams.setMargins((selectedDimensionSize * marginPercentage).toInt())
    }

    /**
     * Sets Padding of View
     *
     * @param view View to be Modified
     * @param leftPercentage Percentage of screen width to set the left padding
     * @param topPercentage Percentage of screen height to set the top padding
     * @param rightPercentage Percentage of screen width to set the right padding
     * @param bottomPercentage Percentage of screen height to set the bottom padding
     *
     * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
     */
    fun setPadding(
            view: View,
            leftPercentage: Float,
            topPercentage: Float,
            rightPercentage: Float,
            bottomPercentage: Float
    ) {
        view.setPadding(
                (_width * leftPercentage).toInt(),
                (_height * topPercentage).toInt(),
                (_width * rightPercentage).toInt(),
                (_height * bottomPercentage).toInt()
        )
    }

    /**
     * Sets Padding of View
     *
     * @param view View to be Modified
     * @param paddingPercentage Percentage of screen height/width to set the padding
     * @param comparedToHeight true by default to set the padding as a percentage of screen height,
     *                          set it to false to set padding as a percentage of screen width
     *
     * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
     */
    fun setPadding(view: View, paddingPercentage: Float, comparedToHeight: Boolean = true)
    {
        val selectedDimensionSize = if(comparedToHeight) _height else _width

        view.setPadding((selectedDimensionSize * paddingPercentage).toInt())
    }

    /**
     * Sets Elevation of View
     *
     * @param view View to be Modified
     * @param elevationPercentage Percentage of screen height/width to set the elevation
     * @param comparedToHeight true by default to set the elevation as a percentage of screen height,
     *                          set it to false to set elevation as a percentage of screen width
     *
     * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
     */
    fun setElevation(view: View, elevationPercentage: Float, comparedToHeight: Boolean = true)
    {
        val selectedDimensionSize = if(comparedToHeight) _height else _width

        view.elevation = selectedDimensionSize * elevationPercentage
    }

    /**
     * Sets Text Size of TextView
     *
     * @param view TextView to be Modified
     * @param textSizePercentage Percentage of screen height/width to set the text size
     * @param comparedToHeight true by default to set the text size as a percentage of screen height,
     *                          set it to false to set text size as a percentage of screen width
     *
     * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
     */
    fun setTextSize(view: TextView, textSizePercentage: Float, comparedToHeight: Boolean = true)
    {
        val selectedDimensionSize = if(comparedToHeight) _height else _width

        view.textSize = selectedDimensionSize * textSizePercentage
    }

    @Suppress("DEPRECATION")
    private fun setDimensions(context: Context) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            val bounds = metrics.bounds

            _width = bounds.width()
            _height = bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)

            _width = displayMetrics.widthPixels
            _height = displayMetrics.heightPixels
        }
    }
}