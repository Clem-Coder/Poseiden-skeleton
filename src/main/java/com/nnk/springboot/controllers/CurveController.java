package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * here is the CRUD for curvePoint entities
 */

@Controller
@SessionAttributes("userInfo")
public class CurveController {
    // TODO: Inject Curve Point service -> CHECK

    private static final Logger logger = LogManager.getLogger("CurveController");

    @Autowired
    CurvePointRepository curvePointRepository;

    /**
     * Show the template to list curvePoints
     *
     * @return the curveList template
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        logger.info("New request: show all curves in the view ");
        return "curvePoint/list";
    }

    /**
     * Show the template to add new curvePoint
     *
     * @return the template to add new curvePoint
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        logger.info("New request: show form to add new curve in the view ");
        return "curvePoint/add";
    }

    /**
     * this method allows you to add a new curvePoint
     *
     * @param curvePoint a valid curvePoint
     * @return the template to list curvePoints if the curvePoint in parameter is valid or
     * @return the template to add new curvePoint if the curvePoint as error
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if(!result.hasErrors()){
            curvePointRepository.save(curvePoint);
            model.addAttribute("bidList",curvePointRepository.findAll());
            logger.info("New request: curve added in db ");
            return "redirect:/curvePoint/list";
        }
        logger.error("An error occurred during new curve recording ");
        return "curvePoint/add";
    }

    /**
     * Show the template to update a curvePoint
     *
     * @param id the id of the curvePoint to update
     * @return the template to update curvePoint
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        logger.info("New request: show form to update curve in the view ");
        return "curvePoint/update";
    }

    /**
     * this method allows you to update a curvePoint
     *
     * @param id the id of the curvePoint to update
     * @return the template to list curvePoints if the curvePoint in parameter is valid or
     * @return the template to update curvePoint if the curvePoint as error
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.error("An error occurred during curve updating ");
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        logger.info("New request: curve with id " +id+ " updated in db");
        return "redirect:/curvePoint/list";
    }


    /**
     * This method allows you to delete a curvePoint
     *
     * @param id the id of the curvePoint to delete
     * @return the curveList template
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        logger.info("New request: curve with id " +id+ " deleted from db");
        return "redirect:/curvePoint/list";
    }
}
