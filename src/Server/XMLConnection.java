package Server;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Generated;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlConnection {

	private static Document xmlDoc;
	private static DocumentBuilder docReader;
	private static XPath xPath;

	public XmlConnection() throws ParserConfigurationException, SAXException,
			IOException {
		docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xmlDoc = docReader.parse(new File("./XML/User.xml"));

		xPath = XPathFactory.newInstance().newXPath();

	}

	public static int userLogin(String username, String password) {
		password = makeSecurepw(password);
		String expression = "//user[@name =\"" + username
				+ "\" and @password=\"" + password + "\"]";

		System.out.println(expression);

		try {
			Node user = (Node) xPath.compile(expression).evaluate(xmlDoc,
					XPathConstants.NODE);
			if (user == null) {
				String expression2 = "//user[@name =\"" + username + "\"]";
				Node user2 = (Node) xPath.compile(expression2).evaluate(xmlDoc,
						XPathConstants.NODE);
				if (user2 == null) {

					Element newUser = xmlDoc.createElement("user");
					newUser.setAttribute("name", username);
					newUser.setAttribute("password", password);
					xmlDoc.getElementsByTagName("users").item(0)
							.appendChild(newUser);

					StreamResult file = new StreamResult(new File(
							"./XML/user.xml"));
					Transformer transformer;
					try {
						transformer = TransformerFactory.newInstance()
								.newTransformer();
						DOMSource source = new DOMSource(xmlDoc);
						transformer.transform(source, file);
						return 0;
					} catch (TransformerFactoryConfigurationError
							| TransformerException e1) {

						System.err.println("Transformacija nije uspjela.");
						return -4;
					}
				} else {
					System.out.println("Novi korisnik unesen i prijavljen.");
					return -1;
				}
			}
		} catch (XPathExpressionException e) {
			System.err.println("Greska u Xpath-u");
			return -3;
		}
		System.out.println("Korisnik uspjesno prijavljen.");
		return 0;

	}

	private static String makeSecurepw(String password) {

		String passwordToHash = password;
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(generatedPassword);

		return generatedPassword;
	}

	public static void main(String[] args) {
		try {
			XmlConnection test = new XmlConnection();
			int rezultat = XmlConnection.userLogin("Fata", "Semsa");
			System.out.println(rezultat);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}
}
