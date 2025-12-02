package cl.duocuc.gymcel.core.navigation

sealed class Segment {
    data class Static(val value: String) : Segment()
    data class Param(val name: String) : Segment() {
        val placeholder get() = "{$name}"
    }
}

class Route private constructor(
    private val segments: List<Segment>,
    private val params: List<Segment.Param>
) {

    private val pattern: String = segments.joinToString("/") {
        when (it) {
            is Segment.Static -> it.value
            is Segment.Param -> it.placeholder
        }
    }


    operator fun invoke(): String = this.toString()

    operator fun invoke(
        vararg mappers: (Segment.Param) -> Any?
    ): String {
        var idx = 0

        return segments.joinToString("/") { seg ->
            when (seg) {
                is Segment.Static -> seg.value
                is Segment.Param -> {
                    val value = mappers[idx++](seg) ?: error("Param '${seg.name}' returned null")
                    value.toString()
                }
            }
        }
    }


    operator fun invoke(vararg values: Any?): String {
        if (values.size != params.size)
            error("Expected ${params.size} params, got ${values.size}")

        var idx = 0

        return segments.joinToString("/") { seg ->
            when (seg) {
                is Segment.Static -> seg.value
                is Segment.Param  -> values[idx++].toString()
            }
        }
    }

    override fun toString(): String = pattern


    override fun equals(other: Any?) =
        if (other is String) pattern == other else super.equals(other)

    override fun hashCode(): Int {
        return pattern.hashCode()
    }


    class Builder {
        private val segments = mutableListOf<Segment>()
        private val params   = mutableListOf<Segment.Param>()

        fun text(value: String): Builder {
            segments += Segment.Static(value)
            return this
        }

        fun param(name: String): Builder {
            val p = Segment.Param(name)
            segments += p
            params += p
            return this
        }

        fun build(): Route = Route(segments, params)
    }
}

fun route(build: Route.Builder.() -> Unit): Route =
    Route.Builder().apply(build).build()
