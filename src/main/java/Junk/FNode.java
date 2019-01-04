package Junk;

import java.util.HashMap;

public class FNode extends Node {

    double[][] truthTable;

    int rows;
    int cols;

    int counter = 0;

    HashMap<Integer, Integer> idToIndex = new HashMap<Integer, Integer>();
    HashMap<Integer, Edge> indexToInEdge = new HashMap<Integer, Edge>();


    public FNode(int id, int neighbours, double[] beliefs) {
        super(id);

        cols = neighbours+1;
        rows = beliefs.length;

        truthTable = new double[rows][cols];

        for (int k = 0; k <rows; k++) {
            truthTable[k][neighbours] = beliefs[k];
        }

        for (int i = 0; i < neighbours; i++) {

            int alternation = i* GF.getField();
            if(alternation == 0)
                alternation = 1;
            int count = 0;
            int variable = 0;

            for (int j = 0; j <rows; j++) {

                if (count >= alternation){
                    count=0;
                    variable = (variable+1) % GF.getField();
                }

                truthTable[j][i] = variable;

                count++;

            }

        }
        /*
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(truthTable[i][j] + " ");
            }
            System.out.println();

        }
        */

    }

    @Override
    public void propagateBeliefs(int toId){

        int toIndex = idToIndex.get(toId);

        double zeroSum = 0;
        double oneSum = 0;
        double aSum = 0;
        double bSum = 0;

        double[] message = new double[GF.getField()];

        for (int i = 0; i < rows; i++) {
            double product =1;

            for (int j = 0; j < cols-1; j++) {
                if (j != toIndex && indexToInEdge.get(j).hasMessage()) { //MAYBE INIT MESSSAGESES ON EDGES TO 1 INSTEAD OF CHECK TODO?
                    product *= indexToInEdge.get(j).getMessage()[(int) truthTable[i][j]];
                }
            }

                product *= truthTable[i][cols - 1];

                if (truthTable[i][toIndex] == 0)
                    zeroSum += product;
                else if (truthTable[i][toIndex] == 1)
                    oneSum += product;
                else if (truthTable[i][toIndex] == 2)
                    aSum += product;
                else
                    bSum += product;

        }

        getIdToOutEdge().get(toId).setMessage(new double[]{zeroSum, oneSum, aSum, bSum});
    }

    @Override
    public void addinEdge(Edge e){
        getInEdge().add(e);
        idToIndex.put(e.getFrom().getId(), counter);
        indexToInEdge.put(counter++, e);
    }

    @Override
    public void  addOutEdge(Edge e){
        getOutEdge().add(e);
        getIdToOutEdge().put(e.getTo().getId(), e);
    }



}
