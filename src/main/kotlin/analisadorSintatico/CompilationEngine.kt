package analisadorSintatico;

interface CompilationEngine {
    /* Compila uma classe completa. */
    fun compileClass()

    /* Compila uma declaração estática ou uma declaração de campo. */
    fun compileClassVarDec()

    /* Compila um método, função ou construtor completo */
    fun compileSubroutine()

    /* Compila uma lista de parâmetros (possivelmente vazia), sem incluir o caractere "()". */
    fun compileParameterList()

    /* Compiles a var declaration. */
    fun compileVarDec()

    /* Compila uma sequência de declarações, sem incluir o delimitador ‘‘ {} ’’. */
    fun compileStatements()

    /* Compila uma instrução do. */
    fun compileDo()

    /* Compila uma instrução let. */
    fun compileLet()

    /* Compila uma instrução de retorno. */
    fun compileReturn()

    /* Compiles an if statement, possibly with a trailing else clause. */
    fun compileIf()

    /* Compila uma expressão. */
    fun compileExpression()

    /* Compila um termo.
    * Essa rotina enfrenta uma pequena dificuldade ao tentar decidir entre algumas das regras alternativas de análise.
    * Especificamente, se o token atual for um identificador, a rotina deve distinguir entre uma variável,
    * uma entrada de matriz e uma chamada de sub-rotina.
    * Um único token lookahead, que pode ser "[", "(" ou ".",
    * É suficiente para distinguir entre as três possibilidades.
    * Qualquer outro token não faz parte deste termo e não deve ser avançado sobre.
    * */
    fun compileTerm()

    /* Compiles a (possibly empty) comma-separated list of expressions. */
    fun compileExpressionList()
}
