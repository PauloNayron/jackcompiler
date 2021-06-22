package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/**
'class' | 'constructor' | 'function' |
'method' | 'field' | 'static' | 'var' | 'int' |
'char' | 'boolean' | 'void' | 'true' | 'false' |
'null' | 'this' | 'let' | 'do' | 'if' | 'else' |
'while' | 'return’
*/
data class Keyword(
    val token: String,
    val tokenType: TokenType = TokenType.KEYWORD,
    val stringTag: String = "keyword"
): Token {
    override fun toString() = "<$stringTag> ${this.token} </$stringTag>"
}