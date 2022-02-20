package msprinfra.factory;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import msprinfra.model.AD;
import msprinfra.model.Guest;
import msprinfra.model.User;

public class ADFactory {

	private static final ResourceBundle resource = ResourceBundle.getBundle("info");

	public static Boolean connection(String login, String password) {

		AD activeDirectory = new AD();
		Guest guest = new Guest();

		activeDirectory.setServerIP(resource.getString("AD.Ip"));
		activeDirectory.setServerPort(resource.getString("AD.ServerPort"));
		activeDirectory.setServerDomain(resource.getString("AD.DomainName"));
		activeDirectory.setServerDomainExtension(resource.getString("AD.DomainExtension"));
		activeDirectory.setServerLogin(login + "@" + activeDirectory.getServerDomain());
		activeDirectory.setServerPass(password);

		Hashtable<String, String> environnement = new Hashtable<String, String>();

		environnement.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environnement.put(Context.PROVIDER_URL,
				"ldap://" + activeDirectory.getServerIP() + ":" + activeDirectory.getServerPort() + "/");
		environnement.put(Context.SECURITY_AUTHENTICATION, "simple");
		environnement.put(Context.SECURITY_PRINCIPAL, activeDirectory.getServerLogin());
		environnement.put(Context.SECURITY_CREDENTIALS, activeDirectory.getServerPass());
		try {

			// tentative de conexion à l'active directory avec le login et password spécifié
			DirContext contexte = new InitialDirContext(environnement);

			System.out.println("Connexion au serveur : SUCCES");

			try {
				Attributes attrs = contexte.getAttributes("CN=" + login + ",OU=UTILISATEURS,DC="
						+ activeDirectory.getServerDomain() + ",DC=" + activeDirectory.getServerDomainExtension());
				System.out.println("Recuperation de l'utilisateur : SUCCES");

				String name = (String) attrs.get("name").get();
				String groupe = (String) attrs.get("memberof").get();
				User user = new User();
				user.setName(name);
				user.setGroupe(groupe);
				System.out.println(user);
				return true;

			} catch (NamingException e) {
				System.out.println("Recuperation des attributs de l'utilisateur : ECHEC");
				bruteForce(password, guest);
				return false;
			}

		} catch (NamingException e) {
			System.out.println("Connexion au serveur : ECHEC");
			bruteForce(password, guest);
			return false;
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
}
