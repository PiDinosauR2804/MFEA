package GA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class IDPC extends Task {
	private int domain, numcity;
    private int matrix1[][];
    private int domcity[][];
    private int lengthdom[];
    int begin,end;

    public IDPC(String fileName,int dimen) {
        super();

        parseFile(fileName);
        dimension=dimen;
    }

/**
 
/**
 * Hàm nhập dữ liệu file vào task
 */
    private void parseFile(String fileName) {
        try {
        	Scanner fileIn = new Scanner(new File(fileName));
            numcity = fileIn.nextInt();
            domain = fileIn.nextInt();
            begin=fileIn.nextInt();
            end=fileIn.nextInt();
            lengthdom =new int[domain];
            domcity = new int[domain][];
            matrix1 = new int[numcity+1][numcity+1];
            fileIn.nextLine();
            for(int i=0;i<domain;i++) {
                String[] nums = fileIn.nextLine().split("\\s");
                int k=0;
                lengthdom[i]=nums.length;
                domcity[i]=new int[lengthdom[i]];
                for (String s : nums){
                    domcity[i][k]=Integer.parseInt(s);
                    k++;
            }
     
    }
            while(fileIn.hasNextLine()) {
            	matrix1[fileIn.nextInt()][fileIn.nextInt()]=fileIn.nextInt();
            }
            for(int i=1;i<numcity+1;i++) {
            	for(int k=1;k<numcity+1;k++) {
            		
            		if(matrix1[i][k]==0 && i!=k ) matrix1[i][k]=Integer.MAX_VALUE;
            	}
        }
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int FindDomain(int city) {
    	for(int i=0;i<domain;i++) {
    		for(int k=0;k<lengthdom[i];k++) {
    			if(domcity[i][k]==city) return i;
    		}
    	}
		return 0;
    }
    
    @Override
    public int computeFitness(List<Integer> ind) {
        List<Integer> chomo =decode(ind);
//        System.out.println("Done");
        
        int shortestpath[] =new int[numcity+1];
        for(int i=1;i<=numcity;i++) shortestpath[i]=Integer.MAX_VALUE;
        shortestpath[1]=0;
        int min;
        int point=begin;
        int nowpath[]=new int[numcity+1];
        int matrix[][]=new int[numcity+1][numcity+1];
        List<Integer> pointed = new ArrayList<>();
        List<Integer> unpointed = new ArrayList<>();
        for(int i=begin;i<=end;i++) unpointed.add(i);
        
        for(int i=1;i<numcity+1;i++) {
        	for(int j=1;j<numcity+1;j++) {
        		matrix[i][j]=matrix1[i][j];
        		int a=chomo.indexOf(FindDomain(i));
        		int b=chomo.indexOf(FindDomain(j));
        		if(a==-1||b==-1) matrix[i][j]=Integer.MAX_VALUE;
        		if(b-a!=1&&b-a!=0) matrix[i][j]=Integer.MAX_VALUE;
        	}
        }
        
        
        for(int m=0;m<numcity;m++) {
          	 min=Integer.MAX_VALUE;
          	 
          	for(int i = 0;i<unpointed.size();i++) {
       		if(unpointed.get(i)==point) {
       			unpointed.remove(i);
       			break;
       		}
       	}
          	 
          	 for(Integer i:unpointed) {
          		 if(matrix[point][i]!=Integer.MAX_VALUE) {
          			 nowpath[i]=shortestpath[point]+matrix[point][i];
          			 if(nowpath[i]<shortestpath[i]) shortestpath[i]=nowpath[i];
          		 }
          	 }
          	 
     
       	pointed.add(point);
       	
       	// Tim diem xet tiep theo
       	for(int i=0;i<unpointed.size();i++) {
       		if(shortestpath[unpointed.get(i)]<min) {
       			min=shortestpath[unpointed.get(i)];
       			point=unpointed.get(i);
       		}
       	}
       	if(pointed.indexOf(point)!=-1) {
       		
       		return Integer.MAX_VALUE;}
       	

       	// Lay du lieu lan chay tiep theo
       		if(point==end) break;
     
       	
   }
//        System.out.println(shortestpath[point]);
//        System.out.println(pointed);
//        System.out.println("Done");
        return shortestpath[point];
    }

    @Override
    public void makeIndividualVail(List<Integer> ind) {
    }

    @Override
    public boolean checkIndividualVail(List<Integer> ind) {
        return true;
    }
/**
 * Quy chromosome ve array gom 0 va 1 de xet dang Knapsack
 * @param x
 * @return
 */
    public List<Integer> decode(List<Integer> tx) {	
    	 List<Integer> lA =new ArrayList<>();
    	 for(int i=0;i<dimension-1;i++) {
    		 lA.add(tx.get(i));
    	 }
    	 lA.add(FindDomain(end));
         return lA;
    }

    @Override
    public int getLenGen() {
        return dimension;
    }
}
