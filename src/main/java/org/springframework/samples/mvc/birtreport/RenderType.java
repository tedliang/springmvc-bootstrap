package org.springframework.samples.mvc.birtreport;

import org.eclipse.birt.report.engine.api.*;
import org.springframework.http.MediaType;
import sun.misc.BASE64Encoder;

import java.io.OutputStream;

public enum RenderType {
    HTML ("html", MediaType.TEXT_HTML_VALUE) {
        @Override
        public RenderOption buildRenderOption(OutputStream ostream) {
            HTMLRenderOption opt = new HTMLRenderOption();

            opt.setOutputFormat(this.getOutputFormat());
            opt.setOutputStream(ostream);
            opt.setBaseImageURL(null);
            opt.setImageDirectory(null);
            opt.setEnableAgentStyleEngine(true);

            opt.setEmbeddable(true);
            opt.setSupportedImageFormats("JPG");
            opt.setImageHandler(new HTMLServerImageHandler() {
                @Override
                protected String handleImage(IImage image, Object context, String prefix, boolean needMap) {
                    return "data:image;base64," + new BASE64Encoder().encode(image.getImageData());
                }
            });

            return opt;
        }
    },

    PDF ("pdf", "application/pdf") {
        @Override
        public RenderOption buildRenderOption(OutputStream ostream) {
            RenderOption opt = new PDFRenderOption();
            opt.setOutputFormat(this.getOutputFormat());
            opt.setOutputStream(ostream);
            return opt;
        }
    },

    EXCEL ("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
        @Override
        public RenderOption buildRenderOption(OutputStream ostream) {
            EXCELRenderOption opt = new EXCELRenderOption();
            opt.setOutputFormat(this.getOutputFormat());
            opt.setOutputStream(ostream);
            return opt;
        }
    };

    private final String outputFormat;

    private final String contentType;

    RenderType(String outputFormat, String contentType) {
        this.outputFormat = outputFormat;
        this.contentType = contentType;
    }

    public abstract RenderOption buildRenderOption(OutputStream ostream);

    public static RenderType resolveByOutputFormat(String extension) {
        for (RenderType renderType : values()) {
            if (renderType.outputFormat.equalsIgnoreCase(extension)) {
                return renderType;
            }
        }
        return null;
    }

    public String getContentType() {
        return contentType;
    }

    public String getOutputFormat() {
        return outputFormat;
    }
}
