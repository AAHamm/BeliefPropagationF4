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

        return 3;

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

    public BeliefVector copy(){
        return new BeliefVector(belief[0], belief[1], belief[2], belief[3]);
    }

    public static void main(String[] args) {

        BeliefVector x0 = new BeliefVector(0.05, 0.7, 0.1, 0.15);
        BeliefVector x1 = new BeliefVector(0.05, 0.1, 0.7, 0.15);
        BeliefVector x2 = new BeliefVector(0.25, 0.25, 0.25, 0.25);


        BeliefVector mx0 = new BeliefVector(0.05, 0.1, 0.7, 0.15);
        BeliefVector mx1 = new BeliefVector(0.05, 0.7, 0.1, 0.15);
        BeliefVector mx2 = new BeliefVector(0.25, 0.25, 0.25, 0.25);


        BeliefVector mx0tox1 = dSS(x0,mx2);
        BeliefVector mx1tox0 = dSS(x1, mx2);

        BeliefVector mx0tox2 = dSS(x0,mx1);
        BeliefVector mx2tox0 = dSS(x2,mx1);

        BeliefVector mx1tox2 = dSS(x1,mx0);
        BeliefVector mx2tox1 = dSS(x2,mx0);

        /*
        System.out.println(mx0tox1);
        System.out.println(mx1tox0);

        System.out.println(mx0tox2);
        System.out.println(mx2tox0);

        System.out.println(mx1tox2);
        System.out.println(mx2tox1);
        */

        BeliefVector x0Marginal = dot(x0,dSX(mx2tox0,mx1tox0));
        BeliefVector x1Marginal = dot(x1,dSX(mx2tox1, mx0tox1));
        BeliefVector x2Marginal = dot(x2, dSX(mx1tox2, mx0tox2));

        x0Marginal.normalize();
        x1Marginal.normalize();
        x2Marginal.normalize();

        /*
        System.out.println(x0Marginal);
        System.out.println(x1Marginal);
        System.out.println(x2Marginal);
        */

        BeliefVector lcx0 = new BeliefVector(0.03,0.16,0.05,0.76);
        BeliefVector lcx1 = new BeliefVector(0.04, 0.16, 0.25, 0.55);
        BeliefVector lcx2 = new BeliefVector(0.08, 0.85, 0.05, 0.2);


        BeliefVector lcmx0 = new BeliefVector(0.03, 0.05, 0.16, 0.76);
        BeliefVector lcmx1 = new BeliefVector(0.04, 0.25, 0.16, 0.55);
        BeliefVector lcmx2 = new BeliefVector(0.08, 0.05, 0.85, 0.2);

        BeliefVector lcmx0tox1 = dSS(lcx0,lcmx2);
        BeliefVector lcmx0tox2 = dSS(lcx0,lcmx1);

        /*
        System.out.println(lcmx0tox1);
        System.out.println(lcmx0tox2);
        */

        BeliefVector lcx0Marginal = dot(lcx0,dSX(lcmx1, lcmx2));
        BeliefVector lcx1Marginal = dot(lcx1, lcmx0tox1);
        BeliefVector lcx2Marginal = dot(lcx2, lcmx0tox2);

        lcx0Marginal.normalize();
        lcx1Marginal.normalize();
        lcx2Marginal.normalize();

        System.out.println(lcx0Marginal);
        System.out.println(lcx1Marginal);
        System.out.println(lcx2Marginal);

    }
}
