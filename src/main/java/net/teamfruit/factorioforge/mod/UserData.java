package net.teamfruit.factorioforge.mod;

public class UserData {

	private String username;
	private String token;

	public String getUsername() {
		return this.username;
	}

	public String getToken() {
		return this.token;
	}

	public UserData setUsername(final String username) {
		this.username = username;
		return this;
	}

	public UserData setToken(final String token) {
		this.token = token;
		return this;
	}

	public boolean isValid() {
		return this.username!=null&&this.token!=null;
	}
}
