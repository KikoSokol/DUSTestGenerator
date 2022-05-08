package com.privateAPI.DUSTestGenerator.test_generator.service;

import java.io.IOException;

public interface TestExportService
{
    String htmlTestToPdf(String html) throws IOException;
}
