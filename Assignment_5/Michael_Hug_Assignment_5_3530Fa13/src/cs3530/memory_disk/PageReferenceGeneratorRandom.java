package cs3530.memory_disk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javolution.util.function.Consumer;


/**
 * Generate a series of page references according to percentages provided.
 * 
 * First reference is 0
 * References after that are determined according to percentage chances:
 * A% the next reference is the same
 * B% the next reference is the next page (if any)
 * C% the next page is another randomly chosen page
 * D% the next page is one of a list of recent references (if any)
 * 
 * @author Ben
 *
 */
public class PageReferenceGeneratorRandom implements PageReferenceGenerator {

	private double a, b, c;   // cutoffs for choosing cases
	private int numberOfPages;
	private int numberOfReferences;
	private int numRecents;
	
	private static Random gen = new Random();
	
	private int lastGenerated = -1;
	private int numGenerated = 0;
	private List<Integer> recentRefs = new ArrayList<>();
	
	/**
	 *  wA, wB, wC and wD are weights that will be normalized to sum to 1.
	 *  All should be non-negative integers.

	 * @param numberOfPages  Number of pages for the process being simulated
	 * @param numberOfReferences  Number of references to generate
	 * @param numRecents Number of recent references to remember for case D
	 */
	public PageReferenceGeneratorRandom(int wA, int wB, int wC, int wD,
				int numberOfPages, int numberOfReferences, int numRecents) {
		double sum = wA + wB + wC + wD;
		a = wA / sum;
		b = a + wB / sum;
		c = b + wC / sum;
		//System.out.printf("%f %f %f\n", a, b, c);
		this.numberOfPages = numberOfPages;
		this.numberOfReferences = numberOfReferences;
		this.numRecents = numRecents;
	}

	/**
	 *  Same operation as the other constructor but passing an array of 
	 *  	weights rather than the individual weights.
	 * @param wABCD  Should be of length 4, non-negative integers with a positive sum
	 * @param numberOfPages  Number of pages for the process being simulated
	 * @param numberOfReferences  Number of references to generate
	 * @param numRecents Number of recent references to remember for case D
	 */
	public PageReferenceGeneratorRandom(int[] wABCD,
			int numberOfPages, int numberOfReferences, int numRecents) {
		this(wABCD[0],wABCD[1],wABCD[2],wABCD[3], numberOfPages, numberOfReferences, numRecents);
	}
	
	@Override
	public boolean hasNext() {
		return numGenerated < numberOfReferences;
	}

	@Override
	public Integer next() {
		if(numGenerated == 0) {
			numGenerated++;
			lastGenerated = 0;
			return 0;
		} else if(hasNext()) {
			boolean chosen = false;
			while(!chosen) {
				double x = gen.nextDouble();
				if(x <= a) {
					chosen = true;
				} else if(x <= b) {
					if(lastGenerated < numberOfPages - 1){
						lastGenerated++;
						chosen = true;
					} else {
						int p = gen.nextInt(numberOfPages);
						if( p != lastGenerated && p != lastGenerated+1) {
							lastGenerated = p;
							chosen = true;
						}
					}
				} else if(x <= c) {
					int p = gen.nextInt(numberOfPages);
					if( p != lastGenerated && p != lastGenerated+1) {
						lastGenerated = p;
						if(!recentRefs.contains(p)) {
							if(recentRefs.size() >=  numRecents) {
								recentRefs.remove(0);
							}
							recentRefs.add(p);
						}
						chosen = true;
					}
				} else {
					if(recentRefs.size() > 0) {
						int i = gen.nextInt(recentRefs.size());
						int p = recentRefs.get(i);
						if(p != lastGenerated && p != lastGenerated+1) {
							lastGenerated = p;
							chosen = true;
						}
					}
				}
			}
			numGenerated++;
			return lastGenerated;
		} else {
			throw new IllegalStateException("There are no more references to generate");
		}
		
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("PageReferenceGeneratorRandom.remove not implemented");
	}

	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

	@Override
	public int oracle() {
		throw new UnsupportedOperationException("PageReferenceGeneratorRandom.oracle not implemented");
	}

		public void forEach(Consumer<? super Integer> arg0) {
		// TODO Auto-generated method stub
		
	}

		public void forEachRemaining(Consumer<? super Integer> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfReferences() {
		return numberOfReferences;
	}

	@Override
	public int getNumberOfPages() {
		return numberOfPages;
	}

}
