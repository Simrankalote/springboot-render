package com.example.pdfgenerator.controller;

import com.example.pdfgenerator.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody String xslFoPayload) {
        try {
            byte[] pdfBytes = pdfService.generatePdfFromXslFo(xslFoPayload);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");  // Set correct Content-Type for PDF
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}


