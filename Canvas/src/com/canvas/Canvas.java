package com.canvas;

/** Canvas class
 *  Represents a canvas object
 *  The canvas is implemented as a 2-D char array, with two extra rows and two extra column added as border on the
 *  given width and height. The fact that array index start from 0 and end at total length-1 make separating the border
 *  and the actual canvas field straightforward by using the coordinates as index directly.
 *
 *  E.g. if we have a canvas of (4,3), this means 4 columns and 3 rows
 *  Once the borders is added, the canvas is a 2D char array of 6 columns and 5 rows, which can be noted as
 *  an array of canvas[5][6]. Now canvas[0] will the top border.
 *
 *  Now when we have coordinates (1,2) this mean the first column and the second row which is exactly the char at canvas[2][1]
 */
public class Canvas {
    private static final char HORIZONTAL_CHAR = '-';
    private static final char VERTICAL_CHAR = '|';
    private static final char SHAPE_CHAR = 'x';

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private final int totalColumn;
    private final int totalRow;

    private final int borderFirstRow = 0;
    private final int borderLastRow;
    private final int borderFirstColumn = 0;
    private final int borderLastColumn;

    private final char[][] actualCanvas;

    // Column = x coordinates, Row = y coordinates
    public Canvas(int width, int height) {
        this.totalColumn = width + 2;
        this.totalRow = height + 2;
        borderLastColumn = totalColumn - 1;
        borderLastRow = totalRow - 1;
        actualCanvas = new char[this.totalRow][this.totalColumn];
        initCanvas();
    }

    /** Method to output the whole canvas on to screen
     *  This method need to be call everytime after actual drawing command has been processed
     */
    public void draw() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < totalRow; i++) {
            builder.append(actualCanvas[i]);
            builder.append(LINE_SEPARATOR);
        }

        System.out.println(builder.toString());
    }

    /** Draw a line with the given coordinates.
     *  Coordinates will be validated against canvas boundary.
     * @param startColumn The x coordinates for starting point
     * @param startRow The y coordinates for starting point
     * @param endColumn The x coordinates for ending point
     * @param endRow The y coordinates for ending point
     */
    public void drawLine(int startColumn, int startRow, int endColumn, int endRow) {
        validateCoordinates(startColumn, startRow, endColumn, endRow);
        drawLine(startColumn, startRow, endColumn, endRow, SHAPE_CHAR);
    }

    /** Draw a rectangle with the given coordinates.
     *  The method will first validate the coordinates with canvas boundary
     *  Then it will exchange the starting point and end point from coordinates to
     *  ensure the rectangle is being drawn from top-left to bottom-right
     *
     *  Then it will be drawing the top, the left, the right and the bottom edges using drawLine
     * @param startColumn The x coordinates for starting point
     * @param startRow The y coordinates for starting point
     * @param endColumn The x coordinates for ending point
     * @param endRow The y coordinates for ending point
     */

    public void drawRectangle(int startColumn, int startRow, int endColumn, int endRow) {
        validateCoordinates(startColumn, startRow, endColumn, endRow);

        // Exchange coordinates, so we start from top-left and end at bottom-right
        int startC = Math.min(startColumn, endColumn);
        int endC = Math.max(startColumn, endColumn);
        int startR = Math.min(startRow, endRow);
        int endR = Math.max(startRow, endRow);

        // Top
        drawLine(startC, startR, endC, startR, SHAPE_CHAR);
        // Left
        drawLine(startC, startR + 1, startC, endR - 1, SHAPE_CHAR);
        // Right
        drawLine(endC, startR+ 1, endC, endR- 1, SHAPE_CHAR);
        // Bottom
        drawLine(startC, endR, endC, endR, SHAPE_CHAR);
    }

    /** Draw a line from given coordinates and with the given char to display on canvas
     *
     * @param startColumn The x coordinates for starting point
     * @param startRow The y coordinates for starting point
     * @param endColumn The x coordinates for ending point
     * @param endRow The y coordinates for ending point
     * @param fillChar The char used to draw the line
     */
    private void drawLine(int startColumn, int startRow, int endColumn, int endRow, char fillChar) {
        boolean horizontal;
        int start;
        int end;

        // Decide if it is horizontal or vertical. Exchange coordinates to make sure we draw from top-bottom or left-right
        if (startRow == endRow) {
            horizontal = true;
            start = Math.min(startColumn, endColumn);
            end = Math.max(startColumn, endColumn);
        } else if (startColumn == endColumn) {
            horizontal = false;
            start = Math.min(startRow, endRow);
            end = Math.max(startRow, endRow);
        } else {
            throw new IllegalArgumentException("Coordinates cannot be used to draw a line");
        }

        for (int i = start; i <= end; i++) {
            if (horizontal) {
                actualCanvas[startRow][i] = fillChar;
            }
            else{
                actualCanvas[i][startColumn] = fillChar;
            }
        }
    }

    /**
     * Initialize canvas to draw borders and fill the middle
     */
    private void initCanvas() {
        if (totalColumn == 0 || totalRow == 0) {
            throw new IllegalArgumentException("Canvas length or width cannot be 0");
        }

        for (int i = 1; i <= totalRow - 2; i++) {
            for (int j = 1; j <= totalColumn - 2; j++) {
                actualCanvas[i][j] = ' ';
            }
        }

        drawLine(borderFirstColumn, borderFirstRow, borderLastColumn, borderFirstRow, HORIZONTAL_CHAR);
        drawLine(borderFirstColumn, borderFirstRow + 1, borderFirstColumn, borderLastRow - 1, VERTICAL_CHAR);
        drawLine(borderLastColumn, borderFirstRow + 1, borderLastColumn, borderLastRow - 1, VERTICAL_CHAR);
        drawLine(borderFirstColumn, borderLastRow, borderLastColumn, borderLastRow, HORIZONTAL_CHAR);
    }

    /**
     * Validate coordinates against canvas boundary. Drawing cannot reach out of bounds
     * @param startColumn The x coordinates for starting point
     * @param startRow The y coordinates for starting point
     * @param endColumn The x coordinates for ending point
     * @param endRow The y coordinates for ending point
     */
    private void validateCoordinates(int startColumn, int startRow, int endColumn, int endRow) {
        if ((startColumn <= borderFirstColumn || startColumn >= borderLastColumn)
                || (endColumn <= borderFirstColumn || endColumn >= borderLastColumn)
                || (startRow <= borderFirstRow || startRow >= borderLastRow)
                || (endRow <= borderFirstRow || endRow >= borderLastRow))
        {
            throw new IllegalArgumentException("Drawing run outside the canvas");
        }
    }
}
