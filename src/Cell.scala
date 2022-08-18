package eu.shooktea.sudoku

case class Cell(possibleValues: Seq[Int], index: Int) {
  val convertToString: String = if (possibleValues.length == 1) possibleValues.head.toString else "x"
  val isSolved: Boolean = possibleValues.length == 1
  val notSolved: Boolean = !isSolved

  val row: Int = (index - index % 9) / 9 + 1
  val column: Int = index % 9 + 1
  val box: Int =
    if (Seq(1, 2, 3) contains row) {
      if (Seq(1, 2, 3) contains column) 1
      else if (Seq(4, 5, 6) contains column) 2
      else 3
    } else if (Seq(4, 5, 6) contains row) {
      if (Seq(1, 2, 3) contains column) 4
      else if (Seq(4, 5, 6) contains column) 5
      else 6
    } else {
      if (Seq(1, 2, 3) contains column) 7
      else if (Seq(4, 5, 6) contains column) 8
      else 9
    }

  val position: String = s"R${row}C${column}"

  def containsAnyOf(pair: (Int, Int)): Boolean = possibleValues.contains(pair._1) || possibleValues.contains(pair._2)
  def withoutAnyOf(pair: (Int, Int)): Cell = Cell(possibleValues.filter(i => i != pair._1 && i != pair._2), index)
}

object Cell {
  def fromChar(c: Char, index: Int): Cell = {
    if (c == 'x') Cell(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9), index)
    else Cell(Seq(c.asDigit), index)
  }
}