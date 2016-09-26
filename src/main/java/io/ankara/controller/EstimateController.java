package io.ankara.controller;

import io.ankara.service.EstimateService;
import io.ankara.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/25/16.
 */
@Controller
@RequestMapping("/estimate")
public class EstimateController {
    @Inject
    private EstimateService estimateService;

    @RequestMapping(value = "/{estimateID}", method = RequestMethod.GET)
    public String show(@PathVariable Long estimateID, Model model) {
        model.addAttribute("cost", estimateService.getEstimate(estimateID));
        return "estimate";
    }
}
