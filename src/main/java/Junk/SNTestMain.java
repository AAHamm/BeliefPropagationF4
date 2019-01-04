package Junk;

import Structures.F4;
import Structures.Vector;

import java.util.ArrayList;
import java.util.Scanner;

public class SNTestMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Vector> lines = new ArrayList<Vector>();
        for (int j = 0; j < 3; j++) {


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

            // solve test case and output answer
        /*
        int[][] generator = new int[lines.size()][lines.get(0).size()];

        for (int i = 0; i < generator.length; i++) {
            for (int j = 0; j < generator.length; j++) {
                generator[i][j] = lines.get(i)[j];
            }
        }
        */

        int codeAmt = (int) Math.pow(2, lines.size());


        int cols = lines.size();
        int rows = (int) Math.pow(2, cols);

        int[][] permutations = new int[rows][cols];

        for (int i = 0; i < cols; i++) {

            int alternation = i*2;
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


        SuperNode n0 = new SuperNode(0,codes,generator,new double[]{0.033, 0.033, 0.034, 0.9});
        SuperNode n1 = new SuperNode(1,codes,generator,new double[]{0.1, 0.1, 0.1, 0.7});
        SuperNode n2 = new SuperNode(2,codes,generator,new double[]{0.2, 0.2, 0.45, 0.15});

        nodes.add(n0); nodes.add(n1); nodes.add(n2);




        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();

        adjList.add(n0.getNeighbours());
        adjList.add(n1.getNeighbours());
        adjList.add(n2.getNeighbours());



        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.print(n0.createBeliefAbout(1)[i] + " ");
        }

        for (int i = 0; i < 10; i++) {
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


        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.print(n0.marginal()[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < 4; i++) {
            System.out.print(n1.marginal()[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.print(n2.marginal()[i] + " ");
        }

    }

}
