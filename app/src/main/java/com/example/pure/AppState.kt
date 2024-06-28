/*
 * AppState.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure

import com.example.pure.model.Person
import com.example.pure.model.PersonIdType
import com.example.pure.util.Store

typealias AppState = Store<Roger>

data class Roger (
    val sys: Sys = Sys(),
    val route: Route = Route(),
    val query: Query = Query(),
    val field: Field = Field()
) {
    data class Sys(
        val last: Signal = Signal.READY,
    )

    data class Route(
        val uis: PersonIdType = ""
    )

    data class Query(
        val metas: List<Person.Meta> = listOf()
    )

    data class Field(
        val isUsername: Boolean = true,
        val isRegion: Boolean = true
    )

    enum class Signal {
        FAILURE, READY, SUCCESS
    }
}