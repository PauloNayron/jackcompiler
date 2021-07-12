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
    override fun getValue(): String = this.token

    override fun toString() = "<$stringTag> ${this.token} </$stringTag>"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Keyword

        if (token != other.token) return false

        return true
    }

    override fun hashCode(): Int {
        return token.hashCode()
    }


}