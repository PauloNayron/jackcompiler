package analisadorLexico.enums

import analisadorLexico.Token
import analisadorLexico.impl.token.*

enum class TokenType(val regex: Regex) {
    KEYWORD(Regex("class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return")),
    SYMBOL(Regex("[{]|[}]|[(]|[)]|[\\[]|[]]|[. ]|[, ]|[; ]|[+]|[-]|[*]|[/]|[&]|[|]|[<]|[>]|[=]|[~]")),
    IDENTIFIER(Regex("[aA-zZ_][aA0-zZ9_]*")),
    INT_CONST(Regex("[0-9][0-9]*")),
    STRING_CONST(Regex("[\"][aA-zZ_][aA0-zZ9_]*[ ]*[aA0-zZ9_]*[\"]"));

    fun getToken(currentToken: String, line: Int = 0): Token = when (this) {
        KEYWORD -> Keyword(currentToken, line)
        SYMBOL -> Symbol(currentToken, line)
        IDENTIFIER -> Identifier(currentToken, line)
        INT_CONST -> IntConst(currentToken, line)
        STRING_CONST -> StringConst(currentToken, line)
    }
}