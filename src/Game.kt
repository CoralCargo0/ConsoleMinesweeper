class Game(_mines: Int) {
    private val mines = _mines
    private var playingFields = FieldsForGame(mines)

    fun play() {
        endOfGame = false
        var coordinates: Coordinates
        var isExploring: Boolean

        playingFields.printField()
        firstAttempt()

        while (true) {
            coordinates = invitationAndSetCoordinates()
            isExploring = isExploring()

            if (!coordinates.isValid()) {
                println("You enter wrong coordinates, try again...")
                continue
            }
            if (isExploring) {
                playingFields.openCell(coordinates)
                if (endOfGame) {
                    playingFields.setBombsOnPlayersField()
                    playingFields.printField()
                    println("You stepped on a mine and failed!")
                    return
                }
            } else {
                playingFields.setMarkInCell(coordinates)
            }
            playingFields.printField()
            if (playingFields.remainingMines == 0) {
                println("Congratulations! You found all the mines!")
                return
            }
        }
    }

    private fun invitationAndSetCoordinates(): Coordinates {
//        println("Set/delete mine marks (x and y coordinates):")
        println("Enter 'x and y coordinates free' to explore cell or 'x and y coordinates mine' to mark cell as mine")
        println("Example of input: \"1 5 free\"")
        val x = scanner.nextInt() - 1
        val y = scanner.nextInt() - 1
        return Coordinates(x, y)
    }

    private fun isExploring(): Boolean {
        return scanner.nextLine() == " free"
    }

    private fun firstAttempt() {
        var coordinates: Coordinates
        var isExploring: Boolean
        var repeater: Boolean = false
        do {
            coordinates = invitationAndSetCoordinates()
            isExploring = isExploring()

            if (!coordinates.isValid()) {
                println("You enter wrong coordinates, try again...")
                continue
            }
            if (isExploring) {

                do {
                    playingFields.openCell(coordinates)
                    if (endOfGame) {
                        endOfGame = false

                        playingFields = FieldsForGame(mines)
                        repeater = true
                    } else repeater = false
                }while (repeater)

            } else {
                playingFields.setMarkInCell(coordinates)
            }
            playingFields.printField()
        }while(false)

    }

    companion object {
        var endOfGame: Boolean = false
    }

}