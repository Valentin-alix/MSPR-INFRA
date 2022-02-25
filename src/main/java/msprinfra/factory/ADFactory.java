package msprinfra.factory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import msprinfra.model.Guest;
import msprinfra.model.User;

public class ADFactory {

	private static final ResourceBundle resource = ResourceBundle.getBundle("info");

	public static Boolean authUser(String login, String password) {

		Guest guest = new Guest();

		Hashtable<String, String> environnement = new Hashtable<String, String>();

		environnement.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environnement.put(Context.PROVIDER_URL,
				"ldap://" + resource.getString("AD.Ip") + ":" + resource.getString("AD.ServerPort") + "/");
		environnement.put(Context.SECURITY_AUTHENTICATION, "simple");
		environnement.put(Context.SECURITY_PRINCIPAL, login + "@" + resource.getString("AD.DomainName"));
		environnement.put(Context.SECURITY_CREDENTIALS, password);
		try {

			// tentative de conexion à l'active directory avec le login et password spécifié
			DirContext contexte = new InitialDirContext(environnement);

			System.out.println("Connexion au serveur : SUCCES");
			System.out.println(getAttribute(contexte, login, password));
			return true;

		} catch (NamingException e) {
			System.out.println("Connexion au serveur : ECHEC");
			bruteForce(password, guest);
			return false;
		}

	}

	public static User getAttribute(DirContext contexte, String login, String password) {
		try {
			Attributes attrs = contexte.getAttributes("CN=" + login + ",OU=UTILISATEURS,DC="
					+ resource.getString("AD.DomainName") + ",DC=" + resource.getString("AD.DomainExtension"));
			System.out.println("Recuperation de l'utilisateur : SUCCES");

			String name = (String) attrs.get("name").get();
			String groupe = (String) attrs.get("memberof").get();
			User user = new User();
			user.setName(name);
			user.setGroupe(groupe);
			return user;

		} catch (NamingException e) {
			System.out.println("Recuperation des attributs de l'utilisateur : ECHEC");
			return null;
		}
	}

	// retourne vrai si l'utilisateur a dépasser MAXATTEMPTS tentatives de login
	// avec un password différent (brute force)
	// permet à  l'utilisateur de changer de login sans le bloquer
	public static Boolean bruteForce(String password, Guest guest) {

		if (guest.getBruteForcePasswordList().contains(password) == false) {
			guest.setDifferentAttempt(guest.getDifferentAttempt() + 1);
			guest.getBruteForcePasswordList().add(password);
		}

		if (guest.getDifferentAttempt() > guest.getMAXATTEMPTS()) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean ipControl(String ip) throws UnknownHostException {
		return InetAddress.getByName(ip).getHostName().endsWith("fr");
	}
}
