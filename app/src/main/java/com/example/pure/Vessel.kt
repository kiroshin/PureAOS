/*
 * Vessel.kt
 * Created by Kiro Shin <mulgom@gmail.com> on 2024.
 */

package com.example.pure

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import com.example.pure.model.Gender
import com.example.pure.model.Person
import com.example.pure.util.MutableStore
import com.example.pure.util.mxStorage
import com.example.pure.usecase.*
import com.example.pure.util.mxReadValue
import com.example.pure.util.mxWriteValue
import com.example.pure.worker.PersonDBRepository
import com.example.pure.worker.PersonWebRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class Vessel(context: Context): MutableStore<Roger> by MutableStateFlow(Roger()), Serving  {
    private val SYS_LAUNCHED_EPOCH_SEC = longPreferencesKey("SYS_LAUNCHED_EPOCH_SEC")
    val appStorage = context.mxStorage
    val vesselScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    val personDBWork: PersonDBWork = PersonDBRepository(context, ::databaseDidUpdate)
    val personWebData: PersonWebWork by lazy { PersonWebRepository() }

    override val appState: AppState get() = this
    override val loadPersonAction: LoadPersonUsecase get() = ::loadPerson
    override val applyIsRegionAction: ApplyIsRegionUsecase get() = ::applyIsRegion

    init { loadQuery() }

    private fun loadQuery() { vesselScope.launch(Dispatchers.IO) {
        if (0L == appStorage.mxReadValue(SYS_LAUNCHED_EPOCH_SEC, 0L)) {
            runCatching {
                personWebData.getAllPerson()
            }.onSuccess {
                personDBWork.updateManyPerson(it)
                val current = Instant.now().epochSecond
                appStorage.mxWriteValue(SYS_LAUNCHED_EPOCH_SEC, current)
                update { it.copy(sys = it.sys.copy(last = Roger.Signal.SUCCESS)) }
            }.onFailure {
                update { it.copy(sys = it.sys.copy(last = Roger.Signal.FAILURE)) }
            }
        } else {
            val metas = personDBWork.readAllPersonMeta()
            update {
                it.copy(sys = it.sys.copy(
                    last = Roger.Signal.SUCCESS),
                    query = it.query.copy(metas = metas)
                )
            }
        }
        println(appState.value)
    } }

    private fun databaseDidUpdate(metas: List<Person.Meta>) {
        update { it.copy(query = it.query.copy(metas = metas)) }
    }
}



object Raft: Serving {
    private val storage: MutableStore<Roger> = MutableStateFlow(Roger())
    override val appState: AppState get () = storage
    override val loadPersonAction: LoadPersonUsecase
        get() = usecase@{
            delay(2000)
            return@usecase Person("ABCD", "Jane", "jjnn", Gender.FEMALE, "jn@abc.com", 19, "KO", "010-1111-2222", "https://randomuser.me/api/portraits/women/5.jpg")
        }
    override val applyIsRegionAction: ApplyIsRegionUsecase
        get() = usecase@{ isRegion ->
            storage.update { it.copy(field = it.field.copy(isRegion = isRegion)) }
        }
}

