package gruentausch.mail;

public enum MailAccounts {
	GMX("mail.gmx.net", 25, "patrick.liersch@gmx.de", "Rias=rs2!g", "patrick.liersch@gmx.de"), 
	GOOGLEMAIL("smtp.googlemail.com", 25, "login", "passwort", "absender"), 
	ARCOR("mail.arcor.de", 25, "login", "passwort", "absender"), 
	WEB("smtp.web.de", 25, "login", "passwort", "absender"), 
	YAHOO("smtp.mail.yahoo.de", 25, "login", "passwort", "absender");

	private String smtpHost;
	private int port;
	private String username;
	private String password;
	private String email;

	private MailAccounts(String smtpHost, int port, String username, String password, String email) {
		this.smtpHost = smtpHost;
		this.port = port;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getPort() {
		return port;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public MailAuthenticator getPasswordAuthentication() {
		return new MailAuthenticator(username, password);
	}

	public String getEmail() {
		return email;
	}
}
