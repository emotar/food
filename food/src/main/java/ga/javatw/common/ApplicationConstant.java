package ga.javatw.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("appstting")
public class ApplicationConstant {
	private String filesavepath;

	public String getFilesavepath() {
		return filesavepath;
	}

	public void setFilesavepath(String filesavepath) {
		this.filesavepath = filesavepath;
	}


}
