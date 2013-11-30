package cs3530.tracer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvFileTracer extends CsvTracer {

	public CsvFileTracer()  {
		super();
		File f;
		try {
			f = outputFile();
			PrintStream out = new PrintStream(f);
			setOut(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		getOut().close();
		//System.out.println("Closing file " + getOut());
	}
	
	
	public static File outputFile() throws IOException {
		return outputFile(".csv");
	}

	public static File outputFile(String extension) throws IOException {
		//DateFormat df = new SimpleDateFormat("File-ddMMyy-hhmmss.SSS.txt");
		DateFormat df = new SimpleDateFormat("'Trace-'yyMMdd'-'hhmm'-'");
		String date = df.format(new Date());
		File f = File.createTempFile(date, extension,new File("."));
		return f;
	}
}
