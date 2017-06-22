var stompClient = null;
var highestBid = 0;
var auctionData;
var backendUrl = 'http://192.168.1.161:8080';

$(function() {
  var socket = new SockJS(backendUrl + '/simple-auction');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function(frame) {
    auctionData = JSON.parse(localStorage.getItem('auction_data'));
    stompClient.subscribe('/topic/users', function(user) {
      var userResponse = JSON.parse(user.body);
      addUser(userResponse);
    });
    stompClient.subscribe('/topic/bids/updates', function(userThrow) {
      addThrow(JSON.parse(userThrow.body));
    });
    stompClient.subscribe('/topic/winner', function(userThrow) {
      updateHighThrow(JSON.parse(userThrow.body));
    });
    stompClient.send("/app/start", {}, localStorage.getItem('auction_data'));
    $.get(backendUrl + '/rest/auctions/' + auctionData.auctionId, function(data) {
      updateAuctionDetails(data);
    });
  });
});

function updateAuctionDetails(auction) {
  auctionData.good = auction.good;
  auctionData.minimumBid = auction.minimumBid;
  highestBid = auction.minimumBid;
  $('#minBid').text(auction.minimumBid);
  $('#good').text(auction.good);

  addUser(auction);
  if(auction.highestBid.user != null) {
    highestBid = auction.highestBid.value;
    updateHighThrow(auction.highestBid);
  }
  auction.userThrows.forEach(function(throwEntry) {
    addThrow(throwEntry);
  });
}

function disconnect() {
  if (stompClient != null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  stompClient.send("/app/start", {}, JSON.stringify({
    'name': auctionData.name
  }));
  $('#send').attr('disabled', 'disabled');
}

function sendBid() {
  if ($("#bid").val() > highestBid) {
    stompClient.send("/app/bid", {}, JSON.stringify({
      'id': auctionData.auctionId,
      'user': auctionData.name,
      'value': $("#bid").val()
    }));
  }
}

function addUser(users) {
  $("#users").empty();
  for (idx in users.participants) {
    $("#users").append("<tr><td>" + users.participants[idx] + "</td></tr>");
  }
}

function updateHighThrow(userThrow) {
  $("#high-bid").empty();
  highestBid = userThrow.value;
  $("#bid").focus();
  $("#high-bid").append(userThrow.value + " by " + userThrow.user + " on " + userThrow
    .dateStr);
}

function addThrow(userThrow) {
  $("#bids").prepend("<tr><td>" + userThrow.user + "</td><td>" + userThrow.value + "</td><td>" + userThrow
    .dateStr + "</td></tr>");
}

$(function() {
  $("form").on('submit', function(e) {
    e.preventDefault();
  });
  $("#connect").click(function() {
    connect();
  });
  $("#disconnect").click(function() {
    disconnect();
  });
  $("#send").click(function() {
    sendName();
  });
  $("#sendBid").click(function() {
    sendBid();
  });
  $("#bid").focus(function() {
    $('#bidval').text('Bid needs to be higher than the winning bid: ' + highestBid);
  });
});
