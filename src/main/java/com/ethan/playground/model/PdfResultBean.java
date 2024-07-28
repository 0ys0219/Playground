package com.ethan.playground.model;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.Serializable;

@Data
public class PdfResultBean implements Serializable {

    private PDPage currentPage;

    private float yStart;
}