package Noise;

public class WordCompare {

    public static int compare(String in, String out){

        int diff = 0;

        for (int i = 0; i < in.length(); i++) {
            if(in.charAt(i) != out.charAt(i))
                diff++;
        }

        return diff;
    }
}
