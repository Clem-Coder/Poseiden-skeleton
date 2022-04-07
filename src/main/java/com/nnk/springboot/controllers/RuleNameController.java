package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * here is the CRUD for RuleName entities
 */

@Controller
@SessionAttributes("userInfo")
public class RuleNameController {
    // TODO: Inject RuleName service  ->CHECK

    private static final Logger logger = LogManager.getLogger("RuleNameController");

    @Autowired
    RuleNameRepository ruleNameRepository;

    /**
     * Show the template to list ruleNames
     *
     * @return the ruleNameList template
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        logger.info("New request: show all rules in the view ");
        return "ruleName/list";
    }

    /**
     * Show the template to add new ruleName
     *
     * @return the template to add new ruleName
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {

        logger.info("New request: show form to add new rules in the view ");
        return "ruleName/add";
    }

    /**
     * this method allows you to add a new ruleName
     *
     * @param ruleName a valid ruleName
     * @return the template to list ruleName if the ruleName in parameter is valid or
     * @return the template to add new ruleName if the ruleName as error
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if(!result.hasErrors()){
            ruleNameRepository.save(ruleName);
            model.addAttribute("ruleName",ruleNameRepository.findAll());
            logger.info("New request: rule added in db ");
            return "redirect:/ruleName/list";
        }
        logger.error("An error occurred during new rule recording ");
        return "ruleName/add";
    }

    /**
     * Show the template to update a ruleName
     *
     * @param id the id of the ruleName to update
     * @return the template to update ruleName
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        model.addAttribute("ruleName", ruleName);
        logger.info("New request: show form to update rule in the view ");
        return "ruleName/update";
    }

    /**
     * this method allows you to update a ruleName
     *
     * @param id the id of the ruleName to update
     * @return the template to list ruleName if the ruleName in parameter is valid or
     * @return the template to update ruleName if the ruleName as error
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.error("An error occurred during rule updating ");
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        logger.info("New request: rule with id " +id+ " updated in db");
        return "redirect:/ruleName/list";
    }

    /**
     * This method allows you to delete a ruleName
     *
     * @param id the id of the ruleName to delete
     * @return the ruleNameList template
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute("ruleNames",ruleNameRepository.findAll());
        logger.info("New request: rule with id " +id+ " deleted from db");
        return "redirect:/ruleName/list";
    }
}
