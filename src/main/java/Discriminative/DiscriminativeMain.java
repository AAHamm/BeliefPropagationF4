package Discriminative;

import Noise.AWGN;
import Structures.BeliefVector;

import java.util.ArrayList;

public class DiscriminativeMain {
    public static void main(String[] args) {

        /*

        Scanner sc = new Scanner(System.in);
        int amtLines = Integer.parseInt(sc.nextLine());
        int[][] adjacencyMatrix = new int[amtLines][amtLines];
        for (int j = 0; j < amtLines; j++) {


            String a = sc.nextLine();

            for (int i = 0; i < a.length(); i++) {
                if(a.charAt(i) == '0'){
                    adjacencyMatrix[j][i] = 0;
                }
                else if(a.charAt(i) == '1'){
                    adjacencyMatrix[j][i] = 1;
                }
                else if(a.charAt(i) == 'w'){
                    adjacencyMatrix[j][i] = 2;
                }
                else if(a.charAt(i) == '3'){// <------------------------- DANGER STRANGER
                    adjacencyMatrix[j][i] = 3;
                }
            }
        }

        for (int i = 0; i < amtLines; i++) {
            for (int j = 0; j < amtLines; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }


        DiscriminativeDecoder decoder = new DiscriminativeDecoder(adjacencyMatrix);

        */

        DiscriminativeDecoder decoder = new DiscriminativeDecoder();

        String w = "w11";
        //w = "00000000000000000000";

        for (int E_b = 1; E_b < 10; E_b++) {

            int bitErrors = 0;
            int bits = 0;


            for (int iteration = 0; iteration < 100; iteration++) {
                bits+= decoder.nodes.size();


                for (int i = 0; i < decoder.nodes.size(); i++) {

                    decoder.clear();

                    double[] d = AWGN.probability(0, 0.5, E_b, 3.0);
                    if(E_b == 2 && iteration == 0 && i == 0){
                        System.out.println(d[0] +" "+ d[1]);
                    }
                    if (w.charAt(i) == 'w') {
                        decoder.setBelief(i, new BeliefVector(d[1], d[1], d[0], d[1]));
                    }
                    if (w.charAt(i) == '1') {
                        decoder.setBelief(i, new BeliefVector(d[1], d[0], d[1], d[1]));
                    } else {
                        decoder.setBelief(i, new BeliefVector(d[0], d[1], d[1], d[1]));
                    }
                    //decoder.setBelief(i, new BeliefVector(0.97,0.1,0.1,0.1));
                }



                for (int i = 0; i < 50; i++) {
                    decoder.flood();
                }


                ArrayList<BeliefVector> marginals = decoder.marginals();

                for (int i = 0; i < w.length(); i++) {
                    if(w.charAt(i) == 'w'){
                        if(marginals.get(i).greatestValue() != 2){
                            bitErrors++;
                        }
                    }
                    if(w.charAt(i) == '1'){
                        if(marginals.get(i).greatestValue() != 1){
                            bitErrors++;
                        }
                    }
                    if(w.charAt(i) == '0'){
                        if(marginals.get(i).greatestValue() != 0){
                            bitErrors++;
                        }
                    }
                }


            /*
            for (int i = 0; i < marginals.size(); i++) {
                System.out.println();
                System.out.print("X" + i + ": (");

                int largest = 1;
                double val = 0;



                for (int j = 0; j < 4; j++) {
                    System.out.print(marginals.get(i).get(j) + ", ");
                    if (marginals.get(i).get(j) > val) {
                        largest = j;
                        val = marginals.get(i).get(j);
                    }
                }

                System.out.print(")");
                if (largest == 0)
                    System.out.println("Most probable bit: 0");
                if (largest == 1)
                    System.out.println("Most probable bit: 1");
                if (largest == 2)
                    System.out.println("Most probable bit: w");
                if (largest == 3)
                    System.out.println("Most probable bit: w^2  ");


            }
            */

            }
            System.out.println("Bits " + bits +  ", BitErrors: " + bitErrors);
        }

       // System.out.println(decoder.getNodes().get(0).isSuperInternal());

       // System.out.println(decoder.getNodes().get(1).isLeafNode());

    }
}
