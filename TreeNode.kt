open class lexNode<T>(value:T){
    var value:T = value
    var parent: lexNode<T>? = null
    var left: lexNode<T>? = null
    var right: lexNode<T>? = null
}

class exprNode<String>(value:String): lexNode<String>(value) {

    fun setLhs (newVal: lexNode<String>?) {
        this.left = newVal
    }

    fun getLhs (): lexNode<String>? {
        if (this.left != null) {
            return this.left
        }

        else {
            return this
        }
    }

    fun setRhs (newVal: lexNode<String>?) {
        this.right = newVal
    }

    fun getRhs (): lexNode<String>? {
        if (this.right != null) {
            return this.right
        }

        else {
            return this
        }
    }
}