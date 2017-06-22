package decamargo.tutorials.java.socket.ws;

import decamargo.tutorials.java.socket.domain.StartAuctionRequest;
import decamargo.tutorials.java.socket.repository.StartAuctionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionRestController {

    @Autowired
    private StartAuctionRepository startAuctionRepository;

    @RequestMapping(value = "/rest/auctions", method = RequestMethod.GET)
    public List<StartAuctionRequest> getAuctions() throws Exception {
        return startAuctionRepository.findByRunningTrue();
    }
}
