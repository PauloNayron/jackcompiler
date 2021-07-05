import analisadorLexico.impl.JackTokenizerImpl
import java.io.File

fun readFileLineByLine (fileName: String) : Collection<String> = File(fileName).readLines().toList()

fun readFile (fileName: String) : String = File(fileName).readText()

fun main(args: Array<String>) {
    println("<tokens>")
    val src = readFile("./src/main/resources/Square.jack")
    val jackTokenizer = JackTokenizerImpl(src)

    while (jackTokenizer.hasMoreTokens()) {
        println(jackTokenizer.advance())
    }
    println("</tokens>")
}