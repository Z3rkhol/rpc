import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int computerScore = 0;
        int draws = 0;

        System.out.println("Welcome to the Advanced Rock-Paper-Scissors!");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        System.out.println("Choose computer difficulty:");
        System.out.println("1 - Random");
        System.out.println("2 - Smart");
        int difficulty = scanner.nextInt();
        scanner.nextLine();

        System.out.println("How many rounds would you like to play? (Enter 0 for unlimited until you quit)");
        int totalRounds = scanner.nextInt();
        scanner.nextLine();

        int[] moveHistory = new int[3];
        int currentRound = 0;
        boolean keepPlaying = true;

        while (keepPlaying) {
            currentRound++;
            System.out.println("\n--- Round " + currentRound + " ---");
            System.out.print("Choose your move (1 = Rock, 2 = Paper, 3 = Scissors) or 0 to quit: ");
            int userChoice = scanner.nextInt();

            if (userChoice == 0) {
                break;
            }
            if (userChoice < 1 || userChoice > 3) {
                System.out.println("Invalid choice. Please try again.");
                currentRound--;
                continue;
            }

            if (difficulty == 2) {
                moveHistory[userChoice - 1]++;
            }

            int computerChoice;
            if (difficulty == 2) {
                computerChoice = getSmartComputerChoice(moveHistory, random);
            } else {
                computerChoice = random.nextInt(3) + 1;
            }

            System.out.println(playerName + " chose: " + translateChoice(userChoice));
            System.out.println("Computer chose: " + translateChoice(computerChoice));

            if (userChoice == computerChoice) {
                System.out.println("It’s a draw!");
                draws++;
            } else if (
                (userChoice == 1 && computerChoice == 3) ||
                (userChoice == 2 && computerChoice == 1) ||
                (userChoice == 3 && computerChoice == 2)
            ) {
                System.out.println(playerName + " wins this round!");
                playerScore++;
            } else {
                System.out.println("Computer wins this round!");
                computerScore++;
            }

            if (totalRounds > 0 && currentRound >= totalRounds) {
                keepPlaying = false;
            }
        }

        System.out.println("\nGame Over!");
        System.out.println("Total rounds played: " + currentRound);
        System.out.println(playerName + "'s Score: " + playerScore);
        System.out.println("Computer's Score: " + computerScore);
        System.out.println("Number of Draws: " + draws);

        if (playerScore > computerScore) {
            System.out.println("Congratulations, " + playerName + ", you won the game!");
        } else if (computerScore > playerScore) {
            System.out.println("Computer is the winner this time!");
        } else {
            System.out.println("It's an overall draw!");
        }

        scanner.close();
    }

    private static int getSmartComputerChoice(int[] moveHistory, Random random) {
        int maxCount = 0;
        int frequentIndex = -1;
        for (int i = 0; i < moveHistory.length; i++) {
            if (moveHistory[i] > maxCount) {
                maxCount = moveHistory[i];
                frequentIndex = i;
            }
        }

        if (maxCount == 0) {
            return random.nextInt(3) + 1;
        }

        double probability = random.nextDouble();
        if (probability < 0.5) {
            switch (frequentIndex) {
                case 0: return 2; // User mostly Rock => pick Paper
                case 1: return 3; // User mostly Paper => pick Scissors
                case 2: return 1; // User mostly Scissors => pick Rock
            }
        }
        return random.nextInt(3) + 1;
    }

    private static String translateChoice(int choice) {
        switch(choice) {
            case 1: return "Rock";
            case 2: return "Paper";
            case 3: return "Scissors";
            default: return "Unknown";
        }
    }
}