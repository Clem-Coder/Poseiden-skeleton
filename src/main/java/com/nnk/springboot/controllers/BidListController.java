package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

/**
 * here is the CRUD for Bid entities
 */

@Controller
@SessionAttributes("userInfo")
public class BidListController {
    // TODO: Inject Bid service -> CHECK

    private static final Logger logger = LogManager.getLogger("BidListController");

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Show the template to list bids
     *
     * @return the bidList template
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        logger.info("New request: show all Bids in the view ");
        model.addAttribute("bidList", bidListRepository.findAll());
        return "bidList/list";
    }

    /**
     * Show the template to add new bid
     *
     * @return the template to add new bid
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("New request: show form to add new bid in the view ");
        return "bidList/add";
    }

    /**
     * this method allows you to add a new bid
     *
     * @param bid a valid bid
     * @return the template to list bids if the bid in parameter is valid or
     * @return the template to add new bid if the bid as error
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if(!result.hasErrors()){
            bidListRepository.save(bid);
            model.addAttribute("bidList",bidListRepository.findAll());
            logger.info("New request: bid added in db ");
            return "redirect:/bidList/list";
        }
        logger.error("An error occurred during new bid recording ");
        return "bidList/add";
    }

    /**
     * Show the template to update a bid
     *
     * @param id the id of the bid to update
     * @return the template to update bid
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id:" + id));
        model.addAttribute("bidList", bidList);
        logger.info("New request: show form to update bid in the view ");
        return "bidList/update";
    }


    /**
     * this method allows you to update a bid
     *
     * @param id the id of the bid to update
     * @return the template to list bids if the bid in parameter is valid or
     * @return the template to update bid if the bid as error
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {

        if(result.hasErrors()){
            logger.error("An error occurred during bid updating ");
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        logger.info("New request: bid with id " +id+ " updated in db");
        return "redirect:/bidList/list";
    }

    /**
     * This method allows you to delete a bid
     *
     * @param id the id of the bid to delete
     * @return the bidList template
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        logger.info("New request: bid with id " +id+ " deleted from db");
        return "redirect:/bidList/list";
    }
}
