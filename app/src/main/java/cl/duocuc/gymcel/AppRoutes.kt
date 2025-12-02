package cl.duocuc.gymcel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import cl.duocuc.gymcel.core.navigation.route;
import cl.duocuc.gymcel.presentacion.ui.components.BottomNavItem

object AppRoutes {

    val HOME = route {
        text("home")
    }

    val WORKOUT_LOG = route {
        text("wklog")
    }

    val SELECTOR_RUTINA = route {
        text("selrutina")
    }

    @Volatile
    private var navItems: List<BottomNavItem>? = null

    fun getBottomNavItems(): List<BottomNavItem> {
        return navItems ?: synchronized(this) {
            navItems ?: listOf(
                BottomNavItem(
                    title = "Inicio",
                    route = HOME(),
                    icon = Icons.Default.Home
                ),
                BottomNavItem(
                    title = "Log",
                    route = "rutinasPorDia",
                    icon = Icons.Default.Book
                ),
                BottomNavItem(
                    title = "Crear Rutina",
                    route = "searchExercise",
                    icon = Icons.Default.FitnessCenter
                )
            ).also{ navItems = it }
        }
    }


    val TREINO_FORM = route {
        text("mktreino")
        param("id")
    }

    val RUTINA_FORM = route {
        text("mkrutina")
    }

    val BUSCAR_EJERCICIO = route {
        text("search")
        text("exercise")
    }

    val DETALLE_EJERCICIO = route {
        text("exercise")
        param("id")
    }


}