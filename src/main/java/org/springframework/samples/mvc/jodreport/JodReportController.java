package org.springframework.samples.mvc.jodreport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
@RequestMapping("/jodreport")
public class JodReportController {

    private JodReportView jodReportView;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView run() {
        ModelAndView template = jodReportView.loadTemplate("/WEB-INF/reports/hello-template.odt");
        template.addObject("name", "world");
        return template;
    }

    @Inject
    public void setJodReportView(JodReportView jodReportView) {
        this.jodReportView = jodReportView;
    }
}
