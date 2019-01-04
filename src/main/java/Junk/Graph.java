package Junk;

import Junk.Node;
import Structures.F4;
import Structures.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Åsmund Hammer on 02.05.18.
 *
 * A node based Graph structure
 */
public class Graph {
    private List<Node> nodes;

    /**
     * constructs Graph from Paritycheck matrix
     */

    public Graph(Matrix PCM){
        nodes = new ArrayList<Node>();
        long nodeIndex = 0;
        for (int i = 0; i < PCM.getRows(); i++) {
            //Node n = new Node(nodeIndex++);
            //.add(n);
        }
        for (int i = 0; i < PCM.getCols(); i++) {
            for (int j = 0; j < PCM.getRows(); j++) {
                if(PCM.get(i,j) == F4.ONE)
                    nodes.get(i).addNeighbour(nodes.get(j));
            }
        }
    }

    /**
     * Prints all the edges of the graph (twice, once for each direction)
     */
    public void printGraph(){
        for (Node n: nodes) {
            for(Node neighbour: n.getNeighbors()){
                System.out.println("(V" + n.getId() + ", V" + neighbour.getId() + ")");
            }
        }
    }

}
