package NonDiscriminative;

import Structures.BeliefVector;
import Structures.F4;

public class AMain {
    public static void main(String[] args) {
        ADecoder decoder = new ADecoder();

        //String w = "w11110001000";

       //decoder.initAWGNWord(w);
        decoder.initConfidentCodeWord("w11");
        //decoder.setBelief(1, new BeliefVector(0.2, 0.2, 0.3, 0.3));
        //decoder.setBelief(6, new BeliefVector(0.2, 0.2, 0.3, 0.3));
        //decoder.setBelief(8, new BeliefVector(0.2, 0.2, 0.3, 0.3));
        //decoder.flood(50);


        System.out.println("Original Beliefs:");
        for (int i = 0; i < decoder.getNodes().size(); i++) {
            BeliefVector v = decoder.getNodes().get(i).beliefs;
            System.out.println(F4.fromIndex(v.greatestValue()).toString() +": " + v.toString());
        }

        System.out.println("Decoded Beliefs:");
        for (int i = 0; i < decoder.getNodes().size(); i++) {
            BeliefVector v = decoder.getNodes().get(i).marginal();
            System.out.println(F4.fromIndex(v.greatestValue()).toString() +": " + v.toString());
        }

    }
}
