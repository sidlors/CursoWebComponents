/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sl314.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ExitoTransaccionTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		final JspWriter out = getJspContext().getOut();
		out.println( "Se registro Correctamente, regresa al menu y consulta nuevamente" );
	}
}