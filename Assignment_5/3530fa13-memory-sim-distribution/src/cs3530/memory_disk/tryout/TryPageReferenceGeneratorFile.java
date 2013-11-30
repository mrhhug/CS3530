package cs3530.memory_disk.tryout;

import cs3530.memory_disk.PageReferenceGenerator;
import cs3530.memory_disk.PageReferenceGeneratorFile;

public class TryPageReferenceGeneratorFile {

	public static void main(String[] args) {
		PageReferenceGenerator prg = new PageReferenceGeneratorFile("tex.din", 12);
		for(int x : prg) {
			System.out.println(x);
		}
	}

}
