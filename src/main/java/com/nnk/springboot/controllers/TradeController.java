package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * here is the CRUD for trade entities
 */

@Controller
@SessionAttributes("userInfo")
public class TradeController {
    // TODO: Inject Trade service -> CHECK

    private static final Logger logger = LogManager.getLogger("TradeController");

    @Autowired
    TradeRepository tradeRepository;

    /**
     * Show the template to list trades
     *
     * @return the tradeList template
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeRepository.findAll());
        logger.info("New request: show all trades in the view ");
        return "trade/list";
    }

    /**
     * Show the template to add new trade
     *
     * @return the template to add new trade
     */
    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        logger.info("New request: show form to add new trade in the view ");
        return "trade/add";
    }

    /**
     * this method allows you to add a new trade
     *
     * @param trade a valid trade
     * @return the template to list trade if the trade in parameter is valid or
     * @return the template to add new trade if the trade as error
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(!result.hasErrors()){
            tradeRepository.save(trade);
            model.addAttribute("trades",tradeRepository.findAll());
            logger.info("New request: trade added in db ");
            return "redirect:/trade/list";
        }
        logger.error("An error occurred during new trade recording ");
        return "trade/add";
    }

    /**
     * Show the template to update a trade
     *
     * @param id the id of the trade to update
     * @return the template to update trade
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        logger.info("New request: show form to update trade in the view ");
        return "trade/update";
    }

    /**
     * this method allows you to update a trade
     *
     * @param id the id of the trade to update
     * @return the template to list trade if the trade in parameter is valid or
     * @return the template to update trade if the trade as error
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.error("An error occurred during trade updating ");
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeRepository.save(trade);
        model.addAttribute("trades", tradeRepository.findAll());
        logger.info("New request: trade with id " +id+ " updated in db");
        return "redirect:/trade/list";
    }

    /**
     * This method allows you to delete a trade
     *
     * @param id the id of the trade to delete
     * @return the tradeList template
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);
        model.addAttribute("trades",tradeRepository.findAll());
        logger.info("New request: trade with id " +id+ " deleted from db");
        return "redirect:/trade/list";
    }
}
