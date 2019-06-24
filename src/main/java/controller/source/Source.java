package controller.source;

public interface Source<S> {

    void update(S source);
    S getSource();
}
