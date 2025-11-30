package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.domain.model.Rutina
import java.time.DayOfWeek

fun RutinaEntity.toDomain(): Rutina = Rutina(
    id = id,
    nombre = name,
    descripcion = desc,
    dia = dia?.let { DayOfWeek.valueOf(it) }
)

fun Rutina.toEntity(): RutinaEntity = RutinaEntity(
    id = id,
    name = nombre,
    desc = descripcion,
    dia = dia?.name
)
