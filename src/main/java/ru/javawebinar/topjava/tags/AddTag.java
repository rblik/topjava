package ru.javawebinar.topjava.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class AddTag extends SimpleTagSupport {
    int op1, op2;

    public AddTag() {
        op1 = op2 = 0;
    }

    public void setOp1(int op1) {
        this.op1 = op1;
    }

    public void setOp2(int op2) {
        this.op2 = op2;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.print(op1 + op2);
    }
}
