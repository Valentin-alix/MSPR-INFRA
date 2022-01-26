package msprinfra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Utilisateur {

	private String name;
	private String groupe;

	@Override
	public String toString() {
		return "Utilisateur [name=" + name + ", groupe=" + groupe + "]";
	}

}
