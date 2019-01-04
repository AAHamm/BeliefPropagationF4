package Junk;

import Structures.BeliefVector;

import java.util.ArrayList;
import java.util.HashMap;

public class GNode {
    int id;

    BeliefVector beliefs = new BeliefVector();

    ArrayList<BeliefVector> messages = new ArrayList<BeliefVector>();

    ArrayList<GNode> neighbours = new ArrayList<GNode>();

    HashMap<Integer, Integer> idToIndex = new HashMap<Integer, Integer>();

    protected Boolean superInternal = null;


    public GNode(int id) {
        this.id = id;
    }


    public void setBeliefs(BeliefVector beliefs){
        this.beliefs = beliefs;
    }

    public void addNeighbour(GNode n){
        messages.add(new BeliefVector());
        idToIndex.put(n.getId(), neighbours.size());
        neighbours.add(n);
    }


    public BeliefVector createMessage(GNode recipient){
        if(neighbours.size() == 1){
            return new BeliefVector(beliefs);
        }
        else{
            BeliefVector product = dProduct(recipient);
            BeliefVector message = BeliefVector.dSS(product, beliefs);
            return message;
        }
    }

    public BeliefVector marginal(){

       return BeliefVector.dot(internalProduct(), this.beliefs);
    }

    public BeliefVector dProduct(GNode recipient){
        int recipientIndex = idToIndex.get(recipient.getId());
        BeliefVector product = null;

        for (int i = 0; i < messages.size(); i++) {
            if(i != recipientIndex){
                if(product == null){
                    product = messages.get(i);
                }
                else {
                    product = BeliefVector.dSX(messages.get(i), product);
                }
            }
        }
        return product;
    }

    public BeliefVector internalProduct(){
        BeliefVector product = new BeliefVector(messages.get(0));

        for (int i = 1; i < messages.size(); i++) {
            product = BeliefVector.dSX(messages.get(i), product);
            System.out.println("here");
        }

        System.out.println(product.toString());

        return product;
    }

    public void setMessage(BeliefVector v, GNode sender){
        int index = idToIndex.get(sender.getId());

        messages.set(index, v);
    }

    public int getId() {
        return id;
    }

    public void clear(){
        for (int i = 0; i < messages.size(); i++) {
            messages.set(i, new BeliefVector());
        }
    }


}
