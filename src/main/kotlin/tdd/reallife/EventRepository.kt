package tdd.reallife

interface EventRepository {
    fun send(message: String)
}