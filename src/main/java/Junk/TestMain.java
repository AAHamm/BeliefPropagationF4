package Junk;

import Junk.Edge;
import Junk.FNode;
import Junk.FactorGraph;
import Junk.VNode;

import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        FNode f = new FNode(0, 2, new double[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16});

        VNode a = new VNode(0);
        VNode b = new VNode(1);
        VNode c = new VNode(2);

        a.setBeliefs(new double[] {0.7, 0.1, 0.1, 0.1});
        b.setBeliefs(new double[] {0.7, 0.1, 0.1, 0.1});
        c.setBeliefs(new double[] {0.5, 0.5, 0.5, 0.5});


        Edge e1 = new Edge(a, b);
        Edge e2 = new Edge(b, a);

        Edge e3 = new Edge(b, c);
        Edge e4 = new Edge(c , b);


        ArrayList<Edge> inEdges = new ArrayList<Edge>();
        ArrayList<Edge> outEdges = new ArrayList<Edge>();

        inEdges.add(e1); inEdges.add(e3);
        outEdges.add(e2); outEdges.add(e4);

        FactorGraph g = new FactorGraph(inEdges, outEdges);


        a.propagateBeliefs(1);
        c.propagateBeliefs(1);
        b.propagateBeliefs(2);
        c.propagateBeliefs(1);


        for (int i = 0; i < a.marginal().length; i++) {
            System.out.println(e3.getMessage()[i]);
        }
        System.out.println();

        for (int i = 0; i < a.marginal().length; i++) {
            System.out.println(e4.getMessage()[i]);
        }
        System.out.println();

        for (int i = 0; i < a.marginal().length; i++) {
            System.out.println(c.marginal()[i]);
        }
        System.out.println();


        /*
        for (int i = 0; i < 10; i++) {
            g.iteration();
        }

        for (int i = 0; i < a.marginal().length; i++) {
            System.out.println(a.marginal()[i]);
        }

        System.out.println();


        for (int i = 0; i < b.marginal().length; i++) {
            System.out.println(b.marginal()[i]);
        }

        System.out.println();

        for (int i = 0; i < c.marginal().length; i++) {
            System.out.println(c.marginal()[i]);
        }


        */

    }
}
