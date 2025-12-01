
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.duocuc.gymcel.AppConstants
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.data.local.dao.RutinaDao
import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.time.DayOfWeek

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: GymDatabase

    @Before
    fun setUp() {
        db = AppConstants.getDatabase(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertarRutinaUsandoRepository() = runBlocking {
        // Registrar DAOs en FactoryProvider
        val registry = FactoryProvider.registry(db)

        val rutina = RutinaEntity(
            id = 1,
            name = "Rutina de ejemplo",
            desc = "Descripción de la rutina de ejemplo",
            dia = DayOfWeek.MONDAY.name
        )

        // Obtener repository y guardar
        val rutinaRepo = FactoryProvider.repositoryFactory(registry)
            .create(RutinaEntity::class.java)
        val rutinaDao: RutinaDao = FactoryProvider.daoFactory(registry)
            .create(RutinaEntity::class.java)

        val idGenerado = rutinaRepo.save(rutina)

        assertTrue("la instancia de idGenerado no es Long", idGenerado is Long)
        assertEquals("el id generado no es el esperado", 1L, idGenerado)


//         Validación directa vía DAO
        //TODO: hacer validacion 'deep equals' de toda la entidad.
        val daoResult = rutinaDao.getById(idGenerado)


        Assert.assertNotNull("[TESTING DAO] - el resultado es nulo", daoResult)
        Assert.assertEquals("[TESTING DAO] - el valor de nombre de rutina no es igual al que insertamos", "Rutina de ejemplo", daoResult?.name)

        val repoResult = rutinaRepo.getById(idGenerado)
        Assert.assertNotNull("[TESTING REPO] - el resultado es nulo",repoResult)
        Assert.assertEquals("[TESTING REPO] - el valor de nombre de rutina no es igual al que insertamos", "Rutina de ejemplo", repoResult?.name)
    }
}
