package net.teamfruit.factorioforge.factorioapi.data;

import java.util.List;

public interface IInfo {

	List<String> getAuthor();

	String getContact();

	List<String> getDependencies();

	String getDescription();

	String getFactorioVersion();

	String getHomePage();

	String getName();

	String getTitle();

	String getVersion();

}
