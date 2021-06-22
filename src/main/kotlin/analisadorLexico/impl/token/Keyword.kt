package analisadorLexico.impl.token

import analisadorLexico.Token

/**
'class' | 'constructor' | 'function' |
'method' | 'field' | 'static' | 'var' | 'int' |
'char' | 'boolean' | 'void' | 'true' | 'false' |
'null' | 'this' | 'let' | 'do' | 'if' | 'else' |
'while' | 'return’
*/
class Keyword(token: String) : Token(token) {
    override fun toString() = "<keyword> ${this.token} </keyword>"
}