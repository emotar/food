package ga.javatw.common.utils;

import org.springframework.data.domain.Page;

import ga.javatw.common.AjaxPageResult;


public class AjaxPageResultUtil {
	public static <T> AjaxPageResult<T> transform(Page<T> page) {

		AjaxPageResult<T> result = new AjaxPageResult<T>();
		result.setStatus("ok");
		result.setMessage("");
		result.setData(page.getContent());
		result.setTotalPages(page.getTotalPages());
		result.setTotalElements(page.getTotalElements());
		result.setNumberOfCurrentPage(page.getNumberOfElements());
		result.setCurrentPage(page.getNumber() + 1);

		return result;
	}
}
