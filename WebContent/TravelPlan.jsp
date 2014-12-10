<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="js/jquery-2.1.1.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<script src="js/BpTspSolver.js" type="text/javascript"></script>
<script src="js/tsp.js" type="text/javascript"></script>
<script src="js/formatdate.js" type="text/javascript"></script>
<link href="css/travelplan.css" media="screen" rel="stylesheet"
	type="text/css" />
<link href="css/menu.css" media="screen" rel="stylesheet"
	type="text/css" />
<!--  
<script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?sensor=false&key=AIzaSyBNLDwRv4ItlTJRouExNPpJokdKK859yDk">
</script> -->
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script src="http://thecodeplayer.com/uploads/js/prefixfree-1.0.7.js"
	type="text/javascript" type="text/javascript"></script>
<script type="text/javascript">
$(function() {

	var datesgoing = document.getElementById("datesgoing");
	var mytext = new Date('${from}').format('M jS, Y')+" - "+new Date('${to}').format('M jS, Y')
	datesgoing.appendChild(document.createTextNode(mytext));
});
</script>
<title>Itinerary</title>
</head>
<body>
	<div id='content'>
		<div id="accordian">
			<br>
			<h1 id="mycity" align="center">${city}</h1>
			<br>
			<h2 id="datesgoing" align="center"
				style="font-size: 13px; margin-bottom: 5px"></h2>

			<ul>
				<li>
					<h3>
						<img src="image/spot.png">Tourist Spot
					</h3>
					<ul id="tourist" class="swap">
						<!-- JSON generate list -->
					</ul>
				</li>
				<!-- we will keep this LI open by default -->
				<li>
					<h3>
						<img src="image/coffee.png">Coffee & Tea
					</h3>
					<ul id="coffee">
						<!-- JSON generate list -->
					</ul>
				</li>
				<li>
					<h3>
						<img src="image/restaurant.png">Restaurants
					</h3>
					<ul id="restaurant">
						<!-- JSON generate list -->
					</ul>
				</li>
				<li>
					<h3>
						<img src="image/bar.png">Bars
					</h3>
					<ul id="bar">
						<!-- JSON generate list -->
					</ul>
				</li>
				<li>
					<h3>
						<img src="image/icecream.png">Ice-cream Parlor
					</h3>
					<ul id="icecream">
						<!-- JSON generate list -->
					</ul>
				</li>
				<li>
					<h3>
						<img src="image/donut.png">Donuts
					</h3>
					<ul id="donut">
						<!-- JSON generate list -->
					</ul>
				</li>
				<li>
					<h3>
						<img src="image/bread.png">Bakeries
					</h3>
					<ul id="bakery">
						<!-- JSON generate list -->
					</ul>
				</li>
				<li>
					<h3>
						<img src="image/foodtruck.png">Food Trucks
					</h3>
					<ul id="foodtruck">
						<!-- JSON generate list -->
					</ul>
				</li>

			</ul>
		</div>


	</div>
	<div id='details'>
		<div id="dayaccordian">
			<br>
			<h2 align="center">Your Itinerary</h2>
			<br>
			<ul id="iten">
				<!--			<li>
					<h3>Day 1</h3>
					<ul>
						<li><a href="#">Global favs</a></li>
						<li><a href="#">My favs</a></li>
						<li><a href="#">Team favs</a></li>
						<li><a href="#">Settings</a></li>
					</ul>
				</li>
				<li>
					<h3>Day 2</h3>
					<ul>
						<li><a href="#">Global favs</a></li>
						<li><a href="#">My favs</a></li>
						<li><a href="#">Team favs</a></li>
						<li><a href="#">Settings</a></li>
					</ul>
				</li>
				<li>
					<h3>Day 3</h3>
					<ul>
						<li><a href="#">Global favs</a></li>
						<li><a href="#">My favs</a></li>
						<li><a href="#">Team favs</a></li>
						<li><a href="#">Settings</a></li>
					</ul>
				</li>
				<li>
					<h3>Day 4</h3>
					<ul>
						<li><a href="#">Global favs</a></li>
						<li><a href="#">My favs</a></li>
						<li><a href="#">Team favs</a></li>
						<li><a href="#">Settings</a></li>
					</ul>
				</li>
				<li>
					<h3>Day 5</h3>
					<ul>
						<li><a href="#">Global favs</a></li>
						<li><a href="#">My favs</a></li>
						<li><a href="#">Team favs</a></li>
						<li><a href="#">Settings</a></li>
					</ul>
				</li>
				<li>
					<h3>Day 6</h3>
					<ul>
						<li><a href="#">Global favs</a></li>
						<li><a href="#">My favs</a></li>
						<li><a href="#">Team favs</a></li>
						<li><a href="#">Settings</a></li>
					</ul>
				</li>
