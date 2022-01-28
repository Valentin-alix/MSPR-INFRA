package msprinfra.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AD {

	private String serverIP;
	private String serverPort;
	private String serverLogin;
	private String serverPass;
	private String serverDomain;
	private String serverDomainExtension;

}
