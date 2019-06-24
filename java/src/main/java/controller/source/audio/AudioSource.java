package controller.source.audio;

import controller.source.Source;

public class AudioSource implements Source<Double> {

    private double signal;
    private double rms;

    @Override
    public void update(Double source) {

        //System.out.println(signal);
        signal = source;
    }

    @Override
    public Double getSource() {
        return signal;
    }

    public void setRms(double rms) {
        this.rms = rms;
    }

    public double getRms() {
        return rms;
    }
}
