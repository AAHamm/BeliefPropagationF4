import Discriminative.DiscriminativeDecoder;
import GlobalFunction.GlobalFunctionDecoder;
import LocalComplementation.LCDecoder;
import Noise.AWGN;
import Noise.WordCompare;
import NonDiscriminative.ADecoder;
import Structures.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int amtLines = Integer.parseInt(sc.nextLine());
        int[][] adjacencyMatrix = new int[amtLines][amtLines];
        for (int j = 0; j < amtLines; j++) {


            String a = sc.nextLine();

            for (int i = 0; i < a.length(); i++) {
                if(a.charAt(i) == '0'){
                    adjacencyMatrix[j][i] = 0;
                }
                else if(a.charAt(i) == '1'){
                    adjacencyMatrix[j][i] = 1;
                }
                else if(a.charAt(i) == 'w'){
                    adjacencyMatrix[j][i] = 2;
                }
                else if(a.charAt(i) == 'x'){// <------------------------- DANGER STRANGER
                    adjacencyMatrix[j][i] = 3;
                }
            }
        }


        String option1 = sc.nextLine();


        if(option1.equals("default")){
            DiscriminativeDecoder d = new DiscriminativeDecoder();

            d.initConfidentZeroCodeWord();

            d.flood(50);

            System.out.println(d.StringMarginals());
        }

        if(option1.equals("alternative")){

            ADecoder decoder = new ADecoder(adjacencyMatrix);

            String inputWord = sc.nextLine();

            decoder.setBeliefs(new BeliefVector[]{
                    new BeliefVector(0.133,0.133,0.6,0.133),
                    new BeliefVector(0.133,0.133,0.133,0.6),
                    new BeliefVector(0.6,0.133,0.133,0.133),
                    new BeliefVector(0.6,0.133,0.133,0.133),
                    new BeliefVector(0.133,0.6,0.133,0.133),
            });

            decoder.flood(50);


            System.out.println(decoder.StringMarginals());
        }

        if(option1.equals("global")){

            GlobalFunctionDecoder g = new GlobalFunctionDecoder(adjacencyMatrix);
            String inputWord = sc.nextLine();

            g.setBeliefs(new BeliefVector[]{
                    new BeliefVector(0.133,0.133,0.6,0.133),
                    new BeliefVector(0.133,0.133,0.133,0.6),
                    new BeliefVector(0.6,0.133,0.133,0.133),
                    new BeliefVector(0.6,0.133,0.133,0.133),
                    new BeliefVector(0.133,0.6,0.133,0.133),
            });

            //g.initConfidentZeroCodeword();
            //System.out.println(g.codewords());

            //System.out.println(g.minEditDist());

            double[] c = g.globalMarginals();

            double[][] variables = g.variableMarginals();

            /*
            for (double aC : c) {
                System.out.println(aC);
            }
            */

            System.out.println();
            for (int i = 0; i < variables.length; i++) {
                System.out.print("x" + i + ": " + "(");
                for (int j = 0; j < variables[0].length; j++) {
                    System.out.print(variables[i][j] +" \\\\ ");
                }
                System.out.print(")");
                System.out.println();
            }
        }

        if(option1.equals("avgDeltaProbReadable")){//Get human readable difference in marginals per node, per flooding.

            DiscriminativeDecoder decoder = new DiscriminativeDecoder(adjacencyMatrix);

            decoder.initConfidentZeroCodeWord();

            BeliefVector[][] avrage = decoder.avrageDeltaProbabilityPerFlooding(10);

            for (int i = 0; i < avrage.length; i++) {
                System.out.println("Node: " + i);

                for (int j = 0; j < avrage[0].length; j++) {
                    System.out.println("iteration "  + j + ": " + avrage[i][j].toString());
                }
                System.out.println();
            }
        }

        if(option1.equals("avgDeltaProbM")){

            DiscriminativeDecoder decoder = new DiscriminativeDecoder(adjacencyMatrix);

            decoder.initConfidentCodeWord("w11100");

            decoder.setBelief(0,new BeliefVector(0.25, 0.25, 0.25, 0.25));
            decoder.setBelief(1,new BeliefVector(0.25, 0.25, 0.25, 0.25));



            double[][] avrage = decoder.deltaPerFloodingPerNode(20);

            for (double[] anAvrage : avrage) {

                for (int j = 0; j < avrage[0].length; j++) {
                    System.out.print(anAvrage[j] + "; ");
                }
                System.out.println();
            }
            System.out.println(decoder.StringMarginals());
        }

        if (option1.equals("codewords")){

            GlobalFunctionDecoder decoder = new GlobalFunctionDecoder();

            System.out.println(decoder.codewords());

            System.out.println("minimum edit distance: " + decoder.minEditDist());
        }

        if(option1.equals("lc")){
            LCDecoder decoder= new LCDecoder(adjacencyMatrix);

            decoder.initConfidentCodeWord("w111");

            decoder.LC(0);

            for (int i = 0; i <decoder.getNodes().size() ; i++) {
                System.out.println(decoder.getNodes().get(i).getBeliefs().toString());
            }

        }

        if(option1.equals("lcc")){
            String inputWord = sc.nextLine();
            //int lcNode = Integer.parseInt(sc.nextLine());

            LCDecoder decoder= new LCDecoder(adjacencyMatrix);


            /*
            decoder.setBeliefs(new BeliefVector[]{
                    new BeliefVector(0.25,0.25,0.25,0.25),
                    new BeliefVector(0.2,0.5,0.2,0.1),
                    new BeliefVector(0.2,0.5,0.2,0.1),
                    new BeliefVector(0.25,0.25,0.25,0.25),
                    new BeliefVector(0.5,0.2,0.2,0.1),
                    new BeliefVector(0.7,0.05,0.1,0.15)
            });
            */

            decoder.initConfidentCodeWord(inputWord);

            decoder.RoundLCflood(10,200);

            ArrayList<BeliefVector> marg = decoder.marginals();

            System.out.println();
            for (int i = 0; i < marg.size(); i++) {
                System.out.print("x" + i + "(");
                for (int j = 0; j < 4; j++) {
                    System.out.print(marg.get(i).get(j) + ", ");
                }
                System.out.print(")");
                System.out.println();
            }

            /*
            String out = "";
            for (int i = 0; i < m.size(); i++) {
                out+= "x" + i + ": (" + m.get(i).get(0) + ", " + m.get(i).get(1) + ", " + m.get(i).get(2) + ", " + m.get(i).get(3) + ")";
                if(m.get(i).greatestValue() == 0){
                    out+= ", decoded to: 0";
                }
                if(m.get(i).greatestValue() == 1){
                    out+= ", decoded to: 1";
                }
                if(m.get(i).greatestValue() == 2){
                    out+= ", decoded to: w";
                }
                if(m.get(i).greatestValue() == 3){
                    out+= ", decoded to: w^2";
                }

                out+= "\n";
            }
            System.out.println(out);
            */

            //System.out.println(decoder.printNeighbourHood());

            //System.out.print(decoder.printAdjMat());

            //System.out.println(decoder.printMessages(0));
        }

        if(option1.equals("lccerr")){

            String inputWord = sc.nextLine();
            LCDecoder decoder= new LCDecoder(adjacencyMatrix);


            decoder.initErrorCodeword(inputWord, 1);

            decoder.LC(0);
            decoder.flood(50);


            System.out.println(decoder.StringMarginals(0));
            /*
            String out = "";
            for (int i = 0; i < m.size(); i++) {
                out+= "x" + i + ": (" + m.get(i).get(0) + ", " + m.get(i).get(1) + ", " + m.get(i).get(2) + ", " + m.get(i).get(3) + ")";
                if(m.get(i).greatestValue() == 0){
                    out+= ", decoded to: 0";
                }
                if(m.get(i).greatestValue() == 1){
                    out+= ", decoded to: 1";
                }
                if(m.get(i).greatestValue() == 2){
                    out+= ", decoded to: w";
                }
                if(m.get(i).greatestValue() == 3){
                    out+= ", decoded to: w^2";
                }

                out+= "\n";

            }
            System.out.println(out);

            */
        }

        if(option1.equals("jointlc")){

            String inputWord = sc.nextLine();

            ADecoder NDD = new ADecoder(adjacencyMatrix.clone());
            NDD.initErrorCodeword(inputWord, 2);
            NDD.flood(50);

            ArrayList<BeliefVector> NDDResult = NDD.marginals(); //Results without doing any LC.

            System.out.println("Normal Computation");
            System.out.println(NDD.StringMarginals());

            ArrayList<ArrayList<BeliefVector>> LCDResults = new ArrayList<ArrayList<BeliefVector>>();

            for (int i = 0; i < inputWord.length(); i++) {

                int[][] newMat = new int[adjacencyMatrix.length][adjacencyMatrix.length];
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    System.arraycopy(adjacencyMatrix[j], 0, newMat[j], 0, adjacencyMatrix.length);
                }

                LCDecoder LCD = new LCDecoder(newMat);
                LCD.initErrorCodeword(inputWord, 2);
                LCD.LC(i);
                LCD.flood(50);

                System.out.println("LC on node: " + i);
                System.out.println(LCD.StringMarginals(i));

                ArrayList<BeliefVector> m = LCD.LCmarginals(i);
                LCDResults.add(m);

            }

            ArrayList<BeliefVector> jointlc = new ArrayList<BeliefVector>();
            for (int i = 0; i < adjacencyMatrix.length; i++) { //one beliefvector per node
                jointlc.add(new BeliefVector());
            }


            for (ArrayList<BeliefVector> LCDResult : LCDResults) {

                for (int j = 0; j < LCDResults.get(0).size(); j++) {
                    BeliefVector ans = jointlc.get(j);
                    BeliefVector LCiterationAns = LCDResult.get(j);

                    ans = BeliefVector.dot(ans, LCiterationAns);

                    ans.normalize();
                    jointlc.set(j, ans);
                }
            }

            for (BeliefVector v: jointlc) {
                System.out.println(v.toString());

            }



        }

        if(option1.equals("roundlc")){
            String inputWord = sc.nextLine();

            LCDecoder decoder= new LCDecoder(adjacencyMatrix);

            decoder.initErrorCodeword(inputWord, 3);

            ArrayList<BeliefVector> m = decoder.marginals();

            /*
            for (BeliefVector b: m) {
                System.out.println(F4.fromIndex(b.greatestValue()) + ": " +b.toString());
            }
            */

            decoder.RoundLCflood(10, 50);
             m = decoder.marginals();

            for (BeliefVector b: m) {
                System.out.println(F4.fromIndex(b.greatestValue()) + ": " +b.toString());
            }
        }

        if(option1.equals("lccomparison")){

            String inputWord = sc.nextLine();

            double E_b = 10;

            double N_0 = 0;

            int decodings = 200;

            int[][] globalMat = new int[adjacencyMatrix.length][adjacencyMatrix.length];
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                for (int k = 0; k < adjacencyMatrix.length; k++) {
                    globalMat[j][k] = adjacencyMatrix[j][k];
                }
            }

            GlobalFunctionDecoder codewordsGetter = new GlobalFunctionDecoder(globalMat);

            HashSet<String> codewords = codewordsGetter.codeWordsSet();

          //  System.out.println("BitEnergy/Noise; No-LC bit error rate; No-LC word error rate; No-LC biterrors per worderror; No-LC wrong word is a codeword; LC bit error rate; LC word error rate;  LC bitterors per worderror; LC wrong word is a codeword");
            System.out.println("BitEnergy/Noise; No-LC bit errors; LC bit errors; No-LC word errors; LC worderrors");
            for (int noise = 1; noise < 30; noise++) {

                N_0 = noise;

                int nonLCWordError = 0;
                int nonLCBitError = 0;
                int nonLCACodeWord = 0;

                int lcWordError = 0;
                int lcBitError = 0;
                int lcACodeWord = 0;

                int bits = 0;
                int words = 0;

                for (int i = 0; i < decodings; i++) {

                    words++;
                    bits+= inputWord.length();

                    BeliefVector[] awgn = AWGN.awgnWord(inputWord, E_b, N_0);

                    //Non-LC

                    int[][] newMat = new int[adjacencyMatrix.length][adjacencyMatrix.length];
                    for (int j = 0; j < adjacencyMatrix.length; j++) {
                        System.arraycopy(adjacencyMatrix[j], 0, newMat[j], 0, adjacencyMatrix.length);
                    }

                    ADecoder nonLCDecoder = new ADecoder(newMat);

                    nonLCDecoder.setBeliefs(awgn);

                    nonLCDecoder.flood(50);

                    int biterror = WordCompare.compare(inputWord, nonLCDecoder.decodedWord());

                    if (biterror > 0) {
                        nonLCBitError += biterror;
                        nonLCWordError++;

                        if(codewords.contains(nonLCDecoder.decodedWord()))
                            nonLCACodeWord++;
                    }

                    //LC

                    int[][] newLCmat = new int[adjacencyMatrix.length][adjacencyMatrix.length];
                    for (int j = 0; j < adjacencyMatrix.length; j++) {
                        System.arraycopy(adjacencyMatrix[j], 0, newLCmat[j], 0, adjacencyMatrix.length);
                    }

                    LCDecoder lcDecoder = new LCDecoder(newLCmat);

                    lcDecoder.setBeliefs(awgn);

                    lcDecoder.RoundLCflood(10, 200);

                    biterror = WordCompare.compare(inputWord, lcDecoder.decodedWord());

                    if (biterror > 0) {
                        lcBitError += biterror;
                        lcWordError++;

                        if(codewords.contains(lcDecoder.decodedWord()))
                            lcACodeWord++;
                    }

                }

                String dataPoints = "";


                /*
                if(nonLCBitError == 0) {
                    dataPoints += 10 * Math.log10(E_b / N_0) + "; " + 0 + "; " + 0 + ";" + 0 + "; " + 1 + "; ";
                }
                else {
                    dataPoints += 10 * Math.log10(E_b / N_0) + "; " + (double) nonLCBitError / (double) bits + "; " + (double) nonLCWordError / (double) words + "; " + (double) nonLCBitError/ (double) nonLCWordError + "; " + (double) nonLCACodeWord / (double) nonLCWordError + "; ";
                }


                if(lcBitError == 0) {
                    dataPoints += 0 + "; " + 0 + "; " + 0 + "; " + 1 + "; ";
                }
                else {
                    dataPoints +=  (double) lcBitError / (double) bits + "; " + (double) lcWordError / (double) words + "; " + (double) lcBitError/lcWordError + "; " + (double) lcACodeWord / (double) lcWordError;
                }

                */

                dataPoints += 10 * Math.log10(E_b / N_0) + "; " + nonLCBitError + "; " + lcBitError + "; " +  nonLCWordError  + "; " +lcWordError + "; ";

                System.out.println(dataPoints);
            }

        }

        if(option1.equals("checkmatrix")){
            MatrixReader.checkSymetric(adjacencyMatrix);
        }

        if(option1.equals("timeComparison")){
            String inputWord = sc.nextLine();

            MethodComparator comparator = new MethodComparator(adjacencyMatrix);

            comparator.runComparison(50,10,new int[]{50,100,150,200},500,30, inputWord);
        }

        if(option1.equals("floodingcomparison")){
            String inputword = sc.nextLine();

            MethodComparator comparator = new MethodComparator(adjacencyMatrix);

            comparator.runFloodingComparison(50,new int[]{1,2,4,8,16}, 50,2000,30,inputword);
        }

        if(option1.equals("comparetwo")){
            String inputword = sc.nextLine();

            MethodComparator comparator = new MethodComparator(adjacencyMatrix);

            comparator.runCompareTwoMethods(50,2,500, 4,250,200,21,inputword);
        }

        if(option1.equals("oneLC")){
            String inputword = sc.nextLine();

            MethodComparator comparator = new MethodComparator(adjacencyMatrix);

            comparator.runCompareOneLC(50,50,2000, 30,inputword);
        }

        if (option1.equals("selectiveLC")){
            String inputword = sc.nextLine();

            MethodComparator comparator = new MethodComparator(adjacencyMatrix);

            comparator.runCompareSelectiveLC(50,4, new int[]{100},2000,30,inputword);
        }

        if(option1.equals("generalDecoder")){
            String inputword = sc.nextLine();

            MethodComparator comparator = new MethodComparator(adjacencyMatrix);

            comparator.runCompareGeneralDecoder(new int[]{1,5,10,20,30,40,50,60,70},8000,30,inputword);

        }

        if(option1.equals("globalcompare")){
            String inputword = sc.nextLine();

            MethodComparator comparator = new MethodComparator(adjacencyMatrix);
        }

    }
}
