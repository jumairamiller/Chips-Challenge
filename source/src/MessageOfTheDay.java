import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Retrieves the message of the day from the API by solving the puzzles given in the API
 * <br> Creation Date: 25/11/19.
 * <br> Last Modification Date: 26/11/19.
 * @author Nihal Goindi (976005)
 * @version 1.1
 */

public class MessageOfTheDay {

    private static final String PUZZLE_URL = "http://cswebcat.swan.ac.uk/puzzle";
    private static String messageURL = "http://cswebcat.swan.ac.uk/message?solution=";

    public static void main(String[] args) throws Exception {
        System.out.println(getRequest(PUZZLE_URL));
        System.out.println(getDecodedText());
        System.out.println(getRequest(messageURL + getDecodedText()));
    }

    /**
     * Issues a HTTP get request to URL and reads the text on the web page.
     * @param url - the webpage that we want to access.
     * @return text, i.e. the message to decode.
     * @throws Exception - If getRequest can't establish a connection.
     */
    public static String getRequest(String url) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        //Reads the text on the page
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {

            StringBuilder text = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                text.append(line);
            }

            connection.disconnect();

            return text.toString();
        }

    }


    /**
     * Algorithm used to decode the text retrieved from the API.
     * @param puzzle String - the unsolved key that will be solved to find the message.
     * @return solution, the decoded string of characters.
     *         from the API which can be used to access the message.
     */
    private static String solvePuzzle(String puzzle) {

        String solution = "";

        //iterates through each char in the puzzle
        for (int i = 0; i < puzzle.length(); i++) {

            char c = puzzle.charAt(i);

            //z goes to the ascii char before A before incrementing
            if(i % 2 == 0) {
                int asciiVal = (int) c;
                if(asciiVal == 90){
                    asciiVal = 64;
                }

                asciiVal ++;
                char newLetter = (char) asciiVal;
                solution += newLetter;

            }
            //A goes to ascii char after z before decrementing
            else {
                int asciiVal = (int) c;
                if(asciiVal == 65) {
                    asciiVal = 91;
                }
                asciiVal --;
                char newLetter = (char) asciiVal;
                solution += newLetter;
            }

        }
        return solution;
    }

    /**
     * This uses both methods above to retrieve the decoded message from the API.
     * @return the decoded message.
     * @throws Exception - If getRequest can't establish a connection.
     */
    public static String getDecodedText() throws Exception {
        return(solvePuzzle(getRequest(PUZZLE_URL)));
    }


}