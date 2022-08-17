package eu.shooktea.sudoku

case class Grid(cells: Seq[Cell]) {
  def convertToString: String = cells.map(_.convertToString).mkString
  def isSolved: Boolean = cells.forall(_.possibleValues.length == 1)
}

object Grid {
  def apply(input: String): Grid = {
    if (input.length != 81) {
      throw new Exception(s"Input must contain exactly 81 characters, ${input.length} given")
    }
    Grid(input map Cell.fromChar)
  }
}
