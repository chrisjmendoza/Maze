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

        //myMaze.addHorizontalWall(2, 1, 2);
        //myMaze.addVerticalWall(3, 1, 2);
        //myMaze.addVerticalWall(4, 1, 3);
        //myMaze.addHorizontalWall(0, 2, 2);
        //myMaze.addVerticalWall(1, 0);
        //myMaze.addHorizontalWall(1, 3, 2);
        myMaze.maze1();
        myMaze.display();
    }

    /**
     * Creates a maze based on minus requirements
     */
    public void maze1() {

        ArrayList<Point> source = new ArrayList<>();
        ArrayList<Point> loose = new ArrayList<>();

        // Run a loop to fill the array with source points
        int counterY = 0;
        while (counterY <= height) {
            // Add the points that make up the upper and lower walls
            if (counterY == 0 || counterY == height) {
                for (int i = 0; i <= width; i++) {
                    source.add(new Point(i, counterY));
                }
            } else { // Fills in the points along the edges
                source.add(new Point(0, counterY));
                source.add(new Point(width, counterY));
            }
            counterY++;
        }
        // Run a loop to fill the loose array with the remaining points
        counterY = 1;
        while (counterY < height) {
            for(int x = 1; x < width; x++) {
                loose.add(new Point(x, counterY));
            }
            counterY++;
        }

//        System.out.println("The Source array points");
//        printArray(source);
//        System.out.println("The Loose array points");
//        printArray(loose);

        // ------> START MAZE GENERATION <------- //

        // Pick a random SOURCE Point
        Random rand = new Random();
        Point random = source.get(rand.nextInt(source.size()));

        // Loop through the maze and add walls until the loose points are all gone
        while(loose.size() > 0) {
            // Check for adjacent points that are not connected on the left side
            if (random.getX() == 0 && random.getY() != 0 && random.getY() != height) {
                Point check = new Point((int) (random.getX() + 1), (int) random.getY());
                // If the loose points array contains the same point, add the wall
                // and remove the point from the loose array
                if (loose.contains(check)) {
                    this.addHorizontalWall((int) random.getX(), (int) random.getY(), 1);
                    loose.remove(check); // Remove the loose point as it is now connected
                    source.remove(random); // Remove the source point as it no longer has ability to connect
                }
                // Check for adjacent points that are not connected on the right side
            } else if (random.getX() == width && random.getY() != 0 && random.getY() != height) {
                Point check = new Point((int) (random.getX() - 1), (int) random.getY());
                // If the loose points array contains the same point, add the wall
                // and remove the point from the loose array
                if (loose.contains(check)) {
                    this.addHorizontalWall((int) random.getX(), (int) random.getY(), 1);
                    loose.remove(check); // Remove the loose point as it is now connected
                    source.remove(random); // Remove the source point as it no longer has ability to connect
                }
                // Check for vertical points from the top
            } else if (random.getX() != 0 && random.getX() != width && random.getY() == 0) {
                Point check = new Point((int) (random.getX()), (int) random.getY() + 1);
                // If the loose points array contains the same point, add the wall
                // and remove the point from the loose array
                if (loose.contains(check)) {
                    this.addVerticalWall((int) random.getX(), (int) random.getY(), 1);
                    loose.remove(check); // Remove the loose point as it is now connected
                    source.remove(random); // Remove the source point as it no longer has ability to connect
                }
                // Check for vertical walls from the bottom
            } else if (random.getX() != 0 && random.getX() != width && random.getY() == height) {
                Point check = new Point((int) (random.getX()), (int) random.getY() - 1);
                // If the loose points array contains the same point, add the wall
                // and remove the point from the loose array
                if (loose.contains(check)) {
                    this.addVerticalWall((int) random.getX(), (int) random.getY(), 1);
                    loose.remove(check); // Remove the loose point as it is now connected
                    source.remove(random); // Remove the source point as it no longer has ability to connect
                }
            } else {

            }
        }
    }


    /**
     * A method to print the values of the point arrays for testing
     * @param a The ArrayList of points to print
     */
    public void printArray(ArrayList<Point> a) {

        for(Point p : a) {
            String space;
            String spaceY;
            if(p.getX() < 10) { space = " "; } else space = "";
            if(p.getY() < 10) { spaceY = " "; } else spaceY = "";
            System.out.println(space + p.getX() + " : " + spaceY + p.getY());
        }
    }

}
