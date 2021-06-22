package analisadorLexico.impl.token

import analisadorLexico.Token

/**
'class' | 'constructor' | 'function' |
'method' | 'field' | 'static' | 'var' | 'int' |
'char' | 'boolean' | 'void' | 'true' | 'false' |
'null' | 'this' | 'let' | 'do' | 'if' | 'else' |
'while' | 'return’
*/
data class Keyword(val token: String) : Token {
    override fun toString() = "<keyword> ${this.token} </keyword>"
}