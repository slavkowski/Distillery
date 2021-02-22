package pl.licho.dto;

import java.sql.Timestamp;

public class DistilleryDataDto {
    private float level1;
    private float level2;
    private float level3;
    private float level4;
    private String chart;
    private String limitedChart;
    private Timestamp ts;

    public String getLimitedChart() {
        return limitedChart;
    }

    public void setLimitedChart(String limitedChart) {
        this.limitedChart = limitedChart;
    }

    public float getLevel1() {
        return level1;
    }

    public void setLevel1(float level1) {
        this.level1 = level1;
    }

    public float getLevel2() {
        return level2;
    }

    public void setLevel2(float level2) {
        this.level2 = level2;
    }

    public float getLevel3() {
        return level3;
    }

    public void setLevel3(float level3) {
        this.level3 = level3;
    }

    public float getLevel4() {
        return level4;
    }

    public void setLevel4(float level4) {
        this.level4 = level4;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
}
