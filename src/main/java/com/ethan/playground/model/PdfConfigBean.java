package com.ethan.playground.model;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.Serializable;

@Data
public class PdfConfigBean implements Serializable {

    private PDDocument document;

    private PDPage currentPage;

    private PDFont font;

    private float yStart;
}