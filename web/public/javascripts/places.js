var map;
var infowindow;

function initialize() {

  var search = document.getElementById('search');
  var auto_search = new google.maps.places.Autocomplete(search);

  map = new google.maps.Map(document.getElementById('map'), {
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    zoom: 18
  });

  google.maps.event.addListener(auto_search, 'place_changed', function() {
    var start = auto_search.getPlace().geometry.location;
    map.panTo(start);
    map.setZoom(18);

    var request = {
      location: start,
      radius: 400,
      types: ['parking']
    };

    infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, callback);
  });
}

function callback(results, status) {
  if (status == google.maps.places.PlacesServiceStatus.OK) {
    var bounds = map.getBounds();
    for (var i = 0; i < results.length; i++) {
      createMarker(results[i], bounds);
    }
    map.fitBounds(bounds);
  }
}

function createMarker(place, bounds) {
  var placeLoc = place.geometry.location;
  var marker = new google.maps.Marker({
    map: map,
    position: placeLoc
  });

  bounds.extend(place.geometry.location);

  google.maps.event.addListener(marker, 'click', function() {
    infowindow.setContent(place.name);
    infowindow.open(map, this);
  });
}

google.maps.event.addDomListener(window, 'load', initialize);
