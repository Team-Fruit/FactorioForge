package net.teamfruit.factorioforge.ui;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import net.teamfruit.factorioforge.factorioapi.data.IInfo;

public class UIModDetailController {
	@FXML
	private Text name;
	@FXML
	private Text description;
	@FXML
	private Text status;
	@FXML
	private Text version;
	@FXML
	private Text author;
	@FXML
	private Text contact;
	@FXML
	private Text homepage;
	@FXML
	private Text factorio_version;
	@FXML
	private Text dependencies;

	public void setName(final String text) {
		this.name.setText(text);
	}

	public void setDescription(final String text) {
		this.description.setText(text);
	}

	public void setStatus(final String text) {
		this.status.setText(text);
	}

	public void setVersion(final String text) {
		this.version.setText(text);
	}

	public void setAuthor(final String text) {
		this.author.setText(text);
	}

	public void setAuthor(final List<String> text) {
		setDependencies(StringUtils.join(text, "\n"));
	}

	public void setContact(final String text) {
		this.contact.setText(text);
	}

	public void setHomePage(final String text) {
		this.homepage.setText(text);
	}

	public void setFactorioVersion(final String text) {
		this.factorio_version.setText(text);
	}

	public void setDependencies(final String text) {
		this.dependencies.setText(text);
	}

	public void setDependencies(final List<String> text) {
		setDependencies(StringUtils.join(text, "\n"));
	}

	public void setInfo(final IInfo info) {
		setName(info.getName());
		setDescription(info.getDescription());
		setVersion(info.getVersion());
		setAuthor(info.getAuthor());
		setContact(info.getContact());
		setHomePage(info.getHomePage());
		setFactorioVersion(info.getFactorioVersion());
		setDependencies(info.getDependencies());
	}
}
