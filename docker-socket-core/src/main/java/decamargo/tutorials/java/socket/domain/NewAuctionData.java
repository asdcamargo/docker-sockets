package decamargo.tutorials.java.socket.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAuctionData {

    private Long auctionId;
    private String good;
    private double minimumBid;
    private UserBid highestBid;
    private Set<String> participants = new HashSet<>();
    private final List<UserBid> userThrows = new ArrayList<>();

    public NewAuctionData(Long auctionId, String good, double minimumBid) {
        this.auctionId = auctionId;
        this.good = good;
        this.minimumBid = minimumBid;
        highestBid = new UserBid();
        highestBid.setValue(minimumBid);
    }

    public void addParticipant(String name) {
        participants.add(name);
    }
}
