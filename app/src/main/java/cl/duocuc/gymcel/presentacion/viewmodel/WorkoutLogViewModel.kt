package cl.duocuc.gymcel.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.data.local.entities.TreinoEntity
import cl.duocuc.gymcel.domain.data.RepositoryFactory
import cl.duocuc.gymcel.domain.model.Rutina
import cl.duocuc.gymcel.domain.model.TreinoLogUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class WorkoutLogViewModel(
    repoFactory: RepositoryFactory
) : ViewModel() {

    private val rutinaRepo by lazy {
        repoFactory.create(RutinaEntity::class.java)
    }

    private val treinoRepo by lazy {
        repoFactory.create(TreinoEntity::class.java)
    }


    private val _treinosUI = MutableStateFlow<List<TreinoLogUI>>(emptyList())
    val treinosUI: StateFlow<List<TreinoLogUI>> = _treinosUI.asStateFlow()

    init {
        cargarDatos()
    }

    // cl.duocuc.gymcel.presentacion.viewmodel.WorkoutLogViewModel (MODIFICADO)

    private fun cargarDatos() {
        viewModelScope.launch {

            val rutinas = rutinaRepo.getAll().associateBy { it.id }

            val todosLosTreinos = treinoRepo.getAll()

            val treinosUI = todosLosTreinos.mapNotNull { treino ->
                val rutina = rutinas[treino.rutina_id] ?: return@mapNotNull null

                val fecha = Instant.ofEpochSecond(treino.timestamp).atZone(ZoneId.systemDefault()).toLocalDate()

                TreinoLogUI(
                    treinoId = treino.id,
                    rutinaNombre = rutina.name,
                    fecha = fecha,
                    diaSemana = rutina.dia?.let { DayOfWeek.valueOf(it) },
                    isDone = treino.done
                )
            }

            _treinosUI.value = treinosUI.sortedByDescending { it.fecha }
        }
    }


}