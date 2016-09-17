package burp;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.StringValidator;;

/**
 * @author tosebro
 *
 */
public class BurpExtender implements IBurpExtender, IProxyListener {
	/**
	 * burp objects
	 */
	private IExtensionHelpers helpers;

	/**
	 * Extension name
	 */
	private static final String EXTENSION_NAME = "JPtitle";

	/**
	 * obtain our output and error streams
	 */
	public static PrintWriter stdout;
	/**
	 * obtain our output and error streams
	 */
	public static PrintWriter stderr;

	//
	// implement IBurpExtender
	//
	@Override
	public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
		// obtain an extension helpers object
		helpers = callbacks.getHelpers();

		// set our extension name
		callbacks.setExtensionName(EXTENSION_NAME);
		
		// register extension to callbacks
		callbacks.registerProxyListener(this);

		// obtain our output and error streams
		stdout = new PrintWriter(callbacks.getStdout(), true);
		stderr = new PrintWriter(callbacks.getStderr(), true);
	}

	/*
	 * implement IProxyListener.
	 * search title element and set it to comment in Proxy History  
	 * 
	 * @see burp.IProxyListener#processProxyMessage(boolean,
	 * burp.IInterceptedProxyMessage)
	 */
	@Override
	public void processProxyMessage(boolean messageIsRequest, IInterceptedProxyMessage message) {
		if (!messageIsRequest) {
			// retrieve response text
			String responseText = StringValidator.byteArrayToString(message.getMessageInfo().getResponse());

			// search title element
			Pattern titlePattern = Pattern.compile("<title>([^<]+)</title>", Pattern.CASE_INSENSITIVE);
			Matcher matcher = titlePattern.matcher(responseText.toString());
			if (matcher.find()) {
				// set title to comment in Proxy
				String title = matcher.group(1);
				message.getMessageInfo().setComment(title);
			}
		}
	}

}
