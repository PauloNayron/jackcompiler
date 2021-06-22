package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/*
* uma sequência de letras, digitos e undescore ( '_' ) não iniciando com um dígito.
* */
data class Identifier(
    val token: String,
    val tokenType: TokenType = TokenType.IDENTIFIER,
    val stringTag: String = "identifier"
): Token {
    override fun toString() = "<$stringTag> ${this.token} </$stringTag>"
}