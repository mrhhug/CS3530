package cs3530.tracer;

public class ConsoleTracer extends Tracer {

	
	private ConsoleTracer() {}
	
	private static ConsoleTracer theTracer;
	
	public static  ConsoleTracer get() {
		if(theTracer == null) 
			theTracer = new ConsoleTracer();
		return theTracer;
	}
	
	
	@Override
	public void write(Object... parameters) {
		for(Object x : parameters) {
			System.out.print(x + " ");
		}
		System.out.println();
	}

}
