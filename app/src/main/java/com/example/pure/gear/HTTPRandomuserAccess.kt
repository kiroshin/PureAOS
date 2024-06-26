/*
 * HTTPRandomuserAccess.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.gear

import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException


val HTTPRetrofit.randomuser: HTTPRandomuserAccess
    get() = getCore("https://randomuser.me")
        .create(HTTPRandomuserAccess::class.java)


interface HTTPRandomuserAccess {
    @GET("/api/")
    suspend fun getAllUser(@Query("results") count: Int) : Response<Users>

    data class Users(val results: List<User>)

    data class User(
        val gender: String?,
        val name: Name,
        val email: String? = null,
        val login: Login,
        val dob: Dob,
        val cell: String? = null,
        val picture: Picture,
        val nat: String? = null
    )

    data class Name(
        val first: String? = null,
        val last: String? = null
    )

    data class Login (
        val uuid: String? = null,
        val username: String? = null
    )

    data class Dob (
        val date: String? = null,
        val age: Int = 0
    )

    data class Picture (
        val large: String? = null
    )
}


suspend fun HTTPRandomuserAccess.getAllUser(count: Int, retry: Int) : Response<HTTPRandomuserAccess.Users> = try {
    getAllUser(count)
} catch (e: IOException) {
    if (retry > 0) {
        delay(3000)
        getAllUser(count, retry = retry - 1)
    }
    throw e
}



