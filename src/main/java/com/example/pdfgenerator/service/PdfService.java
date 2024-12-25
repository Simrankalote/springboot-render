package com.example.pdfgenerator.service;

import org.apache.fop.apps.*;
import org.springframework.stereotype.Service;
import org.apache.xmlgraphics.util.MimeConstants;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.sax.SAXResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;

@Service
public class PdfService {

    public byte[] generatePdfFromXslFo(String xslFoPayload) throws Exception {
        // Configure Apache FOP
        FopFactory fopFactory = FopFactory.newInstance(new URI("file:///"));
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        // Input Stream for the XSL-FO payload
        InputStream inputStream = new ByteArrayInputStream(xslFoPayload.getBytes());

        // Output Stream for the generated PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // Setup FOP with PDF output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);

            // Create a Transformer for XSL-FO processing
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(); // Identity transformer

            // Transform the XSL-FO to PDF
            Source src = new StreamSource(inputStream);
            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, res);

        } catch (TransformerException | FOPException e) {
            throw new Exception("Error generating PDF", e);
        } finally {
            inputStream.close();
            outputStream.close();
        }

        return outputStream.toByteArray(); // Return the PDF byte array
    }
}
