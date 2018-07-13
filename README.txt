this folder contains:
	1) a back-end written in java. To launch it, compile and run "Servlet.java" with admin privileges (or at least permission to create a socket connection and to read & write files)

	2) a front-end designed to work nicely with the server. This includes a website and all necessary scripts and stylesheets to support it. Connecting to whatever IP the server is running on through any web browser will get you the site it serves, or you can open the included html file to see how it looks.
		a) an app to keep track of how much time is remaining on spells or actions
		b) an app to keep track of player initiatives (the order in which players take turns, as to reduce the number of fights in which order gives way to chaos and not even the DM knows whose turn it is anymore)
		c) an app for mass-rolling dice to reduce the number of dice hiding under couches and chairs

more notes:
	I've said it in Servlet.java, but this servlet is NOT in any way HTTP compliant. It is ONLY designed to allow the functioning of the front-end I've included in this file and nothing else. Although I can assure that it will work properly with the front-end scripts I've included, anything else is completely at your own risk.

	Trying to run the servlet with insufficient permission will result in it throwing a BindException or an IOException, depending on which permissions it lacks.

	Again, this is only meant to work in the ways that I've intended it to work, and nothing more, nothing less. I also realize that this fails to use a few useful things like REST, javascript frameworks, and "re-invents" the wheel when it comes to HTTP. I did this because it was my first time working with these technologies, and I think it would be more valuble as a learning experience to start with the basics and understand the fundamentals before moving onto any libraries or frameworks that are meant to make those fundamentals easier or quicker. This is not to say I'm not intending to implement these over time, however.

Why is this a thing?
	1) I noticed that during many DnD sessions, any actions that take an amount of time to cast or complete would often take a much longer or much shorter amount of time than they were supposed to because there was so much else going on for the DM to keep track of and they simply forget about them before saying "alright ____ is done." This is meant so simplify the wild scramble of pencil on paper to just a single keypress per turn, and a few command here and there when someone does something so that the DM can focus on more engaging things.

	2) This acted as a great learning experience and taught me a great deal about a few Java classes, javascript, (my first experience with this) HTTP, HTML, and css. (it also helps alot in a few DnD games I'm in)