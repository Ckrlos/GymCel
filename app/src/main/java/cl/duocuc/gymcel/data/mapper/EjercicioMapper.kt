package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.EjercicioEntity
import cl.duocuc.gymcel.domain.model.Ejercicio


fun EjercicioEntity.toDomain(): Ejercicio = Ejercicio(
    id = id,
    nombre = name,
    descripcion = desc,
    imagen = img,
    tipo = type,
    equipo = equipment,
    dificultad = difficulty
)

fun Ejercicio.toEntity(): EjercicioEntity = EjercicioEntity(
    id = id,
    name = nombre,
    desc = descripcion,
    img = imagen,
    type = tipo,
    equipment = equipo,
    difficulty = dificultad
)
