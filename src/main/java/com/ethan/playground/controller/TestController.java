package com.ethan.playground.controller;

import com.ethan.playground.PdfTest;
import com.ethan.playground.model.PdfFillBean;
import com.ethan.playground.service.PDFService;
import com.ethan.playground.service.impl.PDFServiceImpl;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/Test")
public class TestController {
    @Autowired
    PdfTest pdfTest;

   @Autowired
    PDFService pdfService;

//    @PostMapping("/getThread")
//    public List<PdfFillBean> getThread() {
//        List<CompletableFuture<PdfFillBean>> list = new ArrayList<>();
//        System.out.println("start: " + System.currentTimeMillis());
//        for (int i = 0; i < 100; i++) {
//            list.add(testService.printTime());
//        }
//
//        List<PdfFillBean> beanList = list.stream().map(CompletableFuture::join).toList();
//        System.out.println("end: " + System.currentTimeMillis());
//        return beanList;
//    }

    @PostMapping("/doGenPdf")
    public float doGeneratePdf() throws IOException {

        String filePath = "test" + new Date().getTime() + ".pdf";

        @Cleanup FileOutputStream outputStream = new FileOutputStream(filePath);

        pdfService.doFillPdf(outputStream);

//        float yStart = pdfTest.doGeneratePdf(outputStream);

        return 1;
    }
}