package cs3530.memory_disk;

import java.util.Iterator;

/**
 * Represents a process using paging and, perhaps, virtual memory.
 * @author Ben
 *
 */
public class Process{
	
	
	/**
	 * Number of pages in this process.
	 */
	private int memoryNeeded;
	
	private MemoryUnit memory;
	
	// the page generator to use
	private PageReferenceGenerator prg;
	
	private int processId;
	
	private static int processIdCounter = 1;
	
	/**
	 * Initialize a process that will execute for a certain amount of time and
	 * 	will use a certain number of pages of memory.
	 *
	 * @param memoryNeeded	The number of pages this process requires 
	 * @param memory  The memory unit supporting this process
	 */
	public Process( int memoryNeeded, MemoryUnit memory, PageReferenceGenerator prg) {
		this.prg = prg;
		this.memoryNeeded = memoryNeeded;
		this.memory = memory;
		processId = processIdCounter++;
		memory.processStarted(this);
	}
	
	public Iterator<Integer> getBlockRequestStream() {
		return prg;
	}
	
	public void truncateCurrentRequstStream() {
		// a no-op when not time-slicing.
	}
	
	/**
	 * Process runs as long as there are page requests.
	 * @return
	 */
	public boolean isRunning() {
		return prg.hasNext();
	}
	
	private int countPageRequests;
		
	/**
	 * Execute one cycle, that is, make one page request.
	 */
	public void step() {
		countPageRequests++;
		int page = prg.next(); 
		memory.referenceMemory(this, page);
//		System.out.printf("Page %4d --> Frame %4d\n", page, frame);
	}

	public int getCountPageRequests() {
		return countPageRequests;
	}

	
	
	public int getMemoryNeeded() {
		return memoryNeeded;
	}
	
	public int getProcessId() {
		return processId;
	}
	
	public PageReferenceGenerator getPageReferenceGenerator() {
		return prg;
	}
}
