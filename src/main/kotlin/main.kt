import analisadorLexico.impl.JackTokenizerImpl
import java.io.File

fun readFileLineByLine (fileName: String) : Collection<String> = File(fileName).readLines().toList()

fun main(args: Array<String>) {
    val lines = readFileLineByLine("./src/main/resources/Main.jack")

    println("<token>")
    lines.forEach{ line ->
        val jackTokenizer = JackTokenizerImpl(line)
        while (jackTokenizer.hasMoreTokens()) {
            println(jackTokenizer.advance())
        }
    }
    println("</token>")
}