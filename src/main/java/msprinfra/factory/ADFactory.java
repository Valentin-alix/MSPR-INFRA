package msprinfra.factory;

import java.util.Hashtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import msprinfra.model.AD;
import msprinfra.model.Utilisateur;

public class ADFactory {

	private static final ResourceBundle resource = ResourceBundle.getBundle("info");

	public static Boolean connection(String login, String password) {

		AD activeDirectory = new AD();
		activeDirectory.setServerIP(resource.getString("AD.Ip"));
		activeDirectory.setServerPort(resource.getString("AD.ServerPort"));

		activeDirectory.setServerLogin(login);
		activeDirectory.setServerPass(password);

		Hashtable<String, String> environnement = new Hashtable<String, String>();
		environnement.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environnement.put(Context.PROVIDER_URL,
				"ldap://" + activeDirectory.getServerIP() + ":" + activeDirectory.getServerPort() + "/");
		environnement.put(Context.SECURITY_AUTHENTICATION, "simple");
		environnement.put(Context.SECURITY_PRINCIPAL, activeDirectory.getServerLogin());
		environnement.put(Context.SECURITY_CREDENTIALS, activeDirectory.getServerPass());
		try {

			DirContext contexte = new InitialDirContext(environnement);

			System.out.println("Connexion au serveur : SUCCES");

			try {
				Attributes attrs = contexte.getAttributes("CN=Jean Test,OU=UTILISATEURS,DC=LECHATELET,DC=COM");
				System.out.println("Recuperation de jean : SUCCES");

				String name = (String) attrs.get("name").get();
				String groupe = (String) attrs.get("memberof").get();

				Utilisateur utilisateur = new Utilisateur(name, groupe);
				System.out.println(utilisateur);
				return true;

			} catch (NamingException e) {
				System.out.println("Recuperation de jean : ECHEC");
				bruteForce(password);
				e.printStackTrace();
				return false;
			}

		} catch (NamingException e) {
			System.out.println("Connexion au serveur : ECHEC");
			e.printStackTrace();
			return false;
		}

	}

	//Variables gardées à chaques tentatives
	static List<String> bruteForcePasswordList = new ArrayList<String>();
	static Integer differentAttempt = 0;
	//Constante = nombre maximum d'essais
	static Integer MAXATTEMPTS = 5;

	//retourne vrai si l'utilisateur a dépassé MAXATTEMPTS tentatives de login avec un password différent (brute force)
	//permet à l'utilisateur de changer de login sans le bloquer
	public static Boolean bruteForce(String password){

		if(bruteForcePasswordList.contains(password) == false){
			differentAttempt += 1;
			bruteForcePasswordList.add(password);
		}

		if(differentAttempt > MAXATTEMPTS)
		{
			return true;
		}
		else{
			return false;
		}
	}
}
