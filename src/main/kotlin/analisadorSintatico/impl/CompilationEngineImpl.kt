package analisadorSintatico.impl

import analisadorLexico.Token
import analisadorLexico.impl.JackTokenizerImpl
import analisadorLexico.impl.token.Identifier
import analisadorLexico.impl.token.Keyword
import analisadorLexico.impl.token.Symbol
import analisadorSintatico.CompilationEngine

data class CompilationEngineImpl(
    val jackTokenizer: JackTokenizerImpl,
    private var tabs: String = ""
): CompilationEngine {
    override fun compileClass() {
        printToken("<class/>")
        this.tabs += "      "

        printToken(jackTokenizer.advance().toString())
        if(jackTokenizer.currentToken?.equals(Keyword("class"))!!) {
            while (!jackTokenizer.currentToken?.equals(Symbol("{"))!!) {
                printToken(jackTokenizer.advance().toString())
            }
        } else {
            throw IllegalArgumentException("Um programa jack deve iniciar com uma classe.")
        }

        while (jackTokenizer.hasMoreTokens()) {
            val token = jackTokenizer.advance()
            if (subroutineDec(token)) this.compileSubroutine()
            else printToken(jackTokenizer.currentToken.toString())
        }

        printToken(jackTokenizer.currentToken.toString())
        this.tabs = this.tabs.replaceFirst("    ", "")
        printToken("</class>")
    }

    override fun compileClassVarDec() {
        TODO("Not yet implemented")
    }

    override fun compileSubroutine() {
        printToken("<subroutineDec>")
        this.tabs += "      "
        while (!jackTokenizer.currentToken?.equals(Symbol("}"))!!) {
            if (jackTokenizer.currentToken?.equals(Symbol("("))!!) this.compileParameterList()
            else {
                printToken(jackTokenizer.currentToken.toString())
                if (jackTokenizer.hasMoreTokens()) jackTokenizer.advance()
            }

            if (jackTokenizer.currentToken?.equals(Symbol("{"))!!) {
                printToken("<subroutineBody>")
                this.tabs += "      "
                printToken(jackTokenizer.currentToken.toString())

                while (!jackTokenizer.currentToken!!.equals(Symbol("}"))) {
                    if (jackTokenizer.currentToken!!.equals(Symbol("{"))) this.compileStatements()
                    printToken(jackTokenizer.currentToken.toString())
                    jackTokenizer.advance()
                }

                this.tabs = this.tabs.replaceFirst("    ", "")
                printToken("</subroutineBody>")
            }
        }
        this.tabs = this.tabs.replaceFirst("    ", "")
        printToken("</subroutineDec>")
    }

    override fun compileParameterList() {
        printToken(jackTokenizer.currentToken.toString())
        printToken("<parameterList>")

        jackTokenizer.advance()
        while (!jackTokenizer.currentToken?.equals(Symbol(")"))!!) {
            printToken(this.jackTokenizer.currentToken.toString())
            if (jackTokenizer.hasMoreTokens()) jackTokenizer.advance()
        }
        printToken("</parameterList>")
    }

    override fun compileVarDec() {
        TODO("Not yet implemented")
    }

    override fun compileStatements() {
        printToken("<statements>")
        this.tabs += "      "
        jackTokenizer.advance()
        while (!jackTokenizer.currentToken!!.equals(Symbol("}"))) {
            if (jackTokenizer.currentToken!!.equals(Keyword("return"))) this.compileReturn()
        }
        this.tabs = this.tabs.replaceFirst("    ", "")
        printToken("</statements>")
    }

    override fun compileDo() {
        TODO("Not yet implemented")
    }

    override fun compileLet() {
        TODO("Not yet implemented")
    }

    override fun compileReturn() {
        printToken("<returnStatement>")
        this.tabs += "      "
        printToken(jackTokenizer.currentToken.toString()) // printar o return
        jackTokenizer.advance()

        this.compileExpression()

        if(this.jackTokenizer.currentToken!!.equals(Symbol(";"))) {
            printToken(this.jackTokenizer.currentToken.toString())
            jackTokenizer.advance()
        }

        this.tabs = this.tabs.replaceFirst("      ", "")
        printToken("</returnStatement>")
    }

    override fun compileIf() {
        TODO("Not yet implemented")
    }

    override fun compileExpression() {
        printToken("<expression>")
        this.tabs += "      "

        this.compileTerm()

        this.tabs = this.tabs.replaceFirst("      ", "")
        printToken("</expression>")
    }

    override fun compileTerm() {
        printToken("<term>")
        this.tabs += "      "

        when(jackTokenizer.currentToken) {
            is Identifier -> {
                printToken(jackTokenizer.currentToken.toString())
                jackTokenizer.advance()
            }
        }

        this.tabs = this.tabs.replaceFirst("      ", "")
        printToken("</term>")
    }

    override fun compileExpressionList() {
        TODO("Not yet implemented")
    }

    private fun printToken(message: String) = println("${this.tabs}${message}")

    companion object {
        fun subroutineDec(token: Token): Boolean = token.equals(Keyword(token = "method"))
    }
}