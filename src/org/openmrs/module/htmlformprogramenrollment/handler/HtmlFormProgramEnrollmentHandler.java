package org.openmrs.module.htmlformprogramenrollment.handler;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.htmlformentry.handler.TagHandler;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class HtmlFormProgramEnrollmentHandler implements TagHandler {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	public boolean doStartTag(FormEntrySession session, PrintWriter out, Node parent, Node node) {
		Map<String, String> attributes = new HashMap<String, String>();
		NamedNodeMap map = node.getAttributes();
		for (int i = 0; i < map.getLength(); ++i) {
			Node attribute = map.item(i);
			attributes.put(attribute.getNodeName(), attribute.getNodeValue());
		}
		
		Patient patient = session.getPatient();
		
		// parameter processing
		// String windowHeight = (String) attributes.get("windowHeight");
		
		if (patient != null) {
			StringBuilder sb = new StringBuilder("");
			sb.append("todo: genialer ajax-aware html code zur program enrollment editierung");
			out.print(sb.toString());
		} else {
			out.print("<div>You must create the patient first!</div>");
		}
		
		return true;
	}
	
	public void doEndTag(FormEntrySession session, PrintWriter out, Node parent, Node node) {
		// TODO Auto-generated method stub
	}
}
