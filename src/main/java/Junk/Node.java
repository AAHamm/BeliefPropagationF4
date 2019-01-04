package Junk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Åsmund Hammer on 25.04.18.
 */
public class Node {
    private int id;

    private List<Node> neighbors;

    private ArrayList<Edge> inEdge = new ArrayList<Edge>();
    private ArrayList<Edge> outEdge = new ArrayList<Edge>();

    private HashMap<Integer, Edge> idToOutEdge = new HashMap<Integer, Edge>();


    public Node(int id){
        this.id = id;
        this.neighbors = new ArrayList<Node>();
    }

    public void addNeighbour(Node n){
            neighbors.add(n);
            Edge in = new Edge(this, n);
            Edge out = new Edge(n, this);
            inEdge.add(in);
            outEdge.add(out);

            n.addNeighbour(this,out, in);

            //MISSING HASHMAP TODO?
    }

    public void addNeighbour(Node n, Edge in, Edge out){
        neighbors.add(n);
        inEdge.add(in);
        outEdge.add(out);

        idToOutEdge.put(out.getTo().getId(), out);
    }

    public int getId() {
        return id;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void propagateBeliefs(int toId){

        double[] message = new double[GF.getField()];

        for (int i = 0; i < inEdge.size(); i++) {
            Edge from = inEdge.get(i);
            if(from.getFrom().getId() == toId)
                continue;

            for (int j = 0; j < message.length; j++) {
                message[i] *= from.getMessage()[i];
            }
        }

        idToOutEdge.get(toId).setMessage(message);
    }

    public ArrayList<Edge> getInEdge() {
        return inEdge;
    }

    public ArrayList<Edge> getOutEdge() {
        return outEdge;
    }

    public HashMap<Integer, Edge> getIdToOutEdge() {
        return idToOutEdge;
    }

    public void addinEdge(Edge e){
        inEdge.add(e);
    }

    public void  addOutEdge(Edge e){
        outEdge.add(e);
        idToOutEdge.put(e.getTo().getId(), e);
    }
}
