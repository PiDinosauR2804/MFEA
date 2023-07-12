package GA;

import java.util.ArrayList;
import java.util.List;

public abstract class Task {
	protected int dimension, capacity;


    public abstract void makeIndividualVail(List<Integer> ind);

    public abstract boolean checkIndividualVail(List<Integer> c);

    public abstract int computeFitness(List<Integer> chomo);

    public abstract int getLenGen();
    
    public abstract List<Integer> decode(List<Integer> chomo);
    
}
