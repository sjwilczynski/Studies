-- ----------------------------------------------------------------------------
-- Puzzle.hs
-- WERSJA 0.1
-- ZADANIE 5 (Mozaika iloczynów)
-- ----------------------------------------------------------------------------
module Puzzle where

data Puzzle = Puzzle Int [[Int]]
type Result = ([Int], [((Int, Int), (Int, Int))])
type Solver = Int -> [[Int]] -> [Result]

data Test
    = SimpleTest Puzzle Result
    | CountTest  Puzzle Int

runSolver :: Solver -> Puzzle -> [Result]
runSolver solver (Puzzle m l) = solver m l

checkSolution :: Result -> Result -> Bool
checkSolution = ( == )

baseTests :: [Test]

baseTests =
  [
      SimpleTest
          (Puzzle 2 [[2, 0]])
          ([4, 5], [ ((1, 1), (1, 2))])
      , SimpleTest
          (Puzzle 3 [[1, 6, 1], [1, 6, 6]])
          ([4, 4, 4], [ ((1, 1), (1, 2)), ((2, 1), (2, 2)), ((1, 3), (2, 3))])
      , SimpleTest
          (Puzzle 4 [[5, 8, 4, 0], [5, 3, 0, 4], [6, 3, 4, 2]])
          ([5, 6, 7, 8], [ ((3, 2), (2, 3)), ((2, 2), (1, 1)), ((2, 4), (1, 4)), ((3, 3), (3, 4)), ((1, 3), (1, 2)), ((2, 1), (3, 1))])
      , CountTest
          (Puzzle 4 [[5, 8, 4, 0], [5, 3, 0, 4], [6, 3, 4, 2]])
          1
      , CountTest
          (Puzzle 4 [[0, 8, 4, 0], [5, 3, 0, 4], [6, 3, 4, 2]])
          0
      , CountTest
          (Puzzle 3 [[2, 0, 3], [2, 4, 0]])
          2
  ]
