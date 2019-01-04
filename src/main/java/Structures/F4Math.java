package Structures;

public class F4Math {

    public static F4[] scale(F4 scalar, F4[] vector){
        F4[] v = new F4[vector.length];
        for (int i = 0; i < vector.length; i++) {
            v[i] = vector[i].multiply(scalar);
        }

        return v;
    }

    public static F4[] add(F4[] a, F4[] b){
        F4[] v = new F4[a.length];

        for (int i = 0; i < a.length; i++) {
            v[i] = a[i].add(b[i]);
        }

        return v;

    }
}
