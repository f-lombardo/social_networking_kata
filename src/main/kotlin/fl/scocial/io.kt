package fl.scocial

typealias StringSource = () -> String?

class SimpleStringSource(private val s: String): StringSource {
    override fun invoke(): String = s
}


typealias StringDestination = (String) -> Unit

class SimpleStringDestination(): StringDestination {
    val lines = mutableListOf<String>()
    override fun invoke(s: String) {
        lines.add(s)
    }
}
