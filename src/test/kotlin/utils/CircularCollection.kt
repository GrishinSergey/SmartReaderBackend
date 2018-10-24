package utils

import io.ktor.network.selector.SelectInterest.Companion.size
import java.util.ArrayList

/**
 * Realize Cyclic Storage for nodes of type [T]
 * @see java.util.ArrayList for more details of realisation
 */
class CircularCollection<T>(c: Collection<T>) : ArrayList<T>(c) {

    /**
     * Index of next item
     */
    private var nextIndex = -1

    /**
     * Return next item from collection. If [nextIndex] == [size], then turn to first item in collection
     * @return [T] item from collection
     */
    fun next(): T {
        nextIndex = if (nextIndex == size - 1) 0 else nextIndex + 1
        return get(nextIndex)
    }

}
