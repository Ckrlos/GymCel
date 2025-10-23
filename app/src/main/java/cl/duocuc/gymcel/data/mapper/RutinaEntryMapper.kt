package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.RutinaEntryEntity
import cl.duocuc.gymcel.domain.model.RutinaEntry

fun RutinaEntryEntity.toDomain(): RutinaEntry = RutinaEntry(
    id = id,
    rutinaId = rutina_id,
    ejercicioId = ejercicio_id,
    nombre = name,
    orden = order_index,
    cantidadSets = sets_amount,
    rangoMin = set_range_min,
    rangoMax = set_range_max
)

fun RutinaEntry.toEntity(): RutinaEntryEntity = RutinaEntryEntity(
    id = id,
    rutina_id = rutinaId,
    ejercicio_id = ejercicioId,
    name = nombre,
    order_index = orden,
    sets_amount = cantidadSets,
    set_range_min = rangoMin,
    set_range_max = rangoMax
)
