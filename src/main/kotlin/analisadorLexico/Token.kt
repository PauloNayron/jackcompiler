package analisadorLexico

interface Token {
    fun getValue(): String

    fun getLine(): Int
}