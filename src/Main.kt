import java.util.*

const val fieldSize = 9
val scanner = Scanner(System.`in`)

fun main() {
    println("How many mines do you want on the field? > ")
    val mines = scanner.nextInt()
    Game(mines).play()
}