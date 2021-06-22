package analisadorLexico.impl.token

import analisadorLexico.Token

/**
'{' | '}' | '(' | ')' | '[' | ']' | '. ' | ', ' | '; ' | '+' | '-' | '*' |
'/' | '&' | '|' | '<' | '>' | '=' | '~'
*/
class Symbol(token: String) : Token(token) {
    override fun toString() = "<symbol> ${this.token} </symbol>"
}