package Junk;

public class Edge {
    private Node to;
    private Node from;
    private double[] message;

    private boolean hasMessage = false;

    public Edge(Node from, Node to){
        this.to = to;
        this.from = from;

        to.addinEdge(this);
        from.addOutEdge(this);

        message = new double[]{1,1,1,1};
    }

    public Node getTo() {
        return to;
    }

    public Node getFrom() {
        return from;
    }

    public double[] getMessage() {
        return message;
    }

    public boolean hasMessage() {
        return hasMessage;
    }

    public void setMessage(double[] message){
        hasMessage = true;
        this.message = message;
    }

    public void getBeliefs(){
       from.propagateBeliefs(to.getId());
    }

}
