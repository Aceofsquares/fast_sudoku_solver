package sudoku.solver;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SolverPuzzleCell implements Comparable<SolverPuzzleCell>{
    public final int row;
    public final int col;
    private final Queue<Integer> options;

    public SolverPuzzleCell(int r, int c) {
        row = r;
        col = c;

        options = new LinkedList<>();
    }

    public void addOption(int num) {
        options.add(num);
    }

    public int getOption() {
        return options.remove();
    }

    public boolean outOfOptions() {
        return options.isEmpty();
    }

    public boolean equals(Object o) {
        if(o == null || !(o instanceof SolverPuzzleCell)){
            return false;
        } else if(this == o) {
            return true;
        }
        SolverPuzzleCell other = (SolverPuzzleCell) o;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) - %d %s", row, col, options.size(), options);
    }

    @Override
    public int compareTo(SolverPuzzleCell o) {
        return options.size() - o.options.size();
    }
}
