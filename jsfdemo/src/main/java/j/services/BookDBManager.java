package j.services;

import j.project.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
public class BookDBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addBookStmt;
	private PreparedStatement getBookStmt;
	private PreparedStatement deleteAllBookStmt;
	private PreparedStatement findBookByNameStmt;
	private PreparedStatement findBookByTypeStmt;
	private PreparedStatement deleteBookStmt;
	
	List<Integer> listID = new ArrayList<Integer>();
	
	public BookDBManager() 
	{
		try 
		{
			
			conn = DriverManager
					.getConnection("jdbc:hsqldb:hsql://localhost/workdb");

			stmt = conn.createStatement();
			boolean BookTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while (rs.next()) 
			{
				if ("Book".equalsIgnoreCase(rs.getString("TABLE_NAME"))) 
				{
					BookTableExists = true;
					break;
				}
			}

			if (!BookTableExists) 
			{
				stmt.executeUpdate("CREATE TABLE Book(id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,name varchar(40), bookType varchar(20), author varchar(40), releaseYear integer, price integer)");
			}


			addBookStmt = conn.prepareStatement("INSERT INTO Book (name, bookType, author, releaseYear, price) VALUES (?, ?, ?, ?, ?)");

			getBookStmt = conn.prepareStatement("SELECT * FROM Book");
			
			deleteAllBookStmt = conn.prepareStatement("DELETE FROM Book");
			
			findBookByNameStmt = conn.prepareStatement("SELECT id FROM book WHERE name = ?");
			
			findBookByTypeStmt = conn.prepareStatement("SELECT id FROM book WHERE bookType = ?");
			
			deleteBookStmt = conn.prepareStatement("DELETE FROM book WHERE id = ?");
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}
	}

	public void addBook(Book Book) 
	{
		try 
		{
			addBookStmt.setString(1, Book.getName());
			addBookStmt.setString(2, Book.getBookType().toString());
			addBookStmt.setString(3, Book.getAuthor());
			addBookStmt.setInt(4, Book.getReleaseYear());
			addBookStmt.setInt(5, Book.getPrice());
			addBookStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

	}

	public List<Book> getAllBooks() 
	{
		List<Book> Books = new ArrayList<Book>();
		try 
		{
			ResultSet rs = getBookStmt.executeQuery();
			while (rs.next()) 
			{
				BookType bookType = null;
				if (rs.getString("bookType").equals("Fantasy"))
					bookType = BookType.Fantasy;
				if (rs.getString("bookType").equals("History"))
					bookType = BookType.History;
				if (rs.getString("bookType").equals("Criminal"))
					bookType = BookType.Criminal;
				if (rs.getString("bookType").equals("Thriller"))
					bookType = BookType.Thriller;
				if (rs.getString("bookType").equals("Biography"))
					bookType = BookType.Biography;
				if (rs.getString("bookType").equals("Programing"))
					bookType = BookType.Programing;
				if (rs.getString("bookType").equals("Science"))
					bookType = BookType.Science;
				
				Books.add(new Book(rs.getString("name"),bookType,rs.getString("author"),rs.getInt("releaseYear"),rs.getInt("price")));
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return Books;
	}

	public void deleteAllBook() 
	{
		try 
		{
			deleteAllBookStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<Integer> findBookByName(String name)
	{
		try 
		{
			List<Integer> result = new ArrayList<Integer>();
			findBookByNameStmt.setString(1, name);
			ResultSet rs = findBookByNameStmt.executeQuery();
			while (rs.next())
				result.add(rs.getInt("ID"));	
			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Integer> findBookByType(BookType bookType)
	{
		try 
		{
			List<Integer> result = new ArrayList<Integer>();
			findBookByTypeStmt.setString(1, bookType.toString());
			ResultSet rs = findBookByTypeStmt.executeQuery();
			while (rs.next())
				result.add(rs.getInt("ID"));
			return result;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteBook(List<Integer> list)
	{
		try 
		{
			for (Integer id : list)
			{
				deleteBookStmt.setInt(1, id);
				deleteBookStmt.executeUpdate();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void printBookWithCondition(List<Book> listBook,Condition condition)
	{
		for (Book book : listBook)
		{
			if (condition.getCondition(book))
			{
				System.out.println("Name: " + book.getName() + "\tBookType: " + book.getBookType() + "\tAuthor: " + book.getAuthor() + "\tReleasedYear: " + book.getReleaseYear() + "\tPrice: " + book.getPrice());
			}
		}
	}
	


}
