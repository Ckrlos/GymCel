package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.MusculoEntity
import cl.duocuc.gymcel.domain.model.Musculo

fun MusculoEntity.toDomain(): Musculo {
    return Musculo(
        id = id,
        nombre = name,
        descripcion = desc,
        grupo = grupo
    )
}

fun Musculo.toEntity(): MusculoEntity {
    return MusculoEntity(
        id = id,
        name = nombre,
        desc = descripcion,
        grupo = grupo
    )
}