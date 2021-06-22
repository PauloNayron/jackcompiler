package analisadorLexico.impl.token

import analisadorLexico.Token
/*
* uma sequência de letras, digitos e undescore ( '_' ) não iniciando com um dígito.
* */
class Identifier(token: String) : Token(token) {
    override fun toString() = "<identifier> ${this.token} </identifier>"
}