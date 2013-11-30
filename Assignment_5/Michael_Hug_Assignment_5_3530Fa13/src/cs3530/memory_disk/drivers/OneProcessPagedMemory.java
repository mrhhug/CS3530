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
	private static final int NUMBER_OF_PAGES = 200;
	
	
	// these describe the memory unit
	
	//private static final int FRAME_SIZE = 512;
	private static final int FRAME_DEFICIT = 3*NUMBER_OF_PAGES/4;  
		// number by which pages outnumber frames.
	private static final int MEMORY_SIZE_FRAMES = 30; // NUMBER_OF_PAGES-FRAME_DEFICIT;

	//private static final int MEMORY_NEEDED = NUMBER_OF_PAGES * FRAME_SIZE;
	
	/**
	 * Distributions to be used for generating pages
	 */
	private static final int[][] distributions = 
		{ 
			{50, 30, 5, 5},
			{50, 30, 1, 1},
			{100, 10, 1, 1},
		};
	
	
	public static void main(String[] args) {
		MemoryUnit mem = new MemoryUnit(MEMORY_SIZE_FRAMES);
		PageReferenceGeneratorRandom prg1 = new PageReferenceGeneratorRandom(distributions[2], 
						NUMBER_OF_PAGES, EXECUTION_TIME, 2);
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
		System.out.printf("Page Requests: %8d   PageFaults: %8d    Fault ratio: %f\n",
				numPageReq, numFaults, ratio);
	}

}
