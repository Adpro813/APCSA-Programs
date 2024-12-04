import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class responsible for parsing and rendering HTML content into formatted output.
 * It processes an HTML file and outputs formatted HTML content based on predefined tags.
 * The renderer supports elements such as paragraphs, bold, italic, preformatted text, headers, and more.
 * 
 * @author Aditya Dendukuri
 * @since November 28
 */
public class HTMLRender {

    private String[] tokenArray;
    private final int MAX_TOKENS = 100000;

    private SimpleHtmlRenderer render;
    private HtmlPrinter browser;
    private HTMLUtilities htmlUtilities;

    private enum ParserState {
        DEFAULT, BOLD, ITALIC, PRE_FORMATTED, HEADER
    };

    /**
     * Constructor for HTMLRenderer.
     * Initializes the necessary components for HTML rendering.
     */
    public HTMLRender() {
        tokenArray = new String[MAX_TOKENS];
        render = new SimpleHtmlRenderer();
        browser = render.getHtmlPrinter();
        htmlUtilities = new HTMLUtilities();
    }

    /**
     * Main method to run the HTMLRenderer.
     * Takes an input file name as an argument and processes the file.
     *
     * @param arguments Command line arguments, where the first argument is the input file name.
     */
    public static void main(String[] arguments) {
        HTMLRender renderer = new HTMLRender();
        String inputFile = arguments[0];
        renderer.run(inputFile);
    }

    /**
     * Processes the HTML input file and renders the formatted output.
     *
     * @param inputFile The name of the input file to be processed.
     */
    public void run(String inputFile) {
        Scanner scanner;
        ArrayList<String> tokenList = new ArrayList<>();

        // Open the file and read each line
        scanner = FileUtils.openToRead(inputFile);

        while (scanner.hasNext()) {
            String[] extractedTokens = htmlUtilities.tokenizeHTMLString(scanner.nextLine());
            for (String token : extractedTokens) {
                if (token != null) {
                    tokenList.add(token);
                }
            }
        }

        String[] tokens = new String[tokenList.size()];
        for (int i = 0; i < tokenList.size(); i++) {
            tokens[i] = tokenList.get(i);
        }

        int tokenCount = tokens.length;
        int currentIndex = 0;
        ParserState currentParserState = ParserState.DEFAULT;
        int headerLevel = 0;
        int characterCount = 0;
        int wrapLimit = 80;

        // Process each token and render accordingly
        while (currentIndex < tokenCount) {
            String currentToken = tokens[currentIndex];

            if (currentToken != null) {
                switch (currentToken.toLowerCase()) {
                    case "<p>":
                    case "</p>":
                    case "<pre>":
                    case "</pre>":
                    case "<br>":
                        browser.printBreak();
                        characterCount = 0;
                        if (currentToken.equals("<pre>")) {
                            currentParserState = ParserState.PRE_FORMATTED;
                        } else {
                            currentParserState = ParserState.DEFAULT;
                        }
                        break;

                    case "<b>":
                        currentParserState = ParserState.BOLD;
                        break;

                    case "</b>":
                        currentParserState = ParserState.DEFAULT;
                        break;

                    case "<i>":
                        currentParserState = ParserState.ITALIC;
                        break;

                    case "</i>":
                        currentParserState = ParserState.DEFAULT;
                        break;

                    case "<q>":
                        browser.print("\"");
                        break;

                    case "</q>":
                        browser.print("\" ");
                        break;

                    case "<hr>":
                        browser.printHorizontalRule();
                        characterCount = 0;
                        break;

                    case "<h1>":
                    case "<h2>":
                    case "<h3>":
                    case "<h4>":
                    case "<h5>":
                    case "<h6>":
                        currentParserState = ParserState.HEADER;
                        headerLevel = Integer.parseInt(currentToken.substring(2, 3));
                        wrapLimit = 40 + (headerLevel - 1) * 10;
                        characterCount = 0;
                        browser.printBreak();
                        break;

                    case "</h1>":
                    case "</h2>":
                    case "</h3>":
                    case "</h4>":
                    case "</h5>":
                    case "</h6>":
                        currentParserState = ParserState.DEFAULT;
                        wrapLimit = 80;
                        break; 

                    default:
                        if (!currentToken.startsWith("<") && !currentToken.endsWith(">")) {
                            if (currentParserState == ParserState.PRE_FORMATTED) {
                                browser.printPreformattedText(currentToken);
                                browser.printBreak();
                            } else {
                                if (characterCount + currentToken.length() > wrapLimit) {
                                    browser.println();
                                    characterCount = 0;
                                }

                                // Render token based on current state
                                switch (currentParserState) {
                                    case BOLD:
                                        browser.printBold(currentToken);
                                        break;

                                    case ITALIC:
                                        browser.printItalic(currentToken);
                                        break;

                                    case HEADER:
                                        switch (headerLevel) {
                                            case 1:
                                                browser.printHeading1(currentToken);
                                                break;
                                            case 2:
                                                browser.printHeading2(currentToken);
                                                break;
                                            case 3:
                                                browser.printHeading3(currentToken);
                                                break;
                                            case 4:
                                                browser.printHeading4(currentToken);
                                                break;
                                            case 5:
                                                browser.printHeading5(currentToken);
                                                break;
                                            case 6:
                                                browser.printHeading6(currentToken);
                                                break;
                                        }
                                        break;

                                    default:
                                        browser.print(currentToken);
                                        break;
                                }
                            }

                            characterCount += currentToken.length() + 1;
                            // Handle spacing between tokens based on punctuation
                            if (!".,;()?!=&~+:</q></Q>-".contains(tokens[currentIndex + 1])) {
                                if (currentParserState == ParserState.HEADER) {
                                    switch (headerLevel) {
                                        case 1:
                                            browser.printHeading1(" ");
                                            break;
                                        case 2:
                                            browser.printHeading2(" ");
                                            break;
                                        case 3:
                                            browser.printHeading3(" ");
                                            break;
                                        case 4:
                                            browser.printHeading4(" ");
                                            break;
                                        case 5:
                                            browser.printHeading5(" ");
                                            break;
                                        case 6:
                                            browser.printHeading6(" ");
                                            break;
                                    }
                                } else {
                                    browser.print(" ");
                                }
                            }
                        }
                        break;
                }
                currentIndex++;
            }
        }
    }
}