package maze;

import java.io.*;
import java.util.Scanner;

public class Util {
    private static char printMenu(Maze maze) {
        int order = 1;
        char choice = '\0';
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Menu ===");
        System.out.printf("%d. %s\n", order, "Generate a new maze.");
        order++;
        System.out.printf("%d. %s\n", order, "Load a maze.");
        if(maze.isSetBoard()) {
            order++;
            System.out.printf("%d. %s\n", order, "Save the maze.");
            order++;
            System.out.printf("%d. %s\n", order, "Display the maze.");
            order++;
            System.out.printf("%d. %s\n", order, "Find the escape.");
        }
        System.out.printf("%d. %s\n", 0, "Exit.");
        choice = scanner.next().charAt(0);
        return choice;
    }

    public static void doTask(Maze maze) {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            char choice = printMenu(maze);
            if (choice == '1') {
                System.out.println("Enter the size of a new maze");
                int size = scanner.nextInt();
                maze = new Maze(size, size);
                maze.printMaze();
            } else if (choice == '2') {
                int rows = 0;
                int cols = 0;
                String fileName = scanner.next();
                if(!new File(fileName).exists()) {
                    System.out.printf("The file %s does not exist\n", fileName);
                    continue;
                }
                try (FileReader reader = new FileReader(fileName)) {
                    Scanner fileScanner = new Scanner(reader);
                    rows = fileScanner.nextInt();
                    cols = fileScanner.nextInt();
                    int[][] board = new int[rows][cols];
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            board[i][j] = fileScanner.nextInt();
                        }
                    }
                    maze.setBoard(board, rows, cols);
                } catch (IOException e) {
                    System.out.println("Cannot load the maze. It has an invalid format");
                }
            } else {
                if (maze.isSetBoard()) {
                    if (choice == '3') {
                        String fileName = scanner.next();
                        try (FileWriter writer = new FileWriter(fileName)) {
                            String line = String.format("%d %d\n", maze.getRows(), maze.getCols());
                            writer.write(line);
                            for (int i = 0; i < maze.getRows(); i++) {
                                for (int j = 0; j < maze.getCols(); j++) {
                                    writer.write(String.format("%d ", maze.getCell(i, j)));
                                }
                                writer.write('\n');
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (choice == '4') {
                        maze.printMaze();
                    } else if (choice == '5') {
                        maze.printWithPath();
                    }
                }
                if(choice == '0') {
                    System.out.println("Byte!");
                    break;
                } else if (choice > '5' || choice < '0') {
                    System.out.println("Incorrect option. Please try again");
                }
            }
        }
    }
}
