package io.ankara.controller;

import io.ankara.domain.Estimate;
import io.ankara.service.EstimateService;
import io.ankara.service.EstimateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

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

    @RequestMapping(value = "print/{estimateID}", method = RequestMethod.GET)
    public String print(@PathVariable Long estimateID, Model model) {
        model.addAttribute("print", true);
        model.addAttribute("cost", estimateService.getEstimate(estimateID));
        return "estimatePrint";
    }

    @RequestMapping(value = "pdf/{estimateID}", method = RequestMethod.GET)
    public void pdf(@PathVariable Long estimateID, HttpServletResponse response) throws IOException, InterruptedException {
        Estimate estimate = estimateService.getEstimate(estimateID);
        if (estimate == null) return;

        response.setContentType("application/pdf");
        File file = estimateService.generatePDF(estimate);
        OutputStream os = response.getOutputStream();
        Files.copy(file.toPath(), os);
        os.flush();

    }
}
