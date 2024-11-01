/**
 * Represents a group of dice used in the Yahtzee game. This class provides
 * methods to roll the dice, hold specific dice, retrieve the total value,
 * and print a visual representation of the dice.
 */
public class DiceGroup {
    
    private Dice[] die; // the array of dice
    private final int NUM_DICE = 5; // number of dice
    
    // Create the seven different line images of a die
    private String[] line = { 
        " _______ ",
        "|       |",
        "| O   O |",
        "|   O   |",
        "|     O |",
        "| O     |",
        "|_______|"
    };
    
    /**
     * Initializes the array of Dice objects for the group, setting up the number of
     * dice as specified by NUM_DICE.
     */
    public DiceGroup() { 
        die = new Dice[NUM_DICE];
        for (int i = 0; i < NUM_DICE; i++) {
            die[i] = new Dice();
        }
    }
    
    /**
     * Rolls all dice in the group.
     */
    public void rollDice() { 
        for (int i = 0; i < die.length; i++) {
            die[i].roll();
        }
    }
    
    /**
     * Rolls only the dice that are not specified to be held in the input string.
     * For example, if rawHold is "421", then hold dice 1, 2, and 4, and
     * roll dice 3 and 5.
     *
     * @param rawHold A string indicating the dice to hold by position
     */
    public void rollDice(String rawHold) { 
        boolean[] hold = new boolean[NUM_DICE];
        for (int i = 0; i < rawHold.length(); i++) {
            char c = rawHold.charAt(i);
            if (Character.isDigit(c)) {
                int dieNumber = Character.getNumericValue(c);
                if (dieNumber >= 1 && dieNumber <= NUM_DICE) {
                    hold[dieNumber - 1] = true;
                }
            }
        }
        for (int i = 0; i < die.length; i++) {
            if (!hold[i]) {
                die[i].roll();
            }
        }
    }
    
    /**
     * Calculates and returns the total value of all dice in the group.
     *
     * @return The total sum of all dice values in the group
     */
    public int getTotal() { 
        int total = 0;
        for (int i = 0; i < die.length; i++) {
            total += die[i].getValue();
        }
        return total;
    }
    
    /**
     * Returns the array of Dice objects in the group.
     *
     * @return The array of Dice in this DiceGroup
     */
    public Dice[] getDice() {
        return die;
    }
    
    /**
     * Prints a visual representation of all dice in the group.
     */
    public void printDice() {
        printDiceHeaders();
        for (int i = 0; i < 6; i++) {
            System.out.print(" ");
            for (int j = 0; j < die.length; j++) {
                printDiceLine(die[j].getValue() + 6 * i);
                System.out.print("     ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Prints headers for the dice display, showing dice positions.
     */
    private void printDiceHeaders() {
        System.out.println();
        for (int i = 1; i <= NUM_DICE; i++) {
            System.out.printf("   # %d        ", i);
        }
        System.out.println();
    }
    
    /**
     * Prints one line of the ASCII image of a die, corresponding to its current value.
     *
     * @param value The index into the ASCII image lines array
     */
    private void printDiceLine(int value) {
        System.out.print(line[getDiceLine(value)]);
    }
	
    /**
     * Determines the appropriate index into the ASCII image lines for a die based on its
     * value, returning the index for the corresponding visual representation.
     *
     * @param value The calculated index for the dice ASCII representation
     * @return The index in the line array corresponding to the value's visual line
     */
    private int getDiceLine(int value) {
        if (value < 7) return 0;
        if (value < 14) return 1;
        switch (value) {
            case 20: case 22: case 25:
                return 1;
            case 16: case 17: case 18: case 24: case 28: case 29: case 30:
                return 2;
            case 19: case 21: case 23:
                return 3;
            case 14: case 15:
                return 4;
            case 26: case 27:
                return 5;
            default: // value > 30
                return 6;
        }
    }
}
