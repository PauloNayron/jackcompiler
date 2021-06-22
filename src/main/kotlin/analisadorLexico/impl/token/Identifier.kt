package analisadorLexico.impl.token

import analisadorLexico.Token
/*
* uma sequência de letras, digitos e undescore ( '_' ) não iniciando com um dígito.
* */
data class Identifier(val token: String) : Token {
    override fun toString() = "<identifier> ${this.token} </identifier>"
}