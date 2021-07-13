import analisadorLexico.impl.JackTokenizerImpl
import analisadorSintatico.impl.CompilationEngineImpl
import compileXML.impl.XmlCompileEngine
import java.io.File
import java.io.FileOutputStream

fun readFileLineByLine (fileName: String) : Collection<String> = File(fileName).readLines().toList()

fun readFile (fileName: String) : String = File(fileName).readText()

fun main(args: Array<String>) {
    val dir = "./src/main/resources/"
    val nameFile = "Square.jack"
    val name = nameFile.split(".")[0]
    val src = readFile("${dir}$nameFile")

    val jackTokenizer = JackTokenizerImpl(src)
    val xmlCompileEngine = XmlCompileEngine()

    val compilationEngine = CompilationEngineImpl(jackTokenizer, xmlCompileEngine)
    compilationEngine.compileClass()

    compilationEngine.xml.printTerminal()
    compilationEngine.xml.generateXml(dir, "${name}.xml")
}