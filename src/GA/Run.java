package GA;

import java.util.ArrayList;
import java.util.List;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 List<Task> tasks = new ArrayList<>();
		 tasks.add(new IDPC("C:\\Users\\BATMAN\\Downloads\\Data IDPCDU Nodes\\idpc_ndu_52_6_204.txt",5));
		 tasks.add(new IDPC("C:\\Users\\BATMAN\\Downloads\\Data IDPCDU Nodes\\idpc_ndu_52_6_204.txt",6));
//		 tasks.add(new IDPC("C:\\Users\\BATMAN\\Downloads\\Data IDPCDU Nodes\\idpc_ndu_52_6_204.txt",4));
//		for(int i=8;i<20;i++) tasks.add(new IDPC("C:\\Users\\BATMAN\\Downloads\\Data IDPCDU Nodes\\idpc_ndu_2102_19_141155.txt",i));
//		 for(int i=14;i<=17;i++) tasks.add(new IDPC("C:\\Users\\BATMAN\\Downloads\\Data IDPCDU Nodes\\idpc_ndu_1102_17_56280.txt",i));
	     MultiTaskingGA g = new MultiTaskingGA(tasks, 10, 0.8);
	     g.run(25);
		}
	
	

	
}


