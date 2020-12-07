import kotlin.math.pow

class ExprEvaluatorKt {

    /* EVALUATE
     * Functionality: So you've come this far, and beleive me when I say it's all downhill from here. A recursive
     * function that goes through the tree from left to right, and performs the intended operations in that order
     * Author's Comments: idk man this is the one function I didn't write ask Devon himself what he was thinking when
     * he was performing his fancy keyboard slamming routine
     */
    fun evaluate(tree: lexNode<String>) : Double {
            if (tree is lexNode.exprNode<String>) {
                var treeLeft = tree.getLhs()
                var treeRight = tree.getRhs()
                when (tree.value) {
                    "PLUS" -> return evaluate (treeLeft as lexNode<String>) + evaluate (treeRight as lexNode<String>)
                    "MINUS" -> return evaluate (treeLeft as lexNode<String>) - evaluate (treeRight as lexNode<String>)
                    "TIMES" -> return evaluate (treeLeft as lexNode<String>) * evaluate (treeRight as lexNode<String>)
                    "DIVIDES" -> return evaluate (treeLeft as lexNode<String>) / evaluate (treeRight as lexNode<String>)
                    "POWER" -> return evaluate (treeLeft as lexNode<String>).pow (evaluate (treeRight as lexNode<String>))
                }
            }
            return tree.value.toDouble ()
        }
}