-->
			</ul>
		</div>
		<button class="bigbutton" onclick="createItinerary(matrix)">Finalize My Itinerary</button>
	</div>

	<div id='weather'>
		<div id='fordate' class='datedisplay'></div>
		<div id='foricon' class='icondisplay'></div>
		<div id='fordescription' class='descriptiondisplay'></div>
		<div id='fortemp' class='tempdisplay'></div>
	</div>
	<div id='news'>
		<div id='newsadvisor'></div>
		<div id='newsoverall'></div>
		<div id='newsmarquee' class='handlemarquee'></div>
	</div>
	<div id='map-canvas'></div>
	<script>
	function toTitleCase(str) {
	    return str.replace(/\w\S*/g, function (txt) {
	        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
	    });
	}
	
    function createItinerary(numbers) {
        
        var text = "";
        var from = new Date('${from}');
        for(var i = 0; i < numbers.length; i++) {
        	text += "<hr>";
        	text += "<h4 align='center'>Day "+(i+1)+" - "+from.format('M jS, Y')+"</h4>";
        	var tweath = weather['${city}']['${from}']['Description'];
        	tweath = toTitleCase(tweath);
        	var max = weather['${city}']['${from}']['Max Temp'];
        	text += "<h5 align='center'>"+tweath+" with temperatures upto "+max+"°F</h5>";
        	from.setDate(from.getDate()+1);
            var number = numbers[i];
            text += "<ul align='center'>";
            var buffer = "";
            for(var j = 0; j < number.length; j++) {
            	if (number[j] != "") 
                	buffer += "<p>"+number[j]+"</p>";
            }
            if (buffer == "") {
            	buffer = "<p>Just explore!</p>";
            }
            text += buffer;
            text += "</ul>";
        }
        text +="<hr>";
        text +="<h3 align='center'>Enjoy Your Trip!</h3>";
        var opened = window.open("");
        opened.document.write(
        '<html><head><title>Final Itinerary</title></head><body background="image/zz.jpg"><div style="font-family: Nunito, arial, verdana;width: 500px; margin: 0 auto; background: #d3d3d3; color: #000;opacity: 0.7"><H1 style="margin-top:1cm" align="center">${city}, ${country}</H1>'+text+'<input type="submit" value="Print" style="margin-left:45%"/></div></body></html>');
    }       
	
    var directionsService = new google.maps.DirectionsService();
    var directionsDisplay = new google.maps.DirectionsRenderer();
	var mapOptions = {
			zoom: 11,
			center: new google.maps.LatLng(${latitude}, ${longitude}),
			mapTypeId: google.maps.MapTypeId.ROADMAP
		}
	var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	directionsDisplay.setMap(map);

	
	var matrix = new Array();
	for (var NumDays = 0;NumDays < ${days}; NumDays++) {
		matrix[NumDays] = new Array();
		for (var NumSpots = 0; NumSpots < ${numOfSpots}; NumSpots++) {
			matrix[NumDays][NumSpots] = "";
		}
	}

	var weather = ${weather};
	var freebase = ${freebase};
	var yelp = ${yelpSpots};
	var news = ${news};
	
	var touristList = document.getElementById("tourist");
	var coffeeList = document.getElementById("coffee");
	var restaurantList = document.getElementById("restaurant");
	var barList = document.getElementById("bar");
	var icecreamList = document.getElementById("icecream");
	var donutList = document.getElementById("donut");
	var bakeryList = document.getElementById("bakery");
	var foodtruckList = document.getElementById("foodtruck");
	
	for (var key in freebase['${city}']) {
		if (key != "Date") {
			var entry = document.createElement("li");
			var link = document.createElement("a");
			link.setAttribute("title",freebase['${city}'][key]);
			link.appendChild(document.createTextNode(key));
			entry.appendChild(link);
			touristList.appendChild(entry);
		}
	}
	var linebreak = "\x0A";
	for (var key in yelp['${city}']["Coffee & Tea"]) {
		var entry = document.createElement("li");
		var link = document.createElement("a");
		var bname =  yelp['${city}']["Coffee & Tea"][key]["Business Name"];
		var address = yelp['${city}']["Coffee & Tea"][key]["Address"];
		var rating = yelp['${city}']["Coffee & Tea"][key]["Rating"];
		if (bname != null && address != null && rating != null) {
			link.setAttribute("title",bname+linebreak+address+linebreak+"Rating : "+rating);
			link.appendChild(document.createTextNode(bname));
			entry.appendChild(link);
			coffeeList.appendChild(entry);
		}
	}

	for (var key in yelp['${city}']["Restaurants"]) {
		var entry = document.createElement("li");
		var link = document.createElement("a");
		var bname =  yelp['${city}']["Restaurants"][key]["Business Name"];
		var address = yelp['${city}']["Restaurants"][key]["Address"];
		var rating = yelp['${city}']["Restaurants"][key]["Rating"];
		if (bname != null && address != null && rating != null) {
			link.setAttribute("title",bname+linebreak+address+linebreak+"Rating : "+rating);
			link.appendChild(document.createTextNode(bname));
			entry.appendChild(link);
			restaurantList.appendChild(entry);
		}
	}
	
	for (var key in yelp['${city}']["Bars"]) {
		var entry = document.createElement("li");
		var link = document.createElement("a");
		var bname =  yelp['${city}']["Bars"][key]["Business Name"];
		var address = yelp['${city}']["Bars"][key]["Address"];
		var rating = yelp['${city}']["Bars"][key]["Rating"];
		if (bname != null && address != null && rating != null) {
			link.setAttribute("title",bname+linebreak+address+linebreak+"Rating : "+rating);
			link.appendChild(document.createTextNode(bname));
			entry.appendChild(link);
			barList.appendChild(entry);
		}
	}
	
	for (var key in yelp['${city}']["Ice-Cream Parlor"]) {
		var entry = document.createElement("li");
		var link = document.createElement("a");
		var bname =  yelp['${city}']["Ice-Cream Parlor"][key]["Business Name"];
		var address = yelp['${city}']["Ice-Cream Parlor"][key]["Address"];
		var rating = yelp['${city}']["Ice-Cream Parlor"][key]["Rating"];
		if (bname != null && address != null && rating != null) {
			link.setAttribute("title",bname+linebreak+address+linebreak+"Rating : "+rating);
			link.appendChild(document.createTextNode(bname));
			entry.appendChild(link);
			icecreamList.appendChild(entry);
		}
	}
	
	for (var key in yelp['${city}']["Donuts"]) {
		var entry = document.createElement("li");
		var link = document.createElement("a");
		var bname =  yelp['${city}']["Donuts"][key]["Business Name"];
		var address = yelp['${city}']["Donuts"][key]["Address"];
		var rating = yelp['${city}']["Donuts"][key]["Rating"];
		if (bname != null && address != null && rating != null) {
			link.setAttribute("title",bname+linebreak+address+linebreak+"Rating : "+rating);
			link.appendChild(document.createTextNode(bname));
			entry.appendChild(link);
			donutList.appendChild(entry);
		}
	}
	
	for (var key in yelp['${city}']["Bakeries"]) {
		var entry = document.createElement("li");
		var link = document.createElement("a");
		var bname =  yelp['${city}']["Bakeries"][key]["Business Name"];
		var address = yelp['${city}']["Bakeries"][key]["Address"];
		var rating = yelp['${city}']["Bakeries"][key]["Rating"];
		if (bname != null && address != null && rating != null) {
			link.setAttribute("title",bname+linebreak+address+linebreak+"Rating : "+rating);
			link.appendChild(document.createTextNode(bname));
			entry.appendChild(link);
			bakeryList.appendChild(entry);
		}
	}
	
	for (var key in yelp['${city}']["Food Trucks"]) {
		var entry = document.createElement("li");
		var link = document.createElement("a");
		var bname =  yelp['${city}']["Food Trucks"][key]["Business Name"];
		var address = yelp['${city}']["Food Trucks"][key]["Address"];
		var rating = yelp['${city}']["Food Trucks"][key]["Rating"];
		if (bname != null && address != null && rating != null) {
			link.setAttribute("title",bname+linebreak+address+linebreak+"Rating : "+rating);
			link.appendChild(document.createTextNode(bname));
			entry.appendChild(link);
			foodtruckList.appendChild(entry);
		}
	}
	function renderDirections(row) {
		directionsDisplay.setMap(map);
		console.log(row);
		var places = [];
		var col = 0;
		while (matrix[row][col] != "") {
			places.push(matrix[row][col]);
			col++;
		}
		if (places.length < 2) {
			hideAllMarkers();
			row++;
			showRowMarkers(row.toString());
			return;
		}
		var start = places[0];
		var end = places[places.length-1];
		places.shift();
		places.pop();
		var waypts = [];
		for (var j = 0; j < places.length; j++) {
			waypts.push({
		          location:places[j],
		          stopover:true});
		}
		var request = {
		      origin: start,
		      destination: end,
		      waypoints: waypts,
		      optimizeWaypoints: true,
		      travelMode: google.maps.TravelMode.DRIVING
		};
		
		directionsService.route(request, function(response, status) {
			    if (status == google.maps.DirectionsStatus.OK) {
			      directionsDisplay.setDirections(response);
			    }
		});
	}
	
	var pixels = ${numOfSpots} * 30;
    for (var i = 0; i < ${days}; i++) {
    	$("<style type='text/css'> #itenDay"+(i+1)+"{ height: "+pixels+"px; } </style>").appendTo("head");
    }
    
	var itenList = document.getElementById("iten");
	var jqueryIDs = "";
	for (var i = 0; i < ${days}; i++) {
		var dayList = document.createElement("li");
		var dayHead = document.createElement("h3");
		dayHead.setAttribute("id","Day"+(i+1));
		var addList = document.createElement("ul");
		var dummy = document.createElement("li");
		var link = document.createElement("a");
		if (i == 0) {
			dayList.setAttribute("class","active");
		}
		link.appendChild(document.createTextNode(linebreak));
		dummy.appendChild(link);
		dayHead.appendChild(document.createTextNode("Day "+(i+1)));
		jqueryIDs = jqueryIDs + "#itenDay" + (i+1) +",";
		addList.setAttribute("id","itenDay"+ (i+1));
		addList.setAttribute("class","swap");
		addList.appendChild(dummy);
		dayList.appendChild(dayHead);
		dayList.appendChild(addList);
		//var dayPlaces = docuement.createElement("ul");
		var button = document.createElement("button");
		var idb = i+1;
		button.id = idb.toString();
		button.innerHTML = "Show Route";
		button.onclick = function() {renderDirections(parseInt(this.id)-1);};
		button.className = "buttonClass";
		itenList.appendChild(dayList);
		itenList.appendChild(button);
	}
	jqueryIDs = jqueryIDs.substring(0,jqueryIDs.length -1);
	
	
	// Case A: Move within the same list
	// Case B: Move from one list to another

	var addables = "#tourist,#coffee,#restaurant,#bar,#icecream,#donut,#bakery,#foodtruck";
	    $(addables).sortable({
	        stop: function(event, ui) {
	            // Called in case A and B (!!sender == false in both)

	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            event.preventDefault();
	        },
	        connectWith: ".swap",
	        //appendTo: 'body',
	        helper: function (e, ui) { return $(ui.get(0)).clone().appendTo('body').css('zIndex', 5).show();}
	        
	    }).disableSelection();
	    /*
	    $('#coffee').sortable({
	        stop: function(event, ui) {
	            // Called in case A and B (!!sender == false in both)

	                if(!ui.sender) alert("sender not set");
	                else alert("sender is set");
	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            alert("Moved from another list");
	            event.preventDefault();
	        },
	        connectWith: ".sortable",
	        appendTo: 'body'
	    }).disableSelection();
	    
	
	    $('#restaurant').sortable({
	        stop: function(event, ui) {
	            // Called in case A and B (!!sender == false in both)

	                if(!ui.sender) alert("sender not set");
	                else alert("sender is set");
	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            alert("Moved from another list");
	            event.preventDefault();
	        },
	        connectWith: ".sortable",
	        appendTo: 'body'
	    }).disableSelection();
	    	    
	    $('#bar').sortable({
	        stop: function(event, ui) {
	                if(!ui.sender) alert("sender not set");
	                else alert("sender is set");
	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            alert("Moved from another list");
	            event.preventDefault();
	        },
	        connectWith: ".sortable",
	        appendTo: 'body'
	    }).disableSelection();
	    $('#icecream').sortable({
	        stop: function(event, ui) {
	            // Called in case A and B (!!sender == false in both)

	                if(!ui.sender) alert("sender not set");
	                else alert("sender is set");
	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            alert("Moved from another list");
	            event.preventDefault();
	        },
	        connectWith: ".sortable",
	        appendTo: 'body'
	    }).disableSelection();
	    $('#donut').sortable({
	        stop: function(event, ui) {
	                if(!ui.sender) alert("sender not set");
	                else alert("sender is set");
	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            alert("Moved from another list");
	            event.preventDefault();
	        },
	        connectWith: ".sortable",
	        appendTo: 'body'
	    }).disableSelection();
	    $('#bakery').sortable({
	        stop: function(event, ui) {
	            // Called in case A and B (!!sender == false in both)

	                if(!ui.sender) alert("sender not set");
	                else alert("sender is set");
	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            alert("Moved from another list");
	            event.preventDefault();
	        },
	        connectWith: ".sortable",
	        appendTo: 'body'
	    }).disableSelection();
	    $('#foodtruck').sortable({
	        stop: function(event, ui) {
	                if(!ui.sender) alert("sender not set");
	                else alert("sender is set");
	            
	        },
	        receive: function(event, ui) {
	            // Called only in case B (with !!sender == true)
	            alert("Moved from another list");
	            event.preventDefault();
	        },
	        connectWith: ".sortable",
	        appendTo: 'body'
	    }).disableSelection();
	    
*/

		var mapmarker = new Array();
		for (var NumDays = 0;NumDays < ${days}; NumDays++) {
			mapmarker[NumDays] = new Array();
			for (var NumSpots = 0; NumSpots < ${numOfSpots}; NumSpots++) {
				mapmarker[NumDays][NumSpots] = "";
			}
		}
		// Show the location (address) on the map.
		function ShowLocation( latlng, address, row, col )
		{
			// Center the map at the specified location
			//map.setCenter( latlng );
			
			// Set the zoom level according to the address level of detail the user specified
			//var zoom = 11;
			//map.setZoom( zoom );
			
			// Place a Google Marker at the same location as the map center 
			// When you hover over the marker, it will display the title
			var marker = new google.maps.Marker( { 
				position: latlng,     
				map: map,      
				title: address
			});
			mapmarker[row].splice(col,0,marker);
			// mapmarker[row][col] = marker;
			// Create an InfoWindow for the marker
			var contentString = "<b>" + address + "</b>";	// HTML text to display in the InfoWindow
			var infowindow = new google.maps.InfoWindow();
			
			// Set event to display the InfoWindow anchored to the marker when the marker is clicked.
			google.maps.event.addListener( mapmarker[row][col], 'click', function() {            
			infowindow.close();
            infowindow.setContent(contentString);
            infowindow.open(map,mapmarker[row][col]); });
		}
		

		function showRowMarkers(rowstr) {
			var row = parseInt(rowstr.substr(rowstr.length - 1))-1;
			var col = 0;
			while (mapmarker[row][col] != "") {
				mapmarker[row][col].setMap(map);
				col++;
			}
		}
		function hideAllMarkers() {
			for (var row = 0; row < mapmarker.length; row++) {
				var entry = mapmarker[row];
				for (var col = 0; col < entry.length; col++) {
					if(mapmarker[row][col] != ""){
						mapmarker[row][col].setMap(null);
					}					
				}
			}
			directionsDisplay.setMap(null);			
		}
			
		function addMarkers(row,col,address) {
			
			var geocoder = new google.maps.Geocoder();    // instantiate a geocoder object
			
			// Get the user's inputted address
		
			// Make asynchronous call to Google geocoding API
			geocoder.geocode( { 'address': address }, function(results, status) {
				var addr_type = results[0].types[0];	// type of address inputted that was geocoded
				if ( status == google.maps.GeocoderStatus.OK ) 
					ShowLocation( results[0].geometry.location, address, row, col );
				else     
					alert("Geocode was not successful for the following reason: " + status);        
			});
		}
		
	    $(jqueryIDs).sortable({
	        remove: function(e,ui){
	            //After beforeStop and before all other ending events
	            //Occurs only once when an item is removed from a list
	            ui.item.data('sender_id', this.id);
	        },
	        stop: function(event, ui) {
	                //if(!ui.sender) alert("removed from dayList");
	                //else alert("sender is set");
	            //var hasmoved = ui.item.index();
				var row = parseInt(this.id.substr(this.id.length -1)) - 1;
				var tt = ui.item.text();
				var column;
				for (var i = 0; i < matrix[row].length; i++) {
					if (tt.indexOf(matrix[row][i]) > -1) {
						column = i;
						break;
					}
				}
				column--;
				var oldmatrix = matrix[row].splice(column,1);
				mapmarker[row][column].setMap(null);
				var oldmarker = mapmarker[row].splice(column,1);
	            if (typeof ui.item.data('sender_id') === 'undefined') {
	            	var newcolumn = ui.item.index() - 1;
	            	matrix[row].splice(newcolumn,0,oldmatrix[0]);
	            	mapmarker[row].splice(newcolumn,0,oldmarker[0]);
	            	mapmarker[row][newcolumn].setMap(map);
	            }
	             
	            	
	        },
	        receive: function(event, ui) {
	            if ($(this).children().length > ${numOfSpots} + 1) {
                	$(ui.sender).sortable('cancel');
                }
	            var listitem = ui.item.index();
				var row = parseInt(this.id.substr(this.id.length -1)) - 1;
				var column = ui.item.index() - 1;
				var sentby = ui.sender.attr("id");
				var title = ui.item.html();
				var textual = ui.item.text();
				var rgx = /"([^)]+)"/;
				var val = title.match(rgx);
				
				var input;
				if (sentby == "tourist") {
					input = textual + ",${city}, ${country}"; 
				}
				else {
					val[1] = val[1].replace(/Rating.*$/,"");
					input = val[1];
				}
				matrix[row].splice(column,0,input);
				addMarkers(row,column,input);
				
	            event.preventDefault();
	        },
	        //items: ">*:not(.sort-disabled)"
	        //items: "> li:not(.sort-disabled)",
	        connectWith: ".swap",
	        //appendTo: 'body',
	        helper: function (e, ui) { return $(ui.get(0)).clone().appendTo('body').css('zIndex', 5).show();}
	    }).disableSelection();

