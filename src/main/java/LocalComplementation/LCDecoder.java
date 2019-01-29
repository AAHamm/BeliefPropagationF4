package LocalComplementation;

import Noise.AWGN;
import Structures.BeliefVector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class LCDecoder {
    ArrayList<LCNode> nodes = new ArrayList<LCNode>();
    int[][] adjacencyMatrix;

    public LCDecoder(int[][] adjacencyMatrix){


        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodes.add(new LCNode(i));
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

    public void LC(int lcNode){

        LCNode node = nodes.get(lcNode);

        double one = node.beliefs.get(1);
        double omegaSQ = node.beliefs.get(3);
        node.setBeliefs(new BeliefVector(node.beliefs.get(0), omegaSQ , node.beliefs.get(2), one));

        for (int i = 0; i < node.neighbours.size(); i++)

        {

            double omega = node.neighbours.get(i).beliefs.get(2);
            double omega2 = node.neighbours.get(i).beliefs.get(3);
            node.neighbours.get(i).setBeliefs(new BeliefVector(node.neighbours.get(i).beliefs.get(0),  node.neighbours.get(i).beliefs.get(1), omega2, omega));

            for (int j = i+1; j < node.neighbours.size(); j++) {
                if(node.neighbours.get(i).idToIndex.containsKey(node.neighbours.get(j).id)){
                    node.neighbours.get(i).removeNeighbour(node.neighbours.get(j));
                    node.neighbours.get(j).removeNeighbour(node.neighbours.get(i));
                    adjacencyMatrix[node.neighbours.get(i).id][node.neighbours.get(j).id] = 0;
                    adjacencyMatrix[node.neighbours.get(j).id][node.neighbours.get(i).id] =0;
                }
                else{
                    node.neighbours.get(i).addNeighbour(node.neighbours.get(j));
                    node.neighbours.get(j).addNeighbour(node.neighbours.get(i));
                    adjacencyMatrix[node.neighbours.get(i).id][node.neighbours.get(j).id] = 1;
                    adjacencyMatrix[node.neighbours.get(j).id][node.neighbours.get(i).id] = 1;
                }
            }
        }
    }

    public ArrayList<BeliefVector> LCmarginals(int lcNode){
        LCNode node = nodes.get(lcNode);

        ArrayList<BeliefVector> m = marginals();

        double one = m.get(lcNode).get(1);
        double omegaSq = m.get(lcNode).get(3);
        m.set(lcNode, new BeliefVector(m.get(lcNode).get(0), omegaSq, m.get(lcNode).get(2), one));


        for (LCNode n: node.neighbours) {
            int id = n.getId();

            double omega = m.get(id).get(2);
            double omega2 = m.get(id).get(3);
            m.set(id, new BeliefVector(m.get(id).get(0), m.get(id).get(1), omega2, omega));
        }

        //Flip values back

        /*
        double one = node.beliefs.get(1);
        double omegaSQ = node.beliefs.get(3);
        m.set(lcNode, new BeliefVector(node.beliefs.get(0), omegaSQ , node.beliefs.get(2), one));

        for (int i = 0; i < node.neighbours.size(); i++) {
            int n  = node.neighbours.get(i).id;

            double omega = node.neighbours.get(i).beliefs.get(2);
            double omega2 = node.neighbours.get(i).beliefs.get(3);
            node.neighbours.get(i).setBeliefs(new BeliefVector(node.neighbours.get(i).beliefs.get(0),  node.neighbours.get(i).beliefs.get(1), omega2, omega));

        }
        */

        return m;
    }

    public void flood(){
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i; j < adjacencyMatrix[0].length; j++) {
                if(adjacencyMatrix[i][j] == 1){
                    LCNode node1 = nodes.get(i);
                    LCNode node2 = nodes.get(j);

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


    public void initConfidentZeroCodeWord(){
        for (LCNode n: nodes) {
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
            else  if(word.charAt(i) == 'x'){//TODO
                nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.1, 0.7));
            }
        }
    }

    public void initErrorCodeword(String word, int errors){

        int modValue = word.length()/errors;

        for (int i = 0; i < word.length(); i++) {
            if(i % modValue == 0){
                if(word.charAt(i) == '0'){
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.7, 0.1, 0.1));
                }
                else if(word.charAt(i) == '1'){
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.7, 0.1));
                }
                else if(word.charAt(i) == 'w'){
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.1, 0.7));
                }
                else  if(word.charAt(i) == 'x'){//TODO
                    nodes.get(i).setBeliefs(new BeliefVector(0.7, 0.1, 0.1, 0.1));
                }
            }
            else {
                if (word.charAt(i) == '0') {
                    nodes.get(i).setBeliefs(new BeliefVector(0.7, 0.1, 0.1, 0.1));
                } else if (word.charAt(i) == '1') {
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.7, 0.1, 0.1));
                } else if (word.charAt(i) == 'w') {
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.7, 0.1));
                } else if (word.charAt(i) == 'x') {//TODO
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.1, 0.7));
                }
            }
        }

    }

    public void initUncertantyCodeword(String word, int errors){
        int modValue = word.length()/errors;

        for (int i = 0; i < word.length(); i++) {
            if(i % modValue == 0){
                nodes.get(i).setBeliefs(new BeliefVector(0.25, 0.25, 0.25, 0.25));
            }
            else {
                if (word.charAt(i) == '0') {
                    nodes.get(i).setBeliefs(new BeliefVector(0.7, 0.1, 0.1, 0.1));
                } else if (word.charAt(i) == '1') {
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.7, 0.1, 0.1));
                } else if (word.charAt(i) == 'w') {
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.7, 0.1));
                } else if (word.charAt(i) == 'x') {//TODO
                    nodes.get(i).setBeliefs(new BeliefVector(0.1, 0.1, 0.1, 0.7));
                }
            }
        }
    }

    public void initAWGNWord(String word){
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == '0'){
                double[] fst = AWGN.probability(0,0.5, 10.0, 5.0);
                double[] snd = AWGN.probability(0,0.5, 10.0, 5.0);

                BeliefVector v = new BeliefVector(fst[0] + snd[0], fst[0] + snd[1], fst[1] + snd[0], fst[1] + snd[1]);
                v.normalize();
                nodes.get(i).setBeliefs(v);
            }
            else if(word.charAt(i) == '1'){
                double[] fst = AWGN.probability(0,0.5, 10.0, 5.0);
                double[] snd = AWGN.probability(1,0.5, 10.0, 5.0);

                BeliefVector v = new BeliefVector(fst[0] + snd[0], fst[0] + snd[1], fst[1] + snd[0], fst[1] + snd[1]);
                v.normalize();
                nodes.get(i).setBeliefs(v);
            }
            else if(word.charAt(i) == 'w'){
                double[] fst = AWGN.probability(1,0.5, 10.0, 5.0);
                double[] snd = AWGN.probability(0,0.5, 10.0, 5.0);

                BeliefVector v = new BeliefVector(fst[0] + snd[0], fst[0] + snd[1], fst[1] + snd[0], fst[1] + snd[1]);
                v.normalize();
                nodes.get(i).setBeliefs(v);
            }
            else  if(word.charAt(i) == 'x'){
                double[] fst = AWGN.probability(1,0.5, 10.0, 5.0);
                double[] snd = AWGN.probability(1,0.5, 10.0, 5.0);

                BeliefVector v = new BeliefVector(fst[0] + snd[0], fst[0] + snd[1], fst[1] + snd[0], fst[1] + snd[1]);
                v.normalize();
                nodes.get(i).setBeliefs(v);
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

    public ArrayList<LCNode> getNodes() {
        return nodes;
    }

    public void clear(){
        for (LCNode n: nodes) {
            n.clear();
        }
    }

    public String StringMarginals(int lcNode){

        ArrayList<BeliefVector> m = LCmarginals(lcNode);


        String out = "";

        for (int j = 0; j < m.size(); j++) {
            out+= "x" + j + ": (" + m.get(j).get(0) + ", " + m.get(j).get(1) + ", " + m.get(j).get(2) + ", " + m.get(j).get(3) + ")";
            if(m.get(j).greatestValue() == 0){
                out+= ", decoded to: 0";
            }
            if(m.get(j).greatestValue() == 1){
                out+= ", decoded to: 1";
            }
            if(m.get(j).greatestValue() == 2){
                out+= ", decoded to: w";
            }
            if(m.get(j).greatestValue() == 3){
                out+= ", decoded to: w^2";
            }

            out+= "\n";

        }

        return out;
    }

    public String printNeighbourHood(){
        String n = "";
        for (int i = 0; i < nodes.size(); i++) {
            n+=i;
            n+=": ";
            for (int j = 0; j < nodes.get(i).neighbours.size(); j++) {
                n+=nodes.get(i).neighbours.get(j).id;
                n+=", ";
            }
            n+="\n";
        }
        return n;
    }

    public String printAdjMat(){
        String s = "";
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                s+=adjacencyMatrix[i][j];
            }
            s+='\n';
        }
        return s;
    }

    public String printMessages(int node){
        LCNode n = nodes.get(node);

        String s = "";

        for (int i = 0; i < n.messages.size(); i++) {
            s += n.messages.get(i).toString();
            s += '\n';
        }

        return s;

    }

    public int size(){
        return nodes.size();
    }



}
