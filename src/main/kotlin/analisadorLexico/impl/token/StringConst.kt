package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/*
* "uma sequência de caracteres Unicode entre aspas dupla"
* */
data class StringConst(
    val token: String,
    val tokenType: TokenType = TokenType.STRING_CONST,
    val stringTag: String = "stringConstant"
): Token {
    fun token() = this.token.replace("\"", "")
    override fun toString() = "<$stringTag> ${this.token()} </$stringTag>"
}