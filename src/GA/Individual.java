package GA;

import java.util.List;

public class Individual {
	List<Integer> gen;							
    List<Integer> fitnessTask;				// tinh toan Distance
    List<Integer> factorial_rank;			// Mot list gom cac thu hang theo tung tac vu
    private int skillFactor;				// Tac vu thu bao nhieu dat duoc tot nhat
    private double scalarFitness;			// 1/thu hang tai tac vu skill factor

    public Individual(List<Integer> gen, List<Integer> fitnessTask) {
        super();
        this.gen = gen;
        this.fitnessTask = fitnessTask;
    }

    Integer getMinFactorialRank() {
        Integer min = 10000000;
        for (Integer tmp : factorial_rank) {
            if (min > tmp) min = tmp;
        }
        return min;
    }

    List<Integer> getGen() {
        return gen;
    }
/**
 * lấy arraylist fitness của individual
 * @return
 */
    List<Integer> getFitnessTask() {
        return fitnessTask;
    }

    void setFitnessTask(List<Integer> fitnessTask) {
        this.fitnessTask = fitnessTask;
    }

    int getSkillFactor() {
        return skillFactor;
    }

    void setSkillFactor(int skillFactor) {
        this.skillFactor = skillFactor;
    }

    double getScalarFitness() {
        return scalarFitness;
    }

    void setScalarFitness(double scalarFitness) {
        this.scalarFitness = scalarFitness;
    }

    List<Integer> getFactorialRank() {
        return factorial_rank;
    }

    void setFactorial_rank(List<Integer> factorial_rank) {
        this.factorial_rank = factorial_rank;
    }

    @Override
    public String toString() {
        return "Individual [gen=" + gen +", BF     = "+fitnessTask.get(skillFactor) +", fitnessTask=" + fitnessTask
                + ", factorialRank=" + factorial_rank + ", skillFactor="
                + skillFactor + ", scalarFitness=" + scalarFitness + "]";
    }
}
