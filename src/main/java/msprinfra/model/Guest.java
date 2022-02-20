package msprinfra.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Guest {

	// Variable gardé à chaque tentative
	private List<String> bruteForcePasswordList = new ArrayList<String>();
	private Integer differentAttempt = 0;
	// Constante = nombre maximum d'essais
	private final Integer MAXATTEMPTS = 5;

}
