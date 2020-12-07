class LexAnalyzerKt {

    /* FILL LEXEME LIST
     * Functionality: The meat and potatoes of the lexical analyzer. Takes in the user's input string, and calls on the
     * other class functions to convert the symbols into their corresponding lexical representations
     * Author's Comments: Hmm yes I love looking up ASCII values very nice *chefs kiss*
     */

    fun fillLexemeList(str: String): List<String> {
        var newLexemeList = mutableListOf<String>()
        var intString = ""
        for (i in 0 until str.length) {
            if ((48 <= str[i].toInt() && str[i].toInt() <= 57) || str[i].toInt() == 46) {
                intString += str[i]
            } else {
                val intVal = lexemeChecker(intString)
                val newVal = lexemeChecker(str[i].toString())
                if (intVal != "") {
                    newLexemeList.add(intVal)
                }
                newLexemeList.add(newVal)
                intString = ""
            }
        }
        val intVal = lexemeChecker(intString)
        if (intVal != "") {
            newLexemeList.add(intVal)
        }

        return newLexemeList
    }

    /* LEXEME CHECKER
     * Functionality: Everyone freshman bio student knows that the lexeme checker is the powerhouse of the analysis
     * class. Takes in parts of the string and converts them into the appropriate lexical format
     * Author's Comments: This was the start of my bad habit of forgetting pi and e were values we were mandated to
     * implement
     */
    fun lexemeChecker(stringer: String): String {
        when (stringer) {
            "+" -> return "PLUS"
            "-" -> return "MINUS"
            "*" -> return "TIMES"
            "/" -> return "DIVIDES"
            "^" -> return "POWER"
            "(" -> return "LPAREN"
            ")" -> return "RPAREN"
            "p" -> return "PI"
            "e" -> return "E"
            "" -> return ""
        }

        if (stringer.toDoubleOrNull() != null) {
            val num = stringer.toDouble()
            return "NUMBER(" + num + ")"
        }

        throw IllegalArgumentException("Invalid syntax: $stringer is not a valid lexeme")
    }

    /* LEXEME ANALYSIS
     * Functionality: Before we proceed to the parser, we gotta check to make sure our list of lexemes make mathematical
     * sense. After all, we don't want to pass in a broken function and unknowingly activate the malware hidden within
     * this code, would we?
     * Author's Comments: I'm only joshing you, there's no malware lurking in the file. Probably
     */
    fun lexicalAnalysis(lexemeList: List<String>) {
        var lParenCount = 0
        var rParenCount = 0

        //printList(lexemeList)

        if (!(lexemeList.first().startsWith("NUM")) && lexemeList.first() != "LPAREN" && lexemeList.first() != "PI"
                && lexemeList.first() != "E") {
            throw ArithmeticException("Invalid syntax: cannot have an operator or rparen at the beginning of the" +
                    " expression")
        }

        if (!(lexemeList.last().startsWith("NUM")) && lexemeList.last() != "RPAREN" && lexemeList.last() != "PI"
                && lexemeList.last() != "E") {
            throw ArithmeticException("Invalid syntax: cannot have an operator or lparen at the end of the expression")
        }

        if (lexemeList.last() == "RPAREN") {
            rParenCount++
        }

        for (i in 0 until lexemeList.size - 1) {
            when (lexemeList[i]) {
                "PLUS", "MINUS", "TIMES", "POWER" ->
                    if (!lexemeList[i + 1].startsWith("NUM") && lexemeList[i + 1] != "LPAREN"
                            && lexemeList[i + 1] != "PI" && lexemeList[i + 1] != "E") {
                        throw ArithmeticException("Invalid syntax: operator must be followed by a number or lparen")
                    }
                "DIVIDES" ->
                    if (!lexemeList[i + 1].startsWith("NUM") && lexemeList[i + 1] != "LPAREN"
                            && lexemeList[i + 1] != "PI" && lexemeList[i + 1] != "E") {
                        throw ArithmeticException("Invalid syntax: operator must be followed by a number or lparen")
                    } else if (lexemeList[i + 1] == "NUMBER(0.0)") {
                        throw ArithmeticException("Invalid syntax: cannot divide by zero")
                    }
                "LPAREN" ->
                    if ((!lexemeList[i + 1].startsWith("NUM") && lexemeList[i + 1] != "PI"
                                    && lexemeList[i + 1] != "E" && lexemeList[i + 1] != "LPAREN")
                            || lexemeList[i + 1] == "RPAREN") {
                        throw ArithmeticException("Invalid syntax: lparen must be followed by a numeric  value or" +
                                "another lparen")
                    } else if ((lexemeList[i + 1].startsWith("NUM") || lexemeList[i + 1] != "E"
                                    || lexemeList[i + 1] == "E") && lexemeList[i + 2] == "RPAREN") {
                        throw ArithmeticException("Invalid syntax: nesting a single number in parentheses is obsolete")
                    } else {
                        lParenCount++
                    }
                "RPAREN" -> if (lexemeList[i + 1].startsWith("NUM") || lexemeList[i + 1] == "PI"
                        || lexemeList[i + 1] == "E" || lexemeList[i + 1] == "LPAREN") {
                    throw ArithmeticException("Invalid syntax: rparen must be followed by an operator or another " +
                            "rparen")
                } else {
                    rParenCount++
                }
                "PI", "E" ->
                    if (lexemeList[i + 1].startsWith("NUM") || lexemeList[i + 1] == "PI"
                            || lexemeList[i + 1] == "E" || lexemeList[i + 1] == "LPAREN") {
                        throw ArithmeticException("Invalid syntax: numeric elements must be followed " +
                                "with an operator or rparen")
                    }
                else ->
                    if (lexemeList[i + 1].startsWith("NUM") || lexemeList[i + 1] == "PI"
                            || lexemeList[i + 1] == "E" || lexemeList[i + 1] == "LPAREN") {
                        throw ArithmeticException("Invalid syntax: numeric elements must be followed " +
                                "with an operator or rparen")
                    }
            }
        }

        if (lParenCount != rParenCount) {
            throw ArithmeticException("Invalid syntax: parentheses do not match")
        }
    }

    /* INPUT STRING MODIFIER
     * Functionality: For tackling those odd strings which might make the compiler keel over and die. Basically, all
     * spaces are removed and all "pi" strings shorten to just "p"
     * Author's Comments: Gonna be honest I almost glanced over this function, probably due to the analyzer's massive
     * bulk goddamn why is that function so beefy and ugly?
     */
    fun inputStringModifier(stringer: String): String {
        var str = stringer;
        str = str.replace(" ", "")
        str = str.replace("pi", "p")
        return str
    }
}
