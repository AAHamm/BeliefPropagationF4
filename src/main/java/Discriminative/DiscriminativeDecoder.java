package Discriminative;

import Structures.BeliefVector;

import java.util.ArrayList;
import java.util.Scanner;

public class DiscriminativeDecoder {

    public ArrayList<DNode> nodes = new ArrayList<DNode>();
    int[][] adjacencyMatrix;

    public DiscriminativeDecoder(int[][] adjacencyMatrix){


        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodes.add(new DNode(i));
        }

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0+i; j < adjacencyMatrix.length; j++) {
                if(adjacencyMatrix[i][j] == 1){
                  //  System.out.println("added Neighbours " + nodes.get(i).getId() + " and " + nodes.get(j).getId());
                    nodes.get(i).addNeighbour(nodes.get(j));
                    nodes.get(j).addNeighbour(nodes.get(i));
                }
            }
        }

        this.adjacencyMatrix = adjacencyMatrix;

    }

    public DiscriminativeDecoder(){
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
        /*
        for (int i = 0; i < amtLines; i++) {
            for (int j = 0; j < amtLines; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
        */

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodes.add(new DNode(i));
        }

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0+i; j < adjacencyMatrix.length; j++) {
                if(adjacencyMatrix[i][j] == 1){
                //    System.out.println("added Neighbours " + nodes.get(i).getId() + " and " + nodes.get(j).getId());
                    nodes.get(i).addNeighbour(nodes.get(j));
                    nodes.get(j).addNeighbour(nodes.get(i));
                }
            }
        }

        this.adjacencyMatrix = adjacencyMatrix;


    }

    public void flood(){
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0+i; j < adjacencyMatrix[0].length; j++) {
                if(adjacencyMatrix[i][j] == 1){
                    DNode node1 = nodes.get(i);
                    DNode node2 = nodes.get(j);

                    BeliefVector m1 = node1.createMessage(node2);
                    BeliefVector m2 = node2.createMessage(node1);

                    //System.out.println("From " + node1.getId() +" to " + node2.getId() + ": " + m1.get(0) + ", " + m1.get(1) + ", " + m1.get(2) + ", " + m1.get(3));
                    //System.out.println("From " + node2.getId() +" to " + node1.getId() + ": " + m2.get(0) + ", " + m2.get(1) + ", " + m2.get(2) + ", " + m2.get(3));

                    node2.setMessage(m1, node1);
                    node1.setMessage(m2, node2);
                }
            }
        }
    }

    public void flood(int amt){
        for (int i = 0; i < amt; i++) {
            flood();
        }
    }

    public BeliefVector[][] avrageDeltaProbabilityPerFlooding(int floodings){
        BeliefVector[][] diff = new BeliefVector[adjacencyMatrix.length][floodings];


        BeliefVector[] prev = new BeliefVector[adjacencyMatrix.length];

        for (int i = 0; i < prev.length; i++) {
            prev[i] = nodes.get(i).beliefs;
        }



        for (int i = 0; i < floodings; i++) {
            flood();

            for (int j = 0; j <prev.length ; j++) {


                BeliefVector curr = nodes.get(j).marginal();

                BeliefVector difference = new BeliefVector();

                for (int k = 0; k < 4; k++) {
                    difference.set(k, Math.abs(prev[j].get(k) - curr.get(k)));
                }
                diff[j][i] = difference;
                prev[j] = curr;
            }
        }

        return diff;
    }

    public double[][] deltaPerFloodingPerNode(int flodings){
        double[][] diff = new double[flodings][adjacencyMatrix.length];


        BeliefVector[] prev = new BeliefVector[adjacencyMatrix.length];

        for (int i = 0; i < prev.length; i++) {
            prev[i] = nodes.get(i).beliefs;
        }



        for (int i = 0; i < flodings; i++) {
            flood();

            for (int j = 0; j <prev.length ; j++) {


                BeliefVector curr = nodes.get(j).marginal();

                BeliefVector difference = new BeliefVector();

                for (int k = 0; k < 4; k++) {
                    difference.set(k, Math.abs(prev[j].get(k) - curr.get(k)));
                }
                double sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum+=difference.get(k);
                }
                sum/=4;
                diff[i][j] = sum;
                prev[j] = curr;
            }
        }

        return diff;
    }


    public void initConfidentZeroCodeWord(){
        for (DNode n: nodes) {
            n.setBeliefs(new BeliefVector(0.7,0.05,0.075,0.175));
        }
    }

    public void initConfidentCodeWord(String word){
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == '0'){
                nodes.get(i).setBeliefs(new BeliefVector(0.7, 0.1, 0.1, 0.1));
            }
            else if(word.charAt(i) == '1'){
                nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.7, 0.1, 0.1));
            }
            else if(word.charAt(i) == 'w'){
                nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.7, 0.1));
            }
        }
    }

    public ArrayList<BeliefVector> marginals(){

        ArrayList<BeliefVector> marginals = new ArrayList<BeliefVector>();

        for (int i = 0; i < nodes.size(); i++) {
            BeliefVector v = nodes.get(i).marginal();
            v.normalize();
            marginals.add(v);
        }

        return marginals;
    }

   public void setBelief(int node, BeliefVector v){
        nodes.get(node).setBeliefs(v);
    }

    public ArrayList<DNode> getNodes() {
        return nodes;
    }

    public void clear(){
        for (DNode n: nodes) {
            n.clear();
        }
    }

    public String StringMarginals(){
        ArrayList<BeliefVector> m = marginals();

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
        return out;

    }

    public int size(){
        return nodes.size();
    }

}
