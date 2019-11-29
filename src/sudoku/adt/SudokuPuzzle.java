package sudoku.adt;

public class SudokuPuzzle {
    private int puzzle[][];

    public SudokuPuzzle() {
        puzzle = new int[9][9];
    }

    public boolean validPuzzle() {
        for(int r = 0; r < puzzle.length; r++) {
            for(int c = 0; c < puzzle[r].length; c++){
                int value = puzzle[r][c];
                if(value != 0){
                    if(!validateAll(r, c, value)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isSolved() {
        for (int r = 0; r < puzzle.length; r++) {
            for (int c = 0; c < puzzle[r].length; c++) {
                if (!validateAll(r, c, puzzle[r][c])) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateAll(int row, int col, int guess) {
        if(guess == 0) {
            return false;
        }
        return validateV(row, col, guess) &&
                validateH(row, col, guess) &&
                validateB(row, col, guess);
    }

    //Validate row and column on the horizontal
    public boolean validateH(int row, int col, int guess) {
        for (int c = 0; c < puzzle[row].length; c++) {
            if (c != col && puzzle[row][c] == guess) {
                return false;
            }
        }
        return true;
    }

    //Validate row and column on the vertical
    public boolean validateV(int row, int col, int guess) {
        for (int r = 0; r < puzzle.length; r++) {
            if (r != row && puzzle[r][col] == guess) {
                return false;
            }
        }
        return true;
    }

    //Validate row and column on the box area
    public boolean validateB(int row, int col, int guess) {
        int rowOffset = row - (row % 3);
        int colOffset = col - (col % 3);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int rOff = r + rowOffset;
                int cOff = c + colOffset;
                if (rOff != row || cOff != col) {
                    if (puzzle[rOff][cOff] == guess) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void put(int value, int row, int col){
        if(row < 0 || row >= puzzle.length){
            throw new IndexOutOfBoundsException(String.format("Row value [%d] is out of bounds.", row));
        }
        if(col < 0 || col >= puzzle[row].length) {
            throw new IndexOutOfBoundsException(String.format("Col value [%d] is out of bounds.", col));
        }

        puzzle[row][col] = value;
    }

    public int get(int row, int col) {
        if(row < 0 || row >= puzzle.length){
            throw new IndexOutOfBoundsException(String.format("Row value [%d] is out of bounds.", row));
        }
        if(col < 0 || col >= puzzle[row].length) {
            throw new IndexOutOfBoundsException(String.format("Col value [%d] is out of bounds.", col));
        }

        return puzzle[row][col];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < puzzle.length; r++) {
            if (r % 3 == 0) {
                if(r == 3 || r == 6){
                    sb.append("--------+-------+--------\n");
                } else {
                    sb.append("-------------------------\n");
                }
            }
            for (int c = 0; c < puzzle[r].length; c++) {
                if (c % 3 == 0) {
                    sb.append("| ");
                }
                if (puzzle[r][c] != 0) {
                    sb.append(puzzle[r][c]);
                } else {
                    sb.append("_");
                }
                if (c < puzzle[r].length - 1) {
                    sb.append(" ");
                }
            }
            sb.append(" |");
            if (r < puzzle.length - 1) {
                sb.append("\n");
            }
        }
        sb.append("\n-------------------------");
        return sb.toString();
    }

    @Override
    public SudokuPuzzle clone() {
        SudokuPuzzle clonePuzzle = new SudokuPuzzle();
        for(int r = 0; r < puzzle.length; r++){
            for(int c = 0; c < puzzle[r].length; c++){
                int value = puzzle[r][c];
                if(value != 0){
                    clonePuzzle.put(value, r, c);
                }
            }
        }
        return clonePuzzle;
    }

}
