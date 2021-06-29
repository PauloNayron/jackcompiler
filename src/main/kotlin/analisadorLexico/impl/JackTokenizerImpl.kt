package analisadorLexico.impl

import analisadorLexico.JackTokenizer
import analisadorLexico.Token
import analisadorLexico.enums.TokenType

data class JackTokenizerImpl(
    var line: String,
    var currentToken: String? = null
): JackTokenizer {
    constructor(line: String) : this (
        line = line.split("//")[0],
        currentToken = null
    )

    override fun hasMoreTokens(): Boolean = REGEX.containsMatchIn(this.line)

    override fun advance(): Token {
        if (this.hasMoreTokens()) {
            this.currentToken = REGEX.find(this.line)?.value
            this.line = this.currentToken?.let { this.line.replaceFirst(it, "") }.toString()
            return this.tokenType().getToken(this.currentToken.toString())
        }
        throw Exception("Não foi possível encontrar token")
    }

    override fun tokenType(): TokenType {
        TokenType.values().forEach { tokenType -> if (tokenType.regex.matches(this.currentToken.toString())) return tokenType }
        throw Exception("Token Type não encontrado: ${this.currentToken}")
    }

    companion object {
        val REGEX = "[0-9][0-9]*|[\\[]|[\\]]|[aA-zZ][aA-zZ]*|[aA-zZ_][aA0-zZ9_]*|[\"][aA-zZ_][aA0-zZ9_]*[ ]*[aA0-zZ9_]*[\"]|[(]|[)]|[<]|[>]|[=]|[{]|[}]|[\\/]{2}[/]*|[;]|[.]|[,]|[\\|]|[\\-]|[*]|[+]|[&]".toRegex()
    }
}

