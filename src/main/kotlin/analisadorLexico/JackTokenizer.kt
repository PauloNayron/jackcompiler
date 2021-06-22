package analisadorLexico

import analisadorLexico.enums.TokenType

interface JackTokenizer {

    /*
    * Retorna verdadeiro caso exista mais tokens
    * */
    fun hasMoreTokens() : Boolean

    /*
    * Avança para o próximo token, atualizando assim a variável tokenCorrente.
    * Esse método só pode ser executado caso hasMoreTokens retornar verdadeiro.
    * */
    fun advance(): Token

    /*
    * Retorna o tipo do token corrente (keyword,symbol, identifier ... )
    * Usualmente implementado por um enumerate em linguagens estáticas
    * */
    fun tokenType(): TokenType
}