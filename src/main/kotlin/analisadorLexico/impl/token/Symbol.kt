package analisadorLexico.impl.token

import analisadorLexico.Token

/**
'{' | '}' | '(' | ')' | '[' | ']' | '. ' | ', ' | '; ' | '+' | '-' | '*' |
'/' | '&' | '|' | '<' | '>' | '=' | '~'
*/
data class Symbol(val token: String) : Token {
    fun token() = when(token) {
        "<" -> "&lt;"
        ">" -> "&gt;"
        "\"" -> "&quot;"
        "&" -> "&amp;"
        else -> token
    }
    override fun toString() = "<symbol> ${this.token()} </symbol>"
}