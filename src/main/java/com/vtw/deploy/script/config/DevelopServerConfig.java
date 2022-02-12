

package com.vtw.deploy.script.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="develop-server")
public class DevelopServerConfig {
	
	private String keyPath;
	private String deployedSources;
	private String xmlSources;
	
	public String getKeyPath() {
		return keyPath;
	}
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}
	public String getDeployedSources() {
		return deployedSources;
	}
	public void setDeployedSources(String deployedSources) {
		this.deployedSources = deployedSources;
	}
	public String getXmlSources() {
		return xmlSources;
	}
	public void setXmlSources(String xmlSources) {
		this.xmlSources = xmlSources;
	}
	
}
