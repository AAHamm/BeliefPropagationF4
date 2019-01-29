package Structures;

import java.util.ArrayList;

/**
 * Created by Åsmund Hammer on 03.03.18.
 */
public enum F4 {
    ZERO, ONE, OMEGA, OMEGASQ;

    private static final F4[][] additiveTable = new F4[][]{
            {ZERO, ONE, OMEGA, OMEGASQ},
            {ONE, ZERO, OMEGASQ, OMEGA},
            {OMEGA, OMEGASQ, ZERO, ONE},
            {OMEGASQ, OMEGA, ONE, ZERO}
    };

    private static final F4[][] multiplicativeTable = new F4[][]{
            {ZERO, ZERO, ZERO, ZERO},
            {ZERO, ONE, OMEGA, OMEGASQ},
            {ZERO, OMEGA, OMEGASQ, ONE},
            {ZERO, OMEGASQ, ONE, OMEGA}
    };

    public F4 add(F4 element){
        return additiveTable[this.toIndex()][element.toIndex()];
    }

    public F4 multiply(F4 element){
        return multiplicativeTable[this.toIndex()][element.toIndex()];
    }

    public int toIndex(){
        if (this == ZERO)
            return 0;
        if (this == ONE)
            return 1;
        if (this == OMEGA)
            return 2;
        return 3;
    }

    public static F4 fromIndex(int i){
        if(i == 0)
            return F4.ZERO;
        if(i==1)
            return F4.ONE;
        if(i==2)
            return F4.OMEGA;

        return F4.OMEGASQ;
    }

    public static ArrayList<F4> list(){
        ArrayList<F4> list = new ArrayList<F4>();
        list.add(ZERO);
        list.add(ONE);
        list.add(OMEGA);
        list.add(OMEGASQ);
        return list;
    }

    public static F4 random(){
        double rnd = Math.random();
        if(rnd < 0.25)
            return ZERO;
        else if (rnd < 0.5)
            return ONE;
        else if(rnd < 0.75)
            return OMEGA;
        else return OMEGASQ;
    }

    @Override
    public String toString() {
        if(this == ZERO){
            return "0";
        }
        if(this == ONE)
            return "1";
        if(this == OMEGA)
            return "w";
        return "w^2"; // <-------------------------- Danger
    }
}
