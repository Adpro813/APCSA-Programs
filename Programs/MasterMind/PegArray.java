/**
 * This class creates and manages an array of pegs used in the MasterMind game.
 * It provides methods to track and evaluate user guesses against a master code.
 * 
 * @author Aditya Dendukuri
 * @since 9/27
 */
public class PegArray {

    private Peg[] pegs;  // Array of pegs
    private int exactMatches, partialMatches;  // Exact and partial match counts

    /**
     * Constructor to initialize the PegArray with a specified number of pegs.
     * 
     * @param numPegs the number of pegs in the array
     */
    public PegArray(int numPegs) {
        pegs = new Peg[numPegs];
        for (int i = 0; i < numPegs; i++) {
            pegs[i] = new Peg();
        }
    }

    /**
     * Returns the peg object at the specified index.
     * 
     * @param n the peg index in the array
     * @return the peg object at the specified index
     */
    public Peg getPeg(int n) {
        return pegs[n];
    }

    /**
     * Finds exact matches between this peg array and the master peg array.
     * An exact match occurs when both the color and position of the pegs are the same.
     * 
     * @param master the master (code) peg array to compare against
     * @return the number of exact matches found
     */
    public int getExactMatches(PegArray master) {
        exactMatches = 0;  
        for (int i = 0; i < pegs.length; i++) {
            char userChar = pegs[i].getLetter();
            char masterChar = master.getPeg(i).getLetter();
            if (userChar == masterChar) {
                exactMatches++;
            }
        }
        return exactMatches;
    }

    /**
     * Finds partial matches between this peg array and the master peg array.
     * A partial match occurs when a peg color matches but is in the wrong position.
     * 
     * @param master the master (code) peg array to compare against
     * @return the number of partial matches found
     */
    public int getPartialMatches(PegArray master) {
        partialMatches = 0;
        boolean[] matchedUser = new boolean[pegs.length];  
        boolean[] matchedMaster = new boolean[pegs.length];  

        for (int i = 0; i < pegs.length; i++) {
            if (pegs[i].getLetter() == master.getPeg(i).getLetter()) {
                matchedUser[i] = true;     
                matchedMaster[i] = true;   
                exactMatches++;
            }
        }

        for (int i = 0; i < pegs.length; i++) {
            if (!matchedUser[i]) {
                for (int j = 0; j < pegs.length; j++) {
                    if (!matchedMaster[j] && pegs[i].getLetter() == master.getPeg(j).getLetter()) {
                        partialMatches++;
                        matchedMaster[j] = true;  
                        break;  
                    }
                }
            }
        }

        return partialMatches;
    }

    /**
     * Accessor method to retrieve the number of exact matches found.
     * 
     * @return the number of exact matches
     */
    public int getExact() {
        return exactMatches;
    }

    /**
     * Accessor method to retrieve the number of partial matches found.
     * 
     * @return the number of partial matches
     */
    public int getPartial() {
        return partialMatches;
    }

    /**
     * Sets the number of exact matches for this peg array.
     * 
     * @param exact the number of exact matches to set
     */
    public void setExact(int exact) {
        this.exactMatches = exact;
    }

    /**
     * Sets the number of partial matches for this peg array.
     * 
     * @param partial the number of partial matches to set
     */
    public void setPartial(int partial) {
        this.partialMatches = partial;
    }
}
