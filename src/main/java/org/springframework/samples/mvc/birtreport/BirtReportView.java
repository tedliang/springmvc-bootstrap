package org.springframework.samples.mvc.birtreport;

import org.eclipse.birt.report.engine.api.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

@Named
public class BirtReportView extends AbstractView implements InitializingBean {

    public final static String TEMPLATE_KEY = BirtReportTemplate.class.getName();

    private IReportEngine reportEngine;

    private RenderType defaultRenderType = RenderType.HTML;

    public ModelAndView loadTemplate(String path) {
        return loadTemplate(getResourceAsStream(path));
    }

    public ModelAndView loadTemplate(String path, RenderType renderType) {
        return loadTemplate(getResourceAsStream(path), renderType);
    }

    public ModelAndView loadTemplate(InputStream inputStream) {
        return loadTemplate(new BirtReportTemplate(inputStream));
    }

    public ModelAndView loadTemplate(InputStream inputStream, RenderType renderType) {
        return loadTemplate(new BirtReportTemplate(inputStream, renderType));
    }

    public ModelAndView loadTemplate(BirtReportTemplate birtReportTemplate) {
        return new ModelAndView(this, TEMPLATE_KEY, birtReportTemplate);
    }

    public Collection<IParameterDefn> getParameterDefs(String path) throws EngineException, IOException {
        return getParameterDefs(getResourceAsStream(path));
    }

    public Collection<IParameterDefn> getParameterDefs(InputStream inputStream) throws EngineException, IOException {
        try (InputStream internalStream = inputStream) {
            IReportRunnable report = reportEngine.openReportDesign(internalStream);
            IGetParameterDefinitionTask task = reportEngine.createGetParameterDefinitionTask(report);
            return task.getParameterDefns(false);
        }
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        BirtReportTemplate template = (BirtReportTemplate) model.remove(TEMPLATE_KEY);
        if (template == null) {
            throw new IllegalArgumentException("BirtReportTemplate is required in model");
        }

        try (InputStream inputStream = template.getInputStream()) {
            IReportRunnable report = reportEngine.openReportDesign(inputStream);
            IRunAndRenderTask task = reportEngine.createRunAndRenderTask(report);

            task.setLocale(request.getLocale());
            for (Map.Entry<String, Object> e : model.entrySet()) {
                task.setParameterValue(e.getKey(), e.getValue());
            }

            RenderType renderType = template.getRenderType();

            if (renderType == null) {
                String extension = StringUtils.getFilenameExtension(request.getRequestURI());
                renderType = RenderType.resolveByOutputFormat(extension);
            }

            if (renderType == null) {
                renderType = defaultRenderType;
            }

            RenderOption opt = renderType.buildRenderOption(response.getOutputStream());
            response.setContentType(renderType.getContentType());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            task.setRenderOption(opt);

            task.run();
            task.close();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (reportEngine == null) {
            reportEngine = getApplicationContext().getBean(IReportEngine.class);
        }
    }

    public void setDefaultRenderType(RenderType defaultRenderType) {
        this.defaultRenderType = defaultRenderType;
    }

    private InputStream getResourceAsStream(String path) {
        return getServletContext().getResourceAsStream(path);
    }

}
