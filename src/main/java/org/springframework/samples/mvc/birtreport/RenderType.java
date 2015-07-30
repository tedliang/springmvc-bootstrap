package org.springframework.samples.mvc.birtreport;

import org.eclipse.birt.report.engine.api.*;
import org.springframework.http.MediaType;
import sun.misc.BASE64Encoder;

import java.io.OutputStream;

public enum RenderType {
    HTML (MediaType.TEXT_HTML_VALUE) {
        @Override
        public RenderOption buildRenderOption(OutputStream ostream) {
            HTMLRenderOption opt = new HTMLRenderOption();

            opt.setOutputFormat(IRenderOption.OUTPUT_FORMAT_HTML);
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

    PDF ("application/pdf") {
        @Override
        public RenderOption buildRenderOption(OutputStream ostream) {
            RenderOption opt = new PDFRenderOption();
            opt.setOutputFormat(IRenderOption.OUTPUT_FORMAT_PDF);
            opt.setOutputStream(ostream);
            return opt;
        }
    },

    EXCEL ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
        @Override
        public RenderOption buildRenderOption(OutputStream ostream) {
            EXCELRenderOption opt = new EXCELRenderOption();
            opt.setOutputFormat("xlsx");
            opt.setOutputStream(ostream);
            return opt;
        }
    };

    private final String contentType;

    RenderType(String contentType) {
        this.contentType = contentType;
    }

    public abstract RenderOption buildRenderOption(OutputStream ostream);

    public String getContentType() {
        return contentType;
    }
}
