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
	
	private static final int MEMORY_SIZE_FRAMES = 177;

	
	
	
	
	public static void main(String[] args) {
		MemoryUnit mem = new MemoryUnit(MEMORY_SIZE_FRAMES);
		PageReferenceGenerator prg1;
		//prg1 = new PageReferenceGeneratorFile("Dinero-example-trace.din", FRAME_BITS);
		prg1 = new PageReferenceGeneratorFile("tex.din", FRAME_BITS);
		prg1 = new PageReferenceGeneratorFile("cc1.din", FRAME_BITS);
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
		System.out.printf("Page Requests: %8d   PageFaults: %8d    Fault ratio: %.8f\n",
				numPageReq, numFaults, ratio);
	}

}
