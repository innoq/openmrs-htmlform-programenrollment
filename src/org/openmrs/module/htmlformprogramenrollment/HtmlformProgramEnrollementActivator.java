/**
 * Copyright (C) 2011 innoQ Deutschland GmbH
 *
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * @author Arnd Kleinbeck, innoQ Deutschland GmbH, http://www.innoq.com
 */

package org.openmrs.module.htmlformprogramenrollment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.Activator;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.openmrs.module.htmlformprogramenrollment.handler.HtmlFormProgramEnrollmentHandler;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class HtmlformProgramEnrollementActivator implements Activator, Runnable {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @see org.openmrs.module.Activator#startup()
	 */
	public void startup() {
		log.info("Starting Html Form Program Enrollment Module...");
        Thread contextChecker = new Thread(this);
        contextChecker.start();

	}
	
	private void addHandler(HtmlFormEntryService hfes) {
		try {
			hfes.addHandler("programenrollment", new HtmlFormProgramEnrollmentHandler());
		}
		catch (Exception ex) {
			ex.printStackTrace(System.out);
			log.error("failed to register programenrollment tag in htmlformprogramenrollment");
		}
		
	}
	
	public void shutdown() {
		log.info("Shutting down Html Form Program Enrollment Module...");
	}

	// kill me...?
    public final void run() {
        // Wait for context refresh to finish
        HtmlFormEntryService fs = null;
        try {
            while (fs == null) {
                Thread.sleep(30000);
                    try{
                        log.info("HtmlFormFlowsheet still waiting for app context and services to load...");
                        fs = Context.getService(HtmlFormEntryService.class);
                    } catch (APIException apiEx){}
                }
        } catch (InterruptedException ex) {}
        
        try {
            Thread.sleep(10000);
            // Start new OpenMRS session on this thread
            Context.openSession();
            addHandler(fs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Could not pre-load concepts " + ex);
        } finally {
            Context.closeSession();
        }   
    }

}
