package org.springframework.samples.mvc.birtreport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
@RequestMapping("/birtreport")
public class BirtReportController {

    private BirtReportView birtReportView;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView run(@RequestParam RenderType renderType) {
        return birtReportView.loadTemplate(renderType,
                "/WEB-INF/reports/ProductCatalog.rptdesign");
    }

    @Inject
    public void setBirtReportView(BirtReportView birtReportView) {
        this.birtReportView = birtReportView;
    }
}
