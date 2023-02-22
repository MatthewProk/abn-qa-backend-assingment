package util;

import io.restassured.response.Response;

import java.util.Random;

public class Util {

    /**
     * Converts the body of the given response to a string.
     *
     * @param response the response whose body to convert to a string
     * @return the body of the response as a string
     */
    public static String responseToString(Response response) {
        return response.getBody().asString();
    }

    /**
     * Generates a random string of the given length using the provided random generator.
     *
     * @param length The length of the generated string.
     * @param random The random generator to use.
     * @return The generated random string.
     */
    public static String generateRandomString(int length, Random random) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

}