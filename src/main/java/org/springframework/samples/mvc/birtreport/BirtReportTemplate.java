package org.springframework.samples.mvc.birtreport;

import java.io.InputStream;

public class BirtReportTemplate {

    private RenderType renderType;

    private final InputStream inputStream;

    public BirtReportTemplate(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public BirtReportTemplate(InputStream inputStream, RenderType renderType) {
        this.inputStream = inputStream;
        this.renderType = renderType;
    }

    public void setRenderType(RenderType renderType) {
        this.renderType = renderType;
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
