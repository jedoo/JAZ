package j.web;

import j.project.*;
import j.services.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;



@SessionScoped
@Named("bookBean")
public class BookFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Book book = new Book(null, BookType.Biography, null, 0, 0);

	private ListDataModel<Book> books = new ListDataModel<Book>();

	@Inject
	private BookDBManager bookDBManager;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public String addBook() {
		book.setBookType(BookType.valueOf(book.getBookTypeString()));
		bookDBManager.addBook(book);
		return "showBooks";
	}
		
	public ListDataModel<Book> getAllBooks() {
		books.setWrappedData(bookDBManager.getAllBooks());
		return books;
	}


	public void deleteBook() {
		Book bookToDelete = books.getRowData();
		bookDBManager.deleteBook(bookDBManager.findBookByName(bookToDelete.getName()));
	}

}
