package com.privateAPI.DUSTestGenerator.test_generator.controller;

import com.privateAPI.DUSTestGenerator.test_generator.service.TestExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("export")
public class TestExportController
{

    private final TestExportService exportService;

    @Autowired
    public TestExportController(TestExportService exportService)
    {
        this.exportService = exportService;
    }

    @CrossOrigin(origins = "https://lubossremanak.site")
    @PostMapping("html-to-pdf")
    public String testFromHtmlToPdf(@RequestBody String html)
    {
        return this.exportService.htmlTestToPdf(html);
    }

}
