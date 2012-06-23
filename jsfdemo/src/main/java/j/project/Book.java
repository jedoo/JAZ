package j.project;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {

	public String name;
	public BookType bookType;
	public String author;
	public int releaseYear;
	public int price;
	public boolean cleanBox;
	public boolean backup;
	public String bookBoxColor;
	
	public String bookTypeString;

	public Book(String name, BookType bookType, String author, int releaseYear, int price) {
		this.name = name;
		this.bookType = bookType;
		this.author = author;
		this.releaseYear = releaseYear;
		this.price = price;
		this.cleanBox = true;
		this.backup = false;
		this.bookBoxColor = "white";

		this.bookTypeString = bookTypeString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
	
	public String getAuthor() {
		return name;
	}

	public void setAuthor(String name) {
		this.name = name;
	}

	@Min(1000)
	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	@Min(0)
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isCleanBox() {
		return cleanBox;
	}

	public void setCleanBox(boolean cleanBox) {
		this.cleanBox = cleanBox;
	}

	public boolean isBackup() {
		return backup;
	}

	public void setBackup(boolean backup) {
		this.backup = backup;
	}

	public String getBookBoxColor() {
		return bookBoxColor;
	}

	public void setBookBoxColor(String bookBoxColor) {
		this.bookBoxColor = bookBoxColor;
	}

	public String getBookTypeString() {
		return bookTypeString;
	}

	public void setBookTypeString(String bookTypeString) {
		this.bookTypeString = bookTypeString;
	}

}
