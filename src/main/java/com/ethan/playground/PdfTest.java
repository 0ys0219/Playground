package com.ethan.playground;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.utils.FontUtils;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfTest {

    @SneakyThrows
    public float doGeneratePdf(FileOutputStream outputStream) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDFont font = FontUtils.loadFont(document, "pdf/Kaiu.ttf");

        BaseTable table = new BaseTable(700, 700, 100, 500, 50, document, page, true, true);

        for (int i =0;i<50;i++){
            Row<PDPage> row = table.createRow(20f);

            Cell<PDPage> cell = row.createCell(20, "測試一下");
            cell.setTextColor(Color.WHITE);
            cell.setFillColor(Color.DARK_GRAY);
            cell.setFont(font);
            cell.setFontSize(20f);
            cell.setAlign(HorizontalAlignment.CENTER);
            cell.setValign(VerticalAlignment.MIDDLE);
        }

        float yStart = table.draw();
        document.save(outputStream);
        document.close();

        return yStart;
    }

}
