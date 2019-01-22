package LocalComplementation;

import Structures.BeliefVector;

import java.util.ArrayList;
import java.util.HashMap;

public class LCNode {
    int id;

    BeliefVector beliefs = new BeliefVector();

    ArrayList<BeliefVector> messages = new ArrayList<BeliefVector>();

    ArrayList<LCNode> neighbours = new ArrayList<LCNode>();

    ArrayList<LCNode> cliqueNodes = new ArrayList<LCNode>();

    ArrayList<LCNode> externalNodes = new ArrayList<LCNode>();

    HashMap<Integer, Integer> idToIndex = new HashMap<Integer, Integer>();


    public LCNode(int id){
        this.id = id;
    }

    public void setBeliefs(BeliefVector beliefs){
        this.beliefs = beliefs;
    }


    public void addNeighbour(LCNode n){
        messages.add(new BeliefVector());
        idToIndex.put(n.getId(), neighbours.size());
        neighbours.add(n);
    }

    public void removeNeighbour(LCNode n){
        int index = idToIndex.get(n.id);
        idToIndex.remove(n.id);
        messages.remove(index);
        neighbours.remove(index);

        for (int i = 0; i < neighbours.size(); i++) {
            idToIndex.put(neighbours.get(i).id, i);
        }


    }

    public void setMessage(BeliefVector v, LCNode sender){
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

    public BeliefVector createMessage(LCNode recipient){
        int recipientIndex = idToIndex.get(recipient.getId());

        BeliefVector m = new BeliefVector(1, 0, 1, 0);

        for (int i = 0; i < neighbours.size() ; i++) {
            if(i == recipientIndex)
                continue;
            m = BeliefVector.dSX(m, messages.get(i));
        }

        m = BeliefVector.dSS(m, this.beliefs);
        m.normalize();
        return m;
    }

    public BeliefVector createInternalMessage(LCNode recipient){
        int recipientIndex = idToIndex.get(recipient.getId());

        BeliefVector m = new BeliefVector(1, 0, 1, 0);


        for (LCNode n: cliqueNodes) {
            if(n.getId() == recipient.getId())
                continue;

            m = BeliefVector.dSX(m, messages.get(idToIndex.get(n.getId())));
        }

        m = BeliefVector.dSS(m, this.beliefs);
        m.normalize();
        return m;
    }



    public BeliefVector marginal(){
        BeliefVector m = new BeliefVector(1, 0, 1, 0);

        for (int i = 0; i < neighbours.size() ; i++) {
            m = BeliefVector.dSX(m, messages.get(i));
        }

        m = BeliefVector.dot(m, this.beliefs);
        m.normalize();
        return m;
    }

    public BeliefVector getBeliefs() {      
        return beliefs;
    }
}
