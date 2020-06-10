package fl.scocial

typealias StringSource = () -> String

class SimpleStringSource(private val s: String): StringSource {
    override fun invoke(): String = s
}
