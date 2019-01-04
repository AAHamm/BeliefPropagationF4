package Junk;

import Junk.Edge;

import java.util.ArrayList;

public class FactorGraph {
    ArrayList<Edge> inEdges;
    ArrayList<Edge> outEdges;

    public FactorGraph(ArrayList<Edge> inEdges, ArrayList<Edge> outEdges){
        this.inEdges = inEdges;
        this.outEdges = outEdges;
    }

    public void iteration(){
        for (int i = 0; i < inEdges.size(); i++) {
            inEdges.get(i).getBeliefs();
            outEdges.get(i).getBeliefs();
        }
    }

}
