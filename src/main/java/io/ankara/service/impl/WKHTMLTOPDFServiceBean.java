package io.ankara.service.impl;


import io.ankara.service.PDFService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/26/16.
 */
@Service
public class WKHTMLTOPDFServiceBean implements PDFService {
    @Override
    public File generatePDF(String URL, String outputFile) throws IOException, InterruptedException {
        executePDFGenerateCommand(URL, outputFile);
        return new File(outputFile);
    }

    private void executePDFGenerateCommand(String url, String outputFile) {
        String command = String.join(" ", Arrays.asList("xvfb-run wkhtmltopdf -L 15 -R 15 -T 15 -B 15", url, outputFile));
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
