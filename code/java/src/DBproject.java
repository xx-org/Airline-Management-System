/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */

public class DBproject{
	//reference to physical database connection
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static JTextField userText = new JTextField(20);
	
	public DBproject(String dbname, String dbport, String user, String passwd) throws SQLException {
		System.out.print("Connecting to database...");
		try{
			// constructs the connection URL
			String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
			System.out.println ("Connection URL: " + url + "\n");
			
			// obtain a physical connection
	        this._connection = DriverManager.getConnection(url, user, passwd);
	        System.out.println("Done");
		}catch(Exception e){
			System.err.println("Error - Unable to Connect to Database: " + e.getMessage());
	        System.out.println("Make sure you started postgres on this machine");
	        System.exit(-1);
		}
	}
	
	/**
	 * Method to execute an update SQL statement.  Update SQL instructions
	 * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
	 * 
	 * @param sql the input SQL string
	 * @throws java.sql.SQLException when update failed
	 * */
	public void executeUpdate (String sql) throws SQLException { 
		// creates a statement object
		Statement stmt = this._connection.createStatement ();

		// issues the update instruction
		stmt.executeUpdate (sql);

		// close the instruction
	    stmt.close ();
	}//end executeUpdate

	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and outputs the results to
	 * standard out.
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQueryAndPrintResult (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		/*
		 *  obtains the metadata object for the returned result set.  The metadata
		 *  contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCol = rsmd.getColumnCount ();
		int rowCount = 0;
		
		//iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t");
			    }
			    System.out.println();
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i)
				System.out.print (rs.getString (i) + "\t");
			System.out.println ();
			++rowCount;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the results as
	 * a list of records. Each record in turn is a list of attribute values
	 * 
	 * @param query the input query string
	 * @return the query result as a list of records
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
		//creates a statement object 
		Statement stmt = this._connection.createStatement (); 
		
		//issues the query instruction 
		ResultSet rs = stmt.executeQuery (query); 
	 
		/*
		 * obtains the metadata object for the returned result set.  The metadata 
		 * contains row and column info. 
		*/ 
		ResultSetMetaData rsmd = rs.getMetaData (); 
		int numCol = rsmd.getColumnCount (); 
		int rowCount = 0; 
	 
		//iterates through the result set and saves the data returned by the query. 
		boolean outputHeader = false;
		List<List<String>> result  = new ArrayList<List<String>>(); 
		while (rs.next()){
			List<String> record = new ArrayList<String>(); 
			for (int i=1; i<=numCol; ++i) 
				record.add(rs.getString (i)); 
			result.add(record); 
		}//end while 
		stmt.close (); 
		return result; 
	}//end executeQueryAndReturnResult
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the number of results
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQuery (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the result set and count nuber of results.
		if(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to fetch the last value from sequence. This
	 * method issues the query to the DBMS and returns the current 
	 * value of sequence used for autogenerated keys
	 * 
	 * @param sequence name of the DB sequence
	 * @return current value of a sequence
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	
	public int getCurrSeqVal(String sequence) throws SQLException {
		Statement stmt = this._connection.createStatement ();
	        
		ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
		if (rs.next()) return rs.getInt(1);
		return -1;
	}

	/**
	 * Method to close the physical connection if it is open.
	 */
	public void cleanup(){
		try{
			if (this._connection != null){
				this._connection.close ();
			}//end if
		}catch (SQLException e){
	         // ignored.
		}//end try
	}//end cleanup

	/**
	 * The main execution method
	 * 
	 * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
	 */
	public static void main (String[] args) {
	/*
	   JFrame frame = new JFrame("Airline DATABASE");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(600,400);
       JButton button = new JButton("Add Plane");
       button.setSize(50, 50);
       JButton button2 = new JButton("Add Pilot");
       button2.setSize(50, 50);
       JButton button3 = new JButton("Add Flight");
       button3.setSize(50, 50);
       JButton button4 = new JButton("Add Technician");
       button4.setSize(50, 50);
       JButton button5 = new JButton("Book Flight");
       button5.setSize(50, 50);
       JPanel panel = new JPanel();
       panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

       panel.setLayout(null);
       panel.add(button);
       panel.add(button2);
       panel.add(button3);
       panel.add(button4);
       panel.add(button5);
       //panel.add(userText);
       //frame.getContentPane().add(button); // Adds Button to content pane of frame

       frame.add(panel);

       frame.setVisible(true);
       *///GUI
       
		if (args.length != 3) {
			System.err.println (
				"Usage: " + "java [-classpath <classpath>] " + DBproject.class.getName () +
		            " <dbname> <port> <user>");
			return;
		}//end if
		
		DBproject esql = null;
		
		try{
			System.out.println("(1)");
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(Exception e){

				System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
				e.printStackTrace();
				return;
			}
			
			System.out.println("(2)");
			String dbname = args[0];
			String dbport = args[1];
			String user = args[2];
			
			esql = new DBproject (dbname, dbport, user, "");
			
			//String create_seq_1 = "CREATE SEQUENCE plane_id_seq  START WITH 67";
			//int rowCount = esql.executeQuery(create_seq_1);

			boolean keepon = true;
			while(keepon){
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add Plane");
				System.out.println("2. Add Pilot");
				System.out.println("3. Add Flight");
				System.out.println("4. Add Technician");
				System.out.println("5. Book Flight");
				System.out.println("6. List number of available seats for a given flight.");
				System.out.println("7. List total number of repairs per plane in descending order");
				System.out.println("8. List total number of repairs per year in ascending order");
				System.out.println("9. Find total number of passengers with a given status");
				System.out.println("10. < EXIT");
				
				switch (readChoice()){
					case 1: AddPlane(esql); break;
					case 2: AddPilot(esql); break;
					case 3: AddFlight(esql); break;
					case 4: AddTechnician(esql); break;
					case 5: BookFlight(esql); break;
					case 6: ListNumberOfAvailableSeats(esql); break;
					case 7: ListsTotalNumberOfRepairsPerPlane(esql); break;
					case 8: ListTotalNumberOfRepairsPerYear(esql); break;
					case 9: FindPassengersCountWithStatus(esql); break;
					case 10: keepon = false; break;
				}
			}
		}catch(Exception e){
			System.err.println (e.getMessage ());
		}finally{
			try{
				if(esql != null) {
					System.out.print("Disconnecting from database...");
					esql.cleanup ();
					System.out.println("Done\n\nBye !");
				}//end if				
			}catch(Exception e){
				// ignored.
			}
		}
	}

	public static int readChoice() {
		int input;
		// returns only if a correct value is given.
		do {
			System.out.print("Please make your choice: ");
			try { // read the integer, parse it and break.
				input = Integer.parseInt(in.readLine());
				break;
			}catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}//end try
		}while (true);
		return input;
	}//end readChoice

	public static void AddPlane(DBproject esql) {//1_good
		try{

			System.out.print("\tEnter make: $");			
			String make = in.readLine();
			System.out.print("\tEnter model: $");			
			String model = in.readLine();
			System.out.print("\tEnter age: $");			
			String age = in.readLine();
			System.out.print("\tEnter seats: $");			
			String seats = in.readLine();
			
			String query;
			query = "SELECT setval('plane_id_seq', (SELECT MAX(id) FROM Plane))";
			String planeid = esql.executeQueryAndReturnResult(query).get(0).get(0);

			query = String.format("INSERT INTO Plane VALUES (nextval('plane_id_seq'), '%s', '%s', %s, '%s');", make, model, age, seats);
			esql.executeUpdate(query);
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public static void AddPilot(DBproject esql) {//2_good
		try{

			System.out.print("\tEnter first name: $");			
			String first = in.readLine();
			System.out.print("\tEnter last name: $");			
			String last = in.readLine();
			System.out.print("\tEnter nationality: $");			
			String nationality = in.readLine();
			String full = first +" "+ last;
			String query;
			query = "SELECT setval('pilot_id_seq', (SELECT MAX(id) FROM Pilot))";
			String pilotid = esql.executeQueryAndReturnResult(query).get(0).get(0);
			
			query = String.format("INSERT INTO Pilot (id, fullname, nationality ) VALUES (nextval('pilot_id_seq'), '%s', '%s');",  full, nationality);
			esql.executeUpdate(query);
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public static void AddFlight(DBproject esql) {//3
		// Given a pilot, plane and flight, adds a flight in the DB
		try{
			System.out.print("\tEnter flight cost: $");
			String cost = in.readLine();
			System.out.print("\tEnter number of sold seats: $");
			String num_sold = in.readLine();
			System.out.print("\tEnter number of stops: $");
			String num_stops = in.readLine();
			System.out.print("\tEnter actual departure date: $");
                        String actual_depart_date = in.readLine();
			System.out.print("\tEnter actual arrival date: $");
                        String actual_arrival_date = in.readLine();
			System.out.print("\tEnter arrival airport: $");
                        String arrival_airport = in.readLine();
			System.out.print("\tEnter departure airport: $");
                        String departure_airport = in.readLine();

			System.out.print("\tEnter pilot id: $");			
			String pilotid = in.readLine();
			System.out.print("\tEnter plane id: $");			
			String plane = in.readLine();
			
			String query; 
			query = "SELECT setval('fnum_seq', (SELECT MAX(fnum) FROM Flight))";
			String flightid = esql.executeQueryAndReturnResult(query).get(0).get(0);
			query = "SELECT setval('fiid_seq', (SELECT MAX(fiid) FROM FlightInfo))";
			String flightinfoid = esql.executeQueryAndReturnResult(query).get(0).get(0);
			query = "SELECT setval('sched_id_seq', (SELECT MAX(id) FROM Schedule))";
			String schedid = esql.executeQueryAndReturnResult(query).get(0).get(0);

			query = String.format("INSERT INTO Flight VALUES (nextval('fnum_seq'),%s, %s, %s, '%s', '%s', '%s', '%s');", cost, num_sold, num_stops, actual_depart_date, actual_arrival_date, arrival_airport, departure_airport);
			esql.executeUpdate(query);
			
			query = String.format("INSERT INTO FlightInfo VALUES (nextval('fiid_seq'),currval('fnum_seq'), %s, %s);", pilotid, plane);
			esql.executeUpdate(query);

			query = String.format("INSERT INTO Schedule VALUES (nextval('sched_id_seq'),currval('fnum_seq'), '%s', '%s');", actual_depart_date, actual_arrival_date);
			esql.executeUpdate(query);
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public static void AddTechnician(DBproject esql) {//4_good
		try{
			System.out.print("\tEnter the first name: $");			
			String first = in.readLine();
			System.out.print("\tEnter the last name: $");			
			String last = in.readLine();
			String full = first +" "+last;
			String query;
			query = "SELECT setval('tech_id_seq', (SELECT MAX(id) FROM Technician))";
                        String techid = esql.executeQueryAndReturnResult(query).get(0).get(0);

			query = String.format("INSERT INTO Technician (id, full_name) VALUES (nextval('tech_id_seq'), '%s');",  full);
			
			esql.executeUpdate(query);
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public static void BookFlight(DBproject esql) {//5_good
		// Given a customer and a flight that he/she wants to book, add a reservation to the DB
		try{
			System.out.print("\tEnter the customer id: $");
			String cid = in.readLine();
			System.out.print("\tEnter the flight id: $");			
			String fid = in.readLine();

			String q2 = String.format("SELECT f.num_sold FROM Flight f, Plane p, FlightInfo fi WHERE %s = f.fnum AND p.id = fi.plane_id AND f.fnum = fi.flight_id AND f.num_sold < p.seats;", fid);
			
			List<List<String>> result = esql.executeQueryAndReturnResult(q2);
			char status;
			String query;
			query = "SELECT setval('rnum_seq', (SELECT MAX(rnum) FROM Reservation))";
			String rnumid = esql.executeQueryAndReturnResult(query).get(0).get(0);
			
			if(Integer.parseInt(result.get(0).get(0)) >= 0)
			{
				status = 'C';
				query = String.format("Update Flight SET num_sold = num_sold +1 WHERE fnum = %s;INSERT INTO Reservation VALUES (nextval('rnum_seq'), %s, %s, '%s');",fid, cid, fid, status);
				
				esql.executeUpdate(query);
			}else{
				status = 'W';
				String query2 = String.format("INSERT INTO Reservation VALUES (nextval('rnum_seq'), %s, %s, '%s');", cid, fid, status);
	                        esql.executeUpdate(query2);
        	                System.out.print("status:"+status);
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public static void ListNumberOfAvailableSeats(DBproject esql) {//6
		// For flight number and date, find the number of availalbe seats (i.e. total plane capacity minus booked seats )
		try{
			System.out.print("\tEnter the flight number: $");
			String fid = in.readLine();
			System.out.print("\tEnter the date: $");
			String date = in.readLine();
			
			String query = "SELECT (SELECT P.seats FROM Plane P WHERE P.id IN (SELECT F.plane_id FROM FlightInfo F WHERE F.flight_id = ";
			String query1 = ")) - (SELECT F1.num_sold FROM Flight F1 WHERE fnum = ";
			String query2 = " AND actual_departure_date = '";
			String query3 = "') AS \"Availalbe seats\"";
			query += fid;
			query += query1;
			query += fid;
			query += query2;
			query += date;
			query += query3;

			int rowCount = esql.executeQueryAndPrintResult(query);
			System.out.println("total tow(s): " + rowCount);
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public static void ListsTotalNumberOfRepairsPerPlane(DBproject esql) {//7
		// Count number of repairs per planes and list them in descending order
		try{
                        String query = "SELECT R.plane_id, COUNT(R.rid) FROM Repairs R GROUP BY R.plane_id ORDER BY Count(R.rid) DESC";

                        int rowCount = esql.executeQueryAndPrintResult(query);
                        System.out.println("total tow(s): " + rowCount);
                }catch(Exception e){
                        System.err.println(e.getMessage());
                }

	}

	public static void ListTotalNumberOfRepairsPerYear(DBproject esql) {//8
		// Count repairs per year and list them in ascending order
		
		try{
			String query = String.format("SELECT  date_part('year', r.repair_date) AS \"year\", COUNT(r.rid) AS \"Repair times\" FROM Repairs r GROUP BY date_part('year', r.repair_date)");
			esql.executeQueryAndPrintResult(query);
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	public static void FindPassengersCountWithStatus(DBproject esql) {//9_good
		// Find how many passengers there are with a status (i.e. W,C,R) and list that number.
		try{
			System.out.print("\tEnter the status (W, C, or R): ");
			String input = in.readLine();
			char status = input.charAt(0);
			if(status == 'W'){
				String statusw = String.format("SELECT COUNT(r.rnum) FROM Reservation r WHERE r.status = 'W';");
				System.out.println("Waitinglist: ");
				esql.executeQueryAndPrintResult(statusw);}
			else if(status == 'C'){
				String statusc = String.format("SELECT COUNT(r.rnum) FROM Reservation r WHERE r.status = 'C';");
				System.out.println("Conformed: " );
				esql.executeQueryAndPrintResult(statusc);}
			else if(status == 'R'){
				String statusr = String.format("SELECT COUNT(r.rnum) FROM Reservation r WHERE r.status = 'R';");
				System.out.println("Rejected: " );
				esql.executeQueryAndPrintResult(statusr);
			}else{
			System.out.println("Wrong input.");
			}
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
