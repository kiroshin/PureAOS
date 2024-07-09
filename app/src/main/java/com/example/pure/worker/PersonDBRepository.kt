/*
 * PersonDBRepository.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

/*
 * PersonDBRepository.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure.worker

import android.content.Context
import com.example.pure.PersonDBWork
import com.example.pure.model.Fizzle
import com.example.pure.model.Gender
import com.example.pure.model.Person
import com.example.pure.model.PersonIdType

class PersonDBRepository(
    context: Context,
    private val updateBlock: ((List<Person.Meta>) -> Unit)?
): PersonDBWork {
    val access = PureDatabase.getCore(context).getAccess()

    override suspend fun readAllPersonMeta(): List<Person.Meta> {
        return access.readAllMeta().map { it.toValue() }
    }
    override suspend fun readPerson(id: PersonIdType): Person = try {
        access.read(id).toEntity()
    } catch (e: Exception) {
        throw Fizzle.Unknown()
    }
    override suspend fun updateManyPerson(persons: List<Person>) {
        access.updateAll(persons.map { it.toModel() })
        updateBlock?.let {
            it(persons.map { ps -> ps.toMeta() })
        }
    }

    override suspend fun fly(isWing: Boolean): String {
        if (isWing) { return "WING" }
        throw Fizzle.NotFly()
    }

    // protected fun finalize() = println("* GC KILLED: ${this}")
}


private fun Person.toModel(): PureDatabase.HumanMO {
    return PureDatabase.HumanMO(
        id = id,
        name = name,
        username = username,
        gender = gender.name.lowercase(),
        email = email,
        age = age,
        country = country,
        cellphone = cellphone,
        photo = photo
    )
}


private fun PureDatabase.HumanMO.Meta.toValue(): Person.Meta {
    return Person.Meta(
        id = id,
        name = name,
        age = age,
        country = country
    )
}


private fun PureDatabase.HumanMO.toEntity(): Person {
    return Person(
        id = id,
        name = name,
        username = username,
        gender = Gender.valueOf(gender.uppercase()),
        email = email,
        age = age,
        country = country,
        cellphone = cellphone,
        photo = photo
    )
}

