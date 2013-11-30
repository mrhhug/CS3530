package cs3530.memory_disk;

import cs3530.tracer.ConsoleTracer;
import cs3530.tracer.NullTracer;
import cs3530.tracer.Tracer;

public class Tracing {

	private static Tracer tracer;
	static {
		tracer = ConsoleTracer.get();
		tracer = new NullTracer();
	}
	
	public static void trace(Object... parameters) {
		tracer.write(parameters);
	}
	
	public static void closeTrace() {
		tracer.close();
	}
	
}
