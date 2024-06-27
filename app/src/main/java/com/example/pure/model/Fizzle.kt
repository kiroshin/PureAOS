/*
 * Fizzle.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.model

sealed class Fizzle(msg: String): Exception(msg) {
    class Unknown : Fizzle("알 수 없는 문제가 있네요 (ㅜ.ㅜ)")
    class WebDataGetFail : Fizzle("웹에서 기본 자료를 가져오지 못했어요.")
    class InvalidURL(url: String): Fizzle(url)

    override fun getLocalizedMessage(): String {
        return super.getLocalizedMessage()?: ""
    }
}

