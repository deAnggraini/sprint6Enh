package id.co.bca.pakar.be.wf.service;

public interface Delegate<T> {
    void execute(T obj);
}
