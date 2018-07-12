function setup(){
				var elements = document.getElementsByTagName("p");
				var index = [3, 5, 7, 9, 99, 11, 19];
				for(var i = 0; i < 7; i++){
					elements[i].innerHTML = Math.round(Math.random() * index[i]+1) + ", " +
											Math.round(Math.random() * index[i]+1) + ", " +
											Math.round(Math.random() * index[i]+1) + ", " +
											Math.round(Math.random() * index[i]+1) + ", " +
											Math.round(Math.random() * index[i]+1) + ", " +
											Math.round(Math.random() * index[i]+1);
				}
			}