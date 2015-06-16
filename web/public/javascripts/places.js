var map;
var infowindow;

function initialize() {

  var search = document.getElementById('search');
  var auto_search = new google.maps.places.Autocomplete(search);

  google.maps.event.addListener(auto_search, 'place_changed', function() {
    var start = auto_search.getPlace().geometry.location;
    
    if (map) {
      map.panTo(start);
      map.setZoom(17);

      var request = {
        location: start,
        radius: 400,
        types: ['parking']
      };

      infowindow = new google.maps.InfoWindow();
      var service = new google.maps.places.PlacesService(map);
      service.nearbySearch(request, callback);
    } else {
      map = new google.maps.Map(document.getElementById('map'), {
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: start,
        zoom: 17
      });

      var request = {
        location: start,
        radius: 400,
        types: ['parking']
      };

      infowindow = new google.maps.InfoWindow();
      var service = new google.maps.places.PlacesService(map);
      service.nearbySearch(request, callback);
    }
  });
}

function callback(results, status) {
  if (status == google.maps.places.PlacesServiceStatus.OK) {
    for (var i = 0; i < results.length; i++) {
      createMarker(results[i]);
    }
  }
}

function createMarker(place) {
  var placeLoc = place.geometry.location;
  var marker = new google.maps.Marker({
    map: map,
    position: placeLoc
  });

  google.maps.event.addListener(marker, 'click', function() {
    infowindow.setContent(place.name);
    infowindow.open(map, this);
  });
}

google.maps.event.addDomListener(window, 'load', initialize);
