package org.springframework.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;
import org.sitemesh.tagprocessor.Tag;
import org.sitemesh.tagprocessor.util.CharSequenceList;

import java.io.IOException;

public class JavaScriptTagRuleBundle implements TagRuleBundle {

    private static final String TAG = "javascript";

    @Override
    public void install(State defaultState, ContentProperty contentProperty,
                        SiteMeshContext siteMeshContext) {
        defaultState.addRule(TAG, new JavaScriptTagsToContentRule(siteMeshContext, contentProperty.getChild(TAG), false));
    }

    @Override
    public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {}

    private class JavaScriptTagsToContentRule extends ExportTagToContentRule {

        private final CharSequenceList contents = new CharSequenceList() {
            @Override
            public void writeTo(Appendable out) throws IOException {
                out.append("<script type=\"text/javascript\">");
                super.writeTo(out);
                out.append("</script>");
            }
        };

        public JavaScriptTagsToContentRule(SiteMeshContext context, ContentProperty targetProperty, boolean includeInContent) {
            super(context, targetProperty, includeInContent);
            targetProperty.setValue(contents);
        }

        @Override
        protected void processEnd(Tag tag, Object data) throws IOException {
            contents.append(tagProcessorContext.currentBufferContents());
            super.processEnd(tag, data);
        }

    }

}
