var auctionId;

$(function() {
  $.get("http://localhost:8080/rest/auctions", function(data) {
      updateAuctions(data);
      $('#btnStartAuction').click(function() {
          var url = window.location.origin + '/app/auction.html';
          window.location = url;
          localStorage.setItem('auction_data', JSON.stringify({
            'name': $("#name").val(),
            'auctionId': auctionId
          }));
      });
  });
});

function updateAuctions(auctions) {
  for (idx in auctions) {
    $('#content').append("<tr><td>" + auctions[idx].good + "</td><td>" + auctions[idx].minimumBid +
      "</td><td>" + auctions[idx].startTime + "</td><td>" + auctions[idx].endTime + "</td>" +
      "<td><button class='btn btn-success' onclick='setAuctionId(" + auctions[idx].id + ");' id='start' data-toggle='modal' data-target='#exampleModalLong'>" +
      "<i class='glyphicon glyphicon-circle-arrow-right'></i></button></td></tr>");
  }
}

function setAuctionId(id) {
  auctionId = id;
}
