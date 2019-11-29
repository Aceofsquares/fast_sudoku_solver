package sudoku.solver;

import sudoku.adt.SudokuPuzzle;

import java.util.ArrayList;

public class Solver {

    public static boolean solve(SudokuPuzzle puzzle) {
        ArrayList<SolverPuzzleCell> openCells = getEmptyCells(puzzle);
        return solve(puzzle, openCells, getBestOption(openCells, puzzle));
    }

    private static ArrayList<SolverPuzzleCell> getEmptyCells(SudokuPuzzle puzzle) {
        ArrayList<SolverPuzzleCell> openCells = new ArrayList<>();
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                if(puzzle.get(r, c) == 0) {
                    openCells.add(new SolverPuzzleCell(r, c));
                }
            }
        }
        return openCells;
    }

    //Uses the minimum remaining valued cell to find the next available spot to check
    //Scans through the cells array, which should be the open cells, and calculates the cell which
    //has the smallest number of options.
    private static SolverPuzzleCell getBestOption(ArrayList<SolverPuzzleCell> cells, SudokuPuzzle puzzle) {
        //Used to keep track of the cell with the smallest number of options.
        SolverPuzzleCell min = null;

        //Loop through the cells and perform the check
        for(SolverPuzzleCell cell : cells) {
            int row = cell.row;
            int col = cell.col;
            SolverPuzzleCell currentCell = new SolverPuzzleCell(row, col);

            //For each possible guess, validate it is possible to place the number in
            //the location of currentCell.
            for(int guess = 1; guess <= 9; guess += 1) {
                //If the guess is possible, add it to the options for the cell.
                if(puzzle.validateAll(row, col, guess)) {
                    currentCell.addOption(guess);
                }
            }
            //If min is null then we are checking the first open cell.
            //If min is not null, then compare min to currentCell.  The compareTo method
            //compares how many options each cell has against each other.  If compareTo returns
            // a number greater than 0 then min has more options than currentCell.  Set min to
            //currentCell.  Otherwise, any other returned result will be ignored.
            if(min == null || min.compareTo(currentCell) > 0) {
                min = currentCell;
            }
        }
        //The cell with the smallest number of options is returned OR null is returned.  If null is returned
        //then the puzzle is solved.  If a cell with no options is returned then a mistake was made somewhere.
        return min;
    }

    private static boolean solve(SudokuPuzzle puzzle, ArrayList<SolverPuzzleCell> openCells, SolverPuzzleCell cell) {
        //If cell is null then the puzzle is solved.
        if(cell == null) {
            return true;
        }
        //If cell has no options then a mistake was made somewhere.
        if(cell.outOfOptions()) {
            return false;
        }

        //Removes the cell we are making guesses on from the list of open cells.
        openCells.remove(cell);
        int row = cell.row;
        int col = cell.col;
        boolean solved = false;

        //If the cell is out of options or we have a solution then stop making guesses.
        //Otherwise, check guesses for the cell that was calculated.
        while(!cell.outOfOptions() && !solved) {
            int guess = cell.getOption();
            //Validate the guess
            if(puzzle.validateAll(row, col, guess)) {

                //Place the guess in the position of row, col. This is so later cells can
                //make a proper guess.
                puzzle.put(guess, row, col);

                //Get the result of the next best option in the openCells.  Store if the puzzle
                //gets solved.
                solved = solve(puzzle, openCells, getBestOption(openCells, puzzle));

                //If the puzzle is not solved after making guesses, then we made a bad guess
                //set the position to 0 to retry for the next guess.
                if(!solved) {
                    puzzle.put(0, row, col);
                }
            }
        }
        //If we made guesses for all possible options and we still didn't solve the puzzle then
        //place the cell in the list of openCells.  This will allow the cell to be revisited once
        //a different guess is made for previous cells.
        if(!solved) {
            openCells.add(cell);
        }
        return solved;
    }
}
