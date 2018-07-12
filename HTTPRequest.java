import java.util.ArrayList;
import java.util.regex.*;
import java.util.Scanner;

import java.io.*;

public class HTTPRequest{
	Pattern headerPattern = Pattern.compile("(.*): (.*)");

	ArrayList<String> headerNames = new ArrayList<>();
	ArrayList<String> headerValues = new ArrayList<>();
	String method;
	String uri;
	String version;
	ArrayList<Matcher> headerLine;
	String body;

	InputStreamReader reader;

	public HTTPRequest(InputStream inputStream) throws IOException{
		reader = new InputStreamReader(new BufferedInputStream(inputStream));

		//-------------------request line-------------------------------------------------------------
		Scanner requestLineScanner = new Scanner(nextLine());
		method = requestLineScanner.next();
		uri = requestLineScanner.next();
		version = requestLineScanner.next();

		//-------------------headers------------------------------------------------------------------
		String nextLine = nextLine();
		while(nextLine.contains(":")){
			Scanner headerScanner = new Scanner(nextLine);
			headerScanner.useDelimiter(":");
			headerNames.add(headerScanner.next());
			headerValues.add(headerScanner.next());
			nextLine = nextLine();
		}

		//-------------------body---------------------------------------------------------------------
		if(method.equals("GET")){
			return;
		}
		//after this point, we can assume that the method is not GET.
		//GET requests should not have a body, and if they do, it is to be ignored
		body = "";
		while(reader.ready()){
			body = body + (char)reader.read();
		}
	}

	private String nextLine() throws IOException{
		char ch;
		String line = "";
		while((ch = (char)reader.read()) != '\n'){
			line = line + ch;
		}

		return line;
	}

	public String getMethod(){return method;}
	public String getURI(){return uri;}
	public String getVersion(){return version;}
	public String getBody(){return body;}
	public ArrayList<String> getHeaderNames(){return headerNames;}
	public ArrayList<String> getHeaderValues(){return headerValues;}
}	