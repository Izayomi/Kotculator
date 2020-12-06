fun lexemeTree(s: List<String>): lexNode.exprNode<String> {
    var header = lexNode.exprNode<String>("")
    var current = header

    if (s[0] != "LPAREN") {
        var parentNode = lexNode.exprNode<String>(s[1])
        var valNode = lexNode.numNode<String>(s[0])
        header = parentNode
        header.setLhs (valNode)
    }

    for (i in 0 until s.size) {
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
            }
            "LPAREN" -> {
                var recursiveList = mutableListOf<String>()
                loop@ for (j in i until s.size) {
                    if (s[j] != "RPAREN") {
                        recursiveList.add(s[j])
                    } else {
                        break@loop
                    }
                }
                var newNode = lexemeTree(recursiveList)
                current.setRhs(newNode)
                newNode.setPar (current)
                current = newNode
            }
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
