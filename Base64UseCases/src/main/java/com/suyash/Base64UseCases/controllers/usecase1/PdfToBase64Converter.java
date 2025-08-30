package com.suyash.Base64UseCases.controllers.usecase1;

import com.suyash.Base64UseCases.services.usecase1.PdfToBase64ConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
public class PdfToBase64Converter {

    private final PdfToBase64ConverterService pdfToBase64ConverterService;

    public PdfToBase64Converter(PdfToBase64ConverterService pdfToBase64ConverterService) {
        this.pdfToBase64ConverterService = pdfToBase64ConverterService;
    }

    //Endpoint 1: Returns BASE64 String inside a JSON structure
    @GetMapping("/base64")
    public ResponseEntity<Map<String, String>> getPdfAsBase64() throws IOException{

        String base64Pdf =  pdfToBase64ConverterService.convertToBase64();

        Map<String, String> response = new HashMap<>();
        response.put("base64", base64Pdf);

        return ResponseEntity.ok(response);
    }
}
