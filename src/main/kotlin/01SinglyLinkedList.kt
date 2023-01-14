/*
We've a basic problem in ArrayList and that is its performance while inserting.
If the ArrayList is full, and we try to insert an item, we need to create a bigger array and copy all existing items
to the new one, which might take more time if the size is big.
This is a problem where LinkedList comes into play and solves the insertion problem.
 */
fun main() {
    println("Welcome to LinkedList!")
    val singlyLinkList = SinglyLinkList<String>()
    singlyLinkList.add("I")
    singlyLinkList.add("love")
    singlyLinkList.add("Kotlin!")

}

class SinglyLinkList<E> {
    private var size = 0
    private var head: Node<E>? = null
    private var tail: Node<E>? = null

    private inner class Node<E> constructor(internal var element: E, internal var next: Node<E>?)

    private fun validatePositionIndex(index: Int) {
        if (index < 0 || index > size)
            throw IndexOutOfBoundsException(outOfBoundsMsg(index))
    }

    private fun validateElementIndex(index: Int) {
        if (index < 0 || index >= size)
            throw IndexOutOfBoundsException(outOfBoundsMsg(index))
    }

    private fun outOfBoundsMsg(index: Int): String {
        return "Index: $index, Size: $size"
    }

    private fun getPrevious(node: Node<E>): Node<E> ? {
        if (head != null && node == head) return null
        var curr = head
        while (curr != null) {
            if (curr.next == node) {
                return curr
            }
            curr = curr.next
        }
        return null
    }

    private fun unlink(curr: Node<E>): E {
        val element = curr.element
        val next = curr.next
        val prev = getPrevious(curr)
        if (prev == null) {
            head = next
        } else {
            prev.next = next
            curr.next = null
        }
        if (next == null) {
            prev?.next = null
            tail = prev
        } else {
            prev?.next = next
            curr.next = null
        }
        size--
        return element
    }

    fun add(element: E) {
        val h = head
        val newNode = Node(element, h)
        head = newNode
        if (h == null) {
            tail = newNode
        }
        size++
    }

    fun add(index: Int, element: E) {
        validatePositionIndex(index)
        if (index == 0) add(element)
        else {
            var x = head
            val prevIndex = index - 1
            for (i in 0 until prevIndex) {
                x = x!!.next
            }
            val next = x!!.next
            val newNode = Node(element, next)
            x.next = newNode
            size++
        }
    }

    fun addLast(element: E) {
        val t = tail
        val newNode = Node(element, null)
        tail = newNode
        if (t == null) {
            head = newNode
        } else {
            t.next = newNode
        }
        size++
    }

    fun getFirst() = head?.element

    fun getLast() = tail?.element

    fun get(index: Int): E {
        validateElementIndex(index)
        var x = head
        for (i in 0 until index) {
            x = x!!.next
        }
        return x!!.element
    }

    fun set(index: Int, element: E): E {
        validateElementIndex(index)
        var x = head
        for (i in 0 until index) {
            x = x!!.next
        }
        val oldVal = x!!.element
        x.element = element
        return oldVal
    }

    fun removeFirst() {
        head?.let {
            val next = it.next
            it.next = null
            head = next
            if (next == null) {
                tail = null
            }
            size--
        }
    }

    fun removeLast() {
        tail?.let {
            val prev = getPrevious(it)
            tail = prev
            if (prev == null) {
                head = null
            } else {
                prev.next = null
            }
            size--
        }
    }

    fun remove(element: E): Boolean {
        var curr = head
        while (curr != null) {
            if (curr.element == element) {
                unlink(curr)
                return true
            }
            curr = curr.next
        }
        return false
    }

    fun remove(index: Int): E {
        validateElementIndex(index)
        var x = head
        for (i in 0 until index) {
            x = x!!.next
        }
        return unlink(x!!)
    }

    fun clear() {
        var x = head
        while (x != null) {
            val next = x.next
            x.next = null
            x = next
        }
        tail = null
        head = tail
        size = 0
    }

    fun indexOf(element: E): Int {
        var index = 0
        var x = head
        while (x != null) {
            if (element == x.element)
                return index
            index++
            x = x.next
        }
        return -1
    }

    operator fun contains(element: E) = indexOf(element) != -1

}