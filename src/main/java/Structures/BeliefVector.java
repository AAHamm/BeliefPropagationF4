package Structures;

public class BeliefVector {
    double[] belief;

    public BeliefVector(){
        belief = new double[4];

        for (int i = 0; i < belief.length; i++) {
            belief[i] = 1;
        }
    }

    public BeliefVector(double[] belief){
        this.belief = belief;
    }

    public BeliefVector(double a, double b, double c, double d){
        belief = new double[]{a,b,c,d};
    }

    public BeliefVector(BeliefVector v){

        belief = new double[v.belief.length];

        for (int i = 0; i < v.belief.length; i++) {
            belief[i] = v.get(i);
        }
    }


    public static BeliefVector dot(BeliefVector u, BeliefVector v){
        double[] product = new double[4];
        for (int i = 0; i < 4; i++) {
            product[i] = u.get(i)*v.get(i);
        }
        return new BeliefVector(product);
    }

    public static BeliefVector dSS(BeliefVector u, BeliefVector v){
        double[] product = new double[4];

        product[0] = v.get(0)*u.get(0) + v.get(1)*u.get(1);
        product[1] = v.get(2)*u.get(2) + v.get(3)*u.get(3);
        product[2] = v.get(0)*u.get(1) + v.get(1)* u.get(0);
        product[3] = v.get(2)*u.get(3) + v.get(3)*u.get(2);
        /*
        System.out.println("u: " + u.toString());
        System.out.println("v: " + v.toString());
        System.out.println("dSS: " + new BeliefVector(product).toString());
        System.out.println();
        */
        return new BeliefVector(product);
    }

    public static BeliefVector dSX(BeliefVector u, BeliefVector v){
        double[] product = new double[4];

        product[0] = v.get(0)*u.get(0) + v.get(1)*u.get(1);
        product[1] = v.get(0)*u.get(1) + v.get(1)*u.get(0);
        product[2] = v.get(2)*u.get(2) + v.get(3)* u.get(3);
        product[3] = v.get(2)*u.get(3) + v.get(3)*u.get(2);

        /*
        System.out.println("u: " + u.toString());
        System.out.println("v: " + v.toString());
        System.out.println("dSX: " + new BeliefVector(product).toString());
        System.out.println();
        */


        return new BeliefVector(product);

    }

    public static BeliefVector tSX(BeliefVector u, BeliefVector v){
        double[] product = new double[4];

        product[0] = v.get(0)*u.get(0) + v.get(2)*u.get(1);
        product[1] = v.get(0)*u.get(1) + v.get(2)*u.get(0);
        product[2] = v.get(1)*u.get(2) + v.get(3)* u.get(3);
        product[3] = v.get(1)*u.get(3) + v.get(3)*u.get(2);

        /*
        System.out.println("u: " + u.toString());
        System.out.println("v: " + v.toString());
        System.out.println("tSX: " + new BeliefVector(product).toString());
        System.out.println();
        */

        return new BeliefVector(product);
    }
    public double get(int i){
        return belief[i];
    }

    public String toString(){
        String r = "(";

        for (int i = 0; i < 4; i++) {
            r+=belief[i];
            if(i != 3)
                r+=", ";
        }
        r+=")";
        return r;
    }

    public void set(int i, double val){
        belief[i] = val;
    }

    public int greatestValue(){
        if(belief[0] > belief[1] && belief[0] > belief[2] && belief[0] > belief[3]){
            return 0;
        }
        if(belief[1] > belief[0] && belief[1] > belief[2] && belief[1] > belief[3]){
            return 1;
        }
        if(belief[2] > belief[1] && belief[2] > belief[0] && belief[2] > belief[3]){
            return 2;
        }
        if(belief[3] > belief[1] && belief[3] > belief[2] && belief[3] > belief[0]){
            return 3;
        }
        return -1;
    }

    public void normalize(){
        double sum = 0;

        for (int i = 0; i < belief.length; i++) {
            sum+=belief[i];
        }

        if(sum != 0)
        for (int i = 0; i < belief.length; i++) {
            belief[i] = belief[i]/sum;
        }
    }
}
