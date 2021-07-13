package analisadorLexico.impl

import analisadorLexico.JackTokenizer
import analisadorLexico.Token
import analisadorLexico.enums.TokenType
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

data class JackTokenizerImpl(
    var line: String = "",
    var count: Int = 1,
    var mapLines: Map<Int, String>?,
    var currentToken: Token? = null
): JackTokenizer {
    constructor(src: String) : this (
        mapLines = exec(src)
    )

    override fun hasMoreTokens(): Boolean {
        try {
            while (this.line.isBlank()) {
                this.line = this.mapLines?.get(this.count)!!
                this.count += 1
            }
            return REGEX.containsMatchIn(this.line)
        } catch (e: NullPointerException) {
            return false
        }
    }

    override fun advance(): Token {
        if (this.hasMoreTokens()) {
            val valueToken = REGEX.find(this.line)?.value
            this.line = valueToken?.let { this.line.replaceFirst(it, "") }.toString()
            this.currentToken = valueToken?.let { this.tokenType(it) }
            return this.currentToken!!
        }
        throw IllegalArgumentException("Não foi possível encontrar token.")
    }

    override fun tokenType(tokenValue: String): Token {
        TokenType.values().forEach { tokenType -> if (tokenType.regex.matches(tokenValue)) return tokenType.getToken(tokenValue) }
        throw Exception("Token Type não encontrado: ${this.currentToken}")
    }

    companion object {
        val REGEX = "[0-9][0-9]*|[\\[]|[\\]]|[aA-zZ][aA-zZ]*|[aA-zZ_][aA0-zZ9_]*|[\"][aA-zZ_][aA0-zZ9_]*[ ]*[aA0-zZ9_]*[\"]|[(]|[)]|[<]|[>]|[=]|[{]|[}]|[\\/]{2}[/]*|[;]|[.]|[,]|[\\|]|[\\-]|[*]|[+]|[&]|[~]".toRegex()

        fun exec (src: String): Map<Int, String> {
            val lines = src.split("\n")

            val mapLine = HashMap<Int, String>()
            var count = 1
            var linhaComentada = false
            for (line in lines) {
                if (linhaComentada && !line.contains("*/")) {
                    mapLine.put(count++, "")
                } else if (line.contains("/*")) {
                    val split = line.split("/*")
                    if (split[1].contains("*/")) {
                        linhaComentada = false
                        val split2 = split[1].split("*/")[1]
                        mapLine.put(count++, "${split[0]}${split2}")
                    } else {
                        linhaComentada = true
                        mapLine.put(count++, split[0])
                    }
                } else if (line.contains("*/")) {
                    linhaComentada = false
                    mapLine.put(count++, line.split("*/")[1])
                } else {
                    mapLine.put(count++, line.split("//")[0])
                }
            }
            return mapLine
        }
    }
}

