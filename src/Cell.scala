package eu.shooktea.sudoku

case class Cell(possibleValues: Seq[Int]) {
  def convertToString: String = if (possibleValues.length == 1) possibleValues.head.toString else "x"
  def isSolved: Boolean = possibleValues.length == 1
}

object Cell {
  def fromChar(c: Char): Cell = {
    if (c == 'x') Cell(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9))
    else Cell(Seq(c.asDigit))
  }
}