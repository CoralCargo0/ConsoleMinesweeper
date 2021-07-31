class Coordinates(_x: Int, _y: Int) {
    var x = _x
    var y = _y

    fun isValid(): Boolean = this.x in 0 until fieldSize && this.y in 0 until fieldSize
}