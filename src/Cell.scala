package eu.shooktea.sudoku

case class Cell(possibleValues: Seq[Int])

object Cell {
  def fromChar(c: Char): Cell = {
    if (c == 'x') Cell(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9))
    else Cell(Seq(c.asDigit))
  }
}