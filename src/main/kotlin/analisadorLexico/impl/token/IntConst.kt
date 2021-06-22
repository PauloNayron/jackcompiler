package analisadorLexico.impl.token

import analisadorLexico.Token

/*
* um número decimal inteiro
* */
data class IntConst(val token: String) : Token {
    override fun toString() = "<integerConstant> ${this.token} </integerConstant>"
}