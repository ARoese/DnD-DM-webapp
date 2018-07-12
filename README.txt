note: I don't own "36845609-dragon-picture.jpg". I couldn't find a source on it, but credit
	goes to the artist

this folder contains:
	A) a back-end written in java. To launch it, run "Servlet.java" with admin privileges (or at least 
	permission to create a socket connection, and to read & write files)

	B) a front-end designed to work nicely with the server. This includes a website and all necessary
	scripts and stylesheets to support it. Connecting to whatever IP the server is running on through
	any web browser will get you the site it serves. (you can also just submit a GET request to the server
	to get it manually but I don't know why you would go through the trouble)

more notes:
	I've said it in Servlet.java, but this servlet is NOT in any way HTTP compliant. It is ONLY designed to
	allow the functioning of the front-end I've included in this file and nothing else. Although I can
	assure that it will work properly with the front-end scripts I've included, anything else is completely
	at your own risk.

	Trying to run the servlet with insufficient permission will result in it throwing a BindException or an
	IOException, depending on which permissions it lacks.

	Again, this is only meant to work in the ways that I've intended it to work, and nothing more, nothing less.