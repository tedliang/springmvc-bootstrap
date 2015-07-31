package org.springframework.samples.mvc.birtreport;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;

@Controller
@RequestMapping("/birtreport")
public class BirtReportController {

    private BirtReportView birtReportView;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView run() {
        return birtReportView.loadTemplate("/WEB-INF/reports/ProductCatalog.rptdesign");
    }

    @RequestMapping(value = "/parameterDefs", method = RequestMethod.GET)
    public String parameterDefs(ModelMap modelMap) throws IOException, EngineException {
        Collection<IParameterDefn> paramDefs = birtReportView.getParameterDefs("/WEB-INF/reports/ProductCatalog.rptdesign");
        modelMap.put("paramDefs", paramDefs);
        return "birtreport-parameter-defs";
    }

    @Inject
    public void setBirtReportView(BirtReportView birtReportView) {
        this.birtReportView = birtReportView;
    }
}