$("#dayaccordian h3").click(function(){
	//slide up all the link lists
	$("#dayaccordian ul ul").slideUp();
	//slide down the link list below the h3 clicked - only if its closed
	if(!$(this).next().is(":visible"))
	{
		$(this).next().slideDown();
	}
});

$("#accordian h3").click(function(){
	//slide up all the link lists
	$("#accordian ul ul").slideUp();
	//slide down the link list below the h3 clicked - only if its closed
	if(!$(this).next().is(":visible"))
	{
		$(this).next().slideDown();
	}
});
 
 
for (var i = 0; i < ${days}; i++) {
	document.getElementById("Day"+(i+1)).addEventListener("click", function() {
	    showWeather(this.id), hideAllMarkers(), showRowMarkers(this.id);
	}, false);
}

showWeather = function(day) {
	var show = day.substring(3);
	var Dday = new Date('${from}');
	Dday.setDate(Dday.getDate() + parseInt(show)-1);
    var dd = Dday.getDate();
    var mm = Dday.getMonth()+1; //January is 0!
    var yyyy = Dday.getFullYear();
    if(dd<10) {
        dd='0'+dd;
    } 

    if(mm<10) {
        mm='0'+mm;
    } 
    var thedate = mm+"-"+dd+"-"+yyyy;
    renderWeather(weather['${city}'][thedate],thedate);
}

