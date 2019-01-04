package Noise;

import java.util.Random;

public class AWGN {
    public static double[] getV(){
        Random r =  new Random();

        double s = 0;
        double u = 0;
        double v = 0;
        do  {
            u = r.nextDouble() * 2 - 1; // u, v in [-1, 1]
            v = r.nextDouble() * 2 - 1;

            s=u*u;
            s+=v*v;
        }
        while(s >= 1 || s == 0);

        double z0 = u*Math.sqrt(-2*Math.log(s)/s);
        double z1 = v*Math.sqrt(-2*Math.log(s)/s);

        return new double[]{z0, z1};
    }

    public static double[] getVWithVariance(double variance){
        Random r =  new Random();

        double s = 0;
        double u = 0;
        double v = 0;
        do  {
            u = (r.nextDouble() * 2 - 1); // u, v in [-1, 1]
            v = (r.nextDouble() * 2 - 1);

            s=u*u;
            s+=v*v;
        }
        while(s >= 1 || s == 0);

        double z0 = Math.sqrt(variance)*u*Math.sqrt(-2*Math.log(s)/s);
        double z1 = Math.sqrt(variance)*v*Math.sqrt(-2*Math.log(s)/s);

        return new double[]{z0, z1};
    }

    public static double ri(int bit, double codeRate, double bitEnergy, double bitNoiseN_0){
        double t = ((2*bit)-1)*Math.sqrt(codeRate*bitEnergy);
        double v = getVWithVariance(bitNoiseN_0/2)[0]; //NEED VARIANCE N_0/2 TODO

        return (t+v);
    }

    public static double[] probability(int bit ,double codeRateR, double bitEnerghyE_b, double bitNoiseN_0){

        double[] probs = new double[2];
        double ri  = ri(bit, codeRateR, bitEnerghyE_b, bitNoiseN_0);

        probs[1] = 1/(1+Math.exp((0-2)* Math.sqrt(codeRateR*bitEnerghyE_b)*ri/(bitNoiseN_0/2)));
        probs[0] = 1 - probs[1];

        return probs;
    }
}
