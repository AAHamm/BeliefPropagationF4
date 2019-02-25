package Structures;

import LocalComplementation.LCDecoder;
import Noise.AWGN;
import Noise.WordCompare;
import NonDiscriminative.ADecoder;

import java.util.ArrayList;

public class MethodComparator {

    public int[][] adjacencyMatrix;

    public MethodComparator(int[][] adjMatrix){
        this.adjacencyMatrix = adjMatrix;
    }

    public void runComparison(int normalFloodings, int lcFloodings, int[] lcOperations, int runs,  int levelsOfNoise, String codeword){

        System.out.println();

        double E_b = 10;

        for (int i = 1; i < levelsOfNoise; i++) {

            double N_0 = (double )i / 2;

            BeliefVector[][] softInformation = new BeliefVector[runs][codeword.length()];

            for (int j = 0; j < runs; j++) {
                BeliefVector[] awgn = AWGN.awgnWord(codeword, E_b, N_0);

                for (int k = 0; k < awgn.length; k++) {
                    softInformation[j][k] = awgn[k];
                }
            }

            double noise = Math.log10(E_b/N_0);

            DataPoint noLC = collectFloodingPoint(noise,runs,normalFloodings,codeword,softInformation);
            System.out.print(noLC.toString());

            ArrayList<DataPoint> lcDatapoints = new ArrayList<DataPoint>();
            for (int j = 0; j < lcOperations.length; j++) {
                lcDatapoints.add(collectLCPoint(noise,runs,lcFloodings,lcOperations[j],codeword,softInformation));
                System.out.print(lcDatapoints.get(j).toString2());
            }


            System.out.println();


        }
    }

    public void runFloodingComparison(int normalFloodings, int lcFloodings[], int lcOperations, int runs,  int levelsOfNoise, String codeword){

        System.out.println();

        double E_b = 10;

        for (int i = 1; i < levelsOfNoise; i++) {

            double N_0 = (double )i / 2;

            BeliefVector[][] softInformation = new BeliefVector[runs][codeword.length()];

            for (int j = 0; j < runs; j++) {
                BeliefVector[] awgn = AWGN.awgnWord(codeword, E_b, N_0);

                for (int k = 0; k < awgn.length; k++) {
                    softInformation[j][k] = awgn[k];
                }
            }

            double noise = Math.log10(E_b/N_0);

            DataPoint noLC = collectFloodingPoint(noise,runs,normalFloodings,codeword,softInformation);
            System.out.print(noLC.toString());

            ArrayList<DataPoint> lcDatapoints = new ArrayList<DataPoint>();
            for (int j = 0; j < lcFloodings.length; j++) {
                lcDatapoints.add(collectLCPoint(noise,runs,lcFloodings[j],lcOperations,codeword,softInformation));
                System.out.print(lcDatapoints.get(j).toString2());
            }


            System.out.println();


        }
    }


    public DataPoint collectFloodingPoint(double noise, int runs, int floodings, String codeword, BeliefVector[][] softInformation){

        int bits = 0;
        int words = 0;

        int bitErrors = 0;
        int wordErrors = 0;

        long time = 0;

        for (int i = 0; i < runs; i++) {
            words++;
            bits+= codeword.length();

            int[][] newMat = new int[adjacencyMatrix.length][adjacencyMatrix.length];
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                for (int k = 0; k < adjacencyMatrix.length; k++) {
                 newMat[j][k] = adjacencyMatrix[j][k];
                }
            }


            ADecoder nonLCDecoder = new ADecoder(newMat);

            nonLCDecoder.setBeliefs(softInformation[i]);


            long start = System.nanoTime();

            nonLCDecoder.flood(floodings);

            long end = System.nanoTime()  - start;

            time += end;

            int biterror = WordCompare.compare(codeword, nonLCDecoder.decodedWord());

            if (biterror > 0) {
                bitErrors += biterror;
                wordErrors++;

            }


        }

        double bitErrorRate = Math.log10((double) bitErrors/ (double) bits);
        double wordErrorRate = Math.log10((double )wordErrors/ (double )words);
        double avgtime = (double )time/ (double) runs / (double) 1000000000;

        return new DataPoint(noise,bitErrorRate,wordErrorRate,avgtime);
    }

    public DataPoint collectLCPoint(double noise, int runs, int floodings, int lcOperations, String codeword, BeliefVector[][] softInformation){
        int bits = 0;
        int words = 0;

        int bitErrors = 0;
        int wordErrors = 0;

        long time = 0;

        for (int i = 1; i < runs; i++) {
            words++;
            bits+= codeword.length();

            int[][] newMat = new int[adjacencyMatrix.length][adjacencyMatrix.length];
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                for (int k = 0; k < adjacencyMatrix.length; k++) {
                    newMat[j][k] = adjacencyMatrix[j][k];
                }
            }


            LCDecoder lcDecoder = new LCDecoder(newMat);

            lcDecoder.setBeliefs(softInformation[i]);


            long start = System.nanoTime();

            lcDecoder.RoundLCflood(floodings, lcOperations);

            long end = System.nanoTime()  - start;

            time += end;

            int biterror = WordCompare.compare(codeword, lcDecoder.decodedWord());

            if (biterror > 0) {
                bitErrors += biterror;
                wordErrors++;

            }


        }

        double bitErrorRate = Math.log10((double) bitErrors/ (double) bits);
        double wordErrorRate = Math.log10((double) wordErrors/ (double) words);
        double avgtime = (double) time / (double) runs / (double) 1000000000;

        return new DataPoint(noise,bitErrorRate,wordErrorRate,avgtime);
    }

}
