fun main () {
    var str = "pi + pi + 5 ^ (3 * 0.5)"
    str = str.replace (" ", "")
    str = str.replace ("i", "")
    var lexemeList = fillLexemeList (str)

    for (i in 0 until lexemeList.size) {
        print(lexemeList[i] + " ")
    }
}

fun fillLexemeList (str: String): List<String> {
    var newLexemeList = mutableListOf<String> ()
    var intString = ""
    for (i in 0 until str.length) {
        if ((48 <= str [i].toInt () && str [i].toInt () <= 57) || str[i].toInt() == 46) {
            intString += str[i]
        } /*else if (str[i].toInt() == 112 && str[i + 1].toInt() == 105) {
            val piString = "" + str [i] + str [i + 1]
            val newVal = lexemeChecker (piString)
            newLexemeList.add (newVal)
        } */else {
            val intVal = lexemeChecker(intString)
            val newVal = lexemeChecker(str[i].toString())
            if (intVal != "") {
                newLexemeList.add (intVal)
            }
            newLexemeList.add (newVal)
            intString = ""
        }
    }
    val intVal = lexemeChecker (intString)
    if (intVal != "") {
        newLexemeList.add (intVal)
    }

    return newLexemeList
}

fun lexemeChecker (stringer: String): String {
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
    }

    if (stringer.toDoubleOrNull () != null) {
        val num = stringer.toDouble ()
        return "NUMBER(" + num + ")"
    }

    return ""
}