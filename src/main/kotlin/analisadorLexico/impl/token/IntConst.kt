package analisadorLexico.impl.token

import analisadorLexico.Token

/*
* um número decimal inteiro
* */
class IntConst(token: String) : Token(token) {
    override fun toString() = "<intConst> ${this.token} </intConst>"
}