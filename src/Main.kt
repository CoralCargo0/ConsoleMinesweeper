import java.util.*

const val fieldSize = 9
val scanner = Scanner(System.`in`)

fun main() {
    do {
        println("How many mines do you want on the field? > ")
        val mines = scanner.nextInt()
        Game(mines).play()
        println("If you want to play a new game, enter 0")
    } while (scanner.nextInt() == 0)
}