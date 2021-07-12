package analisadorSintatico.impl

import analisadorLexico.Token
import analisadorLexico.impl.JackTokenizerImpl
import analisadorLexico.impl.token.Identifier
import analisadorLexico.impl.token.IntConst
import analisadorSintatico.CompilationEngine
import analisadorSintatico.enums.LexicalElements
import kotlin.concurrent.thread

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

        if (jackTokenizer.currentToken is Identifier) tokenConsumer()
        else throw IllegalArgumentException("É espera um token Identifier, porém foi passado ${jackTokenizer.currentToken}")

        tokenConsumer("{")

        if (this.isClassVarDec()) this.compileClassVarDec()

        if (this.isSubroutineDec()) this.compileSubroutine() else throw IllegalArgumentException("esperado token de subroutine")

        tokenConsumer("}")

        closeTag("class")
    }

    /**
     * ('static'|'field') type varName (',' varName)*
     * */
    override fun compileClassVarDec() {
        openTag("classVarDec")

        // TODO - verificar implementação

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

        if (jackTokenizer.currentToken is Identifier) tokenConsumer() // subroutineName

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
            if (jackTokenizer.currentToken is Identifier) {
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

    override fun compileVarDec() {
        TODO("Not yet implemented")
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

    override fun compileDo() {
        TODO("Not yet implemented")
    }

    override fun compileLet() {
        TODO("Not yet implemented")
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


    override fun compileIf() {
        TODO("Not yet implemented")
    }

    override fun compileWhile() {
        TODO("Not yet implemented")
    }

    /**
     * term (op term)*
     * */
    override fun compileExpression() {
        openTag("expression")

        this.compileTerm()

        if(isOp()) {
            tokenConsumer()
            this.compileTerm()
        }

        closeTag("expression")
    }


    override fun compileTerm() {
        openTag("term")
        if (isTerm()) tokenConsumer() else throw IllegalArgumentException("esperado um Term, porém foi passado: ${jackTokenizer.currentToken}")
        closeTag("term")
    }

    override fun compileExpressionList() {
        TODO("Not yet implemented")
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

    private fun isType(): Boolean = LexicalElements.TYPE.elements.contains(jackTokenizer.currentToken?.getValue()) ||
                jackTokenizer.currentToken is Identifier

    private fun isStatement(): Boolean = LexicalElements.STATEMENT.elements.contains(jackTokenizer.currentToken?.getValue())

    private fun isTerm(): Boolean = jackTokenizer.currentToken is Identifier || jackTokenizer.currentToken is IntConst

    private fun isOp(): Boolean = LexicalElements.OP.elements.contains(jackTokenizer.currentToken?.getValue())

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
