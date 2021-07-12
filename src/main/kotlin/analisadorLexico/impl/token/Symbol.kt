package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/**
'{' | '}' | '(' | ')' | '[' | ']' | '. ' | ', ' | '; ' | '+' | '-' | '*' |
'/' | '&' | '|' | '<' | '>' | '=' | '~'
*/
data class Symbol(
    val token: String,
    val tokenType: TokenType = TokenType.SYMBOL,
    val stringTag: String = "symbol"
): Token {
    override fun getValue(): String = this.token

    fun token() = when(token) {
        "<" -> "&lt;"
        ">" -> "&gt;"
        "\"" -> "&quot;"
        "&" -> "&amp;"
        else -> token
    }
    override fun toString() = "<$stringTag> ${this.token()} </$stringTag>"
}