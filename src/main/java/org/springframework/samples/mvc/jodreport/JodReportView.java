package org.springframework.samples.mvc.jodreport;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import org.apache.commons.io.IOUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.document.DefaultDocumentFormatRegistry;
import org.artofsolving.jodconverter.document.DocumentFormat;
import org.artofsolving.jodconverter.document.DocumentFormatRegistry;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Named
public class JodReportView extends AbstractView implements ApplicationContextAware, DisposableBean {

    public final static String TEMPLATE_KEY = JodReportView.class.toString();

    private final static String ODT = "odt";

    private DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();

    private DocumentFormatRegistry formatRegistry = new DefaultDocumentFormatRegistry();

    private OfficeManager officeManager;

    public ModelAndView loadTemplate(String path) {
        return loadTemplate(getResourceAsStream(path));
    }

    public ModelAndView loadTemplate(InputStream inputStream) {
        return new ModelAndView(this, TEMPLATE_KEY, inputStream);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        try (InputStream inputStream = (InputStream) model.remove(TEMPLATE_KEY)) {
            DocumentTemplate template = documentTemplateFactory.getTemplate(inputStream);


            String extension = StringUtils.getFilenameExtension(request.getRequestURI());
            if (StringUtils.isEmpty(extension)) {
                extension = ODT;
            }

            DocumentFormat outputFormat = formatRegistry.getFormatByExtension(extension);
            response.setContentType(outputFormat.getMediaType());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            if (extension.equalsIgnoreCase(ODT)) {
                template.createDocument(model, response.getOutputStream());
            }
            else {
                File srcFile = createTempFile(ODT);
                File targetFile = createTempFile(extension);

                template.createDocument(model, new FileOutputStream(srcFile));

                OfficeDocumentConverter converter = new OfficeDocumentConverter(getOfficeManager());
                converter.convert(srcFile, targetFile, outputFormat);

                try (FileInputStream input = new FileInputStream(targetFile)) {
                    IOUtils.copy(input, response.getOutputStream());
                }
            }
        }
    }

    private File createTempFile(String extension) throws IOException {
        File tempFile = File.createTempFile("document", "." + extension);
        tempFile.deleteOnExit();
        return tempFile;
    }

    private OfficeManager getOfficeManager() {
        if (officeManager == null) {
            officeManager = new DefaultOfficeManagerConfiguration().buildOfficeManager();
            officeManager.start();
        }
        return officeManager;
    }

    @Override
    public void destroy() throws Exception {
        if (officeManager == null) {
            officeManager.stop();
            officeManager = null;
        }
    }

    private InputStream getResourceAsStream(String path) {
        return getServletContext().getResourceAsStream(path);
    }
}
