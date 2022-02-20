package msprinfra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String name;
	private String groupe;

	@Override
	public String toString() {
		return "Utilisateur [name=" + name + ", groupe=" + groupe + "]";
	}

}
