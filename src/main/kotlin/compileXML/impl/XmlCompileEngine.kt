package compileXML.impl

import analisadorLexico.Token
import compileXML.Xml
import java.io.File
import java.io.FileOutputStream

class XmlCompileEngine(
    var xml: String = "",
    private var tab: String = ""
): Xml {
    override fun printToken(token: Token) {
        val message = "${tab}${token}"
        printMessage(message)
    }

    override fun openTag(tag: String) {
        val message = "${tab}<${tag}>"
        printMessage(message)
        tab += "  "
    }

    override fun closeTag(tag: String) {
        tab = tab.replaceFirst("  ", "")
        val message = "${tab}</${tag}>"
        printMessage(message)
    }

    override fun printTerminal() = println(this.xml)

    override fun generateXml(dir: String, src: String) {
        val xmlFile = File(dir, src)
        FileOutputStream(xmlFile).use {
            it.write(xml.toByteArray())
        }
    }

    private fun printMessage(message: String) {
        this.xml += message + "\n"
        println(message)
    }
}