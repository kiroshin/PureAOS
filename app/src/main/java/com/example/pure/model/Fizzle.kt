/*
 * Fizzle.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.model

sealed class Fizzle(msg: String): Exception(msg) {
    class Unknown : Fizzle("알 수 없는 문제가 있네요 (ㅜ.ㅜ)")
    class NoInternet: Fizzle("인터넷이 끊겼어요..")
    class WebGetFail: Fizzle("서버에서 자료를 가져올 수 없네요.")
    class DbReadError: Fizzle("데이터베이스에서 자료를 읽을 수 없어요.")
    class InvalidURL(url: String): Fizzle(url)
    class WalkFail: Fizzle("걷기 실패")
    class NotFly: Fizzle("못날어")

    override fun getLocalizedMessage(): String {
        return super.getLocalizedMessage()?: ""
    }
}

