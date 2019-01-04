package Junk;

import Structures.BeliefVector;

import java.util.ArrayList;
import java.util.Scanner;

public class GDecoder {
    ArrayList<GNode> nodes = new ArrayList<GNode>();
    int[][] adjacencyMatrix;

    public GDecoder(int[][] adjacencyMatrix){


        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodes.add(new GNode(i));
        }

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0+i; j < adjacencyMatrix.length; j++) {
                if(adjacencyMatrix[i][j] == 1){
                    System.out.println("added Neighbours " + nodes.get(i).getId() + " and " + nodes.get(j).getId());
                    nodes.get(i).addNeighbour(nodes.get(j));
                    nodes.get(j).addNeighbour(nodes.get(i));
                }
            }
        }

        this.adjacencyMatrix = adjacencyMatrix;

    }

    public GDecoder(){
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

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodes.add(new GNode(i));
        }

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0+i; j < adjacencyMatrix.length; j++) {
                if(adjacencyMatrix[i][j] == 1){
                    System.out.println("added Neighbours " + nodes.get(i).getId() + " and " + nodes.get(j).getId());
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
                    GNode node1 = nodes.get(i);
                    GNode node2 = nodes.get(j);

                    BeliefVector m1 = node1.createMessage(node2);
                    BeliefVector m2 = node2.createMessage(node1);

                    m1.normalize();
                    m2.normalize();

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

    public void initConfidentZeroCodeWord(){
        for (GNode n: nodes) {
            n.setBeliefs(new BeliefVector(1,0,0,0));
        }
    }

    public ArrayList<BeliefVector> marginals(){

        ArrayList<BeliefVector> marginals = new ArrayList<BeliefVector>();

        for (int i = 0; i < nodes.size(); i++) {
            marginals.add(nodes.get(i).marginal());
        }

        return marginals;
    }

    public void setBelief(int node, BeliefVector v){
        nodes.get(node).setBeliefs(v);
    }

    public ArrayList<GNode> getNodes() {
        return nodes;
    }

    public void clear(){
        for (GNode n: nodes) {
            n.clear();
        }
    }

    public String StringMarginals(){
        ArrayList<BeliefVector> m = marginals();

        String out = "";
        for (int i = 0; i < m.size(); i++) {
            out+= "x" + i + ": (" + m.get(i).get(0) + ", " + m.get(i).get(1) + ", " + m.get(i).get(2) + ", " + m.get(i).get(3) + ")\n";
        }
        return out;

    }

    public int size(){
        return nodes.size();
    }
}
