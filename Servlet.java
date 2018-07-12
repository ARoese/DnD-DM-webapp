import java.io.*;

import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

//main class.

/*
	Note: this servlet is *NOT* HTTP compliant, and is only meant to work as a back-end specifically
	for the webapp. It is very flimsy, and may even crash if it recieves a bad request.

	USE WITH CAUTION
*/
public class Servlet{
	static ArrayList<Item> effectItems = new ArrayList<>();
	static ArrayList<String> initiativeItems = new ArrayList<>();
	public static void main(String[] args) throws IOException, FileNotFoundException{
		Pattern timePattern = Pattern.compile("((((\\d*):)?([0-2]?\\d):)?([0-6]?\\d):)?([0-6]?\\d)");
		Pattern effectElementPattern = Pattern.compile("\"name\":\"(.*)\",\"days\":([0-9]*),\"hours\":([0-9]*),\"minutes\":([0-9]*),\"seconds\":([0-9]*),\"expired\":(true|false)");

		//get items from previous session------------------------------------------------------------------------------
		Scanner session = new Scanner(new File("effect-session.json"));
		session.useDelimiter("}");
		while(true){
			Matcher elementMatcher;
			try{
				elementMatcher = effectElementPattern.matcher(session.next());
				elementMatcher.find();
			}catch(Exception e){
				break;
			}

			try{
				effectItems.add(new Item(elementMatcher.group(1), Integer.parseInt(elementMatcher.group(2)),
			                   Integer.parseInt(elementMatcher.group(3)), Integer.parseInt(elementMatcher.group(4)),
			                   Integer.parseInt(elementMatcher.group(5))) );

				System.out.println("effect item loaded from previous session");
			}catch(Exception e){
				break;
			}
		}









		//-----------------------------------start server----------------------------------
		ServerSocket ss = new ServerSocket(80);
		Socket socket;

		InputStream in;
		OutputStream out;

		while(true){
			socket = ss.accept();
			socket.setSoTimeout(2000);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			System.out.println("\nconnection established");

			HTTPRequest request = null;
			try{
				request = new HTTPRequest(in);
			}catch(java.net.SocketTimeoutException e){
				System.out.println("Socket timed out");
				socket.close(); //clean up
				continue;
			}
			System.out.println("method: " + request.getMethod());
			System.out.println("   URI: " + request.getURI());
			System.out.println("  body: " + request.getBody());


			PrintStream outputWriter = new PrintStream(out);
			
			//if is a GET request-------------------------------------------------------------------------
			if(request.getMethod().equals("GET")){
				String uri = request.getURI();

				if(uri.equals("/")){
					uri = "/default.html";
				}

				try{ //if requested file does not exist, return a 404
					FileInputStream thrower = new FileInputStream(uri.substring(1, uri.length()));
					thrower.close();
				}catch(IOException e){
					outputWriter.print("HTTP/1.1 404 Not Found\r\n");
					outputWriter.print("Server: custom\r\n");
					outputWriter.print("Content-Type: text/html\r\n");
					outputWriter.print("\r\n");
					outputWriter.print("<h1>404 Not Found</h1>");

					//clean up prematurely
					socket.close();

					continue;
				}

				//get file type and send headers--------------------------------------------------------
				outputWriter.print("HTTP/1.1 200 OK\r\n");
				outputWriter.print("Server: custom\r\n");
				if(request.getURI().contains(".html")){
					outputWriter.print("Content-Type: text/html\r\n");
					outputWriter.print("Cache-Control: max-age=86400\r\n");
				}else if(request.getURI().contains(".css")){
					outputWriter.print("Content-Type: text/css\r\n");
					outputWriter.print("Cache-Control: max-age=86400\r\n");
				}else if(request.getURI().contains(".ico")){
					outputWriter.print("Content-Type: image/x-icon\r\n");
					outputWriter.print("Cache-Control: max-age=86400\r\n");
				}else if(request.getURI().contains(".png")){
					outputWriter.print("Content-Type: image/png\r\n");
					outputWriter.print("Cache-Control: max-age=86400\r\n");
				}else if(request.getURI().contains(".jpg")){
					outputWriter.print("Content-Type: image/jpeg\r\n");
					outputWriter.print("Cache-Control: max-age=86400\r\n");
				}else if(request.getURI().contains(".json")){
					outputWriter.print("Content-Type: application/json\r\n");
					outputWriter.print("Cache-Control: no-store\r\n");
				}else if(request.getURI().contains(".js")){
					outputWriter.print("Content-Type: application/javascript\r\n");
					outputWriter.print("Cache-Control: max-age=86400\r\n");
				}
				outputWriter.print("\r\n");
				//send body----------------------------------------------------------------------------
				BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(uri.substring(1, uri.length())), 500);
				int nextByte;
				try{
					while((nextByte = fileInput.read()) != -1){
						out.write((byte)nextByte);
					}
				}catch(Exception e){
					socket.close(); //close socket
					continue;
				}

				fileInput.close();
			
			//if is a POST request------------------------------------------------------------------
			}else if(request.getMethod().equals("POST")){
				Scanner commandScanner = new Scanner(request.getBody());
				boolean invalidTime = false;
				String token = commandScanner.next();	

				//send response line and headers-----------------------------
				outputWriter.print("HTTP/1.1 200 OK\r\n");
				outputWriter.print("Server: custom\r\n");
				outputWriter.print("Content-Type: text/plain\r\n");
				outputWriter.print("\r\n");

				//parse command------------------------------------------------------------------------------------------	
				//if is from effect list
				if(token.equals("effect:")){
					token = commandScanner.next();
					if(token.toLowerCase().equals("add")){
						try{
							String name = commandScanner.next();
							Scanner timeScanner = new Scanner(commandScanner.next());
							timeScanner.useDelimiter(":");	

						try{
								ArrayList<Integer> timeList = new ArrayList<>();
								while(timeScanner.hasNext()){
								timeList.add(Integer.parseInt(timeScanner.next())); //add numbers user enters in
								}
								while(timeList.size() < 4){
									timeList.add(0, 0); //add zeroes until length is 4
								}
								if(timeList.get(1) >= 24 || timeList.get(2) >= 60 || timeList.get(3) >= 60){ //validate time
									invalidTime = true;
								}else{
									effectItems.add(new Item(name, timeList.get(0), timeList.get(1), timeList.get(2), timeList.get(3)));
								}
							}catch(Exception e){
								invalidTime = true;
							}
						}catch(Exception e){
							outputWriter.print("error: missing parameter from add command");
						}
					}else if(token.toLowerCase().equals("remove")){
						String target = "";
						try{
							target = commandScanner.next();
						}catch(Exception e){
							outputWriter.print("error: missing parameter from remove command");
						}
						for(int i = 0; i < effectItems.size(); i++){
							if(effectItems.get(i).getName().equals(target)){
								effectItems.remove(i);
								i--;
							}
						}
					}else if(token.toLowerCase().equals("progress")){
						try{
							Scanner timeScanner = new Scanner(commandScanner.next());
							timeScanner.useDelimiter(":");
							ArrayList<Integer> timeList = new ArrayList<>();
							while(timeScanner.hasNext()){
								timeList.add(Integer.parseInt(timeScanner.next())); //add numbers user enters in
							}
							while(timeList.size() < 4){
								timeList.add(0, 0); //add zeroes until length is 4
							}
							if(timeList.get(1) >= 24 || timeList.get(2) >= 60 || timeList.get(3) >= 60){ //validate time
								invalidTime = true;
							}else{
								for(Item i : effectItems){
									i.customProgress(timeList.get(0), timeList.get(1), timeList.get(2), timeList.get(3));
								}
							}
						}catch(Exception e){
							outputWriter.print("error: missing parameter from progress command");
						}
					}else if(token.toLowerCase().equals("exit")){
						System.exit(0);
					}else{
						outputWriter.print("error: invalid command");
					}	

				//if is from initiative	
				}else if(token.equals("initiative:")){
					token = commandScanner.next();
					if(token.toLowerCase().equals("add")){
						try{
							initiativeItems.add(commandScanner.next());
						}catch(Exception e){
							outputWriter.print("error: missing parameter from add command");
						}
					}else if(token.toLowerCase().equals("remove")){
						String target = "";
						try{
							target = commandScanner.next();
						}catch(Exception e){
							outputWriter.print("error: missing parameter from remove command");
						}
						for(int i = 0; i < initiativeItems.size(); i++){
							if(initiativeItems.get(i).equals(target)){
								initiativeItems.remove(i);
								i--;
							}
						}
					}else{
						outputWriter.print("error: invalid command");
					}
				}

				//handle errors---------------------------------------------------
				if(invalidTime){
					outputWriter.print("error: invalid time string");
				}
				saveItemState(); //save current state of objects to JSON
			}

			//clean up
			socket.close();
		}
	}

	private static void saveItemState() throws FileNotFoundException{

		//save effects-------------------------------------------------------------------------------------
		PrintWriter fileWriter = new PrintWriter(new FileOutputStream("effect-session.json"));
		fileWriter.println("{");
		fileWriter.println("\t\"items\":[");
		String items = "";
		for(Item i : effectItems){
			items = items + "\t\t" + i.toJSON() + ",\n";
		}
		if(items.length() != 0){
			fileWriter.println(items.substring(0, items.length()-2));
		}

		fileWriter.println("\t]");
		fileWriter.print("}");
		fileWriter.close();

		for(int i = 0; i < effectItems.size(); i++){ //remove expired elements
			if(effectItems.get(i).isExpired()){
				effectItems.remove(i);
				i--;
			}
		}

		//save initiative------------------------------------------------------------------------------

		fileWriter = new PrintWriter(new FileOutputStream("initiative-session.json"));
		fileWriter.println("{");
		fileWriter.println("\t\"items\":[");
		items = "";
		for(String i : initiativeItems){
			items = items + "\t\t\"" + i + "\",\n";
		}
		if(items.length() != 0){
			fileWriter.println(items.substring(0, items.length()-2));
		}

		fileWriter.println("\t]");
		fileWriter.print("}");
		fileWriter.close();
	}
}