package com.suyash.Base64UseCases.services.usecase1;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class PdfToBase64ConverterService {

    private static final String PDF_PATH="src/main/java/com.suyash.Base64UseCases/resources/sample.pdf";

    //Convert PDF to BASe64
    public String convertToBase64() throws IOException{
        byte[] pdfBytes = Files.readAllBytes(Path.of(PDF_PATH));

        return Base64.getEncoder().encodeToString(pdfBytes);
    }

    //Convert BASE64 back to PDF
    public byte[] convertBase64ToPdf(String base64Data){
        return Base64.getDecoder().decode(base64Data);
    }


}
