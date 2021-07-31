import kotlin.random.Random

class FieldsForGame(mines: Int) {
    private val fieldWithMines = emptyFieldInitializer()
    private val fieldForPlayer = emptyFieldInitializer()
     var remainingMines = mines

    init {
        fieldWithMines.setMines(mines)
    }



    private fun Array<Array<Char>>.setMines(minesAmount: Int) {
        var mines = minesAmount
        var row: Int
        var column: Int
        for (i in 0 until mines) {
            row = Random.nextInt(0, fieldSize)
            column = Random.nextInt(0, fieldSize)
            if (this[row][column] == 'X') {
                mines++
            } else {
                this[row][column] = 'X'

                var q = row - 1
                var w: Int


                while (q <= row + 1) {
                    if (q in this.indices) {
                        w = column - 1
                        while (w <= column + 1) {
                            if (w in this[q].indices) {
                                this[q][w] = setHintInCell(this[q][w])
                            }
                            w++
                        }
                    }
                    q++
                }
            }
        }
    }

    private fun getEmptyCoordinatesAround(coordinates: Coordinates): ArrayList<Coordinates> {
        val arrayOfEmptyCoordinates: ArrayList<Coordinates> = arrayListOf()
        if (fieldWithMines[coordinates.y][coordinates.x] == '.') {

            var i = coordinates.y - 1
            var j: Int

            while (i <= coordinates.y + 1) {
                j = coordinates.x - 1
                while (j <= coordinates.x + 1) {
                    if (Coordinates(i, j).isValid() && (fieldForPlayer[i][j] == '.'  || fieldForPlayer[i][j] == '*')) {
                        arrayOfEmptyCoordinates.add(Coordinates(j, i))
                    }
                    j++
                }
                i++
            }
        }
        return arrayOfEmptyCoordinates
    }

     fun setMarkInCell(coordinate: Coordinates) {
        coordinate.apply {
            if ((fieldWithMines[y][x] == 'X' && fieldForPlayer[y][x] == '.')
                || (fieldWithMines[y][x] == '.' && fieldForPlayer[y][x] == '*')) remainingMines--
            if ((fieldWithMines[y][x] == '.' && fieldForPlayer[y][x] == '.')
                || (fieldWithMines[y][x] == 'X' && fieldForPlayer[y][x] == '*')) remainingMines++
        }
        fieldForPlayer[coordinate.y][coordinate.x] = when (fieldForPlayer[coordinate.y][coordinate.x]) {
            '.' -> '*'
            '*' -> '.'
            in '1'..'8' -> {
                println("There is a number here!")
                fieldForPlayer[coordinate.y][coordinate.x]
            }
            else -> {
                println("There is no bomb!")
                '/'
            }
        }
    }

     fun openCell(coordinates: Coordinates) {
        when (fieldWithMines[coordinates.y][coordinates.x]) {
            '.' -> {
                if(fieldForPlayer[coordinates.y][coordinates.x] == '*') remainingMines -= 1
                fieldForPlayer[coordinates.y][coordinates.x] = '/'
                for (i in getEmptyCoordinatesAround(coordinates)) {
                    openCell(i)
                }

            }
            in '1'..'8' -> fieldForPlayer[coordinates.y][coordinates.x] = fieldWithMines[coordinates.y][coordinates.x]
            'X' -> Game.endOfGame = true

        }
    }
     fun setBombsOnPlayersField() {
        for (i in fieldWithMines.indices) {
            for (j in fieldWithMines[i].indices) {
                if (fieldWithMines[i][j] == 'X') fieldForPlayer[i][j] = 'X'
            }
        }
    }

    private fun setHintInCell(cell: Char): Char = when (cell) {
        '.' -> '1'
        '1' -> '2'
        '2' -> '3'
        '3' -> '4'
        '4' -> '5'
        '5' -> '6'
        '6' -> '7'
        '7' -> '8'
        '8' -> '8'
        else -> 'X'
    }

    fun printField() {
        println(" │1  2  3  4  5  6  7  8  9│")
        println("—│—————————————————————————│")
        for (i in fieldForPlayer.indices) {
            println("${i + 1}|${fieldForPlayer[i].joinToString("  ", "", "")}|")
        }
        println("—│—————————————————————————│")
    }

    private fun emptyFieldInitializer() = arrayOf(
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
    )
}