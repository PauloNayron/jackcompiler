package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/**
'{' | '}' | '(' | ')' | '[' | ']' | '. ' | ', ' | '; ' | '+' | '-' | '*' |
'/' | '&' | '|' | '<' | '>' | '=' | '~'
*/
data class Symbol(
    val token: String,
    val linha: Int = 0,
    val tokenType: TokenType = TokenType.SYMBOL,
    val stringTag: String = "symbol"
): Token {
    override fun getValue(): String = this.token
    override fun getLine(): Int = this.linha

    fun token() = when(token) {
        "<" -> "&lt;"
        ">" -> "&gt;"
        "\"" -> "&quot;"
        "&" -> "&amp;"
        else -> token
    }
    override fun toString() = "<$stringTag> ${this.token()} </$stringTag>"
}