package com.example.jdbcexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class JDBCExample {
	public static void main (String... args) {
		// 1. Carico il driver JAR
		// 2. Registro il driver con Java
		try {	Class.forName("com.mysql.cj.jdbc.Driver"); // sempre uguale
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // non ho aggiunto il jar
		}
		try {
			// 3. mi connetto al DB che sta qui ----------------------vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
			Connection mysqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:/sakila", "", ""); // analogo del workbench 
			// 4. apro un canale di comunicazione
			Statement statement = mysqlConnection.createStatement(); //analogo della pagina vuota dove scrivete il codice SQL in workbench			
			//4.5 insert values
			String name_c="Mary";
			String surname_c="Smith";
			String name_s="Mike";
			String surname_s="Hillyer";
			int store_id = 1;
			String film="Academy Dinosaur";
			//select per 
			statement.executeUpdate("select customer_id from customer where first_name='"+name_c+"'\r\n"
					+ "and last_name='"+surname_c+"'\r\n");
			ResultSet id_c_set = statement.executeQuery("SELECT * FROM tmp_customer_id");
				id_c_set.next();
				int id_c = id_c_set.getInt("customer_id");
			
			statement.executeUpdate("create temporary table tmp_staff_id\r\n"
					+ "select staff_id from staff where first_name='"+name_s+"'\r\n"
					+ "and last_name='"+surname_s+"'\r\n");
			ResultSet id_s_set = statement.executeQuery("SELECT * FROM tmp_staff_id");
				id_s_set.next();
				int id_s = id_s_set.getInt("staff_id");
			
			statement.executeUpdate("create temporary table tmp_store\r\n"
					+ "select inventory.inventory_id from inventory join film on inventory.film_id = film.film_id \r\n"
					+ "where inventory.store_id='"+store_id+"' and film.title='"+film+"';");
			ResultSet available_set = statement.executeQuery("SELECT * FROM tmp_store");
//			while(available_set.next()) { //col while li stampa tutti e 4 gli inventory, 1 2 3 4
				available_set.next();
				int inv_id = available_set.getInt("inventory_id");		//}
			statement.executeUpdate("insert into rental(rental_date,inventory_id,customer_id, staff_id) values ("+"NOW(),"+id_c+","+id_s+","+inv_id+");");					
		} catch (SQLException e) {
			// errore di MySQL
			e.printStackTrace();
		}
	}

}
