/*
 * AppState.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure

import com.example.pure.model.Person
import com.example.pure.util.Store

typealias AppState = Store<Roger>

data class Roger (
    val sys: Sys = Sys(),
    val field: Field = Field(),
    val query: Query = Query()
) {
    data class Sys(
        val last: Signal = Signal.READY,        // 초기 데이터 로드 여부
        val count: Int = 0,                     // 컨트롤 버튼 누른 횟수
    )
    data class Route(
        val uis: String = ""                    // 마지막 선택 유저 ID
    )
    data class Query(
        val metas: List<Person.Meta> = listOf() // 로드한 메타 데이타
    )
    data class Field(
        val isUsername: Boolean = true,         // 유저네임 필드 보일지 여부
        val isRegion: Boolean = true              // 지역 보일지 여부
    )
    enum class Signal {
        FAILURE, READY, SUCCESS
    }
}