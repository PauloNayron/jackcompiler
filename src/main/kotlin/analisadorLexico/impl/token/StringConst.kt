package analisadorLexico.impl.token

import analisadorLexico.Token

/*
* "uma sequência de caracteres Unicode entre aspas dupla"
* */
class StringConst(token: String) : Token(token) {
    override fun toString() = "<stringConst> ${this.token} </keyword>"
}