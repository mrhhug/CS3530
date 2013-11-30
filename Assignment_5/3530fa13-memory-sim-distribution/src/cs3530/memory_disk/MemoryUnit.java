package cs3530.memory_disk;

import java.util.Arrays;

import static cs3530.memory_disk.Tracing.*;

public class MemoryUnit {
	
	
	private int numberOfFrames;
	private int[] frameToPageMap;  // maps frames to pages
		// an entry of -1 means that the frame is unused
	
	private int[] frameRefTime;
			// this stores a timestamp for when the frame is referenced.
			// we'll use the current count of memory references
	private int[] pageLoadTime;
			// this stores a timestamp for when the page was loaded into the frame
	
	private int[] pageTable;
	private Process theProcess;
	
	/**
	 * Create a memory unit with the given number of Frames
	 * @param memorySizeInFrames Should be positive
	 * 
	 * 
	 */
	public MemoryUnit(int memorySizeInFrames) {
		numberOfFrames = memorySizeInFrames;
		frameToPageMap = new int[numberOfFrames];
		Arrays.fill(frameToPageMap, -1);
		frameRefTime = new int[numberOfFrames];
		pageLoadTime = new int[numberOfFrames];
	}
	
	/**
	 * Find the index of an unused frame and return it.
	 * Return -1 if no frame is empty.
	 * @return Index of empty frame or -1 if no frame is available.
	 */
	private int findEmptyFrame() {
		int i = 0;
		while(i < numberOfFrames && frameToPageMap[i] >= 0) 
			i++;
		if(i < numberOfFrames)
			return i;
		else 
			return -1;
	}
	
	
	/**
	 * This picks a page to evict from memory
	 * and returns the index of the frame freed up.
	 * @return
	 */
	private int evictPage() {
		int x = choose_constant();
		int page = frameToPageMap[x];
		//System.out.printf("Process %4d Page %4d evicted from frame %4d\n",
		//		theProcess.getProcessId(), page, x);
		trace(String.format("Process %4d Page %4d evicted from frame %4d",theProcess.getProcessId(), page, x));
		// set the processes page table to indicate no frame is mapped
		pageTable[page] = -1;
		frameToPageMap[x] = -1;
		return x;
	}

	/**
	 * Picks a page to evict, always the same one
	 * @return
	 */
	@SuppressWarnings("unused")
	private int choose_constant() {
		int x = 0;
		return x;

	}
	
	/**
	 * Picks a page to evict, using LRU
	 * @return
	 */
	@SuppressWarnings("unused")
	private int choose_LRU() {
		int x = 0;
		//System.out.printf("frame %4d   ref-time %4d\n", 0, frameRefTime[0]);
		trace(String.format("frame %4d   ref-time %4d\n", 0, frameRefTime[0]));
		for(int i = 1; i < numberOfFrames; i++ ) {
			//System.out.printf("frame %4d   ref-time %4d", i, frameRefTime[i]);
			if(frameRefTime[x] > frameRefTime[i]) {
				x = i;
			//	System.out.print(" ****");
			}
			//System.out.println();
		}
		return x;
	}
	
	/**
	 * This picks a page to evict randomly.
	 * @return
	 */
	@SuppressWarnings("unused")
	private int choose_random() {
		// pick a frame at random
		int x = (int)(Math.random() * numberOfFrames);
		return x;
	}
	
	/**
	 * This uses the oracle in the page reference generator.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private int choose_optimal() {
		int x = theProcess.getPageReferenceGenerator().oracle();
		return x;
	}
	
	/**
	 * Process p is signaling that it had a page fault when accessing
	 * page number p in its space.
	 * 
	 * @param p
	 * @param page
	 */
	public void pageFault(Process p, int page) {
		countPageFaults++;
		//System.out.printf("Page %4d --> PAGE FAULT\n", page);
		trace(String.format("Page %4d --> PAGE FAULT\n", page));
		int frame = findEmptyFrame();
		if(frame < 0) {
			frame = evictPage();
		}
		frameToPageMap[frame] = page;
		pageTable[page]  = frame;
		p.truncateCurrentRequstStream();
		pageLoadTime[frame] = countMemoryReferences;
	}
	
	
	public void processStarted(Process p) {
		int pages = p.getMemoryNeeded();
		pageTable = new int[pages];
		Arrays.fill(pageTable, -1);
		theProcess = p;
	}
	
	private int countPageFaults;
	private int countMemoryReferences;
	
	public void referenceMemory(Process p, int page) {
		countMemoryReferences++;
		int frame = pageTable[page];
		if(frame < 0) {
			pageFault(p, page);
			frame = pageTable[page];
		}
		frameRefTime[frame] = countMemoryReferences;
		//System.out.printf("Page %4d --> Frame %4d\n", page, frame);
		trace(String.format("Page %4d --> Frame %4d\n", page, frame));
	}

	public int getCountPageFaults() {
		return countPageFaults;
	}

	public int getCountMemoryReferences() {
		return countMemoryReferences;
	}
	
	
	public int getMemorySize() {
		return numberOfFrames;
	}
	
	public int[] getFrameToPageMap() {
		return frameToPageMap;
	}
}
