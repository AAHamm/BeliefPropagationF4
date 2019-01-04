package Junk;

import Structures.F4;
import Structures.Vector;

import java.util.ArrayList;
import java.util.Scanner;

public class SNLarge {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int amtLines = Integer.parseInt(sc.nextLine());
        ArrayList<Vector> lines = new ArrayList<Vector>();
        for (int j = 0; j < amtLines; j++) {


            String a = sc.nextLine();

            Vector m = new Vector(a.length());

            for (int i = 0; i < a.length(); i++) {
                if(a.charAt(i) == '0'){
                    m.set(i, F4.ZERO);
                }
                else if(a.charAt(i) == '1'){
                    m.set(i, F4.ONE);
                }
                else if(a.charAt(i) == 'w'){
                    m.set(i, F4.OMEGA);
                }
                else if(a.charAt(i) == '3'){// <------------------------- DANGER STRANGER
                    m.set(i, F4.OMEGASQ);
                }
            }

            lines.add(m);
        }


        int codeAmt = (int) Math.pow(2, lines.size());


        int cols = lines.size();
        int rows = (int) Math.pow(2, cols);

        int[][] permutations = new int[rows][cols];

        for (int i = 0; i < cols; i++) {

            int alternation = (int) Math.pow(2, i);
            if(alternation == 0)
                alternation = 1;
            int count = 0;
            int variable = 0;

            for (int j = 0; j <rows; j++) {

                if (count >= alternation){
                    count=0;
                    variable = (variable+1) % 2;
                }

                permutations[j][i] = variable;

                count++;

            }

        }
        /*
        for (int i = 0; i < permutations.length; i++) {

            for (int j = 0; j < permutations[i].length; j++) {
                System.out.print(permutations[i][j] + " ");
            }
            System.out.print(" : "+ i);
            System.out.println();
        }
        */

        Vector[] codeWords = new Vector[rows];

        for (int i = 0; i < permutations.length; i++) {

            Vector sum = new Vector(lines.size());

            for (int j = 0; j < permutations[i].length; j++) {
                if(permutations[i][j] == 1){
                    sum = sum.add(lines.get(j));
                }
            }
            codeWords[i] = sum;

        }
        /*
        System.out.println(permutations.length);
        System.out.println(codeWords.length);
        System.out.println(codeWords.length == (int) Math.pow(2, 6));
        */
        int[][] generator = new int[lines.size()][lines.get(0).size()];
        for (int i = 0; i < lines.size(); i++) {
            generator[i] = lines.get(i).toArray();
        }

        int[][] codes = new int[codeWords.length][codeWords[0].size()];
        for (int i = 0; i < codes.length ; i++) {
            codes[i] = codeWords[i].toArray();
        }

        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes[i].length; j++) {
                System.out.print(codes[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        ArrayList<SuperNode> nodes = new ArrayList<SuperNode>();

        for (int i = 0; i < lines.size(); i++) {
            nodes.add(new SuperNode(i, codes, generator, new double[]{1,1,1,1}));
        }

        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < nodes.size(); i++) {
            adjList.add(nodes.get(i).getNeighbours());
        }




        for (int i = 0; i <amtLines ; i++) {
            nodes.get(i).setSoftInfo(new double[]{0.1, 0.8, 0.05, 0.05});
        }

        nodes.get(0).setSoftInfo(new double[]{0.1, 0.1, 0.7, 0.1});
        nodes.get(1).setSoftInfo(new double[]{0.1, 0.1, 0.7, 0.1});
        nodes.get(2).setSoftInfo(new double[]{0.7, 0.1, 0.1, 0.1});
        nodes.get(3).setSoftInfo(new double[]{0.25, 0.25, 0.25, 0.25});
        nodes.get(4).setSoftInfo(new double[]{0.25, 0.25, 0.25, 0.25});
        nodes.get(5).setSoftInfo(new double[]{0.7, 0.1, 0.1, 0.1});






        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < adjList.size(); j++) {
                for (int k = 0; k < adjList.get(j).size(); k++) {
                    if(nodes.get(j).id != adjList.get(j).get(k)) {
                        double[] m = nodes.get(j).createBeliefAbout(adjList.get(j).get(k));
                        nodes.get(adjList.get(j).get(k)).setFactorBelief(j, m);

                        double[] m2 = nodes.get(j).createMyBeliefs(adjList.get(j).get(k));
                        nodes.get(adjList.get(j).get(k)).setVariableBelief(j, m2);

                    }
                }
            }
        }

        double[] ambigous = nodes.get(0).createBeliefAbout(2);

        for (int i = 0; i < 4; i++) {
            System.out.println(ambigous[i]);
        }

        System.out.println();

        for (int i = 0; i < nodes.size(); i++) {
            System.out.print("Node" + i + ": ");
            double[] marginal = nodes.get(i).marginal();
            for (int j = 0; j < 4; j++) {

                System.out.print(marginal[j] + " ");
            }
            System.out.print("Most likely bit: ");
            if(marginal[0] > marginal[1] && marginal[0] > marginal[2] && marginal[0] > marginal[3]){
                System.out.print(0);
            }
            else if(marginal[1] > marginal[0] && marginal[1] > marginal[2] && marginal[1] > marginal[3]){
                System.out.print(1);
            }
            else if(marginal[2] > marginal[1] && marginal[2] > marginal[0] && marginal[2] > marginal[3]){
                System.out.print("w");
            }
            else if(marginal[3] > marginal[1] && marginal[3] > marginal[2] && marginal[3] > marginal[0]){
                System.out.print("w^2");
            }
            System.out.println();
        }


    }
}
