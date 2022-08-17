package eu.shooktea.sudoku

case class Grid(cells: Seq[Cell]) {
  def convertToString: String = cells.map(_.convertToString).mkString
  def isSolved: Boolean = cells.forall(_.possibleValues.length == 1)

  def row(i: Int): Seq[Cell] = cells.slice(i * 9, i * 9 + 9)
  def rows: Seq[Seq[Cell]] = for (i <- 0 to 8) yield row(i)
}

object Grid {
  def apply(input: String): Grid = {
    if (input.length != 81) {
      throw new Exception(s"Input must contain exactly 81 characters, ${input.length} given")
    }
    Grid(input map Cell.fromChar)
  }
}