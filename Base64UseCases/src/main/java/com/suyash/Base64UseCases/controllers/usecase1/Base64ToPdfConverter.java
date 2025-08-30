package com.suyash.Base64UseCases.controllers.usecase1;


import com.suyash.Base64UseCases.services.usecase1.PdfToBase64ConverterService;
import io.netty.handler.codec.http.HttpScheme;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

// Wrapper API

@RestController
@RequestMapping("/api/wrapper")
public class Base64ToPdfConverter {

    private final WebClient webClient;
    private final PdfToBase64ConverterService pdfService;

    @Autowired
    public Base64ToPdfConverter(PdfToBase64ConverterService pdfService) {
        this.pdfService = pdfService;

        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    // Endpoint 2: Wrapper API to convert BASE64 string back to original pdf

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getDecodedPdf(){
        Mono<Map> responseMono = webClient.get()
                .uri("/api/pdf/base64")
                .retrieve()
                .bodyToMono(Map.class);

        Map<String, String> response = responseMono.block();

        String base64Pdf = response.get("base64");

        //Decode back to pdf bytes
        byte[] pdfBytes = pdfService.convertBase64ToPdf(base64Pdf);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=decoded.pdf")
                .body(pdfBytes);
    }


}
