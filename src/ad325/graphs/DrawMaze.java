package ad325.graphs;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * A simple class to support drawing the maze.
 * @author Dan
 */
public class DrawMaze extends JPanel {

    /**
     * The width of the maze given in the constructor
     */
    public final int width;
    /**
     * The height of the maze given in the constructor
     */
    public final int height;
    /**
     * The width of the maze halls, a constant
     */
    public static final int SIZE = 35;
    /**
     * The thickness of the maze walls, a constant
     */
    public static final int WALL = 5;
    /**
     * The thickness of the border around the maze, a constant
     */
    public static final int BORDER = 10;

    // storage for the walls that are added
    private Set<Wall> walls;

    /**
     * Construct a simple maze
     * @param w The width of the maze in "cells"
     * @param h The height of the maze in "cells"
     */
    public DrawMaze(int w, int h) {
        width = w;
        height = h;
        setPreferredSize(new Dimension(width * SIZE + 2 * BORDER + WALL,
                height * SIZE + 2 * BORDER + WALL));
        walls = new HashSet<>();
    }

    /**
     * Add a vertical wall to the maze.
     * @param x The horizontal offset for the wall
     * @param y The vertical offset for the wall
     * @param len The length of the wall
     * @return True, if the requested wall added to the set
     * of walls
     */
    public boolean addVerticalWall(int x, int y, int len) {
        if(x > width || y+len > height)
            throw new IllegalArgumentException("Wall exceeds maze boundary");
        boolean added = false;
        for(int i = 0; i < len; i++) {
            if(addVerticalWall(x, y + i)) added = true;
        }
        return added;
    }

    /**
     * Add a vertical wall one cell long to the maze.
     * @param x The horizontal offset for the wall
     * @param y The vertical offset for the wall
     * @return True, if the requested wall added to the set
     * of walls
     */
    public boolean addVerticalWall(int x, int y) {
        if(x > width || y+1 > height)
            throw new IllegalArgumentException("Wall exceeds maze boundary");
        return walls.add(new Wall(x, y, false));
    }

    /**
     * Add a horizontal wall to the maze.
     * @param x The horizontal offset for the wall
     * @param y The vertical offset for the wall
     * @param len The length of the wall
     * @return True, if the requested wall added to the set
     * of walls
     */
    public boolean addHorizontalWall(int x, int y, int len) {
        if(x+len > width || y > height)
            throw new IllegalArgumentException("Wall exceeds maze boundary");
        boolean added = false;
        for(int i = 0; i < len; i++) {
            if(addHorizontalWall(x + i, y)) added = true;
        }
        return added;
    }

    /**
     * Add a horizontal wall one cell long to the maze.
     * @param x The horizontal offset for the wall
     * @param y The vertical offset for the wall
     * @return True, if the requested wall added to the set
     * of walls
     */
    public boolean addHorizontalWall(int x, int y) {
        if(x+1 > width || y > height)
            throw new IllegalArgumentException("Wall exceeds maze boundary");
        return walls.add(new Wall(x, y, true));
    }

    /**
     * Display the maze in a JFrame
     */
    public void display() {
        JFrame win = new JFrame("My Maze");
        win.setLocation(25, 25);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win.add(this);
        win.pack();
        win.setVisible(true);
    }

    /**
     * Paint the maze component
     * @param g The graphics object for rendering
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(WALL));
        int[] xs = {BORDER + WALL, BORDER, BORDER, BORDER + (width-1)*SIZE + WALL};
        int[] ys = {BORDER, BORDER, BORDER + height*SIZE, BORDER + height*SIZE};
        g2.drawPolyline(xs, ys, 4);
        int[] xs2 = {BORDER + SIZE - WALL, BORDER + width*SIZE, BORDER + width*SIZE, BORDER + width*SIZE - WALL};
        g2.drawPolyline(xs2, ys, 4);
        // code that was used to create the intermediate images
//        g2.setColor(Color.LIGHT_GRAY);
//        for(int i = 1; i < height; i++) {
//            int where = BORDER + i*SIZE;
//            g2.drawLine(BORDER + WALL, where, BORDER + width * SIZE - WALL, where);
//        }
//        for(int i = 1; i < width; i++) {
//            int where = BORDER + i*SIZE;
//            g2.drawLine(where, BORDER + WALL, where, BORDER + height * SIZE - WALL);
//        }
//        g2.setColor(Color.red);
        for(Wall wall : walls) {
            drawWall(g2, wall);
        }
    }

    // helper method to draw a horizontal wall
    void drawWall(Graphics g, Wall wall) {
        int x = wall.x;
        int y = wall.y;
        if(wall.horz) {
            g.drawLine(BORDER + x*SIZE, BORDER + y*SIZE, BORDER + (x+1)*SIZE, BORDER + y*SIZE);
        } else {
            g.drawLine(BORDER + x*SIZE, BORDER + y*SIZE, BORDER + x*SIZE, BORDER + (y+1)*SIZE);
        }
    }

    /**
     * Access to the set of walls
     * @return the set of walls
     */
    public Set<Wall> getWallSet() {
        return walls;
    }

    /**
     * Sample application method, showing how to use DrawMaze
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        DrawMaze myMaze = new DrawMaze(5, 4);
//        for(int i = 0; i < myMaze.width; i++) {
//            for (int j = 0; j < myMaze.height; j++) {
//                myMaze.addHorizontalWall(i, j, 1);
//                myMaze.addVerticalWall(i, j, 1);
//            }
//        }
        myMaze.addHorizontalWall(2, 1, 2);
        myMaze.addVerticalWall(3, 1, 2);
        myMaze.addVerticalWall(4, 1, 3);
        myMaze.addHorizontalWall(0, 2, 2);
        myMaze.addVerticalWall(1, 0);
        myMaze.addHorizontalWall(1, 3, 2);
        myMaze.display();
    }

}