function renderWeather(jsonWeather,thedate) {
	$("#fordate").empty();
	$("#foricon").empty();
	$("#fordescription").empty();
	$("#fortemp").empty();
	var dateDiv = document.getElementById("fordate");
	var mydate = document.createElement("h3");
	mydate.appendChild(document.createTextNode(new Date(thedate).format('F jS, Y')));
	dateDiv.appendChild(mydate);
	var descriptionDiv = document.getElementById("fordescription");
	var iconDiv = document.getElementById("foricon");
	var tempDiv = document.getElementById("fortemp");
	var describe = jsonWeather.Description;
	var maxtemp = jsonWeather["Max Temp"];
	var mintemp = jsonWeather["Min Temp"];
	var daytemp = jsonWeather["Day Temp"];
	var img = document.createElement("img");
	if (/rain/i.test(describe)) {
		img.setAttribute("src","image/rain.png");
	}
	else if (/storm/i.test(describe)){
		img.setAttribute("src","image/storm.png");
	}
	else if (/cloud/i.test(describe)){
		img.setAttribute("src","image/clouds.png");
	}
	else if (/snow/i.test(describe)){
		img.setAttribute("src","image/snow.png");
	}
	else if (/clear/i.test(describe)){
		img.setAttribute("src","image/clear.png");
	}
	else {
		img.setAttribute("src","image/mist.png");
	}
	iconDiv.appendChild(img);
	var description = document.createElement("h4");
	description.appendChild(document.createTextNode(describe));
	descriptionDiv.appendChild(description);
	var dayt = document.createElement("h4");
	dayt.appendChild(document.createTextNode("Day Temp : "+daytemp+" °F"));
	var maxt = document.createElement("h4");
	maxt.appendChild(document.createTextNode("Max Temp : "+maxtemp+" °F"));
	var mint = document.createElement("h4");
	mint.appendChild(document.createTextNode("Min Temp : "+mintemp+" °F"));
	tempDiv.appendChild(dayt);
	tempDiv.appendChild(maxt);
	tempDiv.appendChild(mint);
}
renderWeather(weather['${city}']['${from}'],'${from}');

