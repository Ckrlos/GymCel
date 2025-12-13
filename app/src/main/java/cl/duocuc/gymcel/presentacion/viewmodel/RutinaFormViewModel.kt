package cl.duocuc.gymcel.presentacion.viewmodel


import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duocuc.gymcel.domain.model.DetalleRutina
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.model.Rutina
import cl.duocuc.gymcel.domain.model.TipoSerie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@Immutable
data class RutinaHeaderUi(
    val nombre: String = "",
    val descripcion: String = "",
    val dia: DayOfWeek? = null
)

@Immutable
data class RutinaDetalleItemUi(
    val localId: Long,
    val ejercicioId: String,
    val ejercicioNombre: String,
    val detalle: DetalleRutina
)

@Immutable
data class RutinaFormUiState(
    val header: RutinaHeaderUi = RutinaHeaderUi(),
    val detalles: List<RutinaDetalleItemUi> = emptyList(),
    val isSaving: Boolean = false,
    val canSave: Boolean = false,
    val errorMessage: String? = null
)

class RutinaFormViewModel(
    // TODO: inyectar aquí tu Repository / UseCase de Rutina
    // private val rutinaRepository: RutinaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RutinaFormUiState())
    val uiState: StateFlow<RutinaFormUiState> = _uiState.asStateFlow()

    // ID local para diferenciar items en la UI (no es el ID de BD)
    private var detalleAutoId: Long = 0L

    // --- HEADER ---

    fun onNombreChange(nombre: String) {
        _uiState.update { state ->
            val newHeader = state.header.copy(nombre = nombre)
            state.copy(
                header = newHeader,
                canSave = validateCanSave(newHeader, state.detalles)
            )
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update { state ->
            val newHeader = state.header.copy(descripcion = descripcion)
            state.copy(
                header = newHeader,
                canSave = validateCanSave(newHeader, state.detalles)
            )
        }
    }

    fun onDiaChange(dia: DayOfWeek?) {
        _uiState.update { state ->
            val newHeader = state.header.copy(dia = dia)
            state.copy(
                header = newHeader,
                canSave = validateCanSave(newHeader, state.detalles)
            )
        }
    }

    /**
     * Llamar a esto cuando el usuario haya seleccionado un ejercicio
     * desde la pantalla de búsqueda.
     */
    fun addDetalleForExercise(ejercicio: Ejercicio) {
        val localId = ++detalleAutoId
        val current = _uiState.value
        val orden = current.detalles.size + 1

        val detalleBase = DetalleRutina(
            id = 0L,
            ejercicioId = ejercicio.id,
            orden = orden,
            series = 3,
            objetivoReps = 10,
            rangoReps = null,
            tipoSerie = TipoSerie.STRAIGHT
        )

        _uiState.update { state ->
            val newList = state.detalles + RutinaDetalleItemUi(
                localId = localId,
                ejercicioId = ejercicio.id,
                ejercicioNombre = ejercicio.nombre,
                detalle = detalleBase
            )

            state.copy(
                detalles = newList,
                canSave = validateCanSave(state.header, newList)
            )
        }
    }

    fun removeDetalle(localId: Long) {
        _uiState.update { state ->
            val filtered = state.detalles
                .filterNot { it.localId == localId }
                .mapIndexed { index, item ->
                    // re-ordenamos para que el orden quede 1..N
                    item.copy(detalle = item.detalle.copy(orden = index + 1))
                }

            state.copy(
                detalles = filtered,
                canSave = validateCanSave(state.header, filtered)
            )
        }
    }

    fun onDetalleChanged(localId: Long, detalleActualizado: DetalleRutina) {
        _uiState.update { state ->
            val newList = state.detalles.map {
                if (it.localId == localId) it.copy(detalle = detalleActualizado) else it
            }

            state.copy(
                detalles = newList,
                canSave = validateCanSave(state.header, newList)
            )
        }
    }

    // --- GUARDAR ---

    fun onGuardarClick() {
        val state = _uiState.value
        if (!validateCanSave(state.header, state.detalles)) return

        val rutina = buildRutinaFromState(state)

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            try {
                // TODO: aquí llamas a tu capa de dominio/repositorio.
                // Ejemplo:
                // val newId = rutinaRepository.insert(rutina)
                // rutinaRepository.insertDetalles(newId, rutina.detalleRutina ?: emptyList())

                // Por ahora simulamos que se guardó OK:
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = null
                    )
                }

                // Podrías exponer un SharedFlow/Event para notificar éxito al screen.

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = e.message ?: "Error al guardar la rutina"
                    )
                }
            }
        }
    }

    // --- PRIVATE HELPERS ---

    private fun validateCanSave(
        header: RutinaHeaderUi,
        detalles: List<RutinaDetalleItemUi>
    ): Boolean {
        val nombreOk = header.nombre.trim().isNotEmpty()
        val tieneDetalles = detalles.isNotEmpty()
        return nombreOk && tieneDetalles
    }

    private fun buildRutinaFromState(state: RutinaFormUiState): Rutina {
        val header = state.header

        val detallesOrdenados = state.detalles
            .sortedBy { it.detalle.orden }
            .map { it.detalle }

        return Rutina(
            id = 0L, // lo asigna la BD al insertar
            nombre = header.nombre.trim(),
            descripcion = header.descripcion.takeIf { it.isNotBlank() },
            dia = header.dia,
            detalles = detallesOrdenados
        )
    }
}