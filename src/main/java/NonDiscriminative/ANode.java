package NonDiscriminative;

import Structures.BeliefVector;

import java.util.ArrayList;
import java.util.HashMap;

public class ANode {
    int id;

    BeliefVector beliefs = new BeliefVector();

    ArrayList<BeliefVector> messages = new ArrayList<BeliefVector>();

    ArrayList<ANode> neighbours = new ArrayList<ANode>();

    HashMap<Integer, Integer> idToIndex = new HashMap<Integer, Integer>();


    public ANode(int id){
        this.id = id;
    }

    public void setBeliefs(BeliefVector beliefs){
        this.beliefs = beliefs;
    }


    public void addNeighbour(ANode n){
        messages.add(new BeliefVector());
        idToIndex.put(n.getId(), neighbours.size());
        neighbours.add(n);
    }

    public void setMessage(BeliefVector v, ANode sender){
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

    public BeliefVector createMessage(ANode recipient){
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

    public BeliefVector marginal(){
        BeliefVector m = new BeliefVector(1, 0, 1, 0);

        for (int i = 0; i < neighbours.size() ; i++) {
            m = BeliefVector.dSX(m, messages.get(i));
        }

        m = BeliefVector.dot(m, this.beliefs);
        m.normalize();
        return m;
    }

}
