/*
 * Fizzle.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.model

sealed class Fizzle(msg: String): Exception(msg) {
    class Unknown : Fizzle("An unknown error has occurred.")
    class NoInternet: Fizzle("Check Your Internet Connection.")
    class WebGetFail: Fizzle("Cannot download data from our server.")
    class DbReadError: Fizzle("Cannot read the item.")
    class InvalidURL(url: String): Fizzle(url)
    class WalkFail: Fizzle("Cannot walk.")
    class NotFly: Fizzle("NotFly.")

    override fun getLocalizedMessage(): String {
        return super.getLocalizedMessage()?: ""
    }
}

