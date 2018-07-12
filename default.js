function submit(){
				var command = document.getElementById("textBox").value;
				if(command == ""){
					command = "progress 6"
				}
				console.log("command \"" + command + "\" submitted to server.");
				document.getElementById("textBox").value = null;

				var request = new XMLHttpRequest();
				request.onreadystatechange = function() {
    				if (this.readyState == 4 && this.status == 200) {
						if(request.responseText.indexOf("error: ") !== -1){
							alert(request.responseText);
						}
						refresh();
    				}
  				};

				request.open("POST", "effect-session.json", true);
				request.setRequestHeader("Content-Encoding", "identity");
				request.send("effect: " + command);
			}
			function refresh(){
				var request = new XMLHttpRequest();
				request.onreadystatechange = function() {
    				if (this.readyState == 4 && this.status == 200) {
						var items = JSON.parse(this.responseText).items;
						var generatedElements = "";
						for(var i = 0; i < items.length; i++){
							if(items[i].expired){
								generatedElements = generatedElements +
								"<li id=\"expiredElement\" onclick=\"removeElement('" + items[i].name + "')\">" +
								items[i].name + ": " + 
								items[i].days + " days, " + 
								items[i].hours + " hours, " + 
								items[i].minutes + " minutes, and " +
								items[i].seconds + " seconds " +
								"remaining until removal</li>"
							}else{
								generatedElements = generatedElements +
								"<li onclick=\"removeElement('" + items[i].name + "')\">" +
								items[i].name + ": " + 
								items[i].days + " days, " + 
								items[i].hours + " hours, " + 
								items[i].minutes + " minutes, and " +
								items[i].seconds + " seconds " +
								"remaining until removal</li>"
							}
						}
						if(generatedElements == ""){
							generatedElements = "no active effects."
						}
						document.getElementById("itemListElement").innerHTML = generatedElements;
						console.log("refreshed");
    				}
  				};
				request.open("GET", "effect-session.json", true);
				request.send("");
			}
			function removeElement(target){
				document.getElementById("textBox").value = "remove " + target;
				submit();
			}