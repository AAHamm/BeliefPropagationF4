package Discriminative;

import Structures.BeliefVector;

import java.util.ArrayList;
import java.util.HashMap;

public class DNode {
    int id;

    BeliefVector beliefs = new BeliefVector();

    ArrayList<BeliefVector> messages = new ArrayList<BeliefVector>();

    ArrayList<DNode> neighbours = new ArrayList<DNode>();

    HashMap<Integer, Integer> idToIndex = new HashMap<Integer, Integer>();

    protected Boolean superInternal = null;


    public DNode(int id) {
        this.id = id;
    }


    public boolean isLeafNode(){

        return false;
        //return neighbours.size() == 1;

    }

    public void setBeliefs(BeliefVector beliefs){
        this.beliefs = beliefs;
    }

    public void addNeighbour(DNode n){
        messages.add(new BeliefVector());
        idToIndex.put(n.getId(), neighbours.size());
        neighbours.add(n);
    }


    public BeliefVector createMessage(DNode recipient){
        if(isLeafNode()){
            return beliefs;
        }

        BeliefVector[] products = leafAndInternalProduct(recipient);

        BeliefVector leafProduct = products[0];
        BeliefVector internalProduct = products[1];


        BeliefVector message;
        if (internalProduct != null){
            message = BeliefVector.dSX(leafProduct, beliefs);
            message = BeliefVector.dSS(internalProduct, message);

        }
        else {
          //  System.out.println("b dSS");
            message = BeliefVector.dSS(leafProduct, beliefs);
          //  System.out.println("e dSS");

        }

        double sum = 0;
        for (int i = 0; i < 4; i++) {
            sum+= message.get(i);
        }

        if(sum != 0)
        for (int i = 0; i < 4; i++) {
            message.set(i, message.get(i)/sum);
        }



        return message;

    }

    public BeliefVector marginal(){

        if(isLeafNode()){
            return BeliefVector.dot(messages.get(0), this.beliefs);
        }

        BeliefVector[] products = leafAndInternalProduct();

        BeliefVector leafProduct = products[0];
        BeliefVector internalProduct = products[1];

        if(internalProduct == null){
            return BeliefVector.dot(leafProduct, beliefs);
        }
        else if(internalProduct != null && isSuperInternal()){
            return BeliefVector.dot(internalProduct, beliefs);
        }

        return BeliefVector.dot(BeliefVector.dSX(internalProduct, leafProduct), beliefs) ;
    }

    public BeliefVector[] leafAndInternalProduct(DNode recipient){
        int recipientIndex = idToIndex.get(recipient.getId());

        BeliefVector leafProduct = new BeliefVector(new double[]{1,0,1,0});
        BeliefVector internalProduct = null;

        for (int i = 0; i < neighbours.size(); i++) {
            if(i == recipientIndex)
                continue;

            if (neighbours.get(i).isLeafNode()){
                leafProduct = BeliefVector.tSX(messages.get(i), leafProduct);
            }
            else{
                if(internalProduct == null){
                    internalProduct = messages.get(i);
                }
                else{
                    internalProduct = BeliefVector.dSX(messages.get(i), internalProduct);
                }
            }
        }

        return new BeliefVector[]{leafProduct, internalProduct};
    }

    public BeliefVector[] leafAndInternalProduct(){
        BeliefVector leafProduct = new BeliefVector(new double[]{1,0,1,0});
        BeliefVector internalProduct = null;

        for (int i = 0; i < neighbours.size(); i++) {

            if (neighbours.get(i).isLeafNode()){
                leafProduct = BeliefVector.tSX(messages.get(i), leafProduct);
            }
            else{
                if(internalProduct == null){
                    internalProduct = messages.get(i);
                }
                else{
                    internalProduct = BeliefVector.dSX(messages.get(i), internalProduct);
                }
            }
        }

        return new BeliefVector[]{leafProduct, internalProduct};
    }

    public void setMessage(BeliefVector v, DNode sender){
        int index = idToIndex.get(sender.getId());

        messages.set(index, v);
    }

    public boolean isSuperInternal(){

        if (superInternal != null){
            return superInternal;
        }

        for (int i = 0; i < neighbours.size(); i++) {
            if(neighbours.get(i).isLeafNode()){
                superInternal = false;
                return false;
            }
        }

        superInternal = !isLeafNode();
        return superInternal;
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

