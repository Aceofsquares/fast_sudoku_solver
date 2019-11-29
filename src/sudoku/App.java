package sudoku;

import sudoku.adt.SudokuPuzzle;
import sudoku.solver.Solver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.out;

public class App {
    private static File getSudokuFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filters[] = {
                new FileNameExtensionFilter("TXT Text File", "txt"),
                new FileNameExtensionFilter("SD Sudoku File", "sd")
        };
        for(FileNameExtensionFilter filter : filters) {
            chooser.addChoosableFileFilter(filter);
        }
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    private static SudokuPuzzle loadSudokuPuzzle(File sudokuFile) {
        SudokuPuzzle puzzle = new SudokuPuzzle();
        try {
            Scanner fileIn = new Scanner(sudokuFile);
            int row = 0, col = 0;
            while (fileIn.hasNextLine()) {
                String line[] = fileIn.nextLine().split("");
                for (String s : line) {
                    if (!s.equals("-")) {
                        puzzle.put(Integer.parseInt(s), row, col);
                    }
                    col += 1;
                }
                row += 1;
                col = 0;
            }
            fileIn.close();
        } catch (FileNotFoundException e) {
            out.printf("File %s was not found.", sudokuFile);
            exit(1);
        }
        return puzzle;
    }

    public static void main(String args[]) {
        File filepath = getSudokuFile();
        if(filepath != null) {
            SudokuPuzzle puzzle = loadSudokuPuzzle(filepath);

            if(!puzzle.validPuzzle()) {
                System.out.println("Not a valid puzzle");
                System.exit(1);
            }
            out.println(puzzle);
            out.println();
            Solver.solve(puzzle);
            if(puzzle.isSolved()) {
                out.println(puzzle);
            } else {
                System.out.println("Not solvable");
            }
        }
    }
}
