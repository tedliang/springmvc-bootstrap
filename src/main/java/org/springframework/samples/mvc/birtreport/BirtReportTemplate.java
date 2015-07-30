package org.springframework.samples.mvc.birtreport;

import org.springframework.util.ClassUtils;

import java.io.InputStream;

public class BirtReportTemplate {

    private final RenderType renderType;

    private final InputStream inputStream;

    public BirtReportTemplate(RenderType renderType, InputStream inputStream) {
        this.renderType = renderType;
        this.inputStream = inputStream;
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
