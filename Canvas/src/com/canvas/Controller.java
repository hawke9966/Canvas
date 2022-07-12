package com.canvas;

public class Controller {

    private static final int LENGTH_OF_CANVAS_COMMAND = 3;
    private static final int LENGTH_OF_COMMAND_WITH_START_END_COORDINATES = 5;
    private Canvas currentCanvas;

    public Controller() {
    }

    /**
     * Parse command from Main.
     * Based on the command type to execute certain command
     * @param commands Commands from user input
     */
    public void ParseCommand(String... commands) {
        String commandType = commands[0];

        switch (commandType) {
            case "C":
                createCanvas(commands);
                break;
            case "L":
                drawLine(commands);
                break;
            case "R":
                drawRectangle(commands);
                break;
            case "Q": {
                currentCanvas = null;
                System.exit(0);
            }
            default:
                throw new IllegalArgumentException("Unsupported command");
        }

        // Output the canvas after command has been executed
        if (currentCanvas != null) {
            currentCanvas.draw();
        }
        else
        {
            System.out.println("No canvas created. Please create a canvas first");
        }
    }

    /**
     * Create a canvas under user command
     * @param commands User commands in string array of length 3
     */
    private void createCanvas(String[] commands) {
        validateCommandLength(commands, LENGTH_OF_CANVAS_COMMAND);
        currentCanvas = new Canvas(Integer.parseInt(commands[1]),
                Integer.parseInt(commands[2]));
    }

    /**
     * Draw a line on the current canvas
     * @param commands User command in string array of length 5
     */
    private void drawLine(String[] commands) {
        if (currentCanvas != null) {
            validateCommandLength(commands, LENGTH_OF_COMMAND_WITH_START_END_COORDINATES);
            currentCanvas.drawLine(Integer.parseInt(commands[1]),
                    Integer.parseInt(commands[2]),
                    Integer.parseInt(commands[3]),
                    Integer.parseInt(commands[4]));
        }
    }

    /**
     * Draw a rectangle on the current canvas.
     * @param commands User command in string array of length 5
     */
    private void drawRectangle(String[] commands) {
        if (currentCanvas != null) {
            validateCommandLength(commands, LENGTH_OF_COMMAND_WITH_START_END_COORDINATES);
            currentCanvas.drawRectangle(Integer.parseInt(commands[1]),
                    Integer.parseInt(commands[2]),
                    Integer.parseInt(commands[3]),
                    Integer.parseInt(commands[4]));
        }
    }

    /**
     * Validate the command has the right number of arguments
     * @param commands User command in string array
     * @param expectedLength Valid length for given command
     */
    private void validateCommandLength(String[] commands, int expectedLength) {
        if (commands.length != expectedLength) {
            throw new IllegalArgumentException("Cannot process command: Coordinates input are not valid");
        }
    }
}
