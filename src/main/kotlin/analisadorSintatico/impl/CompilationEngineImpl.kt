package analisadorSintatico.impl

import analisadorLexico.Token
import analisadorLexico.impl.JackTokenizerImpl
import analisadorLexico.impl.token.Identifier
import analisadorLexico.impl.token.IntConst
import analisadorLexico.impl.token.StringConst
import analisadorSintatico.CompilationEngine
import analisadorSintatico.enums.LexicalElements

data class CompilationEngineImpl(
    val jackTokenizer: JackTokenizerImpl
): CompilationEngine {

    /**
     *  'class' className '{' classVarDec* subroutineDec '}'
     *  */
    override fun compileClass() {
        openTag("class")
        jackTokenizer.advance()
        tokenConsumer("class")
        this.compileVarName() // className
        tokenConsumer("{")
        if (this.isClassVarDec()) this.compileClassVarDec()
        this.compileSubroutine()
        tokenConsumer("}")

        closeTag("class")
    }

    /**
     * ('static'|'field') type varName (',' varName)* ';'
     * */
    override fun compileClassVarDec() {
        openTag("classVarDec")
        if (isClassVarDec()) tokenConsumer() else IllegalArgumentException("Esperado 'static'|'field', porém foi passado: ${jackTokenizer.currentToken}")
        compileType()
        compileVarName()
        while (jackTokenizer.currentToken?.getValue().equals(",")) {
            tokenConsumer(",")
            compileVarName()
        }
        tokenConsumer(";")
        closeTag("classVarDec")
    }

    /**
     * ('construtor'|'function'|'method') ('void'|type) subroutineName
     * '(' parameterList ')' subroutineBody
     * */
    override fun compileSubroutine() {
        openTag("subroutineDec")
        if (isSubroutineDec()) tokenConsumer() // ('construtor'|'function'|'method')

        // ('void'|type)
        if (jackTokenizer.currentToken?.getValue().equals("void") || isType()) tokenConsumer()
        else throw IllegalArgumentException("É esperado um type, porém foi passado: ${jackTokenizer.currentToken}")

        if (isVarName()) tokenConsumer() // subroutineName

        tokenConsumer("(") // '('
        this.compileParameterList()
        tokenConsumer(")") // ')'

        /** subroutineBody
         * '{' varDec* statements '}'
         * **/
        openTag("subroutineBody")
        tokenConsumer("{") // '{'
        if (jackTokenizer.currentToken?.getValue().equals("var")) this.compileVarDec() // varDec*
        this.compileStatements()
        tokenConsumer("}") // '}'
        closeTag("subroutineBody")

        closeTag("subroutineDec")
    }

    /**
     * ((type varName) (',' varName)*)?
     * */
    override fun compileParameterList() {
        openTag("parameterList")
        while (isType() && !jackTokenizer.currentToken?.getValue().equals(")")) {
            tokenConsumer()
            if (isVarName()) {
                tokenConsumer()
                if (jackTokenizer.currentToken?.getValue().equals(",")) {
                    tokenConsumer()
                }
            } else {
                throw IllegalArgumentException("É esperado um varName, pórem foi passado: ${jackTokenizer.currentToken}")
            }
        }
        closeTag("parameterList")
    }

    /**
     * 'var' type varName (',' varName)* ';'
     * */
    override fun compileVarDec() {
        openTag("varDec")
        tokenConsumer("var")
        this.compileType()
        this.compileVarName()
        while (jackTokenizer.currentToken?.getValue().equals(",")) {
            tokenConsumer(",")
            this.compileVarName()
        }
        tokenConsumer(";")
        closeTag("varDec")
    }

    /**
     * statement*
     * */
    override fun compileStatements() {
        openTag("statements")
        while (isStatement()) {
            when (jackTokenizer.currentToken?.getValue()) {
                "let" -> this.compileLet()
                "if" -> this.compileIf()
                "while" -> this.compileWhile()
                "do" -> this.compileDo()
                "return" -> this.compileReturn()
            }
        }
        closeTag("statements")
    }

    /**
     * 'do' subroutineCall ';'
     * */
    override fun compileDo() {
        openTag("doStatement")
        tokenConsumer("do")
        this.compileSubroutineCall()
        tokenConsumer(";")
        closeTag("doStatement")
    }

    /**
     * 'let' varName ('[' expression ']')? '=' expression ';'
     * */
    override fun compileLet() {
        openTag("letStatement")
        this.tokenConsumer("let")

        if (this.isVarName()) tokenConsumer()
        else throw IllegalArgumentException("Esperado um varName, porém foi passdo: ${jackTokenizer.currentToken}")

        if (jackTokenizer.currentToken?.getValue().equals("[")) {
            tokenConsumer("[")
            this.compileExpression()
            tokenConsumer("]")
        }

        tokenConsumer("=")
        this.compileExpression()
        tokenConsumer(";")

        closeTag("letStatement")
    }

    /**
     * 'return' expression? ';'
     * */
    override fun compileReturn() {
        openTag("returnStatement")
        tokenConsumer("return") // 'return'

        if (!jackTokenizer.currentToken?.getValue().equals(";")) this.compileExpression() // expression?

        tokenConsumer(";")
        closeTag("returnStatement")
    }

    /**
     * 'if' '(' expression ')' '{' statements '}' ('else' '{' statements '}')?
     * */
    override fun compileIf() {
        openTag("ifStatement")
        tokenConsumer("if")
        tokenConsumer("(")
        this.compileExpression()
        tokenConsumer(")")
        tokenConsumer("{")
        this.compileStatements()
        tokenConsumer("}")
        if (jackTokenizer.currentToken?.getValue().equals("else")) {
            tokenConsumer("else")
            tokenConsumer("{")
            this.compileStatements()
            tokenConsumer("}")
        }
        closeTag("ifStatement")
    }

    /**
     * 'while' '(' expression ')' '{' statements '}'
     * */
    override fun compileWhile() {
        openTag("whileStatement")
        tokenConsumer("while")
        tokenConsumer("(")
        this.compileExpression()
        tokenConsumer(")")
        tokenConsumer("{")
        this.compileStatements()
        tokenConsumer("}")
        closeTag("whileStatement")
    }

    /**
     * term (op term)*
     * */
    override fun compileExpression() {
        openTag("expression")

        this.compileTerm()

        while(isOp()) {
            tokenConsumer()
            this.compileTerm()
        }

        closeTag("expression")
    }

    override fun compileTerm() {
        openTag("term")
        if (isTerm()) tokenConsumer() // integerConstant | stringConstant | keywordConstant
        else if (isVarName()) { // varName | varName '[' expression ']'
            compileVarName()
            if (jackTokenizer.currentToken?.getValue().equals("[")) { // '[' expression ']'
                tokenConsumer("[")
                this.compileExpressionList()
                tokenConsumer("]")
            }
        } else if (jackTokenizer.currentToken?.getValue().equals("(")) { // '(' expression ')'
            tokenConsumer("(")
            this.compileExpression()
            tokenConsumer(")")
        } else if (isUnaryOp()) { // unaryOp term
            tokenConsumer()
            this.compileTerm()
        }
        else throw IllegalArgumentException("esperado um Term, porém foi passado: ${jackTokenizer.currentToken}")
        closeTag("term")
    }

    /**
     * (expression (',' expression)* )?
     * */
    override fun compileExpressionList() {
        openTag("expressionList")
        while (isTerm() || jackTokenizer.currentToken is Identifier) {
            this.compileExpression()
            while (jackTokenizer.currentToken?.getValue().equals(",")) {
                tokenConsumer(",")
                this.compileExpression()
            }
        }
        closeTag("expressionList")
    }

    private fun compileVarName() {
        if (isVarName()) tokenConsumer() else IllegalArgumentException("Esperado token do tipo varName, porém foi passado: ${jackTokenizer.currentToken}")
    }

    private fun compileType() {
        if (isType()) tokenConsumer() else IllegalArgumentException("Esperado token do tipo type, porém foi passado: ${jackTokenizer.currentToken}")
    }

    /**
     * subroutineName '(' expressionList ')' |
     * ( className | varName ) '.' subroutineName '(' expressionList ')'
     * */
    private fun compileSubroutineCall() {
        if (isVarName()) {
            compileVarName()
            tokenConsumer(".")
            compileVarName()
            tokenConsumer("(")
            this.compileExpressionList()
            tokenConsumer(")")
        }
    }

    private fun tokenConsumer(token: String) {
        if (jackTokenizer.currentToken?.getValue().equals(token)) {
            printToken(jackTokenizer.currentToken)
            try {
                jackTokenizer.advance()
            } catch (ex: java.lang.IllegalArgumentException) {
                if (!token.equals("}") && !jackTokenizer.hasMoreTokens()) {
                    println(ex)
                }
            }
        } else {
            throw IllegalArgumentException("foi esperado ${token}, porém foi passado ${jackTokenizer.currentToken}")
        }
    }

    private fun tokenConsumer() {
        printToken(jackTokenizer.currentToken)
        jackTokenizer.advance()
    }

    private fun isClassVarDec(): Boolean = LexicalElements.CLASS_VAR_DEC.elements
        .contains(jackTokenizer.currentToken?.getValue())

    private fun isSubroutineDec(): Boolean = LexicalElements.SUBROUTINE_DEC.elements
        .contains(jackTokenizer.currentToken?.getValue())

    private fun isType(): Boolean = LexicalElements.TYPE.elements
        .contains(jackTokenizer.currentToken?.getValue()) || isVarName()

    private fun isStatement(): Boolean = LexicalElements.STATEMENT.elements
        .contains(jackTokenizer.currentToken?.getValue())

    private fun isTerm(): Boolean = jackTokenizer.currentToken is IntConst ||
            jackTokenizer.currentToken is StringConst ||
            LexicalElements.KEYWORD_CONSTANT.elements.contains(jackTokenizer.currentToken?.getValue())

    private fun isVarName() = jackTokenizer.currentToken is Identifier

    private fun isOp(): Boolean = LexicalElements.OP.elements
        .contains(jackTokenizer.currentToken?.getValue())

    private fun isUnaryOp() = LexicalElements.UNARY_OP.elements
        .contains(jackTokenizer.currentToken?.getValue())

    companion object {
        private var tab = ""

        private fun openTag(tag: String) {
            println("${tab}<${tag}>")
            tab += "    "
        }

        private fun closeTag(tag: String) {
            tab = tab.replaceFirst("    ", "")
            println("${tab}</${tag}>")
        }

        private fun printToken(token: Token?) = println("${tab}${token}")
    }
}
