package cs3530.tracer;

public abstract class Tracer {

	public abstract void write(Object... parameters);
	
	public void close() {
		// does nothing, but can be overridden
	}
	
}
