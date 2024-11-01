/**
 * This program is the entry point of the Yahtzee game, handling user input and
 * calling other classes to manage game logic, player turns, and scoring.
 * 
 * @author Aditya Dendukuri
 * @since 10/24/24
 **/
public class Yahtzee {	

    /**
     * The main method of the Yahtzee game. Initializes the game and starts it by
     * calling printHeader and run methods.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Yahtzee yz = new Yahtzee();
        yz.printHeader();
        yz.run();
    }

    /**
     * Prints the introduction header for the game, displaying the rules and
     * general gameplay information.
     */
    public void printHeader() {
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
        System.out.println("|                                                                                    |");
        System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
        System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
        System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
        System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
        System.out.println("| trying to get a good combination.                                                  |");
        System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
        System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
        System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
        System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
        System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
        System.out.println("| on the score card, it can't be chosen again.                                       |");
        System.out.println("|                                                                                    |");
        System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println();
    }

    /**
     * Prompts the user for player names, determines the player order, and
     * initiates the main game loop for 13 rounds. Handles player turns and scoring.
     */
    public void run() {
        String name1 = Prompt.getString("Player 1, please enter your first name");
        String name2 = Prompt.getString("Player 2, please enter your first name");

        YahtzeePlayer player1 = new YahtzeePlayer();
        player1.setName(name1);

        YahtzeePlayer player2 = new YahtzeePlayer();
        player2.setName(name2);

        System.out.print("Let's see who will go first. " + name1 + ", please hit enter to roll the dice");
        Prompt.getString(""); // Wait for enter key

        DiceGroup diceGroup1 = new DiceGroup();
        diceGroup1.rollDice();
        diceGroup1.printDice();
        int total1 = diceGroup1.getTotal();

        System.out.print(name2 + ", it's your turn. Please hit enter to roll the dice");
        Prompt.getString(""); // Wait for enter key

        DiceGroup diceGroup2 = new DiceGroup();
        diceGroup2.rollDice();
        diceGroup2.printDice();
        int total2 = diceGroup2.getTotal();

        System.out.println(name1 + ", you rolled a sum of " + total1 + ", and " + name2 + ", you rolled a sum of " + total2 + ".");

        YahtzeePlayer currentPlayer;
        YahtzeePlayer otherPlayer;

        if (total1 > total2) {
            System.out.println(name1 + ", since your sum was higher, you'll roll first.");
            currentPlayer = player1;
            otherPlayer = player2;
        } else if (total2 > total1) {
            System.out.println(name2 + ", since your sum was higher, you'll roll first.");
            currentPlayer = player2;
            otherPlayer = player1;
        } else {
            // Handle tie by rolling again
            while (total1 == total2) {
                System.out.println("It's a tie! Let's roll again.");

                System.out.print(name1 + ", please hit enter to roll the dice -> ");
                Prompt.getString(""); // Wait for enter key
                diceGroup1.rollDice();
                diceGroup1.printDice();
                total1 = diceGroup1.getTotal();

                System.out.print(name2 + ", it's your turn. Please hit enter to roll the dice -> ");
                Prompt.getString(""); // Wait for enter key
                diceGroup2.rollDice();
                diceGroup2.printDice();
                total2 = diceGroup2.getTotal();

                System.out.println(name1 + ", you rolled a sum of " + total1 + ", and " + name2 + ", you rolled a sum of " + total2 + ".");
            }
            if (total1 > total2) {
                System.out.println(name1 + ", since your sum was higher, you'll roll first.");
                currentPlayer = player1;
                otherPlayer = player2;
            } else {
                System.out.println(name2 + ", since your sum was higher, you'll roll first.");
                currentPlayer = player2;
                otherPlayer = player1;
            }
        }

        printScoreCard(player1, player2);

        for (int round = 1; round <= 13; round++) {
            System.out.println("Round " + round + " of 13 rounds.");
            takeTurn(currentPlayer, player1, player2);
            YahtzeePlayer temp = currentPlayer;
            currentPlayer = otherPlayer;
            otherPlayer = temp;
        }

        printScoreCard(player1, player2);

        int totalScore1 = player1.getScoreCard().getTotalScore();
        int totalScore2 = player2.getScoreCard().getTotalScore();

        System.out.println(player1.getName() +  "\tscore total = " + totalScore1);
        System.out.println(player2.getName() + "\tscore total = " + totalScore2);

        if (totalScore1 > totalScore2) {
            System.out.println(player1.getName() + " wins!");
        } else if (totalScore2 > totalScore1) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    /**
     * Represents one turn in the game for a given player. Prompts the player for
     * dice rolls, offers choices to hold dice between rolls, and allows the player
     * to select a scoring category.
     *
     * @param player The current player taking their turn
     * @param player1 The first player in the game
     * @param player2 The second player in the game
     */
    public void takeTurn(YahtzeePlayer player, YahtzeePlayer player1, YahtzeePlayer player2) {
        System.out.print("\n" + player.getName() + ", it's your turn to play. Please hit enter to roll the dice");
        Prompt.getString("");

        DiceGroup dg = new DiceGroup();
        int rolls = 0;
        boolean endTurn = false;
        while (rolls < 3 && !endTurn) {
            if (rolls == 0) {
                dg.rollDice(); // First roll, roll all dice
            } else {
                String holdPrompt = "Which di(c)e would you like to keep? Enter the values you'd like to 'hold' without\n" +
                        "spaces. For example, to 'hold' dice 1, 2, and 5, enter 125\n" +
                        "(enter -1 if you'd like to end the turn)";
                String hold = Prompt.getString(holdPrompt);
                if (hold.equals("-1")) {
                    endTurn = true;
                    break;
                }
                dg.rollDice(hold);
            }
            dg.printDice();
            rolls++;
            if (rolls < 3 && !endTurn) {
                String holdPrompt = "Which di(c)e would you like to keep? Enter the values you'd like to 'hold' without\n" +
                        "spaces. For example, to 'hold' dice 1, 2, and 5, enter 125\n" +
                        "(enter -1 if you'd like to end the turn)";
                String hold = Prompt.getString(holdPrompt);
                if (hold.equals("-1")) {
                    endTurn = true;
                    break;
                }
                dg.rollDice(hold);
                dg.printDice();
            }
        }

        // After rolling, ask player to choose category to score
        YahtzeeScoreCard scoreCard = player.getScoreCard();
        printScoreCard(player1, player2);
        String categoryPrompt = player.getName() + ", now you need to make a choice. Pick a valid integer from the list above";
        int category = Prompt.getInt(categoryPrompt, 1, 13);
        boolean success = scoreCard.changeScore(category, dg);
        while (!success) {
            System.out.println("That category is already used or invalid. Please choose another category.");
            category = Prompt.getInt(categoryPrompt, 1, 13);
            success = scoreCard.changeScore(category, dg);
        }
        printScoreCard(player1, player2);
    }

    /**
     * Prints the scorecard for both players using the YahtzeeScoreCard class.
     *
     * @param player1 The first player in the game
     * @param player2 The second player in the game
     */
    public void printScoreCard(YahtzeePlayer player1, YahtzeePlayer player2) {
        YahtzeeScoreCard sc1 = player1.getScoreCard();
        YahtzeeScoreCard sc2 = player2.getScoreCard();
        sc1.printCardHeader();
        sc1.printPlayerScore(player1);
        sc2.printPlayerScore(player2);
    }
}
