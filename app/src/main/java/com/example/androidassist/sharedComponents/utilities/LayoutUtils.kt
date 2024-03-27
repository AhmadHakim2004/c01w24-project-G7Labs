package com.example.androidassist.sharedComponents.utilities

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.GridView
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.example.androidassist.sharedComponents.AndroidAssistApplication
import kotlin.math.roundToInt

class LayoutUtils {
    companion object {
        private var _width: Int = AndroidAssistApplication.getAppContext().resources.displayMetrics.widthPixels
        private var _height: Int = AndroidAssistApplication.getAppContext().resources.displayMetrics.heightPixels

        /**
         * Sets Height of View
         *
         * @param view View to be Modified
         * @param heightPercentage Percentage of screen height to set the height
         *
         * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
         */
        fun setHeight(view: View, heightPercentage: Float) {
            val newLayoutParams = view.layoutParams
            val wantedHeight = _height * heightPercentage
            newLayoutParams.height = wantedHeight.roundToInt()
            view.layoutParams = newLayoutParams
        }

        /**
         * Sets Width of View
         *
         * @param view View to be Modified
         * @param widthPercentage Percentage of screen width to set the width
         *
         * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
         */
        fun setWidth(view: View, widthPercentage: Float) {
            val newLayoutParams = view.layoutParams
            val wantedWidth = _width * widthPercentage
            newLayoutParams.width = wantedWidth.roundToInt()
            view.layoutParams = newLayoutParams
        }

        /**
         * Sets Height of View
         *
         * @param view View to be Modified
         * @param heightPercentage Percentage of screen height/width to set the height
         * @param widthPercentage Percentage of screen height/width to set the width
         * @param comparedToHeight true by default to set the margins as a percentage of screen height,
         *                          set it to false to set margins as a percentage of screen width
         *
         * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
         */
        fun setDimensions(view: View, heightPercentage: Float, widthPercentage: Float, comparedToHeight: Boolean = true) {
            val selectedDimensionSize = if (comparedToHeight) _height else _width
            val newLayoutParams = view.layoutParams
            val wantedHeight = selectedDimensionSize * heightPercentage
            val wantedWidth = selectedDimensionSize * widthPercentage

            newLayoutParams.height = wantedHeight.roundToInt()
            newLayoutParams.width = wantedWidth.roundToInt()
            view.layoutParams = newLayoutParams
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
            val layoutParams =
                if (view.layoutParams is MarginLayoutParams) view.layoutParams as MarginLayoutParams
                else MarginLayoutParams(view.layoutParams)

            layoutParams.setMargins(
                (_width * leftPercentage).toInt(),
                (_height * topPercentage).toInt(),
                (_width * rightPercentage).toInt(),
                (_height * bottomPercentage).toInt()
            )

            view.layoutParams = layoutParams
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
        fun setMargins(view: View, marginPercentage: Float, comparedToHeight: Boolean = true) {
            val selectedDimensionSize = if (comparedToHeight) _height else _width
            val layoutParams =
                if (view.layoutParams is MarginLayoutParams) view.layoutParams as MarginLayoutParams
                else MarginLayoutParams(view.layoutParams)

            layoutParams.setMargins((selectedDimensionSize * marginPercentage).toInt())

            view.layoutParams = layoutParams
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
        fun setPadding(view: View, paddingPercentage: Float, comparedToHeight: Boolean = true) {
            val selectedDimensionSize = if (comparedToHeight) _height else _width

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
        fun setElevation(view: View, elevationPercentage: Float, comparedToHeight: Boolean = true) {
            val selectedDimensionSize = if (comparedToHeight) _height else _width

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
        fun setTextSize(view: TextView, textSizePercentage: Float, comparedToHeight: Boolean = true) {
            val selectedDimensionSize = if (comparedToHeight) _height else _width

            view.textSize = selectedDimensionSize * textSizePercentage
        }

        /**
         * Sets Horizontal Spacing of GridView
         *
         * @param view TextView to be Modified
         * @param spacingPercentage Percentage of screen height/width to set the text size
         *
         * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
         */
        fun setHorizontalSpacing(view: GridView, spacingPercentage: Float) {
            view.horizontalSpacing = (_width * spacingPercentage).toInt()
        }

        /**
         * Sets Vertical Spacing of GridView
         *
         * @param view TextView to be Modified
         * @param spacingPercentage Percentage of screen height/width to set the text size
         *
         * @note When setting percentages, set them as: 100% = 1.0, 1% = 0.01, 0.75% = 0.0075
         */
        fun setVerticalSpacing(view: GridView, spacingPercentage: Float) {
            view.verticalSpacing = (_height * spacingPercentage).toInt()
        }


        /**
         * Sets Text Size of the whole app
         *
         * @param context context of the views to be modified
         * @param fontScale Percentage of to scale font by
         *
         * @note 1f = scale font by 1 which is the same size as original
         */
        fun setAppTextSize(context : Context, fontScale : Float){
            val resources = context.resources
            val dm = resources.displayMetrics
            val config: Configuration = resources.configuration
            config.fontScale = fontScale
            resources.updateConfiguration(config, dm)
        }
    }
}