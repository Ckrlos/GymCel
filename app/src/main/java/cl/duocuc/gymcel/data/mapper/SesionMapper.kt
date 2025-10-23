package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.SesionEntity
import cl.duocuc.gymcel.domain.model.Sesion

fun SesionEntity.toDomain(): Sesion = Sesion(
    id = id,
    rutinaId = rutina_id,
    fecha = date,
    notas = notas
)

fun Sesion.toEntity(): SesionEntity = SesionEntity(
    id = id,
    rutina_id = rutinaId,
    date = fecha,
    notas = notas
)
