class exprNode<T> (value: T): lexNode<T> (value) {
    var left: lexNode<T>? = null
    var right: lexNode<T>? = null

    fun setLhs (newVal: lexNode<T>) {
        this.left = newVal
    }

    fun getLhs (): lexNode<T>? {
        if (this.left != null) {
            return this.left
        }

        else {
            return this
        }
    }

    fun setRhs (newVal: lexNode<T>) {
        this.right = newVal
    }

    fun getRhs (): lexNode<T>? {
        if (this.right != null) {
            return this.right
        }

        else {
            return this
        }
    }
}