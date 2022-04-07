package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * here is the CRUD for Rating entities
 */
@Controller
@SessionAttributes("userInfo")
public class RatingController {
    // TODO: Inject Rating service

    private static final Logger logger = LogManager.getLogger("RatingController");

    @Autowired
    RatingRepository ratingRepository;


    /**
     * Show the template to list ratings
     *
     * @return the ratingList template
     */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingRepository.findAll());
        logger.info("New request: show all ratings in the view ");
        return "rating/list";
    }

    /**
     * Show the template to add new rating
     *
     * @return the template to add new rating
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("New request: show form to add new rating in the view ");
        return "rating/add";
    }

    /**
     * this method allows you to add a new rating
     *
     * @param rating a valid rating
     * @return the template to list rating if the rating in parameter is valid or
     * @return the template to add new rating if the rating as error
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(!result.hasErrors()){
            ratingRepository.save(rating);
            model.addAttribute("ratings",ratingRepository.findAll());
            logger.info("New request: rating added in db ");
            return "redirect:/rating/list";
        }
        logger.error("An error occurred during new rating recording ");
        return "rating/add";
    }

    /**
     * Show the template to update a rating
     *
     * @param id the id of the rating to update
     * @return the template to update rating
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        logger.info("New request: show form to update rating in the view ");
        return "rating/update";
    }

    /**
     * this method allows you to update a rating
     *
     * @param id the id of the rating to update
     * @return the template to list rating if the rating in parameter is valid or
     * @return the template to update rating if the rating as error
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.error("An error occurred during rating updating ");
            return "rating/update";
        }
        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("curvePoint", ratingRepository.findAll());
        logger.info("New request: rating with id " +id+ " updated in db");
        return "redirect:/rating/list";
    }

    /**
     * This method allows you to delete a rating
     *
     * @param id the id of the rating to delete
     * @return the ratingList template
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("ratings", ratingRepository.findAll());
        logger.info("New request: rating with id " +id+ " updated in db");
        return "redirect:/rating/list";
    }
}
