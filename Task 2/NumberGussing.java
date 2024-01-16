import java.util.Random;
import java.util.Scanner;

public class NumberGussing {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Guess the Number!");

        int lowerBound = 1;
        int upperBound = 100;
        int maxAttempts = 10;
        int totalScore = 0;
        int rounds = 0;

        while (true) {
            int generatedNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = 0;
            int roundScore = 0;

            System.out.println("\nRound " + (rounds + 1));
            System.out.println("I have selected a number between " + lowerBound + " and " + upperBound + ". Try to guess it.");

            while (true) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == generatedNumber) {
                    roundScore = maxAttempts - attempts + 1;
                    totalScore += roundScore;
                    System.out.println("Congratulations! You guessed the number " + generatedNumber +
                            " in " + attempts + " attempts. Round Score: " + roundScore);
                    break;
                } else if (userGuess < generatedNumber) {
                    System.out.println("Try again! The number is higher.");
                } else {
                    System.out.println("Try again! The number is lower.");
                }

                if (attempts >= maxAttempts) {
                    System.out.println("Sorry, you've reached the maximum number of attempts. The correct number was " + generatedNumber + ".");
                    break;
                }
            }

            System.out.print("Do you want to play another round? (yes/no): ");
            String playAgain = scanner.next().toLowerCase();
            if (!playAgain.equals("yes")) {
                break;
            }

            rounds++;
        }

        System.out.println("\nGame Over! Your Total Score: " + totalScore);
        scanner.close();
    }
}
