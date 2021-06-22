package analisadorLexico.impl.token

import analisadorLexico.Token

/*
* "uma sequência de caracteres Unicode entre aspas dupla"
* */
data class StringConst(
    val token: String
): Token {

    fun token() = this.token.replace("\"", "")

    override fun toString() = "<stringConstant> ${this.token()} </stringConstant>"
}