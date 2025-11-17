package com.suyash.Base64UseCases.services.usecase4;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class InlineAssetService {

    private static final Path PDF_PATH = Path.of("src/main/resources/sample.pdf");

    public String getPdfAsDataUri() throws IOException{

        byte[] pdfBytes = Files.readAllBytes(PDF_PATH);
        String base64 = Base64.getEncoder().encodeToString(pdfBytes);

        return "data:application/pdf;base64,"+ base64;
    }
}
