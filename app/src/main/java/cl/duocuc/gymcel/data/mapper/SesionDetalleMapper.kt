package cl.duocuc.gymcel.data.mapper

import cl.duocuc.gymcel.data.local.entities.SesionDetalleEntity
import cl.duocuc.gymcel.domain.model.SesionDetalle

fun SesionDetalleEntity.toDomain(): SesionDetalle = SesionDetalle(
    id = id,
    sesionId = sesion_id,
    setRutinaId = set_rutina_id,
    linea = linea,
    repeticiones = effective_reps,
    cargaEfectiva = effective_load,
    unidadCarga = load_unit,
    rir = rir,
    rpe = rpe,
    descansoSegundos = rest_time_sec,
    notas = notas
)

fun SesionDetalle.toEntity(): SesionDetalleEntity = SesionDetalleEntity(
    id = id,
    sesion_id = sesionId,
    set_rutina_id = setRutinaId,
    linea = linea,
    effective_reps = repeticiones,
    effective_load = cargaEfectiva,
    load_unit = unidadCarga,
    rir = rir,
    rpe = rpe,
    rest_time_sec = descansoSegundos,
    notas = notas
)
