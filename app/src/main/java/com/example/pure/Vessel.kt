/*
 * Vessel.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import com.example.pure.util.MutableStore
import com.example.pure.util.mxStorage
import com.example.pure.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Vessel(context: Context): MutableStore<Roger> by MutableStateFlow(Roger()), Serving  {
    private val SYS_LAUNCHED_EPOCH_SEC = longPreferencesKey("SYS_LAUNCHED_EPOCH_SEC")
    val appStorage = context.mxStorage
    val vesselScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    val personDBWork: PersonDBWork = PersonDBRepository(context)

    override val appState: AppState get() = this
    override val loadPersonAction: LoadPersonUsecase get() = ::loadPerson
    override val setFieldVisibleAction: SetFieldVisibleUsecase get() = ::setFieldVisible

    init { loadQuery() }

    private fun loadQuery() { vesselScope.launch(Dispatchers.IO) {

    } }
}



//object Raft: Serving {
//    private val storage: MutableStore<Roger> = MutableStateFlow(Roger())
//    override val appState: AppState get () = storage
//    override val loadPersonAction: LoadPersonUsecase get() = usecase@{
//        delay(2000)
//        return@usecase Person("ABCD", "Jane", "jjnn", Gender.FEMALE, "jn@abc.com", 19, "KO", "010-1111-2222", "https://randomuser.me/api/portraits/women/5.jpg")
//    }
//
//
//}