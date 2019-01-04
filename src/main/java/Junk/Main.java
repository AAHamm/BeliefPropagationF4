package Junk;

import Structures.F4;
import Structures.Matrix;
import Junk.Graph;

/**
 * Created by Åsmund Hammer on 03.03.18.
 */
public class Main {
    public static void main(String[] args) {
        Matrix A = new Matrix(2, 2);
        A.set(0, 0, F4.ONE);
        A.set(0,1, F4.ZERO);
        A.set(1, 0, F4.ZERO);
        A.set(1, 1, F4.ONE);

        Matrix B = new Matrix(2, 2);
        B.set(0,0, F4.OMEGA);
        B.set(0,1,F4.OMEGASQ);
        B.set(1,0, F4.ONE);
        B.set(1,1,F4.ZERO);

        Matrix AB = A.multiply(B);

        for (int i = 0; i < AB.getRows(); i++) {
            System.out.println();
            for (int j = 0; j < AB.getCols(); j++) {
                System.out.print(AB.get(i, j));
            }
        }

        Matrix C = new Matrix(3, 3);

        C.set(0, 0, F4.OMEGA);
        C.set(0,1, F4.ONE);
        C.set(0, 2, F4.ONE);
        C.set(1, 0, F4.ONE);
        C.set(1, 1, F4.OMEGA);
        C.set(1,2, F4.ZERO);
        C.set(2, 0, F4.ONE);
        C.set(2, 1, F4.ZERO);
        C.set(2,2, F4.OMEGA);



        Graph g2 = new Graph(C);
        g2.printGraph();




    }
}
