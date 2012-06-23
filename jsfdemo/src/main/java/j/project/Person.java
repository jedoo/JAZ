package j.project;

import java.util.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Person {

	String name;
	List<Book> listOfBook = new ArrayList<Book>();
	Date dateOfBirth = new Date();

	public Person(String name, Date dateOfBirth) {
		this.name = name;
		this.listOfBook = new ArrayList<Book>();
		this.dateOfBirth = dateOfBirth;
	}
	
	@Size(min = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getListOfBook() {
		return listOfBook;
	}

	public void setListOfBook(List<Book> listOfBook) {
		this.listOfBook = listOfBook;
	}

	@Past
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	


}
