package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/*
* uma sequência de letras, digitos e undescore ( '_' ) não iniciando com um dígito.
* */
data class Identifier(
    val token: String,
    val linha: Int = 0,
    val tokenType: TokenType = TokenType.IDENTIFIER,
    val stringTag: String = "identifier"
): Token {
    override fun getValue(): String = this.token
    override fun getLine(): Int = this.linha

    override fun toString() = "<$stringTag> ${this.token} </$stringTag>"
}