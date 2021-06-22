package analisadorLexico.enums

enum class TokenType(val description: String, val regex: Regex) {
    KEYWORD("keyword", Regex("class|constructor|function|method|field|static|var|int|char|boolean|void|true|false|null|this|let|do|if|else|while|return")),
    SYMBOL("symbol", Regex("[{]|[}]|[(]|[)]|[\\[]|[]]|[. ]|[, ]|[; ]|[+]|[-]|[*]|[/]|[&]|[|]|[<]|[>]|[=]")),
    INDENTIFIER("identifier", Regex("")),
    INT_CONST("intConst", Regex("[0-9][0-9]*")),
    STRING_CONST("stringConst", Regex("[\"][aA-zZ_][aA0-zZ9_]*[ ]*[aA0-zZ9_]*[\"]"));
}