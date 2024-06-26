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

    override suspend fun readAllPerson(id: PersonIdType): Person {
        return access.read(id).toEntity()
    }

    override suspend fun updateManyPerson(persons: List<Person>) {
        access.updateAll(persons.map { it.toModel() })
        updateBlock?.let {
            it(persons.map { ps -> ps.toMeta() })
        }
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
        id = this.id,
        name = this.name,
        country = this.country
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

