package com.soecode.lyf.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.soecode.lyf.dto.AppointExecution;
import com.soecode.lyf.dto.Result;
import com.soecode.lyf.entity.Book;
import com.soecode.lyf.enums.AppointStateEnum;
import com.soecode.lyf.exception.NoNumberException;
import com.soecode.lyf.exception.RepeatAppointException;
import com.soecode.lyf.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	private Result<List<Book>> list() {
		List<Book> list = bookService.getList();
		return new Result<>(true, list);
	}

	/**
	 * @param bookId
	 * @return
	 */
	@GetMapping(value = "/{bookId}")
	private Result<Book> detail(@PathVariable("bookId") Long bookId) {
		if (bookId == null) {
			return new Result<>(false, "图书id不能为空");
		}
		Book book = bookService.getById(bookId);
		if (book == null) {
			return new Result<>(false, "书库中没有这本书");
		}
		return new Result<>(true, book);
	}

	/**
	 * @param bookId 要预约的图书id
	 * @param studentId 预约的学生id
	 * @return 返回结果对象
	 */

	@PostMapping(value = "/{bookId}")
	private Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId) {
		if (studentId == null || "".equals(studentId + "")) {
			return new Result<>(false, "学号不能为空");
		}
		AppointExecution execution = null;
		try {
			execution = bookService.appoint(bookId, studentId);
		} catch (NoNumberException e1) {
			execution = new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
		} catch (RepeatAppointException e2) {
			execution = new AppointExecution(bookId, AppointStateEnum.REPEAT_APPOINT);
		} catch (Exception e) {
			execution = new AppointExecution(bookId, AppointStateEnum.INNER_ERROR);
		}
		return new Result<>(true, execution);
	}

}
