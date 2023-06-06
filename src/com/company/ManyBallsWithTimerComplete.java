package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*********************************************************************************
 *
 * Exercise 17B -- Many Balls with a timer
 *
 * NEW STUFF: Ball class
 * 		   Use an actual timer to control the motion!
 *
 * The code I'm giving you manages only one ball.  It moves around the screen,
 * bouncing off the walls.  This version demonstrates an elegant solution to the
 * problem of timing the moving ball!
 *
 * YOUR JOB: 1) Make sure that you can run this program!
 *           2) Extend the program by creating an array of 100 Balls, each of which
 * 				is created with a random color, size, position and velocity and
 * 				each of which moves and considers bouncing at each clock tick.
 *              Be sure each ball starts entirely visible on the window, is equally
 *              likely to be anywhere on the window, and starts with velocity
 *              components that are equally likely to be positive or negative.
 *
 ***********************************************************************************/
public class ManyBallsWithTimerComplete
        extends JFrame
        implements ActionListener
{
    // DATA:

    private static final int MAX_WIDTH = 800;        // Window size
    private static final int MAX_HEIGHT = 700;        // Window size
    private static final int TOP_OF_WINDOW = 22;    // Top of the visible window

    private static final int DELAY_IN_MILLISEC = 500;  // Time delay between ball updates

    private static int numBalls;
    Ball[] randomColorBalls;

    // METHODS:

    /**
     * main -- Start up the window.
     *
     * @param    args
     */
    public static void main(String args[]) {
        // Create the window.
        ManyBallsWithTimerComplete mb = new ManyBallsWithTimerComplete();
    }

    /**
     * Constructor for ManyBallsWithTimer class.
     * Creates one Ball object, starts up the window and starts the timer.
     */
    public ManyBallsWithTimerComplete() {

        String fileName = "ball_data.txt";
        File inputFile = null;

        // loop ensures that a valid inputFile is selected
        while (inputFile == null || !inputFile.exists() || !inputFile.isFile()) {
            //System.out.print("What is the fully qualified name of the file that should be searched? ");
           // fileName = userInput.nextLine();
            if (fileName.isEmpty()) {
                System.out.println("Invalid file name. Try again");
            } else {
                inputFile = new File(fileName);
                if (!inputFile.exists()) {
                    System.out.println("The file '" + fileName + "' does not exist");
                } else if (!inputFile.isFile()) {
                    System.out.println(fileName + " is not a file");
                }
            }
        }

        // Perform word search.
        try (Scanner dataInput = new Scanner (inputFile)) {

            String ballCountString = dataInput.nextLine();
            numBalls = Integer.parseInt(ballCountString);
            System.out.println("numBalls: " + numBalls);
            randomColorBalls = new Ball[numBalls];
            int ballCount = 0;
            while (dataInput.hasNextLine()) {
                String lineOfInput = dataInput.nextLine();
                System.out.println("Ball # " + ballCount + ": " + lineOfInput);

               randomColorBalls[ballCount] = new Ball(lineOfInput);
                ballCount++;

            }

        }
        catch (FileNotFoundException e) {
                System.err.println("The file does not exist");
            }



        // Show the window with the ball in its initial position.
        setSize(MAX_WIDTH, MAX_HEIGHT);
        setVisible(true);

        // Sets up a timer but does not start it.  Once started, the timer will go
        //  off every DELAY_IN_MILLISEC milliseconds.  When it goes off all it does
        //  is call this.actionPerformed().  It then goes back to sleep for another
        //  DELAY_IN_MILLISEC.
        Timer clock = new Timer(DELAY_IN_MILLISEC, this);

        // Now actually start the timer.
        clock.start();
    }

    /**
     * actionPerformed() is called automatically by the timer every time the requested
     * delay has elapsed.  It will keep being called until the clock is stopped or the
     * program ends.  All actions that we want to happen then should be performed here.
     * Any class that implements ActionListener MUST have this method.
     * <p>
     * In this example it is called to move the ball every DELAY_IN_MILLISEC.
     *
     * @param e Contains info about the event that caused this method to be called
     */
    public void actionPerformed(ActionEvent e)        // NEW #5 !!!!!!!!!!
    {
        for (int i = 0; i < numBalls; i++) {
            randomColorBalls[i].move();
            randomColorBalls[i].bounce(0, MAX_WIDTH, TOP_OF_WINDOW, MAX_HEIGHT);
        }


        // Update the window.
        repaint();
    }

    /**
     * paint 		draw the window
     *
     * @param g Graphics object to draw on
     */
    public void paint(Graphics g) {
        // Clear the window.
        g.setColor(Color.white);
        g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);

        // Tell each ball to draw itself.
        for (int i = 0; i < numBalls; i++) {
            randomColorBalls[i].draw(g);
            System.out.println(randomColorBalls[i]);
        }


    }
}