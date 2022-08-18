package eu.shooktea.sudoku

case class Grid(cells: Seq[Cell]) {
  def convertToString: String = cells.map(_.convertToString).mkString
  def isSolved: Boolean = cells.forall(_.isSolved)

  def setCells(newCells: Seq[Cell]): Grid = Grid(
    cells map {
      c => newCells.find(_.index == c.index) match {
        case None => c
        case Some(newCell) => newCell
      }
    }
  )

  def row(i: Int): Seq[Cell] = cells.filter(_.row == i)
  def rows: Seq[Seq[Cell]] = for (i <- 1 to 9) yield row(i)
  def mapRows(converter: Seq[Cell] => Seq[Cell]): Grid =
    rows.map(converter).foldLeft(this)((grid, cells) => grid setCells cells)

  def column(i: Int): Seq[Cell] = cells.filter(_.column == i)
  def columns: Seq[Seq[Cell]] = for (i <- 1 to 9) yield column(i)
  def mapColumns(converter: Seq[Cell] => Seq[Cell]): Grid =
    columns.map(converter).foldLeft(this)((grid, cells) => grid setCells cells)
}

object Grid {
  def apply(input: String): Grid = {
    if (input.length != 81) {
      throw new Exception(s"Input must contain exactly 81 characters, ${input.length} given")
    }
    val cells = input.zipWithIndex map { case (char, index) => Cell.fromChar(char, index) }
    Grid(cells)
  }
}
