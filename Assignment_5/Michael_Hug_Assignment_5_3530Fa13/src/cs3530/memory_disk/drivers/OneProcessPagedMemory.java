package cs3530.memory_disk.drivers;

import cs3530.memory_disk.MemoryUnit;
import cs3530.memory_disk.PageReferenceGenerator;
import cs3530.memory_disk.PageReferenceGeneratorFile;
import cs3530.memory_disk.PageReferenceGeneratorRandom;
import cs3530.memory_disk.PageReferenceGeneratorWithOracle;
import cs3530.memory_disk.Process;

public class OneProcessPagedMemory {
	
	// these describe the process
	private static final int EXECUTION_TIME = 500000;
	private static final int NUMBER_OF_PAGES = 32;
	
	
	// these describe the memory unit
	
	//private static final int FRAME_SIZE = 512;
	private static final int FRAME_DEFICIT = 3*NUMBER_OF_PAGES/4;  
		// number by which pages outnumber frames.
	private static final int MEMORY_SIZE_FRAMES = 8; // NUMBER_OF_PAGES-FRAME_DEFICIT;

	//private static final int MEMORY_NEEDED = NUMBER_OF_PAGES * FRAME_SIZE;
	
	/**
	 * Distributions to be used for generating pages
	 */
	private static final int[][] distributions = 
            { 
//                    {50, 30, 5, 5},
//                    {50, 30, 1, 1},
//                    {100, 10, 1, 1},
                {-1,-1,-1,-1},
                {50, 1, 1, 50},
                {-1,-1,-1,-1}
            };
	
	public static void main(String[] args) 
        {
                
		for (int i =0; i <4;i++)
                {
                    for (int j =0; j <5;j++)
                    {
                        //looop(i+1,j);
                        looop(j+1,i);
                    }
                }
                System.out.println();
	}
        
        public static void looop(int numruns, int selectionstrategy)
        {
            MemoryUnit mem = new MemoryUnit(MEMORY_SIZE_FRAMES, selectionstrategy);
            PageReferenceGeneratorRandom prg1 = new PageReferenceGeneratorRandom(distributions[2], 
                                            NUMBER_OF_PAGES, EXECUTION_TIME, 2);
            PageReferenceGenerator prg = new PageReferenceGeneratorWithOracle(prg1, mem);
            Process proc = new Process( NUMBER_OF_PAGES, mem, prg);
            while(proc.isRunning()) {
                    proc.step();
            }
            int numPageReq = mem.getCountMemoryReferences();
            int numFaults = mem.getCountPageFaults();
            double ratio = (double)numFaults/numPageReq;
            System.out.print("Run number="+numruns+", Selections strategy="+getNameofselectionsStratgy(selectionstrategy)+"\t");
            System.out.printf("Page Requests: %6d PageFaults: %8d Fault ratio: %.8f\n",
				numPageReq, numFaults, ratio);
        }
        
        public static String getNameofselectionsStratgy(int x)
        {
            if (x==0)
                return "choose_optimal";
            if (x==1)
                return "choose_LRU    ";
            if (x==2)
                return "choose_constant";
            if (x==3)
                return "choose_random ";
            return "Whoops.... Looks like all the programmers are busy playing red rover. We'll send someone right over. ";
        }

}
