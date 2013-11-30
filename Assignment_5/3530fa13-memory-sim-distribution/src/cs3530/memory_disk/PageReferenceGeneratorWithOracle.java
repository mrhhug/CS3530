package cs3530.memory_disk;

import java.util.Arrays;
import java.util.Iterator;

public class PageReferenceGeneratorWithOracle implements PageReferenceGenerator {

	
	// store all references
	private int references[];
	// pointer to current reference to return
	private int current;
	
	private int numberOfPages;
	
	private MemoryUnit mu;
	
	// time of next reference to a page
	private int[] nextRef;
	
	public PageReferenceGeneratorWithOracle(PageReferenceGenerator prg, MemoryUnit mu) {
		this.mu = mu;
		int numberOfReferences = prg.getNumberOfReferences();
		references = new int[numberOfReferences];
		int count = 0;
		for(int r : prg) {
			references[count++] = r;
		}
		current = 0;
		numberOfPages = prg.getNumberOfPages();
		initializeNextRef();
	}
	
	

	@Override
	public boolean hasNext() {
		return current < references.length;
	}

	@Override
	public Integer next() {
		int ref = references[current];
		updateNextRef(current);
		current++;
		return ref;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("PageReferenceGeneratorWithOracle.remove not implemented");	
	}

	@Override
	public Iterator<Integer> iterator() {
		return this;
	}
	
	/**
	 * Find the frame with page that will not be used for the longest time.
	 * 
	 * @return Frame with the that will be used least soon.
	 */
	public int oracle() {
		// stub with a random return for now.
		//return (int)(Math.random()*numberOfPages);
		
		int[] ftop = mu.getFrameToPageMap();
		int pickFrame = 0;
		for(int i = 0; i < ftop.length; i++ ) {
			if(nextRef[ftop[i]] > nextRef[ftop[pickFrame]])
				pickFrame = i;
		}
		return pickFrame;
	}


	@Override
	public int getNumberOfReferences() {
		return references.length;
	}

	@Override
	public int getNumberOfPages() {
		return numberOfPages;
	}

	/**
	 * 	The nextref table starts with the time
	 * 	at which each page appears first.
	 * 	A very large value is used for pages that never appear. 
	 */
	private void initializeNextRef() {
		nextRef = new int[getNumberOfPages()];
		Arrays.fill(nextRef, Integer.MAX_VALUE);
		for (int i = 0; i < nextRef.length; i++) {
			int page = references[i];
			if(nextRef[page] < 0) {
				nextRef[page] = i;
			}
		}
	}

	/**
	 * Update the nextRef table for the reference at location i.
	 * When that reference is used, the next reference for that page will be
	 * later in the references list.
	 */
	private void updateNextRef(int i) {
		int ref = references[i];
		int j = i + 1;
		while(j < references.length && references[j] != ref) 
			j++;
		if(j < references.length) 
			nextRef[ref] = j;
		else
			nextRef[ref] = Integer.MAX_VALUE;
	}
	
}
