package com.hazem.homebase.shifts.models

import androidx.annotation.ColorInt
import com.hazem.homebase.shifts.R

enum class Color(val colorName: String, @ColorInt val colorId: Int) {
    RED("red", R.color.red),
    GREEN("green", R.color.green),
    BLUE("blue", R.color.blue),
    BLACK("black", R.color.black),
    PURPLE("purple", R.color.purple),
    TRANSPARENT("transparent",R.color.transparent)
}