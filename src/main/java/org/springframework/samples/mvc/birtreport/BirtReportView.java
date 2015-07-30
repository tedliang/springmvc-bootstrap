package org.springframework.samples.mvc.birtreport;

import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Named
public class BirtReportView extends AbstractView implements InitializingBean {

    public final static String TEMPLATE_KEY = ClassUtils.getShortNameAsProperty(BirtReportTemplate.class);

    private IReportEngine reportEngine;

    public ModelAndView loadTemplate(RenderType renderType, String url) {
        InputStream reportPath = getServletContext().getResourceAsStream(url);
        return loadTemplate(renderType, reportPath);
    }

    public ModelAndView loadTemplate(RenderType renderType, InputStream inputStream) {
        return new ModelAndView(this, TEMPLATE_KEY, new BirtReportTemplate(renderType, inputStream));
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
            RenderOption opt = template.getRenderType().buildRenderOption(response.getOutputStream());
            response.setContentType(template.getRenderType().getContentType());
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

}
