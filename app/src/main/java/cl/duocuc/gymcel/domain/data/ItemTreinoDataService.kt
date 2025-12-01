
import cl.duocuc.gymcel.data.local.dao.ItemTreinoDao
import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.data.local.entities.ItemTreinoEntity
import cl.duocuc.gymcel.domain.data.DataService // Importa tu clase base genérica


class ItemTreinoDataService(
    db: GymDatabase,
    dao: ItemTreinoDao // El constructor recibe el DAO específico
) : DataService<ItemTreinoDao>(db, dao) {

    // Aquí expones el método específico, usando el withDao genérico
    suspend fun getUltimoTreinoPorEjercicio(exerciseId: String, rutinaId: Long): List<ItemTreinoEntity> {
        return dao.getUltimoPorEjercicio(exerciseId, rutinaId)
    }
}