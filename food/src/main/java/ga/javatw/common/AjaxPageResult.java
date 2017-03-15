package ga.javatw.common;

public class AjaxPageResult<T> extends AjaxResult<T> {
	private long currentPage;
	private long numberOfCurrentPage;
	private long totalPages;
	private long totalElements;


	public AjaxPageResult() {

	}

	public AjaxPageResult(String status, String message) {
		super(status, message);
	}


	public long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	public long getNumberOfCurrentPage() {
		return numberOfCurrentPage;
	}
	public void setNumberOfCurrentPage(long numberOfCurrentPage) {
		this.numberOfCurrentPage = numberOfCurrentPage;
	}
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
}
