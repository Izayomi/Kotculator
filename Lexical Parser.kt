class LexParserKt {

    fun main () {
        var stringer = lexemeTree (mutableListOf ("NUMBER(4)", "PLUS", "LPAREN", "NUMBER(9)", "MINUS", "LPAREN", "NUMBER(0)", "TIMES", "NUMBER(7)", "RPAREN", "RPAREN"))
    }

    fun lexemeTree (s: MutableList<String>): lexNode.exprNode<String> {
        var header = lexNode.exprNode<String>("")
        if (s[0] != "LPAREN") {
            var parentNode = lexNode.exprNode<String>(s[1])
            var valNode =
                    when (s[0]) {
                        "PI" -> {
                            lexNode.numNode<String>(Math.PI.toString())
                        }
                        "E" -> {
                            lexNode.numNode<String>(Math.E.toString())
                        }
                        else -> {
                            lexNode.numNode<String>(s[0].substring(7, s[0].indexOf(")")))
                        }
                    }
            header = parentNode
            header.setLhs(valNode)
        } else {
            var recursiveList = recLexemeList(s, 1)
            for (k in 0 until recursiveList.size + 2) {
                s[k] = "RPAREN"
            }
            header = lexemeTree(recursiveList)
            if (recursiveList.size + 2 < s.size) {
                var opNode = lexNode.exprNode<String> (s[recursiveList.size + 2])
                var valNode = when (s[recursiveList.size + 3]) {
                    "PI" -> {
                        lexNode.numNode<String> (Math.PI.toString())
                    }
                    "E" -> {
                        lexNode.numNode<String> (Math.E.toString())
                    }
                    "LPAREN" -> {
                        var newRecList = recLexemeList(s, recursiveList.size + 4)
                        for (k in 0 until newRecList.size) {
                            s[recursiveList.size + 4 + k] = "RPAREN"
                        }
                        lexemeTree (newRecList)
                    }
                    else -> {
                        lexNode.numNode<String> (s[recursiveList.size + 3].substring (7, s[recursiveList.size + 3].indexOf (")")))
                    }
                }
                opNode.setLhs (header)
                opNode.setRhs (valNode)
                header.setPar (opNode)
                header = opNode
                s[recursiveList.size + 2] = "RPAREN"
                s[recursiveList.size + 3] = "RPAREN"
            }
        }
        return lexemeSorter (s, header)
    }

    fun lexemeSorter (s: MutableList<String>, h: lexNode.exprNode<String>): lexNode.exprNode<String> {
        var header = h
        var current = h
        for (i in 2 until s.size) {
            when (s[i]) {
                "PLUS" -> {
                    var newNode = lexNode.exprNode<String>(s[i])
                    newNode.setLhs(header)
                    header.setPar (newNode)
                    header = newNode
                    current = newNode
                }
                "MINUS" -> {
                    var newNode = lexNode.exprNode<String>(s[i])
                    newNode.setLhs(header)
                    header.setPar (newNode)
                    header = newNode
                    current = newNode
                }
                "TIMES" -> {
                    var newNode = lexNode.exprNode<String>(s[i])
                    if (current.value == "POWER" || current.value == "TIMES" || current.value == "DIVIDES") {
                        if (current.getPar () != null) {
                            var parentNode = current.getPar ()
                            parentNode?.setRhs (newNode)
                            newNode.setLhs (current)
                            current.setPar (newNode)
                            newNode.setPar (parentNode)
                        } else {
                            newNode.setLhs(header)
                            header.setPar (newNode)
                            header = newNode
                        }
                    } else {
                        newNode.setLhs (current.getRhs ())
                        current.setRhs (newNode)
                        newNode.setPar (current)
                    }
                    current = newNode
                }
                "DIVIDES" -> {
                    var newNode = lexNode.exprNode<String>(s[i])
                    if (current.value == "POWER" || current.value == "TIMES" || current.value == "DIVIDES") {
                        if (current.getPar () != null) {
                            var parentNode = current.getPar ()
                            parentNode?.setRhs (newNode)
                            newNode.setLhs (current)
                            current.setPar (newNode)
                            newNode.setPar (parentNode)
                        } else {
                            newNode.setLhs(header)
                            header.setPar (newNode)
                            header = newNode
                        }
                    } else {
                        newNode.setLhs (current.getRhs ())
                        current.setRhs (newNode)
                        newNode.setPar (current)
                    }
                    current = newNode
                }
                "POWER" -> {
                    var newNode = lexNode.exprNode<String>(s[i])
                    if (current.value == "POWER") {
                        newNode.setLhs (current.getRhs ())
                        current.setRhs (newNode)
                        newNode.setPar (current)
                    } else {
                        if (current.getPar () != null) {
                            var parentNode = current.getPar ()
                            parentNode?.setRhs (newNode)
                            newNode.setLhs (current)
                            current.setPar (newNode)
                            newNode.setPar (parentNode)
                        } else {
                            newNode.setLhs(header)
                            header.setPar (newNode)
                            header = newNode
                        }
                    }
                    current = newNode
                }
                "LPAREN" -> {
                    var recursiveList = mutableListOf<String>()
                    loop@ for (j in (i + 1) until s.size) {
                        if (s[j] != "RPAREN") {
                            recursiveList.add(s[j])
                        } else {
                            break@loop
                        }
                    }
                    for (k in 0 until recursiveList.size + 3) {
                        s[k] = "RPAREN"
                    }
                    var newNode = lexemeTree(recursiveList)
                    current.setRhs(newNode)
                    newNode.setPar (current)
                }
                "RPAREN" -> {}
                "PI" -> {
                    var newNode = lexNode.numNode<String>(Math.PI.toString())
                    current.setRhs (newNode)
                }
                "E" -> {
                    var newNode = lexNode.numNode<String>(Math.E.toString())
                    current.setRhs (newNode)
                }
                else -> {
                    var newNode = lexNode.numNode<String>(s[i].substring(7, s[i].indexOf(")")))
                    current.setRhs (newNode)
                }
            }
            //println (s)
            //println ("header: " + header.value)
            printEvaluatorOrder (header)
        }
        return header
    }

    fun printEvaluatorOrder (s: lexNode.exprNode<String>) {
        print (s.getLhs ()?.value)
        print (s.value)
        println (s.getRhs ()?.value)
    }

    fun recLexemeList (s: List<String>, j: Int): MutableList<String> {
        var r = mutableListOf<String>()
        var lParenCount = 0
        loop@ for (i in j until s.size) {
            if (s[i] == "LPAREN") {
                lParenCount++
            }
            if (s[i] == "RPAREN") {
                if (lParenCount == 0) {
                    break@loop
                } else {
                    r.add(s[i])
                    lParenCount--
                }
            } else {
                r.add(s[i])
            }
        }
        return r
    }
}
