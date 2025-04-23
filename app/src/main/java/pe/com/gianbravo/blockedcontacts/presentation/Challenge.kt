package pe.com.gianbravo.blockedcontacts.presentation

/**
 * @author Giancarlo Bravo Anlas
 *
 * Given sorted list of integers implement a function which finds the first pair where the sum equals to 0. Return an pair that includes both values that sum to zero or null if a pair does not exist.
sumZero(listOf(1, 2)) // null

sumZero(listOf(-3, -2, 0, 1, 2)) // Pair(-2, 2)

 *
 */
class Challenge {

    fun main(){
        val result = sumZero(listOf(-3, -2, 0, 1, 2))
        println("Result : $result")
    }


    fun sumZero(list: List<Int>): Pair<Int, Int>? {
        list.contains(1)
        list.forEachIndexed { index, value ->
            for (i in index + 1..list.size-1) {
                if (value == -1 * list[i])
                    return Pair(value, list[i])
            }
        }
        return null
    }
}