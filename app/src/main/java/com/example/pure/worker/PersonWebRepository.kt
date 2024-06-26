/*
 * PersonWebRepository.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.worker

import com.example.pure.PersonWebWork
import com.example.pure.gear.HTTPRandomuserAccess
import com.example.pure.gear.HTTPRetrofit
import com.example.pure.gear.getAllUser
import com.example.pure.gear.randomuser
import com.example.pure.model.Fizzle
import com.example.pure.model.Gender
import com.example.pure.model.Person

class PersonWebRepository: PersonWebWork {
    private val access: HTTPRandomuserAccess = HTTPRetrofit.randomuser

    override suspend fun getAllPerson(): List<Person> = try {
        val response = access.getAllUser(100, 3)
        response.body()?.results?.map {
            it.toEntity()
        } ?: emptyList()
    } catch (e: Throwable){
        throw Fizzle.WebDataGetFail()
    }


}


private fun HTTPRandomuserAccess.User.toEntity(): Person = Person(
    id = login.uuid ?: "",
    name = "${name.first ?: ""} ${name.last ?: ""}".trim(),
    username = login.username ?: "",
    gender =  Gender.valueOf(gender?.uppercase() ?: ""),
    email = email ?: "",
    age = dob.age,
    country = nat ?: "ZZ",
    cellphone = cell,
    photo = picture.large
)


