fun main () {
    var stringer = lexemeTree (mutableListOf ("NUMBER(7)", "TIMES", "LPAREN", "NUMBER(5)", "PLUS", "NUMBER(3)", "RPAREN", "PLUS", "NUMBER(9)"))
    printEvaluatorOrder (stringer)
}

fun lexemeTree (s: MutableList<String>): lexNode.exprNode<String> {
    var header = lexNode.exprNode<String>("")
    var current = header

    if (s[0] != "LPAREN") {
        var parentNode = lexNode.exprNode<String>(s[1])
        var valNode = lexNode.numNode<String>(s[0].substring(7, s[0].indexOf(")")))
        header = parentNode
        header.setLhs(valNode)
        current = parentNode
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
                    var parentNode = current.getPar ()
                    parentNode?.setRhs (newNode)
                    newNode.setLhs (current)
                    current.setPar (newNode)
                    newNode.setPar (parentNode)
                } else {
                    newNode.setLhs (current.getRhs ())
                    current.setRhs (newNode)
                    newNode.setPar (current)
                }
                current = newNode
                if (header.parent != null) {
                    header = current
                }
            }
            "DIVIDES" -> {
                var newNode = lexNode.exprNode<String>(s[i])
                if (current.value == "POWER" || current.value == "TIMES" || current.value == "TIMES") {
                    var parentNode = current.getPar ()
                    parentNode?.setRhs (newNode)
                    newNode.setLhs (current)
                    current.setPar (newNode)
                    newNode.setPar (parentNode)
                } else {
                    newNode.setLhs (current.getRhs ())
                    current.setRhs (newNode)
                    newNode.setLhs (current.getRhs ())
                    current.setRhs (newNode)
                    newNode.setPar (current)
                }
                current = newNode
                if (header.parent != null) {
                    header = current
                }
            }
            "POWER" -> {
                var newNode = lexNode.exprNode<String>(s[i])
                if (current.value == "POWER") {
                    var parentNode = current.getPar ()
                    parentNode?.setRhs (newNode)
                    newNode.setLhs (current)
                    current.setPar (newNode)
                    newNode.setPar (parentNode)
                } else {
                    newNode.setLhs (current.getRhs ())
                    current.setRhs (newNode)
                    newNode.setPar (current)
                }
                current = newNode
                if (header.parent != null) {
                    header = current
                }
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
                for (k in 0 until recursiveList.size + 2) {
                    s[k + i] = "RPAREN"
                }
                var newNode = lexemeTree(recursiveList)
                current.setRhs(newNode)
                newNode.setPar (current)
                current = newNode
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
    }
    return header
}

fun printEvaluatorOrder (s: lexNode.exprNode<String>) {
    print (s.getLhs ()?.value)
    print (s.value)
    print (s.getRhs ()?.value)
    /*if (s.getLhs () is lexNode.numNode<String>) {
        print (s.getLhs ()?.value + " ")
        print (s.value + " ")
        if (s.getRhs () is lexNode.numNode<String>) {
            print (s.getRhs ()?.value + " ")
        } else {
            printEvaluatorOrder (s.getRhs () as lexNode.exprNode<String>)
        }
    } else {
        printEvaluatorOrder (s.getLhs () as lexNode.exprNode<String>)
    }*/
}
