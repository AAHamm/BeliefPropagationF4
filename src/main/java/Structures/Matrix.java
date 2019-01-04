package Structures;

import java.util.ArrayList;

/**
 * Created by Åsmund Hammer on 03.05.18.
 */
public class Matrix {
    private ArrayList<Vector> matrix;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;

        matrix = new ArrayList<Vector>();

        for (int j = 0; j < rows; j++) {
            matrix.add(new Vector(cols));
        }
    }

    public Matrix(ArrayList<Vector> matrix){
        rows = matrix.size();
        cols = matrix.get(0).size();
        this.matrix = matrix;
    }

    public Matrix add(Matrix m){

        Matrix solution = new Matrix(getRows(), getCols());
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                solution.set(i,j, this.get(i,j).add(m.get(i,j)));
            }
        }
        return solution;
    }

    //O(n^3)
    public Matrix multiply(Matrix m){
        Matrix AB = new Matrix(this.getRows(), m.getCols());

        for (int i = 0; i < AB.getRows(); i++) {
            for (int j = 0; j < AB.getCols(); j++){
                F4 sum = F4.ZERO;
                for (int k = 0; k < this.getCols(); k++) {
                    //sum = F4Op.add(sum, F4Op.multiply(A.getElement(i, k), B.getElement(k, j)));
                    sum = sum.add(this.get(i,k).multiply(m.get(k,j)));
                }
                AB.set(i, j, sum);
            }
        }
        return AB;
    }

    public Matrix transpose(){
        Matrix m = new Matrix(getRows(), getCols());

        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                m.set(i,j, get(j,i));
            }
        }
        return m;
    }

    public void set(int row, int col, F4 element){
        matrix.get(row).set(col, element);
    }

    public F4 get(int row, int col){
        return matrix.get(row).get(col);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean equals(Matrix m){
        if(getRows() == m.getRows() && getCols() == m.getCols()){
            for (int i = 0; i < getRows(); i++) {
                for (int j = 0; j < getCols(); j++) {
                    if(this.get(i,j) != m.get(i,j))
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    public void fill(F4 element){
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                set(i,j, element);
            }
        }
    }

    public static Matrix generateRandomNxNMatrix(int n){
        Matrix m = new Matrix(n,n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m.set(i,j,F4.random());
            }
        }
        return m;
    }

    public static Matrix generateNxNIdentityMatrix(int n){
        Matrix m = new Matrix(n,n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i==j)
                    m.set(i,j, F4.ONE);
                else m.set(i,j, F4.ZERO);
            }
        }
        return m;
    }



}
