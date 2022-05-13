package com.privateAPI.DUSTestGenerator.test_generator;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.font.FontProvider;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import com.itextpdf.io.font.constants.StandardFonts;

import com.itextpdf.io.font.constants.StandardFonts;

import static com.itextpdf.kernel.pdf.PdfName.BaseFont;


public class PdfTestCreator {
    public String createPdfTestFromHtml(String html) throws IOException {
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new DefaultFontProvider(false, false, false);
        properties.setFontProvider(fontProvider);
        FontProgram fontProgram = FontProgramFactory.createFont(
                "src/main/resources/fonts/Roboto-Medium.ttf"
        );
        fontProvider.addFont(fontProgram);
        properties.setCharset("UTF-8");
        HtmlConverter.convertToPdf(html, target, properties);

        byte[] encoded = Base64.getEncoder().encode(target.toByteArray());

        return new String(encoded);
    }

}
