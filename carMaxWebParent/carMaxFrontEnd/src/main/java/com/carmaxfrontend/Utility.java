package com.carmaxfrontend;

import com.carmaxfrontend.setting.EmailSettingBag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Properties;

public class Utility {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();

		return siteURL.replace(request.getServletPath(), "");
	}

	public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settings) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(settings.getHost());
		mailSender.setPort(settings.getPort());
		mailSender.setUsername(settings.getUsername());
		mailSender.setPassword(settings.getPassword());

		Properties mailProperties = new Properties();
		mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
		mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());

		mailSender.setJavaMailProperties(mailProperties);

		return mailSender;
	}

	public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();
		if (principal == null) return null;

		String customerEmail = null;

		if (principal instanceof UsernamePasswordAuthenticationToken
				|| principal instanceof RememberMeAuthenticationToken) {
			customerEmail = request.getUserPrincipal().getName();
		}

		return customerEmail;
	}
}
