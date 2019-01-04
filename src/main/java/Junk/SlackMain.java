package Junk;

import Discriminative.DiscriminativeDecoder;
import Structures.BeliefVector;

public class SlackMain {

    public static void main(String[] args) {
        DiscriminativeDecoder decoder = new DiscriminativeDecoder();
        //decoder.initConfidentZeroCodeWord();
        decoder.initConfidentCodeWord("0001001001001000111w");
        decoder.setBelief(0,new BeliefVector(0.3, 0.3, 0.3, 0.1));
        decoder.setBelief(6, new BeliefVector(0.3, 0.3, 0.3, 0.1));
        decoder.setBelief(11, new BeliefVector(0.3, 0.3, 0.3, 0.1));

        BeliefVector[][] differences = decoder.avrageDeltaProbabilityPerFlooding(50);

        for (int i = 0; i < differences[0].length; i++) {

            for (int j = 0; j < differences.length; j++) {
                System.out.print((differences[j][i].get(0) + differences[j][i].get(1) + differences[j][i].get(2) + differences[j][i].get(3))/4 + "; ");
            }
            System.out.println();
            //System.out.println(differences[i].toString());
           // System.out.println("avg diff after " + (i+1) +" flooding iterations: " + (differences[0][i].get(0) + differences[0][i].get(1) + differences[0][i].get(2) + differences[0][i].get(3))/4);
        }

        for (int i = 0; i < decoder.nodes.size() ; i++) {
            BeliefVector marginal = decoder.nodes.get(i).marginal();

            if(marginal.get(0) > marginal.get(1) && marginal.get(0) > marginal.get(2) && marginal.get(0) > marginal.get(3))
                System.out.print("0; ");
            else if (marginal.get(1) > marginal.get(0) && marginal.get(1) > marginal.get(2) && marginal.get(1) > marginal.get(3))
                System.out.print("1; ");
            else if (marginal.get(2) > marginal.get(0) && marginal.get(2) > marginal.get(1) && marginal.get(2) > marginal.get(3))
                System.out.print("w; ");
            else if (marginal.get(3) > marginal.get(0) && marginal.get(3) > marginal.get(2) && marginal.get(1) > marginal.get(1))
                System.out.print("w^2; ");
        }

    //    System.out.println();

    //    System.out.println(decoder.StringMarginals());


    }



}
