package cl.duocuc.gymcel.domain.data

interface Repository<T, ID> {
    suspend fun getById(id: ID): T?
    suspend fun getAll(): List<T>
    suspend fun save(entity: T)

    suspend fun saveAll(entity: List<T>)
    suspend fun delete(entity: T)

    suspend fun deleteById(id : ID)
    suspend fun update(entity: T)
}