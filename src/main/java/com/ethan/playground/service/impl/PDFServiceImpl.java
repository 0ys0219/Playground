package com.ethan.playground.service.impl;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.line.LineStyle;
import be.quodlibet.boxable.utils.FontUtils;
import com.ethan.playground.model.PdfConfigBean;
import com.ethan.playground.model.PdfFillBean;
import com.ethan.playground.model.PdfResultBean;
import com.ethan.playground.service.PDFService;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class PDFServiceImpl implements PDFService {

    @SneakyThrows
    private void doFillBorder(PdfConfigBean pdfConfig, PdfFillBean pdfFill) {
        BaseTable table = new BaseTable(pdfConfig.getYStart(), PDRectangle.A4.getHeight() - 75, 50, pdfFill.getTableWidth(), 30, pdfConfig.getDocument(), pdfConfig.getCurrentPage(), true, true);
        Row<PDPage> row = table.createRow(pdfFill.getRowHeight());
        Cell<PDPage> cell = row.createCell(100f, "");
        cell.setBorderStyle(new LineStyle(pdfFill.getFillColor()==null?Color.WHITE:pdfFill.getFillColor(), 0f));
        cell.setTopBorderStyle(new LineStyle(Color.WHITE, 1f));
        cell.setBottomBorderStyle(new LineStyle(Color.WHITE, 1f));
        cell.setRightBorderStyle(new LineStyle(Color.BLACK,1f));
        table.draw();
    }

    @SneakyThrows
    private void doFillContent(PdfConfigBean pdfConfig, PdfFillBean pdfFill) {
        BaseTable table = new BaseTable(pdfConfig.getYStart(), PDRectangle.A4.getHeight() - 75, 50, pdfFill.getTableWidth(), 30, pdfConfig.getDocument(), pdfConfig.getCurrentPage(), true, true);

        Row<PDPage> row = table.createRow(pdfFill.getRowHeight());
        for (int i = 0; i < pdfFill.getHeadWidthList().size(); i++) {
            Cell<PDPage> cell = row.createCell(pdfFill.getHeadWidthList().get(i), pdfFill.getHeadNmList().get(i));
            cell.setFontSize(pdfFill.getFontSize());
            cell.setFont(pdfConfig.getFont());
            cell.setAlign(HorizontalAlignment.CENTER);
            cell.setValign(VerticalAlignment.MIDDLE);
            cell.setFillColor(pdfFill.getFillColor() == null ? Color.WHITE : pdfFill.getFillColor());
            cell.setTextColor(pdfFill.getTextColor() == null ? Color.BLACK : pdfFill.getTextColor());
            cell.setBorderStyle(new LineStyle(pdfFill.getFillColor() == null ? Color.WHITE : pdfFill.getFillColor(), 0f));
            cell.setTopBorderStyle(pdfFill.getTopBorder() == null ? new LineStyle(Color.WHITE, 0f) : pdfFill.getTopBorder());
            cell.setBottomBorderStyle(pdfFill.getBottomBorder() == null ? new LineStyle(Color.WHITE, 0f) : pdfFill.getBottomBorder());
            cell.setLeftBorderStyle(pdfFill.getLeftBorder() == null ? new LineStyle(Color.WHITE, 0f) : pdfFill.getLeftBorder());
            cell.setRightBorderStyle(pdfFill.getRightBorder() == null ? new LineStyle(Color.WHITE, 0f) : pdfFill.getRightBorder());

            if (i == 0) {
                cell.setLeftBorderStyle(new LineStyle(Color.BLACK, 1f));
            }

            if (i == pdfFill.getHeadWidthList().size() - 1) {
                cell.setRightBorderStyle(new LineStyle(Color.BLACK, 1f));
            }
        }

    pdfFill.setRowHeight(row.getHeight());
        pdfFill.setTableWidth(530);
        doFillBorder(pdfConfig,pdfFill);

        float yStart = table.draw();
        pdfConfig.setCurrentPage(table.getCurrentPage());
        pdfConfig.setYStart(yStart);
    }

    @SneakyThrows
    private void doFillTitle(PdfConfigBean pdfConfig) {
        PdfFillBean headFill = new PdfFillBean();
        headFill.setFontSize(15f);
        headFill.setRowHeight(20);
        headFill.setTableWidth(530);
        headFill.setTopBorder(new LineStyle(Color.BLACK, 1f));
        headFill.setBottomBorder(new LineStyle(Color.BLACK, 1f));
        headFill.setHeadWidthList(Arrays.asList(100f));
        headFill.setHeadNmList(Arrays.asList("商店對帳單"));
        doFillContent(pdfConfig, headFill);
    }

    private void doFillInvoice(PdfConfigBean pdfConfig) {
        PdfFillBean pdfFill = new PdfFillBean();
        pdfFill.setHeadWidthList(Arrays.asList(25f, 25f, 25f, 25f));
        pdfFill.setHeadNmList(Arrays.asList("發票明細", "", "", "發票週期：日開"));
        pdfFill.setRowHeight(10f);
        pdfFill.setTableWidth(530f);
        pdfFill.setFontSize(10f);

        doFillContent(pdfConfig, pdfFill);

        pdfFill.setFillColor(new Color(123, 155, 105));
        pdfFill.setTextColor(Color.WHITE);
        pdfFill.setHeadWidthList(Arrays.asList(33f, 33f, 34f));
        pdfFill.setHeadNmList(Arrays.asList("請款類別", "一般交易", "分期交易"));
        pdfFill.setTableWidth(400f);
        doFillContent(pdfConfig, pdfFill);

        List<List<String>> invoiceList = Arrays.asList(
                Arrays.asList("測試1", "0", "0"),
                Arrays.asList("測試2", "0", "0"),
                Arrays.asList("測試3", "0", "0")
        );

        for (int i = 0; i < invoiceList.size(); i++) {
            pdfFill.setFillColor(Color.WHITE);
            pdfFill.setTextColor(Color.BLACK);
            pdfFill.setLeftBorder(new LineStyle(Color.BLACK, 1f));
            pdfFill.setTopBorder(new LineStyle(Color.BLACK, 1f));
            pdfFill.setHeadNmList(invoiceList.get(i));

            if (i == invoiceList.size() - 1) {
                pdfFill.setBottomBorder(new LineStyle(Color.BLACK, 1f));
            }
            pdfFill.setTableWidth(400f);
            doFillContent(pdfConfig, pdfFill);
        }
    }

    private void doFillInfo(PdfConfigBean pdfConfig) {
        List<List<String>> infoList = Arrays.asList(
                Arrays.asList("特店/群組代號", "A123456", "", ""),
                Arrays.asList("營業名稱", "測試商店", "", ""),
                Arrays.asList("撥款日期", "2024/07/28~2024/07/28", "", ""),
                Arrays.asList("請款金額", "一般交易", "分期交易", ""),
                Arrays.asList("金額1", "15", "1300", ""),
                Arrays.asList("金額2", "0", "0", ""),
                Arrays.asList("金額3", "9", "900", ""),
                Arrays.asList("金額4", "3", "300", ""),
                Arrays.asList("金額5", "15", "1300", ""),
                Arrays.asList("金額6", "0", "0", "")
        );

        PdfFillBean headFill = new PdfFillBean();
        headFill.setFontSize(10f);
        headFill.setRowHeight(10);
        headFill.setTableWidth(530);
        headFill.setHeadWidthList(Arrays.asList(25f, 25f, 25f, 25f));

        for (int i = 0; i < infoList.size(); i++) {
            headFill.setHeadNmList(infoList.get(i));

            if (i == infoList.size() - 1) {
                headFill.setBottomBorder(new LineStyle(Color.BLACK, 1f));
            }
            doFillContent(pdfConfig, headFill);
        }
    }

    @SneakyThrows
    private void doFillCard(PdfConfigBean pdfConfig) {
        PdfFillBean headFill = new PdfFillBean();
        headFill.setFillColor(new Color(123, 155, 105));
        headFill.setFontSize(10f);
        headFill.setRowHeight(10);
        headFill.setTextColor(Color.WHITE);
        headFill.setTableWidth(530);
        headFill.setTopBorder(new LineStyle(Color.BLACK, 1f));
        headFill.setHeadWidthList(Arrays.asList(25f, 25f, 25f, 25f));
        headFill.setHeadNmList(Arrays.asList("卡別", "筆數", "請款金額", "手續費"));

        doFillContent(pdfConfig, headFill);

        List<List<String>> cardList = Arrays.asList(
                Arrays.asList("一般卡", "2", "100", "0"),
                Arrays.asList("世界卡", "0", "0", "0"),
                Arrays.asList("聯名卡", "9", "900", "0"),
                Arrays.asList("國外卡", "3", "300", "0"),
                Arrays.asList("小計", "15", "1300", "0")
        );

        for (int i = 0; i < cardList.size(); i++) {
            headFill.setHeadNmList(cardList.get(i));
            headFill.setFillColor(Color.WHITE);
            headFill.setTextColor(Color.BLACK);

            if (CollectionUtils.containsAny(cardList.get(i), Arrays.asList("小計"))) {
                headFill.setFillColor(Color.LIGHT_GRAY);
                headFill.setBottomBorder(new LineStyle(Color.BLACK, 1f));
            }

            doFillContent(pdfConfig, headFill);
        }
    }

    @Override
    @SneakyThrows
    public void doFillPdf(OutputStream outputStream) {
        PdfConfigBean pdfConfig = new PdfConfigBean();
        pdfConfig.setDocument(new PDDocument());
        pdfConfig.setCurrentPage(new PDPage(PDRectangle.A4));
        pdfConfig.getDocument().addPage(pdfConfig.getCurrentPage());
        pdfConfig.setYStart(PDRectangle.A4.getHeight() - 75);
        pdfConfig.setFont(PDType0Font.load(pdfConfig.getDocument(), new ClassPathResource("pdf/Kaiu.ttf").getInputStream()));


        doFillTitle(pdfConfig);
        doFillInvoice(pdfConfig);
        doFillInfo(pdfConfig);

        doFillCard(pdfConfig);

        pdfConfig.getDocument().save(outputStream);
        pdfConfig.getDocument().close();
    }
}
