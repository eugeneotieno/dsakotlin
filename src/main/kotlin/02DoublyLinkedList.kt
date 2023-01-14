/*
In many operations operated on a Singly Linked List, we've observed that, to get a previous node of any particular node,
we need to traverse from the head of the LinkedList. This is a performance hit. Of course, we can ignore it if the
LinkedList is small but, for Linked Lists that are big, the time taken to get the previous node is larger.
To avoid this performance problem, we can simply store the previous node reference of every node in the same way we
store the next node reference.
*/
fun main() {
    println("Welcome to LinkedList!")
    val doublyLinkedList = DoublyLinkList<String>()
    doublyLinkedList.add("I")
    doublyLinkedList.add("love")
    doublyLinkedList.add("Kotlin!")
}

class DoublyLinkList<E> {
    private var size = 0
    private var head: Node<E>? = null
    private var tail: Node<E>? = null

    private inner class Node<E> constructor(internal var prev: Node<E>?,
                                            internal var element: E,
                                            internal var next: Node<E>?)

    fun getFirst() = head?.element

    fun getLast() = tail?.element

    fun removeFirst() = unlinkHead()

    fun removeLast() = unlinkTail()

    fun addFirst(element: E) {
        linkHead(element)
    }

    fun addLast(element: E) {
        linkTail(element)
    }

    fun add(element: E) {
        linkTail(element)
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

    fun clear() {
        var x = head
        while (x != null) {
            val next = x.next
            x.next = null
            x.prev = null
            x = next
        }
        tail = null
        head = tail
        size = 0
    }

    fun size() = size

    operator fun contains(element: E) = indexOf(element) != -1

    fun get(index: Int): E {
        validateElementIndex(index)
        return node(index).element
    }

    fun set(index: Int, element: E): E {
        validateElementIndex(index)
        val x = node(index)
        val oldVal = x.element
        x.element = element
        return oldVal
    }

    fun add(index: Int, element: E) {
        validatePositionIndex(index)
        if (index == size) {
            linkTail(element)
        } else {
            linkBefore(element, node(index))
        }
    }

    fun remove(index: Int): E {
        validateElementIndex(index)
        return unlink(node(index))
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


    private fun linkHead(element: E) {
        val h = head
        val newNode = Node(null, element, h)
        head = newNode
        if (h == null) {
            tail = newNode
        } else {
            h.prev = newNode
        }
        size++
    }

    private fun linkTail(element: E) {
        val t = tail
        val newNode = Node(t, element, null)
        tail = newNode
        if (t == null) {
            head = newNode
        } else {
            t.next = newNode
        }
        size++
    }

    private fun linkBefore(element: E, succ: Node<E>) {
        val pred = succ.prev
        val newNode = Node(pred, element, succ)
        succ.prev = newNode
        if (pred == null) {
            head = newNode
        } else {
            pred.next = newNode
        }
        size++
    }

    private fun unlinkHead() {
        head?.let {
            val next = it.next
            it.next = null
            head = next
            if (next == null) {
                tail = null
            } else {
                next.prev = null
            }
            size--
        }
    }

    private fun unlinkTail() {
        tail?.let {
            val prev = it.prev
            it.prev = null
            tail = prev
            if (prev == null) {
                tail = null
            } else {
                prev.next = null
            }
            size--
        }
    }

    private fun unlink(curr: Node<E> ): E {
        val element = curr.element
        val next = curr.next
        val prev = curr.prev
        if (prev == null) {
            head = next
        } else {
            prev.next = next
            curr.prev = null
        }
        if (next == null) {
            tail = prev
        } else {
            next.prev = prev
            curr.next = null
        }
        size--
        return element
    }

    private fun node(index: Int): Node<E> {
        return if (index < size shr 1) {
            var x = head
            for (i in 0 until index)
                x = x!!.next
            x!!
        } else {
            var x = tail
            for (i in size - 1 downTo index + 1)
                x = x!!.prev
            x!!
        }
    }

    private fun validateElementIndex(index: Int) {
        if (index < 0 || index >= size)
            throw IndexOutOfBoundsException(outOfBoundsMsg(index))
    }

    private fun validatePositionIndex(index: Int) {
        if (index in (size + 1)..-1)
            throw IndexOutOfBoundsException(outOfBoundsMsg(index))
    }

    private fun outOfBoundsMsg(index: Int): String {
        return "Index: $index, Size: $size"
    }
}