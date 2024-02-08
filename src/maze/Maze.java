package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class Maze {
    private int[][] board;
    private int rows = 0;
    private int cols = 0;

    private ArrayList<Pair<Integer, Integer>> path;
    private Pair<Integer, Integer> entry;
    private Pair<Integer, Integer> exit;
    public Maze() {
        this.rows = 0;
        this.cols = 0;
        this.board = null;
    }

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.setUpMaze();
    }

    private void initBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = 1;
            }
        }
    }
    public boolean isSetBoard(){
        if(this.board == null) return false;
        return true;
    }

    private Pair<Integer, Integer> getEntry() {
        Pair<Integer, Integer> node = new Pair<>();
        boolean isSet = false;
        for (int i = 0; i < rows; i++) {
            if(board[i][0] == 0) {
                node.setFirst(i);
                node.setSecond(0);
                isSet = true;
                break;
            }
        }

        if (!isSet) {
            for (int i = 0; i < rows; i++) {
                if (board[i][cols - 1] == 0) {
                    node.setFirst(i);
                    node.setSecond(cols-1);
                    isSet = true;
                    break;
                }
            }
        }

        if(!isSet) {
            for (int i = 0; i < cols; i++) {
                if (board[0][i] == 0) {
                    node.setFirst(0);
                    node.setSecond(i);
                    isSet = true;
                    break;
                }
            }
        }

        if(!isSet) {
            for (int i = 0; i < cols; i++) {
                if (board[rows - 1][i] == 0) {
                    node.setFirst(rows-1);
                    node.setSecond(i);
                    isSet = true;
                    break;
                }
            }
        }
        return node;
    }

    private Pair<Integer, Integer> getExit() {
        Pair<Integer, Integer> node = new Pair<>();
        boolean isSet = false;

        for (int i = 0; i < cols; i++) {
            if (board[rows - 1][i] == 0) {
                node.setFirst(rows-1);
                node.setSecond(i);
                isSet = true;
                break;
            }
        }

        if(!isSet) {
            for (int i = 0; i < cols; i++) {
                if (board[0][i] == 0) {
                    node.setFirst(0);
                    node.setSecond(i);
                    isSet = true;
                    break;
                }
            }
        }

        if (!isSet) {
            for (int i = 0; i < rows; i++) {
                if (board[i][cols - 1] == 0) {
                    node.setFirst(i);
                    node.setSecond(cols-1);
                    isSet = true;
                    break;
                }
            }
        }

        if(!isSet) {
            for (int i = 0; i < rows; i++) {
                if (board[i][0] == 0) {
                    node.setFirst(i);
                    node.setSecond(0);
                    isSet = true;
                    break;
                }
            }
        }

        return node;
    }
    public void setBoard(int[][] board, int rows, int cols) {
        this.board = board;
        this.rows = rows;
        this.cols = cols;
        this.entry = getEntry();
        this.exit = getExit();
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public final int getCell(int row, int col) {
        return this.board[row][col];
    }
    private void setEntry() {
        int row = 0;
        int col = 0;
        for (int i = 1; i < rows - 1; i++) {
            row = i;
            boolean found = false;
            for (int j = 0; j < cols; j++) {
                col = j;
                if(board[i][j] == 0) {
                    found = true;
                    break;
                }
            }
            if(found) break;
        }

        for (int i = col; i >= 0; i--) {
            board[row][i] = 0;
        }
        this.entry = new Pair<>(row, 0);
    }

    private void setExit() {
        int row = 0;
        int col = 0;
        for (int i = rows - 2; i > 0; i--) {
            row = i;
            boolean found = false;
            for (int j = cols-1; j >= 0; j--) {
                col = j;
                if(board[i][j] == 0) {
                    found = true;
                    break;
                }
            }
            if(found) break;
        }

        for (int i = col; i < cols; i++) {
            board[row][i] = 0;
        }
        this.exit = new Pair<>(row, cols-1);
    }
    private void setUpMaze() {
        this.board = new int[rows][cols];
        initBoard();
        boolean [][]isAdded = new boolean[rows][cols];
        Random random = new Random();
        Node<Integer, Integer> chosenCell = new Node<>();
        ArrayList<Node<Integer, Integer>> nextCells = new ArrayList<>();
        nextCells.add(new Node<>(random.nextInt(rows - 2) + 1, random.nextInt(cols - 2) + 1,-1, -1));
        isAdded[nextCells.get(0).getCoordinate().getFirst()][nextCells.get(0).getCoordinate().getSecond()] = true;
        while(!nextCells.isEmpty()) {
            int index = random.nextInt(nextCells.size());
            chosenCell = nextCells.get(index);
            nextCells.remove(index);
            int row = chosenCell.getCoordinate().getFirst();
            int col = chosenCell.getCoordinate().getSecond();
            Pair<Integer, Integer> parent = chosenCell.getParent();
            if(board[row][col] == 0) continue;
            board[row][col] = 0;
            if(parent.getFirst() != -1) {
                int midRow = (row + parent.getFirst())/2;
                int midCol = (col + parent.getSecond())/2;
                board[midRow][midCol] = 0;
            }
            if(row - 2 > 0 && !isAdded[row-2][col]) {
                isAdded[row-2][col] = true;
                nextCells.add(new Node<>(row-2, col, row, col));
            }
            if(col - 2 > 0 && !isAdded[row][col-2]) {
                isAdded[row][col-2] = true;
                nextCells.add(new Node<>(row, col-2, row, col));
            }
            if(col + 2 < cols - 1 && !isAdded[row][col+2]) {
                isAdded[row][col+2] = true;
                nextCells.add(new Node<>(row, col+2, row, col));
            }
            if(row + 2 < rows - 1 && !isAdded[row+2][col]) {
                isAdded[row+2][col] = true;
                nextCells.add(new Node<>(row+2, col, row, col));
            }
        }
        setEntry();
        setExit();
    }

    private void DFS(Pair<Integer, Integer> vertex, Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parents, Map<Pair<Integer, Integer>, Boolean> visited) {
        visited.putIfAbsent(vertex, true);
        int row = vertex.getFirst();
        int col = vertex.getSecond();
        if(row-1 >= 0 && board[row-1][col] == 0) {
            Pair<Integer, Integer> edge = new Pair<>(row-1, col);
            if(!visited.getOrDefault(edge, false)) {
                parents.put(edge, vertex);
                DFS(edge, parents, visited);
            }
        }

        if(row+1 < rows && board[row+1][col] == 0) {
            Pair<Integer, Integer> edge = new Pair<>(row+1, col);
            if(!visited.getOrDefault(edge, false)) {
                parents.put(edge, vertex);
                DFS(edge, parents, visited);
            }
        }

        if(col-1 >= 0 && board[row][col-1] == 0) {
            Pair<Integer, Integer> edge = new Pair<>(row, col-1);
            if(!visited.getOrDefault(edge, false)) {
                parents.put(edge, vertex);
                DFS(edge, parents, visited);
            }
        }

        if(col+1 < cols && board[row][col+1] == 0) {
            Pair<Integer, Integer> edge = new Pair<>(row, col+1);
            if(!visited.getOrDefault(edge, false)) {
                parents.put(edge, vertex);
                DFS(edge, parents, visited);
            }
        }
    }
    private void findPath() {
        if(!this.isSetBoard()) return;
        this.path = new ArrayList<>();
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parents = new HashMap<>();
        parents.put(entry, null);
        Map<Pair<Integer, Integer>, Boolean> visited = new HashMap<>();
        DFS(entry, parents, visited);
        path.add(exit);
        Pair<Integer, Integer> node = parents.get(exit);
        while(node != null) {
            path.add(node);
            node = parents.get(node);
        }
    }

    public void printMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(this.board[i][j] == 1)
                    System.out.print("\u2588\u2588");
                else System.out.print("  ");
            }
            System.out.println();
        }
    }

    public void printWithPath() {
        if(this.path == null) {
            findPath();
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(this.board[i][j] == 1)
                    System.out.print("\u2588\u2588");
                else {
                    Pair<Integer, Integer> edge = new Pair<>(i, j);
                    if(path.contains(edge)) {
                        System.out.print("//");
                    } else
                        System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
