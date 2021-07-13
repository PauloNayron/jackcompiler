package compileXML

import analisadorLexico.Token

interface Xml {
    fun printToken(token: Token)

    fun openTag(tag: String)

    fun closeTag(tag: String)

    fun printTerminal()

    fun generateXml(dir: String, src: String)
}