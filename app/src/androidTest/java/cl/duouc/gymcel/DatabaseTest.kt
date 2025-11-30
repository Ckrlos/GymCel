
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.duocuc.gymcel.data.FactoryProvider
import cl.duocuc.gymcel.data.local.db.GymDatabase
import cl.duocuc.gymcel.data.local.entities.RutinaEntity
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
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GymDatabase::class.java
        )
            .allowMainThreadQueries()  // SOLO PARA TEST
            .build()
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
            .create(RutinaEntity::class.java);

        rutinaRepo.save(rutina)

        // Validación directa vía DAO
        val repoResult = rutinaRepo.getById(1)
        val daoResult = db.rutinaDao().getById(1)

        Assert.assertNotNull(repoResult)
        Assert.assertEquals("Rutina de ejemplo", repoResult?.name)
        Assert.assertNotNull(daoResult)
        Assert.assertEquals("Rutina de ejemplo", daoResult?.name)
    }
}
