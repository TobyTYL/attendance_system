package edu.duke.ece651.team1.enrollmentApp.controller;
/**
 * Interface representing an exit strategy for the application.
 * Implementations of this interface define how the application should exit.
 */
public interface ExitStrategy {
     /**
     * Performs the necessary actions to exit the application.
     */
    void exit();
}
