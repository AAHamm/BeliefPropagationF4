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

        for (int i = -6; i < levelsOfNoise+1; i++) {

            double N_0 = Math.pow(10,(double) i /20);

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

        for (int i = -6; i < levelsOfNoise+1; i++) {

            double N_0 = Math.pow(10,(double) i /20);

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

    public void runCompareTwoMethods(int normalFloodings, int floodings1, int lc1, int floodings2, int lc2, int runs, int levelsOfNoise, String codeword){

        System.out.println();

        double E_b = 10;

        for (int i = 1; i < levelsOfNoise; i++) {

            double N_0 = Math.pow(10,(double) i /20);

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

            System.out.print(collectLCPoint(noise,runs,floodings1, lc1, codeword, softInformation).toString2());
            System.out.print(collectLCPoint(noise,runs,floodings2,lc2,codeword,softInformation).toString2());

            System.out.println();


        }

    }

    public void runCompareOneLC(int normalFloodings, int lcFloodings, int runs, int levelsOfNoise, String codeword){

        System.out.println();

        double E_b = 10;

        for (int i = -6; i < levelsOfNoise+1; i++) {

            double N_0 = Math.pow(10,(double) i /20);

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

            System.out.print(oneLCpoint(noise,runs,lcFloodings,codeword,softInformation).toString2());

            System.out.println();


        }

    }

    public void runCompareSelectiveLC(int normalFloodings, int lcFloodings, int[] lcOperations, int runs,  int levelsOfNoise, String codeword){

        System.out.println();

        double E_b = 10;

        for (int i = -6; i < levelsOfNoise+1; i++) {

            double N_0 = Math.pow(10,(double) i /20);

            BeliefVector[][] softInformation = new BeliefVector[runs][codeword.length()];

            for (int j = 0; j < runs; j++) {
                BeliefVector[] awgn = AWGN.awgnWord(codeword, E_b, N_0);

                for (int k = 0; k < awgn.length; k++) {
                    softInformation[j][k] = awgn[k];
                }
            }

            double noise = Math.log10(E_b/N_0);

            ArrayList<DataPoint> selectivelcDatapoints = new ArrayList<DataPoint>();

            ArrayList<DataPoint> lcDatapoints = new ArrayList<DataPoint>();
            for (int j = 0; j < lcOperations.length; j++) {
                lcDatapoints.add(collectLCPoint(noise,runs,lcFloodings,lcOperations[j],codeword,softInformation));
                System.out.print(lcDatapoints.get(j).toString());
                selectivelcDatapoints.add(collectSelectiveLC(noise,runs,lcFloodings,lcOperations[j],codeword,softInformation));
                System.out.print(selectivelcDatapoints.get(j).toString2());
            }


            System.out.println();


        }

    }

    public void runCompareGeneralDecoder(int[] floodings, int runs, int levelsOfNoise, String codeword){



        double E_b = 10;

        for (int i = -6; i < levelsOfNoise+1; i++) {

            System.out.println();

            double N_0 = Math.pow(10,(double) i /20);

            BeliefVector[][] softInformation = new BeliefVector[runs][codeword.length()];

            for (int j = 0; j < runs; j++) {
                BeliefVector[] awgn = AWGN.awgnWord(codeword, E_b, N_0);

                for (int k = 0; k < awgn.length; k++) {
                    softInformation[j][k] = awgn[k];
                }
            }

            double noise = Math.log10(E_b/N_0);

            for (int j = 0; j < floodings.length; j++) {
                DataPoint noLC = collectFloodingPoint(noise,runs,floodings[j],codeword,softInformation);
                if(j == 0)
                    System.out.print(noLC.toString());
                else
                    System.out.print(noLC.toString2());


            }

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

            nonLCDecoder.flood(floodings); //computation time of flooding

            String decodedWord = nonLCDecoder.decodedWord(); //computation time of marginal()

            long end = System.nanoTime()  - start;

            time += end;

            int biterror = WordCompare.compare(codeword, decodedWord);

            if (biterror > 0) {
                bitErrors += biterror;
                wordErrors++;

            }


        }

        double bitErrorRate = 10*Math.log10((double) bitErrors/ (double) bits);
        double wordErrorRate = 10*Math.log10((double )wordErrors/ (double )words);
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

        double bitErrorRate = 10*Math.log10((double) bitErrors/ (double) bits);
        double wordErrorRate = 10*Math.log10((double) wordErrors/ (double) words);
        double avgtime = (double) time / (double) runs / (double) 1000000000;

        return new DataPoint(noise,bitErrorRate,wordErrorRate,avgtime);
    }

    public DataPoint collectSelectiveLC(double noise, int runs, int floodings, int lcOperations, String codeword, BeliefVector[][] softInformation){

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

            lcDecoder.SelectiveLCFlood(floodings, lcOperations);

            long end = System.nanoTime()  - start;

            time += end;

            int biterror = WordCompare.compare(codeword, lcDecoder.decodedWord());

            if (biterror > 0) {
                bitErrors += biterror;
                wordErrors++;
            }
        }

        double bitErrorRate = 10*Math.log10((double) bitErrors/ (double) bits);
        double wordErrorRate = 10*Math.log10((double) wordErrors/ (double) words);
        double avgtime = (double) time / (double) runs / (double) 1000000000;

        return new DataPoint(noise,bitErrorRate,wordErrorRate,avgtime);

    }

    public DataPoint oneLCpoint(double noise, int runs, int floodings, String codeword, BeliefVector[][] softInformation){

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

            lcDecoder.pureLCflood(floodings, 1);

            long end = System.nanoTime()  - start;

            time += end;

            int biterror = WordCompare.compare(codeword, lcDecoder.decodedWord());

            if (biterror > 0) {
                bitErrors += biterror;
                wordErrors++;
            }
        }

        double bitErrorRate = 10*Math.log10((double) bitErrors/ (double) bits);
        double wordErrorRate = 10*Math.log10((double) wordErrors/ (double) words);
        double avgtime = (double) time / (double) runs / (double) 1000000000;

        return new DataPoint(noise,bitErrorRate,wordErrorRate,avgtime);
    }

}
