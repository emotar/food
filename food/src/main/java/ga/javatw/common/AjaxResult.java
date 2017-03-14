package ga.javatw.common;

import java.util.Arrays;
import java.util.List;

public class AjaxResult<T> {
	private String status;
	private List<T> data;
	private String message;


	public AjaxResult() {

	}

	public AjaxResult(List<T> data) {
		this.data = data;
		this.status = "ok";
		this.message = "";
	}

	public AjaxResult(T item) {
		this(Arrays.asList(item));
	}

	public AjaxResult(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public AjaxResult(String status, List<T> data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}





	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
