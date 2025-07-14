import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolverGUI {

    private JFrame frame;
    private JTextField[][] cells = new JTextField[9][9];

    public SudokuSolverGUI() {
        frame = new JFrame("TASK 03 - Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600); // 🔽 Smaller size
        frame.getContentPane().setBackground(new Color(30, 30, 60));
        frame.setLayout(new BorderLayout());

        // Title
        JLabel header = new JLabel("TASK 03: Sudoku Solver", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20)); // 🔽 Smaller font
        header.setForeground(new Color(0x00E5FF));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        frame.add(header, BorderLayout.NORTH);

        // Instructions
        JLabel instructions = new JLabel("Enter numbers (1–9), blank or 0 for empty.", SwingConstants.CENTER);
        instructions.setFont(new Font("SansSerif", Font.PLAIN, 13));
        instructions.setForeground(Color.LIGHT_GRAY);
        instructions.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        frame.add(instructions, BorderLayout.SOUTH);

        // Grid Panel
        JPanel gridPanel = new JPanel(new GridLayout(9, 9, 1, 1));
        gridPanel.setBackground(new Color(40, 40, 80));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("SansSerif", Font.PLAIN, 14)); // 🔽 Smaller text
                cells[i][j].setBackground(new Color(235, 235, 255));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
                gridPanel.add(cells[i][j]);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(30, 30, 60));

        JButton solveButton = new JButton("Solve");
        solveButton.setBackground(new Color(0x00E5FF));
        solveButton.setForeground(Color.BLACK);
        solveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        solveButton.setFocusPainted(false);

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(180, 180, 180));
        resetButton.setForeground(Color.BLACK);
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        resetButton.setFocusPainted(false);

        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.PAGE_END);

        // Solve Button Logic
        solveButton.addActionListener(e -> solveSudoku());

        // Reset Button Logic
        resetButton.addActionListener(e -> clearGrid());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void solveSudoku() {
        int[][] grid = new int[9][9];
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String text = cells[i][j].getText().trim();
                    grid[i][j] = (text.isEmpty() || text.equals("0")) ? 0 : Integer.parseInt(text);
                }
            }

            if (solve(grid)) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        cells[i][j].setText(String.valueOf(grid[i][j]));
                        cells[i][j].setBackground(new Color(200, 255, 200));
                    }
                }
                JOptionPane.showMessageDialog(frame, "✅ Sudoku Solved!");
            } else {
                JOptionPane.showMessageDialog(frame, "❌ No solution exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "❌ Please enter only numbers (1–9) or leave blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
                cells[i][j].setBackground(new Color(235, 235, 255));
            }
        }
    }

    private boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int x = 0; x < 9; x++)
            if (grid[row][x] == num || grid[x][col] == num)
                return false;

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }

    private boolean solve(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solve(grid))
                                return true;
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolverGUI::new);
    }
}
