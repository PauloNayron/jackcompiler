package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/*
* "uma sequência de caracteres Unicode entre aspas dupla"
* */
data class StringConst(
    val token: String,
    val linha: Int = 0,
    val tokenType: TokenType = TokenType.STRING_CONST,
    val stringTag: String = "stringConstant"
): Token {
    override fun getValue(): String = this.token
    override fun getLine(): Int = this.linha

    fun token() = this.token.replace("\"", "")
    override fun toString() = "<$stringTag> ${this.token()} </$stringTag>"
}