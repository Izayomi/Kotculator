fun lexemeTree(s: List<String>): lexNode.exprNode<String> {
    var header = lexNode.exprNode<String>("")
    var current = header
    for (i in 0 until s.size) {
        when (s[i]) {
            "PLUS" -> {
                var newNode = lexNode.exprNode<String>(s[i])
                newNode.setLhs(header)
                header = newNode
                current = newNode
            }
            "MINUS" -> {
                var newNode = lexNode.exprNode<String>(s[i])
                newNode.setLhs(header)
                header = newNode
                current = newNode
            }
            "TIMES" -> {
                var newNode = lexNode.exprNode<String>(s[i])
                if (current.value != "POWER") {
                    newNode.setLhs (current.getRhs ())
                    current.setRhs (newNode)
                } else {
                    var parentNode = current.getPar ()
                    newNode.setLhs (current)
                    newNode.setPar (parentNode)
                    parentNode.setRhs (newNode)
                }
                current = newNode
            }
            "DIVIDES" -> {
                var newNode = lexNode.exprNode<String>(s[i])
                if (current.value != "POWER") {
                    newNode.setLhs (current.getRhs ())
                    current.setRhs (newNode)
                } else {
                    var parentNode = current.getPar ()
                    newNode.setLhs (current)
                    newNode.setPar (parentNode)
                    parentNode.setRhs (newNode)
                }
                current = newNode
            }
            "POWER" -> {
                var newNode = lexNode.exprNode<String>(s[i])
                newNode.setLhs (current.getRhs ())
                current.setRhs (newNode)
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
