import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class App{
    public static void main(String[] args) {
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;

        JFrame frame = new JFrame();
        frame.setTitle("Number Guessing Game");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(255, 182, 193)); 

        JOptionPane.showMessageDialog(frame,
                "Welcome to theNumber Guessing Game!\nI have selected a number between " + lowerBound
                        + " and " + upperBound + ". Try to guess it!", "Number Guessing Game",
                JOptionPane.INFORMATION_MESSAGE);

        int guess;
        int attempts = 0;
        int maxAttempts = 10; 
        int maxPoints = 100; 
        int points = maxPoints;

        do {
            String input = JOptionPane.showInputDialog(frame, "Enter your guess:", "Number Guessing Game",
                    JOptionPane.PLAIN_MESSAGE);
            guess = Integer.parseInt(input);
            attempts++;

            if (guess < targetNumber) {
                JOptionPane.showMessageDialog(frame, "Too low! Try again.", " Number Guessing Game",
                        JOptionPane.WARNING_MESSAGE);
            } else if (guess > targetNumber) {
                JOptionPane.showMessageDialog(frame, "Too high! Try again.", " Number Guessing Game",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                
                int earnedPoints = calculatePoints(attempts, maxAttempts, maxPoints);
                JOptionPane.showMessageDialog(frame,
                        "Congratulations! You guessed the number in " + attempts + " attempts.\nYou earned "
                                + earnedPoints + " points.", "Number Guessing Game",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } while (guess != targetNumber && attempts < maxAttempts);

        frame.dispose();
    }

    private static int calculatePoints(int attempts, int maxAttempts, int maxPoints) {
        double ratio = (double) (maxAttempts - attempts) / maxAttempts;
        return (int) (ratio * maxPoints);
    }
}

