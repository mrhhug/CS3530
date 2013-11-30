package cs3530.tracer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvTracer extends Tracer {

	private PrintStream out;
	
	
	
	public CsvTracer(PrintStream out) {
		super();
		this.out = out;
	}

	public CsvTracer() {
		super();
	}

	@Override
	public void write(Object... parameters) {
		for (int i = 0; i < parameters.length; i++) {
			Object x = parameters[i];
		
			if(x instanceof Integer) {
				out.printf("%d", x);
			} else if(x instanceof Double) {
				out.printf("%f", x);
			} else {
				out.printf("\"%s\"", x);
			}
			if(i < parameters.length - 1) {
				out.print(",");
			}
		}
		out.println();
	}
	
	
	
	
	
	public void setOut(PrintStream out) {
		this.out = out;
	}

	
	
	public PrintStream getOut() {
		return out;
	}

	public static File outputFile() throws IOException {
		return outputFile(".csv");
	}

	public static File outputFile(String extension) throws IOException {
		//DateFormat df = new SimpleDateFormat("File-ddMMyy-hhmmss.SSS.txt");
		DateFormat df = new SimpleDateFormat("Trace-ddMMyy-hhmm");
		String date = df.format(new Date());
		File f = File.createTempFile(date, extension);
		return f;
	}
}
