package decamargo.tutorials.java.socket.ws;

import decamargo.tutorials.java.socket.domain.NewAuctionData;
import decamargo.tutorials.java.socket.domain.StartAuctionRequest;
import decamargo.tutorials.java.socket.domain.StartAuctionResponse;
import decamargo.tutorials.java.socket.domain.UserBid;
import decamargo.tutorials.java.socket.repository.StartAuctionRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuctionSocketController {

    private static final String DATE_FORMAT = "HH:mm:ss dd-MM-yy";

    private final Map<Long, List<UserBid>> userThrows = new ConcurrentHashMap<>();
    private final Map<Long, NewAuctionData> openAuctions = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private StartAuctionRepository auctionRepository;

    @MessageMapping("/bid")
    @SendTo("/topic/bids/updates")
    public UserBid computeThrow(UserBid userBid) throws Exception {
        Thread.sleep(1000); // simulated delay
        openAuctions.computeIfPresent(userBid.getId(), (id, auctionData) -> {
            Date currentDate = new Date();
            userBid.setTimestamp(currentDate);
            userBid.setDateStr(new SimpleDateFormat(DATE_FORMAT).format(currentDate));
            auctionData.getUserThrows().add(userBid);

            if(userBid.getValue() > auctionData.getHighestBid().getValue()) {
                auctionData.setHighestBid(userBid);
                updateWinner(userBid);
            }
            return auctionData;
        });
        return userBid;
    }

    private void updateWinner(UserBid newHighest) {
        template.convertAndSend("/topic/winner", newHighest);
    }

    @MessageMapping("/start")
    @SendTo("/topic/users")
    public NewAuctionData start(StartAuctionResponse startData) throws Exception {
        updateOpenAuctions();
        return openAuctions.computeIfPresent(startData.getAuctionId(), (auctionId, auctionData) -> {
            auctionData.addParticipant(startData.getName());
            return auctionData;
        });
    }

    @RequestMapping(value = "/rest/auctions/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getCurrentAuction(@PathVariable Long id) {
        return ResponseEntity.ok(openAuctions.getOrDefault(id, null));
    }

    private void updateOpenAuctions() {
        List<StartAuctionRequest> auctions = auctionRepository.findByRunningTrue();
        auctions.forEach(auction -> {
            openAuctions.putIfAbsent(auction.getId(), new NewAuctionData(auction.getId(), auction.getGood(), auction
                    .getMinimumBid()));
            userThrows.putIfAbsent(auction.getId(), new ArrayList<>());
        });
    }
}
