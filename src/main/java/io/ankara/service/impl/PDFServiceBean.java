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
public class PDFServiceBean implements PDFService {
    @Override
    public File generatePDF(String URL, String outputFile) throws IOException, InterruptedException {
        executePDFGenerateCommand(URL, outputFile);
        return new File(outputFile);
    }

    private void executePDFGenerateCommand(String url, String outputFile) {
        String command = String.join(" ", Arrays.asList("xvfb-run wkhtmltopdf", url, outputFile));
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(p.getInputStream()));
//            while ((s = br.readLine()) != null)
//                System.out.println("line: " + s);
            p.waitFor();
//            System.out.println("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
