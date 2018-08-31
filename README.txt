this folder contains:
	1) a back-end written in java. To launch it, compile and run "Servlet.java" with admin privileges (or at least permission to create a socket connection and to read & write files)

	2) a front-end designed to work nicely with the server. This includes a website and all necessary scripts and stylesheets to support it. Connecting to whatever IP the server is running on through any web browser will get you the site it serves, or you can open the included html file to see how it looks.
		a) an app to keep track of how much time is remaining on spells or actions
		b) an app to keep track of player initiatives (the order in which players take turns)
		c) an app for mass-rolling dice

more notes:
	I've said it in Servlet.java, but this is NOT meant to be used as a general purpose web server. It is ONLY designed to allow the functioning of the front-end I've included in this file and nothing more. Although I can assure that it will work properly with the front-end scripts I've included, anything else is completely at your own risk.

	Trying to run the servlet with insufficient permission will result in it throwing a BindException or an IOException, depending on which permissions it lacks.

plans for the future:
	I am currently planning on implementing the following features:
		1) SQL database that allows for multiple different sessions on the same server, and different user types
			a) need to learn SQL first
		2) connections between the "Initiative list" and "effect list" to make constant usage more feasible
			a) put them both on the same page, and move initiaive list per turn such that the name of the person 				currently playing is always first in the list.
		3) removal of the command system in favor of a more usable interface
		4) multithreading in the back-end allowing it to handle multiple requests concurrently (like apache)
	

Why?
	1) I noticed that during many DnD sessions, any actions that take an amount of time to cast or complete would often take a much longer or much shorter amount of time than they were supposed to because there was so much else going on for the DM to keep track of and they simply forget about them before saying "alright ____ is done." This is meant so simplify the wild scramble of pencil on paper to just a single keypress per turn, and a few command here and there when someone does something so that the DM can focus on more engaging things.

	2) This acted as a great learning experience and taught me a great deal about a few Java classes, javascript, (my first experience with this) HTTP, HTML, JSON and css. (it also helps alot in a few DnD games I'm in)
