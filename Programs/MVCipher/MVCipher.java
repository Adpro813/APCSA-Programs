import java.io.PrintWriter;
import java.util.Scanner;

/**
 * MVCipher -
 * A program that encrypts and decrypts messages using a variation of the Caesar
 * Cipher known as the MV Cipher.
 * Requires Prompt and FileUtils classes.
 * 
 * Objective: To use various datadata types and arithmetic to create an MV
 * Cipher.
 * 
 * @author Aditya Dendukuri
 * @since 2024-09-24
 */
public class MVCipher {

    /**
     * Default constructor.
     */
    public MVCipher() {
        // Constructor logic if necessary
    }

   /**
     * The main method to start the MV Cipher program.
     * 
     */
    public static void main(String[] args) {
        MVCipher mvc = new MVCipher();
        mvc.run();
    }

    /**
     * well.
     */
    public void run() {
        System.out.println("\nWelcome to the MV Cipher machine!\n");

        String key = Prompt.getString("Please input a word to use as key (letters only)");

        while (key.length() < 3 || !key.matches("[a-zA-Z]+")) {
            System.out.println("ERROR: Key must be all letters and at least 3 characters long");
            key = Prompt.getString("Please input a word to use as key (letters only)");
        }


        key = key.toUpperCase();
        System.out.println(); // empty line for formatting
        
        int choice = Prompt.getInt("Encrypt or decrypt? (1, 2)", 1, 2);
        if (choice == 1) {
            encrypt(key);
        } else if (choice == 2) {
            decrypt(key);
        } else {
            System.out.println("Invalid choice, exiting.");
        }
    }

    /**
     * Reads the contents of a file into a single string.
     * 
     * @param inputFileName The name of the file to read.
     * @return The contents of the file as a single string.
     */
    public String readFile(String inputFileName) {
        StringBuilder total = new StringBuilder();
        Scanner in = FileUtils.openToRead(inputFileName);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            total.append(line);
            total.append("\n");
        }
        in.close();
        return total.toString();
    }

    /**
     * Processes a string for encryption or decryption using the cipher algorithim.
     * 
     * @param text          The text to be processed.
     * @param key           The encryption/decryption key.
     * @param shouldEncrypt Boolean flag indicating whether to encrypt (true) or
     *                      decrypt (false).
     * @return The processed string.
     */
    public String processString(String text, String key, boolean shouldEncrypt) {
        String total = "";
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            char keyChar = key.charAt(keyIndex);
            char processedChar;

            // Process lowercase letters
            if (currentChar >= 'a' && currentChar <= 'z') {
                processedChar = processLowerCase(currentChar, keyChar, shouldEncrypt);
                keyIndex++;

                // Process uppercase letters
            } else if (currentChar >= 'A' && currentChar <= 'Z') {
                processedChar = processUpperCase(currentChar, keyChar, shouldEncrypt);
                keyIndex++;

            } else {
                processedChar = currentChar;
            }

            total += processedChar;

            // resest the key index when it exceeds the key length
            if (keyIndex >= key.length()) {
                keyIndex = 0;
            }
        }

        return total.toString();
    }

    /**
     * Processes a single uppercase character for encryption or decryption.
     * 
     * @param currentChar   The character to be processed.
     * @param keyChar       The corresponding character from the key.
     * @param shouldEncrypt Boolean indicating whether to encrypt (true) or decrypt
     *                      (false).
     * @return The processed character.
     */
    public char processUpperCase(char currentChar, char keyChar, boolean shouldEncrypt) {
        int keyInt = keyChar - 'A' + 1;
        if (!shouldEncrypt) {
            keyInt = -keyInt; // the shift for decryption
        }
        return shiftCharacter(currentChar, keyInt, 'A', 'Z');
    }

    /**
     * Processes a single lowercase character for encryption or decryption.
     * 
     * @param currentChar   The character to be processed.
     * @param keyChar       The corresponding character from the key.
     * @param shouldEncrypt Boolean indicating whether to encrypt (true) or decrypt
     *                      (false).
     * @return The processed character.
     */
    public char processLowerCase(char currentChar, char keyChar, boolean shouldEncrypt) {
        int keyInt = keyChar - 'A' + 1;
        if (!shouldEncrypt) {
            keyInt = -keyInt; // Reverse the shift for decryption
        }
        return shiftCharacter(currentChar, keyInt, 'a', 'z');
    }

    /**
     * Shifts a character within a specified range, wrapping around if necessary.
     * 
     * @param ch    The character to shift.
     * @param shift The number of positions to shift the character.
     * @param start The starting character of the range (e.g., 'A' or 'a').
     * @param end   The ending character of the range (e.g., 'Z' or 'z').
     * @return The shifted character.
     */
    public char shiftCharacter(char ch, int shift, char start, char end) {
        int range = end - start + 1;
        int shifted = ch + shift;

        if (shifted > end) {
            shifted = start + (shifted - end - 1);
        } else if (shifted < start) {
            shifted = end - (start - shifted - 1);
        }

        return (char) shifted;
    }

    /**
     * Encrypts the contents of a file using the MV Cipher.
     * 
     * @param key The encryption key.
     */
    public void encrypt(String key) {
        System.out.println();
        String inputFileName = Prompt.getString("Name of file to encrypt");
        String outputFileName = Prompt.getString("Name of output file");
        String fileContent = readFile(inputFileName);
        String encryptedContent = processString(fileContent, key, true);

        // Write the encrypted content to the output file using FileUtils.openToWrite
        PrintWriter out = FileUtils.openToWrite(outputFileName);
        out.print(encryptedContent);
        out.close();

        System.out.println("The encrypted file " + outputFileName + " has been created using the keyword -> " + key);
    }

    /**
     * Decrypts the contents of a file using the MV Cipher.
     * 
     * @param key The decryption key.
     */
    public void decrypt(String key) {
        System.out.println(); // Empty line for formatting
        String inputFileName = Prompt.getString("Name of file to decrypt");
        String outputFileName = Prompt.getString("Name of output file");
        String fileContent = readFile(inputFileName);
        String decryptedContent = processString(fileContent, key, false);

        // Write the decrypted content to the output file using FileUtils.openToWrite
        PrintWriter out = FileUtils.openToWrite(outputFileName);
        out.print(decryptedContent);
        out.close();

        System.out.println("The decrypted file " + outputFileName + " has been created using the keyword -> " + key);
    }

}
