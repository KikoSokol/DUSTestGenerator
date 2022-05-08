package com.privateAPI.DUSTestGenerator.test_generator.service.impl;

import com.privateAPI.DUSTestGenerator.test_generator.PdfTestCreator;
import com.privateAPI.DUSTestGenerator.test_generator.service.TestExportService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TestExportServiceImpl implements TestExportService
{
    private final PdfTestCreator pdfCreator;

    public TestExportServiceImpl() {
        this.pdfCreator = new PdfTestCreator();
    }

    @Override
    public String htmlTestToPdf(String html) throws IOException {
        return this.pdfCreator.createPdfTestFromHtml(html);
    }
}
