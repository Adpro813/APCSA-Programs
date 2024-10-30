/**
 * Plays the game of MasterMind, where players attempt to guess a hidden code.
 * The game is played on a four-peg board using six different peg letters (A-F).
 * The player has a limited number of guesses to determine the correct code,
 * receiving feedback on the number of exact and partial matches after each guess.
 * 
 * @author Aditya Dendukuri
 * @since 9/27
 */
public class MasterMind {

    private boolean reveal; // Indicates if the master combination should be revealed
    private PegArray[] guesses; // Array to store guessed peg arrays
    private PegArray master; // The master (key) peg array

    // Constants defining game parameters
    private final int PEGS_IN_CODE = 4; // Number of pegs in the code
    private final int MAX_GUESSES = 10; // Maximum number of guesses allowed
    private final int PEG_LETTERS = 6; // Number of different letters on pegs

    /**
     * Constructs a new MasterMind game instance and initializes the guesses array.
     */
    public MasterMind() {
        guesses = new PegArray[MAX_GUESSES];
        for (int i = 0; i < MAX_GUESSES; i++) {
            guesses[i] = new PegArray(PEGS_IN_CODE);  // Initialize each element
        }
    }

    /**
     * The main entry point for the program, starting the MasterMind game.
     * Initializes the game and runs the main game loop.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        MasterMind m = new MasterMind();
        m.run();
    }

    /**
     * Runs the game by printing the introduction, creating the master code,
     * and initiating the game loop for player guesses.
     */
    public void run() {
        printIntroduction();
        
        // Prompt the user to hit the Enter key to start the game
        String s = Prompt.getString("Hit the Enter key to start the game");
        
        createMasterCode();
        printBoard();  // Show the initial board before the first guess
        playGame();
    }

    /**
     * Manages the overall game loop, allowing the player to make guesses
     * and checking for win conditions after each guess.
     */
    public void playGame() {
        boolean hasWon = false;
        
        for (int i = 0; i < MAX_GUESSES; i++) {
            playTurn(i);
            int exactMatches = guesses[i].getExactMatches(master);
    
            // Check if the guess is an exact match to the master code
            if (exactMatches == PEGS_IN_CODE) {
                hasWon = true;
                System.out.println("Congratulations! You've guessed the master code!");
                break;  // End the loop if the code is guessed correctly
            }
        }
    
        if (!hasWon) {
            reveal = true;
            System.out.println("Oops. You were unable to find the solution in 10 guesses.");
        }
    }

    /**
     * Handles the logic for a single turn of the game.
     * Retrieves the user's guess, calculates exact and partial matches,
     * and updates the game board with the current guess.
     * 
     * @param turn the current turn number
     */
    public void playTurn(int turn) {
        System.out.println("\nGuess " + (turn + 1));
        PegArray userGuess = getUserCode();  // Get the user's guess
        guesses[turn] = userGuess;  // Store the guess in the guesses array
        
        // Calculate exact and partial matches for this turn and store them in the PegArray
        int exactMatches = guesses[turn].getExactMatches(master);
        int partialMatches = guesses[turn].getPartialMatches(master);
        
        guesses[turn].setExact(exactMatches);
        guesses[turn].setPartial(partialMatches);
        
        printBoard();  // Print the game board with the current guess and updates
        
        // New feature: Print a motivational message after each guess
        printMotivationalMessage();
    }

    /**
     * Creates the master code by rolling dice to select peg letters,
     * and initializes the master PegArray with these values.
     */
    public void createMasterCode() {
        Dice dice = new Dice();
        master = new PegArray(PEGS_IN_CODE);
        for (int i = 0; i < PEGS_IN_CODE; i++) {
            int rollValue = dice.roll();
            char pegLetter = (char)(64 + rollValue);
            master.getPeg(i).setLetter(pegLetter);
			System.out.print(master.getPeg(i).getLetter());
        }
    }

    /**
     * Retrieves a valid user input code, ensuring it consists of four characters
     * that are either A, B, C, D, E, or F. Input is case-insensitive.
     * 
     * @return a PegArray representing the user's code
     */
    public PegArray getUserCode() {
        String code;
        boolean valid;
        do {
            code = Prompt.getString("\nEnter the code using (A,B,C,D,E,F). For example, ABCD or abcd from left-to-right");
            code = code.toUpperCase();  
            
            valid = isValidCode(code);  
            
            if (!valid) {
                System.out.println("ERROR: Bad input, try again.");
            }
            
        } while (!valid);
        
        PegArray userCode = new PegArray(PEGS_IN_CODE);
        for (int i = 0; i < PEGS_IN_CODE; i++) {
            userCode.getPeg(i).setLetter(code.charAt(i));  
        }
    
        return userCode;  
    }

