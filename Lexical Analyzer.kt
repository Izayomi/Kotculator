fun main () {
    print ("Please enter a mathematical equation: ")
    var str = readLine ().toString()

    str = inputStringModifier (str)
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
        } else {
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

fun inputStringModifier (stringer: String): String {
    var str = stringer;
    str = str.replace (" ", "")
    str = str.replace ("pi", "p")
    return str
}
