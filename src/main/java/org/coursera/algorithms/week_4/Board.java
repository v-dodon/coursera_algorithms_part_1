package org.coursera.algorithms.week_4;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private final int[] board;
    private Board twinBoard;

    public Board(int[][] blocks) {
        board = new int[blocks.length * blocks.length];
        int index = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                board[index] = blocks[i][j];
                index++;
            }
        }
    }        // construct a board from an n-by-n array of blocks

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return Math.toIntExact(Math.round(Math.sqrt(board.length)));
    }                 // board dimension n

    public int hamming() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                continue;
            }
            int element = board[i] - 1;
            if (element != i) {
                count++;
            }
        }
        return count;
    }                   // number of blocks out of place

    public int manhattan() {
        int distance = 0;
        int blockSize = Math.toIntExact(Math.round(Math.sqrt(board.length)));
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                continue;
            }
            int element = board[i] - 1;
            int actualRow = i / blockSize;
            int actualColumn = i % blockSize;
            int expectedRow = element / blockSize;
            int expectedColumn = element % blockSize;
            distance += Math.abs(expectedRow - actualRow) + Math.abs(expectedColumn - actualColumn);
        }
        return distance;
    }                 // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        int[] boardCopy = Arrays.copyOf(board, board.length);
        Arrays.sort(boardCopy);
        for (int i = 0; i < boardCopy.length; i++) {
            if (i + 1 < boardCopy.length) {
                int temp = boardCopy[i];
                boardCopy[i] = boardCopy[i + 1];
                boardCopy[i + 1] = temp;
            }
        }
        return Arrays.equals(boardCopy, board);
    }                // is this board the goal board?

    public Board twin() {
        if (twinBoard != null) {
            return twinBoard;
        }
        int[] boardCopy = Arrays.copyOf(board, board.length);
        int elementIndex = StdRandom.uniform(board.length);
        int swappedIndex = StdRandom.uniform(board.length);
        while (boardCopy[swappedIndex] == 0 || board[elementIndex] == 0 || boardCopy[swappedIndex] == board[elementIndex]) {
            elementIndex = swappedIndex;
            swappedIndex = StdRandom.uniform(board.length);
        }
        int temp = board[elementIndex];
        boardCopy[elementIndex] = board[swappedIndex];
        boardCopy[swappedIndex] = temp;
        int[][] twin = to2DArray(boardCopy);

        twinBoard = new Board(twin);

        return twinBoard;
    }                    // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object that) {
        return that != null && (this == that || that.getClass().equals(this.getClass()) && Arrays.equals(board, ((Board) that).board));
    }        // does this board equal y?

    public Iterable<Board> neighbors() {
        int[] boardCopy = Arrays.copyOf(board, board.length);
        int blockSize = Math.toIntExact(Math.round(Math.sqrt(board.length)));
        Stack<Board> neighbors = new Stack<>();
        int zeroIndex = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                zeroIndex = i;
                break;
            }
        }

        int column = zeroIndex % blockSize;
        int row = zeroIndex / blockSize;
        int[][] twoDBoard = to2DArray(boardCopy);
        for (int i = row == 0 ? 0 : row - 1; i <= row + 1 && i < blockSize; i++) {
            for (int j = column == 0 ? 0 : column - 1; j <= column + 1 && j < blockSize; j++) {
                if ((i == row && j == column) || (i != row && j != column)) {
                    continue;
                }
                replaceValue(twoDBoard, row, column, i, j);
                neighbors.push(new Board(twoDBoard));
                replaceValue(twoDBoard, i, j, row, column);
            }
        }

        return neighbors;
    }     // all neighboring boards

    private int[][] to2DArray(int[] arr) {
        int blockSize = Math.toIntExact(Math.round(Math.sqrt(arr.length)));
        int[][] twoDArr = new int[blockSize][blockSize];
        for (int i = 0; i < arr.length; i++) {
            int row = i / blockSize;
            int column = i % blockSize;
            twoDArr[row][column] = arr[i];
        }

        return twoDArr;
    }

    private void replaceValue(int[][] resetArr, int row, int column, int replaceRow, int replaceColumn) {
        int temp = resetArr[row][column];
        resetArr[row][column] = resetArr[replaceRow][replaceColumn];
        resetArr[replaceRow][replaceColumn] = temp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int blockSize = Math.toIntExact(Math.round(Math.sqrt(board.length)));
        sb.append(blockSize);
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            int column = i % blockSize;
            sb.append(String.format("%2d ", board[i]));
            if (column == blockSize - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }               // string representation of this board (in the output format specified below)
}