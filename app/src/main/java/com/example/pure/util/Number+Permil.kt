/*
 * Number+Permil.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.util

fun Int.mxPermilFlot(): Float = this.toFloat() / 1000f
fun Float.mxPermilInt(): Int = (this * 1000).toInt()
fun Double.mxPermilInt(): Int = (this * 1000).toInt()

