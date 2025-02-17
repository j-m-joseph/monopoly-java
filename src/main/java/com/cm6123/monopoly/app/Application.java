package com.cm6123.monopoly.app;

import com.cm6123.monopoly.game.Welcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Application {
    /**
     * Create a logger for the class.
     */
    private static Logger logger = LoggerFactory.getLogger(Application.class);


    private Application() {
    }

    /**
     * main entry point.  If args provided, result is displayed and program exists. Otherwise, user is prompted for
     * input.
     *
     * @param args command line args.
     */
    public static void main(final String[] args) {


        logger.info("Application Started with args:{}", String.join(",", args));

        // Create a new instance of the Welcome class. This is an example of
        // how the UI gives and takes information from the game.

        Welcome welcome = new Welcome();

        // Print the welcome message to the console. The content comes from the game package.
        System.out.println(welcome.welcomeMessage());

        logger.info("Application closing");
    }


}
