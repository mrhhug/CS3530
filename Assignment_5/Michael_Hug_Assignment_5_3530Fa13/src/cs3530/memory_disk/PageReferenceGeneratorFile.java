package cs3530.memory_disk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import javolution.util.function.Consumer;

/**
 * Read page references from a Dinero formatted file.
 * 
 * Each line has a label (ignored) and a hex address, 32 bits.
 * 
 * @author Ben
 *
 */
public class PageReferenceGeneratorFile implements PageReferenceGenerator {

	/**
	 * The number of bits allocated to the offset in an address.
	 * The page size is 2^offsetBits. 
	 */
	private int offsetBits;
	private int pageSize;
	private String fileName;
	private BufferedReader input;
	private String nextLine;
        private int NumberOfReferences =500000  ;
	
	/**
	 * Read in data from the given file name.
	 * 
	 * @param filename  A Dinero formatted input file.
	 * @param offsetBits  The number of bits allocated to the offset in an address.
	 */
	public PageReferenceGeneratorFile(String filename, int offsetBits) {
		this.offsetBits = offsetBits;
		this.fileName = filename;
		try {
			FileReader fr = new FileReader(fileName);
			input = new BufferedReader(fr);
			nextLine = input.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		pageSize = 1;
		for(int i = 0; i < offsetBits; i++ )
			pageSize += pageSize;
	}

	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return nextLine != null;
	}

	@Override
	public Integer next() {
		String[] parts = nextLine.split("\\s+");
		int n = Integer.parseInt(parts[1], 16);
		try {
			nextLine = input.readLine();
			if(nextLine == null || nextLine.length() == 0)
				nextLine = null;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return n / pageSize;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("PageReferenceGeneratorFile.remove not implemented");
	}

	@Override
	public int oracle() {
		throw new UnsupportedOperationException("PageReferenceGeneratorFile.oracle not implemented");
	}

		public void forEach(Consumer<? super Integer> arg0) {
		// TODO Auto-generated method stub
		
	}

		public void forEachRemaining(Consumer<? super Integer> arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This is a hack, just looking at the trace files we have.
	 */
	@Override
	public int getNumberOfReferences() {
		return NumberOfReferences;
	}
        
        //
        public void setNumberOfReferences(int x) {
		NumberOfReferences=x;
	}
	@Override
	public int getNumberOfPages() {
		return 1 << (32-offsetBits);
	}
	
	

}
