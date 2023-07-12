package GA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiTaskingGA {
	 private Population population;
	    private double pOfMutation;
	    private List<Task> tasks;
	    private static final int LIMIT = Integer.MAX_VALUE;
	    private static final int generation = 10;

	    public MultiTaskingGA(List<Task> tasks, int numOfInd, double pOfMutation) {
	        this.tasks = tasks;

	        this.pOfMutation = pOfMutation;
	        population = new Population(numOfInd, tasks);
	    }

	    public void run(int nN) {
	        ArrayList<Individual> bestSolution = new ArrayList<>();
	        Random r = new Random();

	        population.init();
// Thiet lap bestSolution 
//	        for(int w=0;w<50;w++) System.out.println(w+" : "+population.getIndividuals().get(w));
	        for (int i = 0; i < tasks.size(); i++)
	            bestSolution.add(population.getIndividuals().get(i));

	      

	        for (int i = 0; i < generation; i++) {
	            for (int ii = 0; ii < tasks.size(); ii++) {
	                Individual ind = population.getIndividualBestOfTask(ii);
	                if (bestSolution.get(ii).fitnessTask.get(ii) > ind.getFitnessTask().get(ii)) {
	                   
	                    bestSolution.set(ii, ind);
	                }

	                System.out.println(i + ":" + ii + ": " + ind.getFitnessTask());
	            }

	       

	            List<Individual> individuals = population.getIndividuals();
	            List<Individual> children = new ArrayList<>();

	            for (int j = 0; j < nN; j++) {
	                Individual a = individuals.get(r.nextInt(individuals.size()));
	                Individual b = individuals.get(r.nextInt(individuals.size()));
	                while (a == b) b = individuals.get(r.nextInt(individuals.size()));
	                int ta = a.getSkillFactor();
	                int tb = b.getSkillFactor();
	                double t = r.nextDouble();

	                if ((ta == tb) || (t > pOfMutation)) {
	                    children.addAll(crossOver(a, b));
	                } else {
	                    Individual ia = mutation(a);
	                    Individual ib = mutation(b);
	                    children.add(ia);
	                    children.add(ib);
	                }
	            }
//	            for(int e=0;e<children.size();e++) System.out.println(e+" : "+children.get(e)); 
	            population.add(children);
	            selection();
	            reComputeFitnessTaskForChild(children);
	            population.updateRankPopulation();
//	            for(int w=0;w<50;w++) System.out.println(w+" : "+population.getIndividuals().get(i)); 
	        }

	        System.out.println("Solution:");
	        for (Individual aBestSolution : bestSolution) {
	            System.out.println(aBestSolution);
	        }
	    }

	    private void reComputeFitnessTaskForChild(List<Individual> children) {
	        for (Individual child : children) {
	            List<Integer> fT = child.getFitnessTask();
	            for (int j = 0; j < tasks.size(); j++)
	                if (fT.get(j) == LIMIT) {
	                    Task t = tasks.get(j);
	                    fT.set(j, t.computeFitness(child.gen));
	                }
	        }
	    }

	    private ArrayList<Individual> crossOver(Individual a, Individual b) {
	        ArrayList<Individual> children = new ArrayList<>();
	        ArrayList<Integer> fR = new ArrayList<>();
	        for (int i = 0; i < tasks.size(); i++) fR.add(population.getIndividuals().size() + 1);
	        Random r = new Random();

	        int t = r.nextInt(a.getGen().size()-1);
	        ArrayList<Integer> cb = new ArrayList<>();
	        ArrayList<Integer> ca = new ArrayList<>();
	        for (int i = 0; i < t; i++) {
	            ca.add(a.getGen().get(i));
	            cb.add(b.getGen().get(i));
	        }
	        for (int i = 0; i < a.getGen().size(); i++) {
	            
	            if(ca.indexOf(b.getGen().get(i))==-1) ca.add(b.getGen().get(i));
	            if(cb.indexOf(a.getGen().get(i))==-1) cb.add(a.getGen().get(i));
	        }

	        if (population.checkIndividualVail(ca))
	            population.makeIndividualVail(ca);

	        Individual ind = new Individual(ca, null);
	        double rand = Math.random();

	        if (rand < 0.5) {
	            ind.setSkillFactor(a.getSkillFactor());
	        } else {
	            ind.setSkillFactor(b.getSkillFactor());
	        }
	        ArrayList<Integer> fitnessTa = new ArrayList<>();
	        for (int i = 0; i < tasks.size(); i++)
	            if (i != ind.getSkillFactor())
	                fitnessTa.add(LIMIT);
	            else
	                fitnessTa.add(tasks.get(i).computeFitness(ca));

	        ind.setFitnessTask(fitnessTa);
	        ind.setFactorial_rank(fR);
	        children.add(ind);

	        if (population.checkIndividualVail(cb))
	            population.makeIndividualVail(cb);

	        Individual ind2 = new Individual(cb, null);

	        rand = Math.random();
	        if (rand < 0.5) {
	            ind2.setSkillFactor(a.getSkillFactor());
	        } else {
	            ind2.setSkillFactor(b.getSkillFactor());
	        }

	        fitnessTa = new ArrayList<>();

	        for (int i = 0; i < tasks.size(); i++)
	            if (i != ind2.getSkillFactor())
	                fitnessTa.add(LIMIT);
	            else
	                fitnessTa.add(tasks.get(i).computeFitness(cb));

	        ind2.setFitnessTask(fitnessTa);
	        ind2.setFactorial_rank(fR);
	        children.add(ind2);

	        return children;
	    }

	    private Individual mutation(Individual a) {
	        Random r = new Random();
	        ArrayList<Integer> fR = new ArrayList<>();

	        for (int i = 0; i < tasks.size(); i++)
	            fR.add(population.getIndividuals().size() + 1);

	        int t = r.nextInt(a.getGen().size()-1);
	        while(t==0 || t==a.getGen().size()-1 ) t = r.nextInt(a.getGen().size()-1);
	        int k= r.nextInt(a.getGen().size()-1);
	        while(k==0 || k==a.getGen().size()-1) k= r.nextInt(a.getGen().size()-1);
	        List<Integer> c = new ArrayList<>(a.getGen());

	        int p=c.get(t);
	        c.set(t, c.get(k));
	        c.set(k, p);

	        if (population.checkIndividualVail(c))
	            population.makeIndividualVail(c);

	        Individual ind = new Individual(c, null);

	        ind.setSkillFactor(a.getSkillFactor());

	        ArrayList<Integer> fitnessTa = new ArrayList<>();

	        for (int i = 0; i < tasks.size(); i++)
	            if (i != ind.getSkillFactor())
	                fitnessTa.add(LIMIT);
	            else
	                fitnessTa.add(tasks.get(i).computeFitness(c));

	        ind.setFitnessTask(fitnessTa);
	        ind.setFactorial_rank(fR);
//	        System.out.println(ind.toString());
	        return ind;
	    }

	    private void selection() {
	        population.getIndividuals().sort((i1, i2) -> {
	            Double di1 = i1.getScalarFitness();
	            Double di2 = i2.getScalarFitness();
	            return di2.compareTo(di1);
	        });

	        ArrayList<Individual> newIndividuals = new ArrayList<>();
//	        for (int i =population.getIndividuals().size()- population.nIndividual; i < population.getIndividuals().size(); i++) {
	for (int i = 0; i < population.nIndividual*0.8; i++) {
		newIndividuals.add(population.getIndividuals().get(i));
	}
	for(int i=newIndividuals.size();i<population.nIndividual;i++) {
		Random f=new Random();
		 newIndividuals.add(population.getIndividuals().get(f.nextInt(population.getIndividuals().size())));
	}


	        population.setIndividuals(newIndividuals);
	    }
}
