import Discriminative.DiscriminativeDecoder;
import GlobalFunction.GlobalFunctionDecoder;
import LocalComplementation.LCDecoder;
import NonDiscriminative.ADecoder;
import Structures.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String option1 = sc.nextLine();

        if(option1.equals("default")){
            DiscriminativeDecoder d = new DiscriminativeDecoder();

            d.initConfidentZeroCodeWord();

            d.flood(50);

            System.out.println(d.StringMarginals());
        }

        if(option1.equals("alternative")){

            ADecoder decoder = new ADecoder();

            decoder.initConfidentCodeWord("w11");
           // decoder.initConfidentZeroCodeWord();

            decoder.flood(50);


            System.out.println(decoder.StringMarginals());
        }

        if(option1.equals("global")){
            GlobalFunctionDecoder g = new GlobalFunctionDecoder();

           // g.initConfidentZeroCodeword();

            System.out.println(g.codewords());


            double[] c = g.globalMarginals();

            double[][] variables = g.variableMarginals();

            for (int i = 0; i < c.length; i++) {
                System.out.println(c[i]);
            }

            System.out.println();
            for (int i = 0; i < variables.length; i++) {
                System.out.print("x" + i + ": " + "(");
                for (int j = 0; j < variables[0].length; j++) {
                    System.out.print(variables[i][j] +", ");
                }
                System.out.print(")");
                System.out.println();
            }
        }

        if(option1.equals("avgDeltaProbReadable")){//Get human readable difference in marginals per node, per flooding.

            int[][] matrix = MatrixReader.readAdjMatrix();

            DiscriminativeDecoder decoder = new DiscriminativeDecoder(matrix);

            decoder.initConfidentZeroCodeWord();

            BeliefVector[][] avrage = decoder.avrageDeltaProbabilityPerFlooding(10);

            for (int i = 0; i < avrage.length; i++) {
                System.out.println("Node: " + i);

                for (int j = 0; j < avrage[0].length; j++) {
                    System.out.println("iteration "  + j + ": " + avrage[i][j].toString());
                }
                System.out.println();
            }
        }

        if(option1.equals("avgDeltaProbM")){

            int[][] matrix = MatrixReader.readAdjMatrix();

            DiscriminativeDecoder decoder = new DiscriminativeDecoder(matrix);

            decoder.initConfidentCodeWord("w11100");

            decoder.setBelief(0,new BeliefVector(0.25, 0.25, 0.25, 0.25));
            decoder.setBelief(1,new BeliefVector(0.25, 0.25, 0.25, 0.25));



            double[][] avrage = decoder.deltaPerFloodingPerNode(20);

            for (int i = 0; i < avrage.length; i++) {

                for (int j = 0; j < avrage[0].length; j++) {
                    System.out.print(avrage[i][j] + "; ");
                }
                System.out.println();
            }
            System.out.println(decoder.StringMarginals());
        }

        if (option1.equals("codewords")){

            GlobalFunctionDecoder decoder = new GlobalFunctionDecoder();

            System.out.println(decoder.codewords());

            System.out.println("minimum edit distance: " + decoder.minEditDist());
        }

        if(option1.equals("lc")){
            int[][] matrix = MatrixReader.readAdjMatrix();
            LCDecoder decoder= new LCDecoder(matrix);

            decoder.initConfidentCodeWord("w111");

            decoder.LC(0);

            for (int i = 0; i <decoder.getNodes().size() ; i++) {
                System.out.println(decoder.getNodes().get(i).getBeliefs().toString());
            }

        }

        if(option1.equals("lcc")){
            int[][] matrix = MatrixReader.readAdjMatrix();
            LCDecoder decoder= new LCDecoder(matrix);

            decoder.initConfidentCodeWord("w11");

            decoder.LC(0);
            decoder.flood(5);
            ArrayList<BeliefVector> m = decoder.LCmarginals(0);


            String out = "";
            for (int i = 0; i < m.size(); i++) {
                out+= "x" + i + ": (" + m.get(i).get(0) + ", " + m.get(i).get(1) + ", " + m.get(i).get(2) + ", " + m.get(i).get(3) + ")";
                if(m.get(i).greatestValue() == 0){
                    out+= ", decoded to: 0";
                }
                if(m.get(i).greatestValue() == 1){
                    out+= ", decoded to: 1";
                }
                if(m.get(i).greatestValue() == 2){
                    out+= ", decoded to: w";
                }
                if(m.get(i).greatestValue() == 3){
                    out+= ", decoded to: w^2";
                }

                out+= "\n";
            }
            System.out.println(out);
        }
    }
}
