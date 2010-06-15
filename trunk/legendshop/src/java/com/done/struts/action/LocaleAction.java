/*
 * Generated by MyEclipse Struts Template path:
 * templates/java/JavaClass.vtl
 */
package com.done.struts.action;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bingosoft.jcf.sql.ConfigCode;

/**
 * <p>
 * 
 * @author hewq 国际化类 2009-03-14
 *         </p>
 */
public final class LocaleAction extends Action {
	private static Logger log = Logger.getLogger(LocaleAction.class);

	private boolean isBlank(String string) {
		return ((string == null) || (string.trim().length() == 0));
	}

	private static final String LANGUAGE = "language";

	/**
	 * <p>
	 * Parameter for {@link java.util.Locale} country property. ["country"]
	 * </p>
	 */
	private static final String COUNTRY = "country";

	/**
	 * <p>
	 * Parameter for response page URI. ["page"]
	 * </p>
	 */
	private static final String PAGE = "page";

	/**
	 * <p>
	 * Parameter for response forward name. ["forward"]
	 * </p>
	 */
	private static final String FORWARD = "forward";
	
	private static final String LOCALEACTION = "localeAction";
	

	/**
	 * <p>
	 * Logging message if LocaleAction is missing a target parameter.
	 * </p>
	 */
	private static final String LOCALE_LOG = "LocaleAction: Missing page or forward parameter";

	/**
	 * <p>
	 * Change the user's Struts {@link java.util.Locale} based on request
	 * parameters for "language", "country". After setting the Locale, control
	 * is forwarded to an URI path indicated by a "page" parameter, or a forward
	 * indicated by a "forward" parameter, or to a forward given as the mappings
	 * "parameter" property. The response location must be specified one of
	 * these ways.
	 * </p>
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return An ActionForward indicate the resources that will render the
	 *         response
	 * @throws Exception
	 *             if an input/output error or servlet exception occurs
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String language = request.getParameter(LANGUAGE);
		String country = request.getParameter(COUNTRY);
		Locale locale = getLocale(request);

		if ((!isBlank(language)) && (!isBlank(country))) {
			locale = new Locale(language, country);
		} else if (!isBlank(language)) {
			locale = new Locale(language, "");
		}

		HttpSession session = request.getSession();
		if (session != null) {
			if (session.getAttribute(Globals.LOCALE_KEY) != null) {
				session.removeAttribute(Globals.LOCALE_KEY);
			}
			log.debug("set locale : " + locale.getLanguage());
			session.setAttribute(Globals.LOCALE_KEY, locale);
			// Locale.setDefault(locale);
		}

		String target = request.getParameter(PAGE);
		
		
		if (!isBlank(target)) {
			if(!(target.indexOf(LOCALEACTION) > -1)){
				return new ActionForward(target);
			}
		}

		target = request.getParameter(FORWARD);
		if (isBlank(target)) {
			target = mapping.getParameter();
		}
		if (isBlank(target)) {
			log.warn(LOCALE_LOG);
			return null;
		}
		return mapping.findForward(target);
	}

}