    /**
     * Validates the user input code to ensure it meets the game's criteria.
     * 
     * @param code the input code to validate
     * @return true if the code is valid, false otherwise
     */
    private boolean isValidCode(String code) {
        if (code.length() != PEGS_IN_CODE) {
            return false;  
        }
    
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c < 'A' || c > 'F') {
                return false;  
            }
        }
    
        return true;  
    }

    /**
     * Prints the introduction screen to welcome players and explain the rules of the game.
     */
    public void printIntroduction() {
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println("| ___  ___             _              ___  ___ _             _                       |");
        System.out.println("| |  \\/  |            | |             |  \\/  |(_)           | |                      |");
        System.out.println("| | .  . |  __ _  ___ | |_  ___  _ __ | .  . | _  _ __    __| |                      |");
        System.out
                .println("| | |\\/| | / _` |/ __|| __|/ _ \\| '__|| |\\/| || || '_ \\  / _` |                      |");
        System.out.println("| | |  | || (_| |\\__ \\| |_|  __/| |   | |  | || || | | || (_| |                      |");
        System.out.println(
                "| \\_|  |_/ \\__,_||___/ \\__|\\___||_|   \\_|  |_/|_||_| |_| \\__,_|                      |");
        System.out.println("|                                                                                    |");
        System.out.println("| WELCOME TO MONTA VISTA MASTERMIND!                                                 |");
        System.out.println("|                                                                                    |");
        System.out.println("| The game of MasterMind is played on a four-peg gameboard, and six peg letters can  |");
        System.out.println("| be used.  First, the computer will choose a random combination of four pegs, using |");
        System.out.println("| some of the six letters (A, B, C, D, E, and F).  Repeats are allowed, so there are |");
        System.out.println("| 6 * 6 * 6 * 6 = 1296 possible combinations.  This \"master code\" is then hidden     |");
        System.out.println("| from the player, and the player starts making guesses at the master code.  The     |");
        System.out.println("| player has 10 turns to guess the code.  Each time the player makes a guess for     |");
        System.out.println("| the 4-peg code, the number of exact matches and partial matches are then reported  |");
        System.out.println("| back to the user. If the player finds the exact code, the game ends with a win.    |");
        System.out.println("| If the player does not find the master code after 10 guesses, the game ends with   |");
        System.out.println("| a loss.                                                                            |");
        System.out.println("|                                                                                    |");
        System.out.println("| LET'S PLAY SOME MASTERMIND!                                                        |");
        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println("\n");
    }

    /**
     * Prints the current state of the peg board, including the master code
     * and all guesses made by the player.
     */
    public void printBoard() {

        // Print header
        System.out.print("+--------+");
        for (int a = 0; a < PEGS_IN_CODE; a++)
            System.out.print("-------+");
        System.out.println("---------------+");
        System.out.print("| MASTER |");
        for (int a = 0; a < PEGS_IN_CODE; a++)
            if (reveal)
                System.out.printf("   %c   |", master.getPeg(a).getLetter());
            else
                System.out.print("  ***  |");
        System.out.println(" Exact Partial |");
        System.out.print("|        +");
        for (int a = 0; a < PEGS_IN_CODE; a++)
            System.out.print("-------+");
        System.out.println("               |");
        // Print Guesses
        System.out.print("| GUESS  +");
        for (int a = 0; a < PEGS_IN_CODE; a++)
            System.out.print("-------+");
        System.out.println("---------------|");
        for (int g = 0; g < MAX_GUESSES - 1; g++) {
            printGuess(g);
            System.out.println("|        +-------+-------+-------+-------+---------------|");
        }
        printGuess(MAX_GUESSES - 1);
        // Print bottom
        System.out.print("+--------+");
        for (int a = 0; a < PEGS_IN_CODE; a++)
            System.out.print("-------+");
        System.out.println("---------------+");
    }

    /**
     * Prints one guess line to the screen, showing the current guess
     * along with the number of exact and partial matches.
     * 
     * @param t the index of the guess turn
     */
    public void printGuess(int t) {
        System.out.printf("|   %2d   |", (t + 1));
        char c = guesses[t].getPeg(0).getLetter();
        if (c >= 'A' && c <= 'F')
            for (int p = 0; p < PEGS_IN_CODE; p++)
                System.out.print("   " + guesses[t].getPeg(p).getLetter() + "   |");
        else
            for (int p = 0; p < PEGS_IN_CODE; p++)
                System.out.print("       |");
        System.out.printf("   %d      %d    |\n",
                guesses[t].getExact(), guesses[t].getPartial());
    }

    /**
     * Prints a motivational message to encourage the player.
     */
    public void printMotivationalMessage() {
        System.out.println("Keep going! You're doing great!");
    }
}

