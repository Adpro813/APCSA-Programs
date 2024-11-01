public class YahtzeeScoreCard {
	private int[] scores;

	public YahtzeeScoreCard() {
		scores = new int[13];
		for (int i = 0; i < scores.length; i++) {
			scores[i] = -1;
		}
	}

	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
				"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
				"---------------------------+\n");
	}

	/**
	 * Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 0; i < 13; i++) {
			if (scores[i] > -1)
				System.out.printf(" %2d |", scores[i]);
			else
				System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
				"---------------------------+\n");
	}

	public int getScore(int choice) {
		if (choice >= 1 && choice <= 13) {
			return scores[choice - 1];
		} else {
			return -1;
		}
	}

	/**
	 * Change the scorecard based on the category choice 1-13.
	 *
	 * @param choice The choice of the player 1 to 13
	 * @param dg     The DiceGroup to score
	 * @return true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {
		if (choice < 1 || choice > 13) {
			return false;
		}
		if (scores[choice - 1] != -1) {
			return false;
		}
		switch (choice) {
			case 1: case 2: case 3: case 4: case 5: case 6:
				numberScore(choice, dg);
				break;
			case 7:
				threeOfAKind(dg);
				break;
			case 8:
				fourOfAKind(dg);
				break;
			case 9:
				fullHouse(dg);
				break;
			case 10:
				smallStraight(dg);
				break;
			case 11:
				largeStraight(dg);
				break;
			case 12:
				chance(dg);
				break;
			case 13:
				yahtzeeScore(dg);
				break;
			default:
				return false;
		}
		return true;
	}

	/**
	 * Change the scorecard for a number score 1 to 6
	 *
	 * @param choice The choice of the player 1 to 6
	 * @param dg     The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg) {
		int sum = 0;
		Dice[] dice = dg.getDice();
		for (int i = 0; i < dice.length; i++) {
			if (dice[i].getValue() == choice) {
				sum += choice;
			}
		}
		scores[choice - 1] = sum;
	}

	/**
	 * Updates the scorecard for Three Of A Kind choice.
	 *
	 * @param dg The DiceGroup to score
	 */
	public void threeOfAKind(DiceGroup dg) {
		int[] counts = new int[6];
		Dice[] dice = dg.getDice();
		for (int i = 0; i < dice.length; i++) {
			int value = dice[i].getValue();
			counts[value - 1]++;
		}
		boolean found = false;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] >= 3) {
				found = true;
			}
		}
		if (found) {
			scores[6] = dg.getTotal();
		} else {
			scores[6] = 0;
		}
	}

	public void fourOfAKind(DiceGroup dg) {
		int[] counts = new int[6];
		Dice[] dice = dg.getDice();
		for (int i = 0; i < dice.length; i++) {
			int value = dice[i].getValue();
			counts[value - 1]++;
		}
		boolean found = false;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] >= 4) {
				found = true;
			}
		}
		if (found) {
			scores[7] = dg.getTotal();
		} else {
			scores[7] = 0;
		}
	}

	public void fullHouse(DiceGroup dg) {
		int[] counts = new int[6];
		Dice[] dice = dg.getDice();
		for (int i = 0; i < dice.length; i++) {
			int value = dice[i].getValue();
			counts[value - 1]++;
		}
		boolean hasThree = false;
		boolean hasTwo = false;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] == 3) {
				hasThree = true;
			} else if (counts[i] == 2) {
				hasTwo = true;
			}
		}
		if (hasThree && hasTwo) {
			scores[8] = 25;
		} else {
			scores[8] = 0;
		}
	}

	public void smallStraight(DiceGroup dg) {
		int[] counts = new int[6];
		Dice[] dice = dg.getDice();
		for (int i = 0; i < dice.length; i++) {
			int value = dice[i].getValue();
			counts[value - 1] = 1;
		}
		boolean hasSmallStraight = false;
		if ((counts[0] == 1 && counts[1] == 1 && counts[2] == 1 && counts[3] == 1) ||
			(counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1) ||
			(counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1)) {
			hasSmallStraight = true;
		}
		if (hasSmallStraight) {
			scores[9] = 30;
		} else {
			scores[9] = 0;
		}
	}

	public void largeStraight(DiceGroup dg) {
		int[] counts = new int[6];
		Dice[] dice = dg.getDice();
		for (int i = 0; i < dice.length; i++) {
			int value = dice[i].getValue();
			counts[value - 1] = 1;
		}
		boolean hasLargeStraight = false;
		if ((counts[0] == 1 && counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1) ||
			(counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1)) {
			hasLargeStraight = true;
		}
		if (hasLargeStraight) {
			scores[10] = 40;
		} else {
			scores[10] = 0;
		}
	}

	public void chance(DiceGroup dg) {
		scores[11] = dg.getTotal();
	}

	public void yahtzeeScore(DiceGroup dg) {
		Dice[] dice = dg.getDice();
		int firstValue = dice[0].getValue();
		boolean isYahtzee = true;
		for (int i = 1; i < dice.length; i++) {
			if (dice[i].getValue() != firstValue) {
				isYahtzee = false;
			}
		}
		if (isYahtzee) {
			scores[12] = 50;
		} else {
			scores[12] = 0;
		}
	}

	public int getTotalScore() {
		int total = 0;
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] != -1) {
				total += scores[i];
			}
		}
		return total;
	}
}