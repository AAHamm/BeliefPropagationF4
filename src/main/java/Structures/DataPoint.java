package Structures;

public class DataPoint {

    double noise;
    double bitErrorRate;
    double wordErrorRate;
    double time;


    public DataPoint(double noise, double bitErrorRate, double wordErrorRate, double time) {
        this.noise = noise;
        this.bitErrorRate = bitErrorRate;
        this.wordErrorRate = wordErrorRate;
        this.time = time;
    }

    public double getNoise() {
        return noise;
    }

    public double getBitErrorRate() {
        return bitErrorRate;
    }

    public double getWordErrorRate() {
        return wordErrorRate;
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "" + noise + "; " + bitErrorRate + "; " + wordErrorRate +"; " + time + "; ";
    }

    public String toString2(){
        return  "" + bitErrorRate + "; " + wordErrorRate +"; " + time + "; ";
    }
}
