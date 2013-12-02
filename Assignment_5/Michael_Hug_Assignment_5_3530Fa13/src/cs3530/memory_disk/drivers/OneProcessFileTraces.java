package cs3530.memory_disk.drivers;

import cs3530.memory_disk.MemoryUnit;
import cs3530.memory_disk.PageReferenceGenerator;
import cs3530.memory_disk.PageReferenceGeneratorFile;
import cs3530.memory_disk.PageReferenceGeneratorWithOracle;
import cs3530.memory_disk.Process;

public class OneProcessFileTraces {
	
	// these describe the process
	private static final int PROCESS_ADDRESS_BITS = 32;
	private static final int FRAME_BITS = 12;
	private static final int NUMBER_OF_PAGE_BITS = PROCESS_ADDRESS_BITS - FRAME_BITS;
	private static final int NUMBER_OF_PAGES = 1 << NUMBER_OF_PAGE_BITS;

	// these describe the memory unit	
	private static final int MEMORY_SIZE_FRAMES = 8;

	public static void main(String[] args) 
        {
                System.out.println("memory reference stream in the file spice.din");
		for (int i =0; i <5;i++)
                {
                    for (int j =0; j <4;j++)
                    {
                        looop(i+1,j);
                    }
                }
	}
        
        public static void looop(int numruns, int selectionstrategy) 
        {
                MemoryUnit mem = new MemoryUnit(MEMORY_SIZE_FRAMES,selectionstrategy);
		PageReferenceGenerator prg1;
		//prg1 = new PageReferenceGeneratorFile("Dinero-example-trace.din", FRAME_BITS);
		prg1 = new PageReferenceGeneratorFile("spice.din", FRAME_BITS);
		//prg1 = new PageReferenceGeneratorFile("cc1.din", FRAME_BITS);
		PageReferenceGenerator prg;
		prg = prg1;
		//prg = new PageReferenceGeneratorWithOracle(prg1, mem);
		Process proc = new Process( NUMBER_OF_PAGES, mem, prg);
		while(proc.isRunning()) {
			proc.step();
		}
		int numPageReq = mem.getCountMemoryReferences();
		int numFaults = mem.getCountPageFaults();
		double ratio = (double)numFaults/numPageReq;
                System.out.println("Run number="+numruns+", Selections strategy="+getNameofselectionsStratgy(selectionstrategy));
		System.out.printf("Page Requests: %6d PageFaults: %8d Fault ratio: %.8f\n",
				numPageReq, numFaults, ratio);
        }
        public static String getNameofselectionsStratgy(int x)
        {
            if (x==0)
                return "choose_optimal";
            if (x==1)
                return "choose_LRU";
            if (x==2)
                return "choose_constant";
            if (x==3)
                return "choose_random";
            return "Whoops.... Looks like all the programmers are busy playing red rover. We'll send someone right over. ";
        }
}
