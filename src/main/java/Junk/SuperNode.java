package Junk;

import java.util.ArrayList;
import java.util.HashMap;

public class SuperNode {

    int id;


    int[][] function;
    double[] softInfo;
    ArrayList<Integer> neighbours = new ArrayList<Integer>();
    HashMap<Integer, Integer> idToIndex = new HashMap<Integer, Integer>();
    HashMap<Integer, double[]> fromFactorNode = new HashMap<Integer, double[]>();
    HashMap<Integer, double[]> fromVariableNode = new HashMap<Integer, double[]>();


    int n = 0;

    public SuperNode(int id, int[][] codes, int[][] adjMatrix, double[] softInfo){
        this.id = id;

        idToIndex.put(id, neighbours.size());
        neighbours.add(id);
        //fromFactorNode.put(id, softInfo);
        fromFactorNode.put(id, softInfo);
        fromVariableNode.put(id, new double[]{1,1,1,1});
        for (int j = 0; j< adjMatrix[id].length; j++) {
            if (adjMatrix[id][j] == 1) {
                idToIndex.put(j, neighbours.size());
                neighbours.add(j);
                fromFactorNode.put(j, new double[]{1,1,1,1});
                fromVariableNode.put(j, new double[]{1,1,1,1});
                n++;
            }
        }


        int rows = codes.length;
        int cols = n+1;

        function = new int[rows][cols];

        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < cols; j++) {
                int index = neighbours.get(j);
                function[i][j] = codes[i][index];
            }


        }
        /*
        for (int i = 0; i < function.length; i++) {
            for (int j = 0; j< function[i].length; j++) {
                System.out.print(function[i][j]+ " ");
            }
            System.out.println();
        }
        */
    }

    public double[] createMyBeliefs(int to){
        double[] b = new double[] {1,1,1,1};
        for (int i = 0; i < neighbours.size(); i++) {
            if (neighbours.get(i) != to){
                for (int j = 0; j < b.length; j++) {
                    b[j] *= fromFactorNode.get(neighbours.get(i))[j];
                }
            }
        }
        return b;
    }


    public double[] createBeliefAbout(int id){

        double zeroSum = 0, oneSum = 0 , omegaSum = 0, omegaSQSum = 0;

        for (int i = 0; i < function.length; i++) {
            double product = 1; //DANGER ADDING 1 To Sum if product at all?

            for (int j = 0; j < function[i].length; j++) {
                if(neighbours.get(j) != id) {
                    product *= fromVariableNode.get(neighbours.get(j))[(int) function[i][j]];
                }
            }

            if(function[i][idToIndex.get(id)] == 0){
                zeroSum += product;
            }
            else if(function[i][idToIndex.get(id)] == 1){
                oneSum += product;
            }
            else if(function[i][idToIndex.get(id)] == 2){
                omegaSum += product;
            }
            else {
                omegaSQSum += product;
            }
        }

        return new double[]{zeroSum, oneSum, omegaSum, omegaSQSum};
    }

    public ArrayList<Integer> getNeighbours(){
        return neighbours;
    }

    public void setFactorBelief(int from, double [] m){
        double total = 0;
        for (int i = 0; i < m.length; i++) {
            total += m[i];
        }
        for (int i = 0; i < m.length; i++) {
            m[i] = m[i] / total;
        }
        fromFactorNode.put(from, m);
    }

    public void setVariableBelief(int from, double[] b){
        double total = 0;
        for (int i = 0; i < b.length; i++) {
            total += b[i];
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = b[i] / total;
        }
        fromVariableNode.put(from, b);
    }

    public double[] marginal(){

            double[] one = createMyBeliefs(neighbours.get(neighbours.size()-1));
            double[] two = fromFactorNode.get(neighbours.get(neighbours.size()-1));

            double[] ret = new double[one.length];

            for (int j = 0; j < ret.length; j++) {
                ret[j] = one[j]*two[j];
            }




        for (int j = 0; j < ret.length; j++) {
            ret[j] = one[j]*two[j];
        }


        double total = 0;
        for (int i = 0; i < ret.length; i++) {
            total += ret[i];
        }
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ret[i] / total;
        }

        return ret;


    }

    public void setSoftInfo(double[] info){
        fromFactorNode.put(id, info);
    }
}
