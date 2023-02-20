package util;

import io.restassured.response.Response;

import java.util.Random;

public class Util {

    public static String responseToString(Response response){
        return response.getBody().asString();
    }

    public static String generateRandomString(int length, Random random) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }
}
