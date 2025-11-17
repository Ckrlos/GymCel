package cl.duocuc.gymcel.domain.usecase

import cl.duocuc.gymcel.data.local.dao.RutinaEntryDao
import cl.duocuc.gymcel.data.local.entities.RutinaEntryEntity
import cl.duocuc.gymcel.data.local.entities.relations.RutinaConEjercicios
import cl.duocuc.gymcel.data.repository.RutinaRepositoryImpl
import cl.duocuc.gymcel.domain.model.Ejercicio
import cl.duocuc.gymcel.domain.model.Musculo
import cl.duocuc.gymcel.domain.repository.EjercicioRepository
import cl.duocuc.gymcel.domain.repository.RutinaConEjerciciosRepository
import cl.duocuc.gymcel.domain.repository.RutinaRepository

class AgregarEjercicioARutinaUseCase(
    private val dao: RutinaEntryDao
) {
    suspend operator fun invoke(entry: RutinaEntryEntity) {
        dao.insertEntry(entry)
    }
}
class ObtenerRutinaConEjerciciosUseCase(
    private val repository: RutinaConEjerciciosRepository
) {
    suspend operator fun invoke(id: Int): RutinaConEjercicios? {
        return repository.obtenerRutinaConTodo(id)
    }
}
class ObtenerRutinasConEjerciciosUseCase(
    private val repository: RutinaConEjerciciosRepository
) {
    suspend operator fun invoke(): List<RutinaConEjercicios> {
        return repository.obtenerTodasConTodo()
    }
}
class GuardarEjercicioCompletoUseCase(
    private val repository: RutinaRepository
) {
    suspend operator fun invoke(
        rutinaId: Int,
        ejercicio: Ejercicio,
        musculos: List<Musculo>,
        series: Int,
        reps: Int,
        carga: Double,
        descanso: Int
    ) {
        repository.guardarEjercicioCompleto(
            rutinaId,
            ejercicio,
            musculos,
            series,
            reps,
            carga,
            descanso
        )
    }
}

class ObtenerEjerciciosUseCase(private val repo: EjercicioRepository) {
    suspend operator fun invoke(): List<Ejercicio> = repo.obtenerEjercicios()
}


