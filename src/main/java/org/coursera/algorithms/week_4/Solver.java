package org.coursera.algorithms.week_4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final Stack<Board> boards;
    private final boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        boards = new Stack<>();
        MinPQ<BoardNode> minPQ = new MinPQ<>();
        minPQ.insert(new BoardNode(initial, null, 0));
        minPQ.insert(new BoardNode(initial.twin(), null, 0));
        while (!minPQ.min().board.isGoal()) {
            BoardNode boardNode = minPQ.delMin();
            Board currentBoard = boardNode.board;
            int currentMoves = boardNode.moves;
            for (Board board : currentBoard.neighbors()) {
                if (boardNode.previous == null || !boardNode.previous.board.equals(board)) {
                    minPQ.insert(new BoardNode(board, boardNode, currentMoves + 1));
                }
            }
        }
        BoardNode current = minPQ.min();
        while (current.previous != null) {
            boards.push(current.board);
            current = current.previous;
        }
        boards.push(current.board);
        isSolvable = current.board.equals(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return boards.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return boards;
    }

    private class BoardNode implements Comparable<BoardNode> {

        private final Board board;
        private final BoardNode previous;
        private final int moves;
        private final int priority;
        private final int manhatan;

        public BoardNode(Board board, BoardNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.manhatan = this.board.manhattan();
            this.priority = this.manhatan + this.moves;
        }

        @Override
        public int compareTo(BoardNode that) {
            int priorityDiff = this.priority - that.priority;
            return priorityDiff == 0 ? this.manhatan - that.manhatan : priorityDiff;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