function renderNews() {
	var advisor = document.getElementById('newsadvisor');
	var overall = document.getElementById('newsoverall');
	var marquee = document.getElementById('newsmarquee');
	var good = "";
	var bad = "";
	var ok = "";
	var emotion = "";
	for (var key in news['${city}']) {
		if (key != "Overall") {
			if (news['${city}'][key] == "negative")
				bad = bad + key + "    |    ";
			else if (news['${city}'][key] == "positive")
				good = good + key + "    |    ";	
			else
				ok = ok + key + "    |    ";
		}
		else {
			emotion = news['${city}'][key];
		}
	}
	var marcus1 = document.createElement("marquee");
	marcus1.setAttribute("style","color:yellow;font-size:14px");
	marcus1.appendChild(document.createTextNode(good));
	var marcus2 = document.createElement("marquee");
	marcus2.setAttribute("style","color:red;font-size:14px");
	marcus2.appendChild(document.createTextNode(bad));
	var marcus3 = document.createElement("marquee");
	marcus3.setAttribute("style","color:white;font-size:14px");
	marcus3.appendChild(document.createTextNode(ok));
	marquee.appendChild(marcus1);
	marquee.appendChild(marcus2);
	marquee.appendChild(marcus3);
	
	var feeling = document.createElement("h3");
	if (emotion == "positve") {
		feeling.setAttribute("style","color:yellow;font-size:14px;margin-top:10px");
		feeling.appendChild(document.createTextNode("${city},${country}:"));
		feeling.appendChild(document.createElement("br"));
		feeling.appendChild(document.createTextNode("Positive Outlook"));
	}
	else if (emotion == "negative") {
		feeling.setAttribute("style","color:red;font-size:14px;margin-top:10px");
		feeling.appendChild(document.createTextNode("${city},${country}:"));
		feeling.appendChild(document.createElement("br"));
		feeling.appendChild(document.createTextNode("Negative Outlook"));
	}
	else {
		feeling.setAttribute("style","color:white;font-size:14px;margin-top:10px");
		feeling.appendChild(document.createTextNode("${city},${country}:"));
		feeling.appendChild(document.createElement("br"));
		feeling.appendChild(document.createTextNode("Neutral Outlook"));
	}
	overall.appendChild(feeling);
}
renderNews();

