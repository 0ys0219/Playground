package com.ethan.playground.service;

import com.ethan.playground.model.PdfConfigBean;

import java.io.OutputStream;

public interface PDFService {

    void doFillPdf(OutputStream outputStream);
}