package decamargo.tutorials.java.socket.ws;

import decamargo.tutorials.java.socket.domain.StartAuctionRequest;
import decamargo.tutorials.java.socket.repository.StartAuctionRepository;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AuctionFormController {

    @Autowired
    private StartAuctionRepository auctionRepository;

    @PostMapping(value = "/form/auction")
    public String greetingSubmit(@ModelAttribute @Valid StartAuctionRequest request, BindingResult bindingResult, Model
            model) {

        if (bindingResult.hasErrors()) {
            log.error("validation error!");
            model.addAttribute("msg", new FormMessage("Validation Error!", FormMessage.MessageType.ERROR));
            model.addAttribute("auction", request);
            return "addAuction";
        }
        auctionRepository.save(request);
        model.addAttribute("msg", new FormMessage("Success!", FormMessage.MessageType.SUCCESS));
        return redirectManageAuctions(model);
    }

    @GetMapping("/form/auction")
    public String redirectManageAuctions(Model model) {
        model.addAttribute("list", auctionRepository.findAll());
        return "manageAuctions";
    }

    @GetMapping("/form/addAuction")
    public String redirectAddAuction() {
        return "addAuction";
    }

    @GetMapping("/auction")
    public String greetingForm(Model model) {
        model.addAttribute("auction", new StartAuctionRequest());
        return "addAuction";
    }

    @PutMapping(value = "/form/auction")
    public String  startAuction(@RequestParam Long id, Model model){
        StartAuctionRequest request = auctionRepository.findOne(Long.valueOf(id));
        request.setRunning(!request.isRunning());
        auctionRepository.save(request);
        log.info(request.getGood());
        return redirectManageAuctions(model);
    }
}