function shortestroute(row) {
	directionsPanel = document.getElementById("details");

	// Create the tsp object
	tsp = new BpTspSolver(myMap, directionsPanel);

	// Set your preferences
	tsp.setTravelMode(google.maps.DirectionsTravelMode.DRIVING);
	var addAddressCallback;
	// Add points (by coordinates, or by address).
	// The first point added is the starting location.
	// The last point added is the final destination (in the case of A - Z mode)
	// tsp.addWaypoint(latLng, addWaypointCallback);  // Note: The callback is new for version 3, to ensure waypoints and addresses appear in the order they were added in.
	for (var i = 0; i < matrix[row].length; i++) {
		if (matrix[row][i] != "")
			break;
		else
			tsp.addAddress(matrix[row][i], addAddressCallback);
	}
	// Solve the problem (start and end up at the first location)
	tsp.solveRoundTrip(onSolveCallback);
	// Or, if you want to start in the first location and end at the last,
	// but don't care about the order of the points in between:
	// tsp.solveAtoZ(onSolveCallback);

	// Retrieve the solution (so you can display it to the user or do whatever :-)
	// var dir = tsp.getGDirections();  // This is a normal GDirections object.
	// The order of the elements in dir now correspond to the optimal route.

	// If you just want the permutation of the location indices that is the best route:
	var order = tsp.getOrder();
	console.log(order);
	// If you want the duration matrix that was used to compute the route:
	// var durations = tsp.getDurations();

	// There are also other utility functions, see the source.
}
	</script>
</body>
</html>