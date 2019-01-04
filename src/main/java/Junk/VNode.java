package Junk;

public class VNode extends Node {

    public double[] beliefs;

    public VNode(int id) {
        super(id);

        beliefs = new double[GF.getField()];

        for (int i = 0; i < beliefs.length; i++) {
            beliefs[i] = 1;
        }
        ;
    }


    @Override
    public void propagateBeliefs(int toId){

        double[] message = new double[GF.getField()];

        for (int i = 0; i < message.length; i++) {
            message[i] = beliefs[i];
        }


        for (int i = 0; i < getInEdge().size(); i++) {
            Edge from = getInEdge().get(i);
            if(from.getFrom().getId() != toId) {


                for (int j = 0; j < message.length; j++) {
                    message[j] *= from.getMessage()[j];
                }
            }
        }

        getIdToOutEdge().get(toId).setMessage(message);
    }

    public void setBeliefs(double [] beliefs){
        this.beliefs = beliefs;
    }

    public double[] marginal(){
        double[] marginal = new double[4];

        for (int i = 0; i < beliefs.length; i++) {
            marginal[i] = beliefs[i];
        }

        for (int i = 0; i < getInEdge().size(); i++) {
            Edge e = getInEdge().get(i);
            for (int j = 0; j < marginal.length; j++) {
                marginal[j] *= e.getMessage()[j];
            }

        }
        return marginal;
    }
}
