package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import cl.duocuc.gymcel.domain.model.Rutina

fun RutinaEntity.toDomain(): Rutina = Rutina(
    id = id,
    nombre = name,
    descripcion = desc
)

fun Rutina.toEntity(): RutinaEntity = RutinaEntity(
    id = id,
    name = nombre,
    desc = descripcion
)
