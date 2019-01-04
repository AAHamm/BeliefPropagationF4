package Junk;

import Structures.BeliefVector;

import java.util.Scanner;
import java.util.stream.DoubleStream;

public class Calc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String product = sc.nextLine();

        double u[] = new double[4];
        double v[] = new double[4];
        for (int i = 0; i < 4; i++) {
            u[i] = sc.nextDouble();
        }
        for (int i = 0; i < 4; i++) {
            v[i] = sc.nextDouble();
        }

        BeliefVector v1 = new BeliefVector(u);
        BeliefVector v2 = new BeliefVector(v);

        System.out.println(v1.toString());
        System.out.println(v2.toString());

        BeliefVector calc = new BeliefVector();

        if(product.equals("dsx")){
            System.out.println("dsx");
            calc = BeliefVector.dSX(v1, v2);
        }
        if(product.equals("dss")){
            System.out.println("dss");
            calc = BeliefVector.dSS(v1, v2);
        }
        if(product.equals("tsx")){
            System.out.println("tsx");
            calc = BeliefVector.tSX(v1,v2);
        }

        System.out.println(calc.toString());
    }
}
