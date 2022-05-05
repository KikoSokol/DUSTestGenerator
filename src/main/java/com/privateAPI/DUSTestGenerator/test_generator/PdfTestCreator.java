package com.privateAPI.DUSTestGenerator.test_generator;

import com.itextpdf.html2pdf.HtmlConverter;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class PdfTestCreator
{
    public String createPdfTestFromHtml(String html)
    {
        ByteArrayOutputStream target = new ByteArrayOutputStream();

        HtmlConverter.convertToPdf(html, target);
        byte[] encoded = Base64.getEncoder().encode(target.toByteArray());

        return new String(encoded);
    }
}
