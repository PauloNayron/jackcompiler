import analisadorLexico.impl.JackTokenizerImpl
import analisadorSintatico.impl.CompilationEngineImpl
import java.io.File

fun readFileLineByLine (fileName: String) : Collection<String> = File(fileName).readLines().toList()

fun readFile (fileName: String) : String = File(fileName).readText()

fun main(args: Array<String>) {
    val src = readFile("./src/main/resources/Main.jack")
    val jackTokenizer = JackTokenizerImpl(src)
    /*
    while (jackTokenizer.hasMoreTokens()) {
        println(jackTokenizer.advance())
    }
    */

    val compilationEngine = CompilationEngineImpl(jackTokenizer)
    compilationEngine.compileClass()
}