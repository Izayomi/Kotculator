open class lexNode<T>(value: T) {

    var value: T = value
    var parent: exprNode<T>? = null

    class numNode<double> (value: double): lexNode<double> (value) {

    }

    class exprNode<T> (value: T): lexNode<T> (value) {
        var left: lexNode<T>? = null
        var right: lexNode<T>? = null

        fun setLhs (newVal: lexNode<T>?) {
            this.left = newVal
        }

        fun getLhs (): lexNode<T>? {
            return if (this.left != null) {
                this.left
            } else {
                null
            }
        }

        fun setRhs (newVal: lexNode<T>?) {
            this.right = newVal
        }

        fun getRhs (): lexNode<T>? {
            return if (this.right != null) {
                this.right
            } else {
                null
            }
        }

        fun getPar (): exprNode<T>? {
            return if (this.parent != null) {
                this.parent
            } else {
                null
            }
        }

        fun setPar (newVal: exprNode<T>?) {
            this.parent = newVal
        }
    }
}
