

/**
 * This program allows the user to play a game or run statistics against the computer where the player rolls a dice and has two choices
 * roll - If the player rolls a
 2 through 6, the number is added to the turn’s total
 1, the player loses their turn and the opponent gets to roll
 hold - The player adds the turn’s total to their score and the opponent gets to roll 
 * 
 * Statistics: The simulation will perform
   many turns, each turn will hold at 20 points.
 * 
 * @author Aditya Dendukuri
 * @since 9/13/2024
 */
public class PigGame {
	public static final int MAX_SCORE = 100; //this is the max score the user or computer must reach to win
	private int userScore; //this is the total user score
	private int compScore; //this is the total computer score
	private Dice dice; //instance of dice used to call dice methods
	private int[] turnScoreCounts = new int[7]; //this is the counts for how many times each score happens in statistics, divided by numTurns to get probability.
	
	/**
	 * This constructor initilizes all the index of the turnScore array to 0
	 */
	public PigGame() {
		userScore = 0;
		compScore = 0;
		dice = new Dice();
		for (int i = 0; i < turnScoreCounts.length; i++) {
			turnScoreCounts[i] = 0;
		}
	}
	/**
	 * This method will call the printIntroduction game which starts the game
	 */
	public static void main(String[] args) {
		PigGame pg = new PigGame();
		pg.printIntroduction();
	}

	/** 
	 * This method will print the introduction to the game and start the game.
	 */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("______ _         _____");
		System.out.println("| ___ (_)       |  __ \\");
		System.out.println("| |_/ /_  __ _  | |  \\/ __ _ _ __ ___   ___");
		System.out.println("|  __/| |/ _` | | | __ / _` | '_ ` _ \\ / _ \\");
		System.out.println("| |   | | (_| | | |_\\ \\ (_| | | | | | |  __/");
		System.out.println("\\_|   |_|\\__, |  \\____/\\__,_|_| |_| |_|\\___|");
		System.out.println("          __/ |");
		System.out.println("         |___/");
		System.out.println("\nThe Pig Game is human vs computer. Each takes a"
				+ " turn rolling a die and the first to score");
		System.out.println("100 points wins. A player can either ROLL or "
				+ "HOLD. A turn works this way:");
		System.out.println("\n\tROLL:\t2 through 6: add points to turn total, "
				+ "player's turn continues");
		System.out.println("\t\t1: player loses turn");
		System.out.println("\tHOLD:\tturn total is added to player's score, "
				+ "turn goes to other player");
		System.out.println("\n");

		String gameChoice = Prompt.getString("Play game or Statistics (p or s)");
		if (gameChoice.equals("p")) {
			runGame();
		} else {
			int numTurns = Prompt.getInt("\nRun statistical analysis - \"Hold at 20\"\n" + "\n" +
					"Number of turns (1000 - 1000000): ");

			runStatistics(numTurns);
		}
	}
	/**
	 * This method runs the statistics, calling the simulate turn numTurns times
	 * 
	 * @param numTurns Number of turns user wants to simulate 
	 */
	private void runStatistics(int numTurns) {
		double [] probabilities = new double[7];
		for (int i = 0; i < numTurns; i++) {
			simulateTurn();
		}

		for (int i = 0; i < turnScoreCounts.length; i++) {
			probabilities[i] = (double)turnScoreCounts[i] / numTurns;
			
		}
		  // Display results
		  System.out.println("\n\tEstimated");
		  System.out.println("\nScore\tProbability");
		  System.out.printf("0\t%.5f\n", probabilities[0]);    
		  System.out.printf("20\t%.5f\n", probabilities[1]);   
		  System.out.printf("21\t%.5f\n", probabilities[2]);  
		  System.out.printf("22\t%.5f\n", probabilities[3]);   
		  System.out.printf("23\t%.5f\n", probabilities[4]);  
		  System.out.printf("24\t%.5f\n", probabilities[5]);  
		  System.out.printf("25\t%.5f\n", probabilities[6]);  
		

	}

	/** 
	 * This method will simulates a single turn for the computer, holding at 20 points 
	 */
	private void simulateTurn() {
		int turnScore = 0;
		boolean rolled1 = false;

		while (!rolled1 && turnScore < 20) {
			int roll = dice.roll();
			if (roll != 1) {
				turnScore += roll;
			} else {
				turnScore = 0;
				rolled1 = true;
			}
		}

		if (turnScore == 0) {
			turnScoreCounts[0]++;  // Score of 0, index 0
		} else if (turnScore == 20) {
			turnScoreCounts[1]++;  // Score of 20, index 1
		} else if (turnScore == 21) {
			turnScoreCounts[2]++;  // Score of 21, index 2
		} else if (turnScore == 22) {
			turnScoreCounts[3]++;  // Score of 22, index 3
		} else if (turnScore == 23) {
			turnScoreCounts[4]++;  // Score of 23, index 4
		} else if (turnScore == 24) {
			turnScoreCounts[5]++;  // Score of 24, index 5
		} else if (turnScore >= 25) {
			turnScoreCounts[6]++;  // Score of 25 or more, index 6
		}
	
		

	}

	/** 
	 * This method runs the actual game for human vs computer, calling methods userTurn and computer Turn
	 */
	public void runGame() {
		while (userScore < MAX_SCORE && compScore < MAX_SCORE) {
			userTurn();
			if (userScore >= MAX_SCORE)
				break;
			computerTurn();
		}
		printWinner();
	}

	/** 
	 * This method handle the user's turn 
	 */
	public void userTurn() {
		int turnScore = 0;
		boolean rolled1 = false;
		boolean userHeld = false;

		System.out.println("**** USER Turn *** \n");
		System.out.println("Your turn score: " + turnScore);
		System.out.println("Your total score: " + userScore + "\n");

		while (!rolled1 && !userHeld) {
			String rollOrHold = Prompt.getChar("(r)oll or (h)old") + "";
			if (rollOrHold.equals("r")) {
				System.out.println("\nYou ROLL");
				int roll = dice.roll();
				dice.printDice();
				if (roll != 1) {
					turnScore += roll;
				} else {
					turnScore = 0;
					rolled1 = true;
				}
			} else if (rollOrHold.equals("h")) {
				userScore += turnScore;
				userHeld = true;
			}

			if (!userHeld) {
				System.out.print("Your turn score: " + turnScore);
			}
			System.out.println("\nYour total score: " + userScore + "\n");
		}
	}

	/** This method handle the computer's turn */
	public void computerTurn() {
		int compTurnScore = 0;
		boolean compRolled1 = false;
		boolean compHeld = false;

		System.out.println("**** COMPUTER'S Turn *** \n");
		System.out.println("Computer turn score: " + compTurnScore);
		System.out.println("Computer total score: " + compScore + "\n");

		while (!compHeld && !compRolled1) {
			Prompt.getString("Press enter for computer's turn");

			System.out.println("Computer will ROLL");
			int roll = dice.roll();
			dice.printDice();
			if (roll != 1) {
				compTurnScore += roll;
			} else {
				compTurnScore = 0;
				compRolled1 = true;
			}

			if (compTurnScore >= 20) {
				compScore += compTurnScore;
				compHeld = true;
				System.out.println("Computer will HOLD");
			}

			if (!compHeld) {
				System.out.println("Computer turn score: " + compTurnScore);
			}
			System.out.println("Computer total score: " + compScore + "\n");
		}
	}

	/** This method prints the winner at the end of the game */
	public void printWinner() {
		if (userScore >= MAX_SCORE) {
			System.out.println("Congratulations!!! YOU WON!!!!");
		} else {
			System.out.println("Computer won. Better luck next time!");
		}
	}
}
