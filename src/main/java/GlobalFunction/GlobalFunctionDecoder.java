package GlobalFunction;

import Structures.F4;
import Structures.F4Math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class GlobalFunctionDecoder {
    int[][] adjacencyMatrix;
    F4[][] F4Matrix;
    F4[][] codewords;
    double[][] beliefs;

    public GlobalFunctionDecoder(){
        Scanner sc = new Scanner(System.in);
        int amtLines = Integer.parseInt(sc.nextLine());
        int[][] adjacencyMatrix = new int[amtLines][amtLines];
        F4[][] F4Matrix = new F4[amtLines][amtLines];

        for (int j = 0; j < amtLines; j++) {


            String a = sc.nextLine();

            for (int i = 0; i < a.length(); i++) {
                if(a.charAt(i) == '0'){
                    adjacencyMatrix[j][i] = 0;
                    F4Matrix[j][i] = F4.ZERO;
                }
                else if(a.charAt(i) == '1'){
                    adjacencyMatrix[j][i] = 1;
                    F4Matrix[j][i] = F4.ONE;
                }
                else if(a.charAt(i) == 'w'){
                    adjacencyMatrix[j][i] = 2;
                    F4Matrix[j][i] = F4.OMEGA;
                }
                else if(a.charAt(i) == 'x'){// <------------------------- DANGER STRANGER
                    adjacencyMatrix[j][i] = 3;
                    F4Matrix[j][i] = F4.OMEGASQ;
                }
            }
        }

        this.adjacencyMatrix = adjacencyMatrix;
        this.F4Matrix = F4Matrix;

        codewords = new F4[(int) Math.pow(2, amtLines)][amtLines];
        ArrayList<F4[]> codewordlist = new ArrayList<F4[]>();

        F4[] zero = new F4[amtLines];
        F4[] wordOne = new F4[amtLines];

        for (int i = 0; i < amtLines; i++) {
            zero[i] = F4.ZERO;
            wordOne[i] = F4Matrix[0][i];
        }
        codewordlist.add(zero);
        codewordlist.add(wordOne);

        for (int i = 1; i < amtLines; i++) {
            ArrayList<F4[]> newcodewords = new ArrayList<F4[]>();

            for (F4[] vector: codewordlist) {
                newcodewords.add(F4Math.add(vector,F4Matrix[i]));
            }

            codewordlist.addAll(newcodewords);
        }

        for (int i = 0; i < codewordlist.size(); i++) {
            codewords[i] = codewordlist.get(i);
        }

        beliefs = new double[amtLines][4];

        for (int i = 0; i < amtLines; i++) {
            for (int j = 0; j < 4; j++) {
                beliefs[i][j] = 1;
            }
        }

    }

    public GlobalFunctionDecoder(int[][] adjMat){

        this.adjacencyMatrix = adjMat;
        int size = adjMat.length;

        F4[][] F4Matrix = new F4[size][size];


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                F4Matrix[i][j] = F4.fromIndex(adjMat[i][j]);
            }
        }

        this.F4Matrix = F4Matrix;

        codewords = new F4[(int) Math.pow(2, size)][size];
        ArrayList<F4[]> codewordlist = new ArrayList<F4[]>();

        F4[] zero = new F4[size];
        F4[] wordOne = new F4[size];

        for (int i = 0; i < size; i++) {
            zero[i] = F4.ZERO;
            wordOne[i] = F4Matrix[0][i];
        }
        codewordlist.add(zero);
        codewordlist.add(wordOne);

        for (int i = 1; i < size; i++) {
            ArrayList<F4[]> newcodewords = new ArrayList<F4[]>();

            for (F4[] vector: codewordlist) {
                newcodewords.add(F4Math.add(vector,F4Matrix[i]));
            }

            codewordlist.addAll(newcodewords);
        }

        for (int i = 0; i < codewordlist.size(); i++) {
            codewords[i] = codewordlist.get(i);
        }

        beliefs = new double[size][4];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 4; j++) {
                beliefs[i][j] = 1;
            }
        }

    }



    public void initConfidentZeroCodeword(){
        for (int i = 0; i < beliefs.length; i++) {
            beliefs[i][0] = 0.7;
            beliefs[i][1] = 0.05;
            beliefs[i][2] = 0.075;
            beliefs[i][3] = 0.175;
        }
    }

    public void initConfidentWord(String word){
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == '0'){
                beliefs[i][0] = 0.7;
                beliefs[i][1] = 0.1;
                beliefs[i][2] = 0.1;
                beliefs[i][3] = 0.1;
            }
            else if(word.charAt(i) == '1'){
                beliefs[i][0] = 0.1;
                beliefs[i][1] = 0.7;
                beliefs[i][2] = 0.1;
                beliefs[i][3] = 0.1;
            }
            else if(word.charAt(i) == 'w'){
                beliefs[i][0] = 0.1;
                beliefs[i][1] = 0.1;
                beliefs[i][2] = 0.7;
                beliefs[i][3] = 0.1;
            }
            else {
                beliefs[i][0] = 0.1;
                beliefs[i][1] = 0.1;
                beliefs[i][2] = 0.1;
                beliefs[i][3] = 0.7;
            }

        }
    }

    public String stringCodewords(){
        String ret = "";

        for (int i = 0; i < codewords.length; i++) {
            for (int j = 0; j < codewords[0].length; j++) {
                ret += codewords[i][j];
            }
            ret += "\n";
        }
        return ret;
    }

    public ArrayList<String> codewords(){

        ArrayList<String> ret = new ArrayList<String>();

        for (int i = 0; i < codewords.length; i++) {
            String str = "";
            for (int j = 0; j < codewords[0].length; j++) {
                str += codewords[i][j];
            }
            ret.add(str);
        }
        return ret;
    }

    public HashSet<String> codeWordsSet(){
        HashSet<String> set = new HashSet<String>();

        for (int i = 0; i < codewords.length; i++) {
            String str = "";
            for (int j = 0; j < codewords[0].length; j++) {
                str += codewords[i][j];
            }
            set.add(str);
        }
        return set;

    }

    public int minEditDist(){
        int min = Integer.MAX_VALUE;

        ArrayList<String> words = codewords();

        for (int i = 0; i < words.size(); i++) {
            for (int j = i+1; j < words.size(); j++) {
                int editDist = 0;
                for (int k = 0; k < words.get(0).length(); k++) {
                    if(words.get(i).charAt(k) != words.get(j).charAt(k)){
                        editDist++;
                    }
                }
                if(editDist < min)
                    min=editDist;
            }
        }
        return min;
    }

    public double[] globalMarginals(){
        double[] marginals = new double[codewords.length];

        //System.out.println(beliefs.length);
        //System.out.print(beliefs[0].length);
        for (int i = 0; i < codewords.length; i++) {
            marginals[i] = 1;

            for (int j = 0; j < codewords[0].length; j++) {
                marginals[i]*=beliefs[j][codewords[i][j].toIndex()];
            }
        }
        return marginals;
    }

    public double[][] variableMarginals(){

        double[] marginals = globalMarginals();

        double[][] variables = new double[adjacencyMatrix.length][4];

        for (int i = 0; i < codewords.length; i++) {
            for (int j = 0; j < codewords[0].length; j++) {
                if(codewords[i][j] == F4.ZERO){
                    variables[j][0] += marginals[i];
                }
                if(codewords[i][j] == F4.ONE){
                    variables[j][1] += marginals[i];
                }
                if(codewords[i][j] == F4.OMEGA){
                    variables[j][2] += marginals[i];
                }
                if(codewords[i][j] == F4.OMEGASQ){
                    variables[j][3] += marginals[i];
                }
            }
        }

        for (int i = 0; i < variables.length; i++) {

            double sum=0;

            for (int j = 0; j < variables[0].length; j++) {
                sum+=variables[i][j];
            }
            for (int j = 0; j < variables[0].length; j++) {
                variables[i][j] = variables[i][j]/sum;
            }
        }

        return variables;

    }

}
