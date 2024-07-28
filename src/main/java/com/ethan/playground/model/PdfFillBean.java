package com.ethan.playground.model;

import be.quodlibet.boxable.line.LineStyle;
import lombok.Data;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PdfFillBean implements Serializable {

    private List<String> headNmList = new ArrayList<String>();

    private List<Float> headWidthList = new ArrayList<Float>();

    private float tableWidth;

    private float rowHeight;

    private float fontSize;

    private LineStyle topBorder;

    private LineStyle bottomBorder;

    private LineStyle leftBorder;

    private LineStyle rightBorder;

    private Color textColor;

    private Color fillColor;
}