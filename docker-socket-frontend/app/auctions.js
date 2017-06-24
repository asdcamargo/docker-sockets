var auctionId;
var backendUrl = 'http://192.168.1.161:8080';

$(function() {
  waitingDialog.show('...Loading auctions', {
    dialogSize: 'sm'
  });
  $.get(backendUrl + "/rest/auctions", function(data) {
      updateAuctions(data);
      $('#btnStartAuction').click(function() {
        var url = window.location.origin + '/app/auction.html';
        window.location = url;
        localStorage.setItem('auction_data', JSON.stringify({
          'name': $("#name").val(),
          'auctionId': auctionId
        }));
        localStorage.setItem('backendUrl', backendUrl);
      });
    }).fail(function() {
      console.log(error);
    })
    .always(function() {
      setTimeout(function() {
        waitingDialog.hide();
      }, 1000);
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
