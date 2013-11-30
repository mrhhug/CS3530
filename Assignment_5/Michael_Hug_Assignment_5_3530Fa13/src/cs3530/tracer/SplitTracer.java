package cs3530.tracer;

public class SplitTracer extends Tracer {

	private Tracer tracer1, tracer2;
	
	
	
	
	public SplitTracer(Tracer tracer1, Tracer tracer2) {
		super();
		this.tracer1 = tracer1;
		this.tracer2 = tracer2;
	}




	@Override
	public void write(Object... parameters) {
		tracer1.write(parameters);
		tracer2.write(parameters);
	}

}
