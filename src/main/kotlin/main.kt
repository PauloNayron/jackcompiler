import java.io.File

fun readFileLineByLine (fileName: String) : Collection<String> = File(fileName).readLines().toList()

fun main(args: Array<String>) {
    val lines = readFileLineByLine("./src/main/resources/Main.jack")
    println("<token>")
    lines.forEach {

    }
    println("</token>")
}