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

				request.open("POST", "initiative-session.json", true);
				request.setRequestHeader("Content-Encoding", "identity");
				request.send("initiative: " + command);
			}
			function refresh(){
				var request = new XMLHttpRequest();
				request.onreadystatechange = function() {
    				if (this.readyState == 4 && this.status == 200) {
						var items = JSON.parse(this.responseText).items;
						var generatedElements = "";
						for(var i = 0; i < items.length; i++){
							generatedElements = generatedElements +
							 "<li onclick=\"removeElement('" + items[i] + "')\">" + items[i] + "</li>";
						}
						if(generatedElements == ""){
							generatedElements = "no active initiatives."
						}
						document.getElementById("itemListElement").innerHTML = generatedElements;
						console.log("refreshed");
    				}
  				};
				request.open("GET", "initiative-session.json", true);
				request.send("");
			}
			function removeElement(target){
				document.getElementById("textBox").value = "remove " + target;
				submit();
			}