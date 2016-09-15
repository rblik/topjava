package ru.javawebinar.topjava.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class CapTag extends SimpleTagSupport{

    public CapTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        StringWriter stringWriter = new StringWriter();
        getJspBody().invoke(stringWriter);
        writer.println(stringWriter.toString().toUpperCase());
    }
}
