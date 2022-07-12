package com.canvas;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();
        printHelp();
        while (true) {
            try {
                System.out.print("enter command: ");
                String input = scanner.nextLine();
                String[] commands = input.split(" ");
                controller.ParseCommand(commands);
            } catch (IllegalArgumentException e) {
                System.out.print("Command failed: ");
                System.out.println(e.getMessage());
                continue;
            }
            catch (NoSuchElementException e)
            {
                System.out.println("No input detected: exit program");
                System.exit(0);
            }
        }
    }

    private static void printHelp() {
        System.out.println("Text Drawing");
        System.out.println("Help: ");
        System.out.println("Enter command via console to draw");
        System.out.println("Supported Command:");
        System.out.println("C w h:  Create a Canvas with width w and height h, Example: C 20 5");
        System.out.println("L x1 y1 x2 y2:  Draw a line from (x1, y1) to (x2, y2), Example: L 3 5 6 5");
        System.out.println("R x1 y1 x2 y2:  Draw a rectangle from (x1, y1) to (x2, y2), Example: R 7 5 12 8");
        System.out.println("--------------------------------------------------------------------------------");
    }
}
