package ru.javawebinar.topjava.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class RequestInfoTag extends SimpleTagSupport {

    String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (type.equalsIgnoreCase("method")) {
            writer.println(request.getMethod());
        } else if (type.equalsIgnoreCase("url")) {
            writer.println(request.getRequestURL());
        } else if (type.equalsIgnoreCase("client")) {
            writer.println(request.getRemoteAddr());
        } else {
            writer.println("No");
        }
    }
}
