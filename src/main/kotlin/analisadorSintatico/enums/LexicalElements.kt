package analisadorSintatico.enums

enum class LexicalElements (val elements: Array<String>) {
    CLASS_VAR_DEC(arrayOf("static", "field")),
    SUBROUTINE_DEC(arrayOf("constructor", "function", "method")),
    TYPE(arrayOf("int", "char", "boolean")),
    STATEMENT(arrayOf("let", "if", "while", "do", "return")),
    OP(arrayOf("+", "-", "*", "/", "&", "|", "<", ">", "="))
}