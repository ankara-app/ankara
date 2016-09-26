package io.ankara.service;

import java.io.File;
import java.io.IOException;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/25/16.
 */
public interface PDFService {

    File generatePDF(String URL, String outputFile) throws IOException, InterruptedException;
}
