package analisadorLexico.impl.token

import analisadorLexico.Token
import analisadorLexico.enums.TokenType

/*
* um número decimal inteiro
* */
data class IntConst(
    val token: String,
    val tokenType: TokenType = TokenType.INT_CONST,
    val stringTag: String = "integerConstant"
): Token {
    override fun getValue(): String = this.token

    override fun toString() = "<$stringTag> ${this.token} </$stringTag>"
}