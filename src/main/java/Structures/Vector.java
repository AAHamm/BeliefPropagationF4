package Structures;

import java.util.ArrayList;

/**
 * Created by Åsmund Hammer on 03.05.18.
 *
 */
public class Vector {
    private ArrayList<F4> vector;

    public Vector(){
        vector = new ArrayList<F4>();
    }

    public Vector(int size){
        vector = new ArrayList<F4>();
        for (int i = 0; i < size; i++) {
            vector.add(F4.ZERO);
        }
    }

    public Vector(ArrayList<F4> vector){
        this.vector = vector;
    }

    public Vector add(Vector v){
        Vector result = new Vector(vector);

        for (int i = 0; i < v.size(); i++) {
            result.set(i, this.get(i).add(v.get(i)));
        }

        return result;

    }

    public Vector dotProduct(Vector v) {

        if(this.size() != v.size())
            throw new IllegalArgumentException("Vectors must have same length under dotProduct");

        Vector result = new Vector(vector);

        for (int i = 0; i < v.size(); i++) {
            result.set(i, this.get(i).multiply(v.get(i)));
        }

        return result;
    }

    public int[] toArray(){
        int[] array = new int[this.size()];
        for (int i = 0; i < this.size(); i++) {
            array[i] = this.get(i).toIndex();
        }
        return array;
    }

    public int size(){
        return vector.size();
    }

    public void set(int index, F4 element){
        vector.set(index, element);
    }

    public F4 get(int index){
        return vector.get(index);
    }

}
