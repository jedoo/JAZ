package j.services;

import j.project.*;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonDBManager {
	
	List<Integer> listID = new ArrayList<Integer>();

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addPersonStmt;
	private PreparedStatement getPersonStmt;
	private PreparedStatement deleteAllPersonStmt;
	private PreparedStatement deletePersonStmt;
	private PreparedStatement findPersonStmt;

	public PersonDBManager() 
	{
		try 
		{
		
			conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb");

			stmt = conn.createStatement();
			boolean personTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while (rs.next()) 
			{
				if ("Person".equalsIgnoreCase(rs.getString("TABLE_NAME"))) 
				{
					personTableExists = true;
					break;
				}
			}

			if (!personTableExists) 
			{
				stmt.executeUpdate("CREATE TABLE person(id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,name varchar(20), dateOfBirth date)");
			}

			addPersonStmt = conn.prepareStatement("INSERT INTO person (name, dateOfBirth) VALUES (?, ?)");

			getPersonStmt = conn.prepareStatement("SELECT * FROM person");
			
			deleteAllPersonStmt = conn.prepareStatement("DELETE FROM person");
			
			findPersonStmt = conn.prepareStatement("SELECT id FROM person WHERE name = ?");
			
			deletePersonStmt = conn.prepareStatement("DELETE FROM person WHERE id = ?");

		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}
	}

	public void addPerson(Person person) 
	{
		try 
		{
			java.sql.Date dt = new java.sql.Date(person.getDateOfBirth().getTime());
			addPersonStmt.setString(1, person.getName());
			addPersonStmt.setDate(2, dt);
			addPersonStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

	}

	public List<Person> getAllPersons() 
	{
		List<Person> persons = new ArrayList<Person>();

		try 
		{
			ResultSet rs = getPersonStmt.executeQuery();

			while (rs.next()) 
			{
				persons.add(new Person(rs.getString("name"),rs.getDate("dateOfBirth")));
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return persons;
	}
	
	public void deleteAllPerson() 
	{
		try 
		{
			deleteAllPersonStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<Integer> findPersonByName(String name)
	{
		try 
		{
			List<Integer> result = new ArrayList<Integer>();
			findPersonStmt.setString(1, name);
			ResultSet rs = findPersonStmt.executeQuery();
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
	
	public void deletePerson(List<Integer> list)
	{
		try 
		{
			for (Integer id : list)
			{
				deletePersonStmt.setInt(1, id);
				deletePersonStmt.executeUpdate();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

}
