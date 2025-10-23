package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.SetRutinaEntity
import cl.duocuc.gymcel.domain.model.SetRutina

fun SetRutinaEntity.toDomain(): SetRutina = SetRutina(
    id = id,
    rutinaEntryId = rutina_entry_id,
    codigo = code,
    repeticiones = reps,
    rangoMin = rep_range_min,
    rangoMax = rep_range_max,
    carga = load,
    unidadCarga = load_unit,
    descansoSegundos = rest_time_sec,
    notas = notas
)

fun SetRutina.toEntity(): SetRutinaEntity = SetRutinaEntity(
    id = id,
    rutina_entry_id = rutinaEntryId,
    code = codigo,
    reps = repeticiones,
    rep_range_min = rangoMin,
    rep_range_max = rangoMax,
    load = carga,
    load_unit = unidadCarga,
    rest_time_sec = descansoSegundos,
    notas = notas
)
