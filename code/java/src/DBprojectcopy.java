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
import javax.swing.JScrollPane;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */

public class DBproject{
	//reference to physical database connection
    static JFrame frame = new JFrame("Airline Management System");
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static JTextField userText = new JTextField(20);
	//------------------------------------------------
	//GUI
	//------------------------------------------------
	static JPanel Tech_Panel = new JPanel();
    static JLabel Label_1 = new JLabel("");
    static JTextField Text_1 = new JTextField(15);
    static JButton Enter_button = new JButton("Enter");
    static JLabel Label_2 = new JLabel("");
    static JTextField Text_2=new JTextField(15);
    static JLabel Label_3 = new JLabel("");
    static JTextField Text_3=new JTextField(15);
    static JLabel Label_4 = new JLabel("");
    static JTextField Text_4=new JTextField(15);
    static JLabel Label_5 = new JLabel("");
    static JTextField Text_5=new JTextField(15);
    static JLabel Label_6 = new JLabel("");
    static JTextField Text_6=new JTextField(15);
    static JLabel Label_7 = new JLabel("");
    static JTextField Text_7=new JTextField(15);
    static JLabel Label_8 = new JLabel("");
    static JTextField Text_8=new JTextField(15);
    static JLabel Label_9 = new JLabel("");
    static JTextField Text_9=new JTextField(15);
    static JLabel Label_10 = new JLabel("");
    static JTextField Text_10=new JTextField(15);
    static JTextArea Output_Label = new JTextArea(30,30);
    
    static JPanel Date_Panel = new JPanel();
    static JTextField year_Text = new JTextField(4);
    static JTextField month_Text = new JTextField(2);
    static JTextField day_Text = new JTextField(2);
    static JPanel Date_Panel2 = new JPanel();
    static JTextField year_Text2 = new JTextField(4);
    static JTextField month_Text2 = new JTextField(2);
    static JTextField day_Text2 = new JTextField(2);
    //---------------------------------------
    //GUI
    //----------------------------------------
	static DBproject esql = null;
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
		int success = stmt.executeUpdate(sql);
		// close the instruction
	    stmt.close ();
	    
		if(success >= 0 )
		{
			Output_Label.setText("Success!");
		}
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
		String s = "";
		//iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					s += rsmd.getColumnName(i) + "\t";
			    }
			    s += "\n";
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i)
				s += rs.getString (i) + "\t";
			s += "\n";
			++rowCount;
		}//end while
		stmt.close ();
		Output_Label.setText(Output_Label.getText()+s);
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
	       
			if (args.length != 3) {
				System.err.println (
					"Usage: " + "java [-classpath <classpath>] " + DBproject.class.getName () +
			            " <dbname> <port> <user>");
				return;
			}//end if
		
			
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
	//---------------GUI---------------------
        Date_Panel.setLayout(new FlowLayout());
        Date_Panel.add(new JLabel("Day: "));
        Date_Panel.add(day_Text);
        Date_Panel.add(new JLabel("Month: "));
        Date_Panel.add(month_Text);
        Date_Panel.add(new JLabel("Year: "));
        Date_Panel.add(year_Text);
        Date_Panel2.setLayout(new FlowLayout());
        Date_Panel2.add(new JLabel("Day: "));
        Date_Panel2.add(day_Text2);
        Date_Panel2.add(new JLabel("Month: "));
        Date_Panel2.add(month_Text2);
        Date_Panel2.add(new JLabel("Year: "));
        Date_Panel2.add(year_Text2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,900);
        Output_Label.setEditable(false);
        JPanel Manu_panel = new JPanel();
        
        JLabel Manu_label = new JLabel("Please select the option you want by clicking the button below: ");
        JButton AddPlane_button = new JButton("Add Plane");
        JButton AddFlight_button = new JButton("Add Flight");
        JButton AddPilot_button = new JButton("Add Pilot");
        JButton AddTechnician_button = new JButton("Add Technician");
        JButton BookFlight_button = new JButton("Book Flight");
        JButton ListNumberOfAvailableSeats_button = new JButton("ListNumberOfAvailableSeats");
        JButton ListsTotalNumberOfRepairsPerPlane_button = new JButton("ListsTotalNumberOfRepairsPerPlane");
        JButton ListTotalNumberOfRepairsPerYear_button = new JButton("ListTotalNumberOfRepairsPerYear");
        JButton FindPassengersCountWithStatus_button = new JButton("FindPassengersCountWithStatus");

        //For ADDTECHNICIAN
        //Tech_Panel.setBorder(BorderFactory.createEmptyBorder(0, 150,150, 150));
        Tech_Panel.setBounds(200,200,200,200);
        Tech_Panel.setVisible(false);
        AddTechnician_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the First name");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the Last name");
                Tech_Panel.add(Text_2);
                Enter_button.removeAll();
                
                AddTechnician(esql);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        AddPlane_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the make");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the model");
                Tech_Panel.add(Text_2);
                Tech_Panel.add(Label_3);
                Label_3.setText("Please enter the age");
                Tech_Panel.add(Text_3);

                Tech_Panel.add(Label_4);
                Label_4.setText("Please enter the seats");
                Tech_Panel.add(Text_4);
                Enter_button.removeAll();
                AddPlane(esql);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        AddFlight_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the cost");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the num sold");
                Tech_Panel.add(Text_2);
                Tech_Panel.add(Label_3);
                Label_3.setText("Please enter the num stop");
                Tech_Panel.add(Text_3);
                
                Tech_Panel.add(Label_5);
                Label_5.setText("Please enter the actual departure date");
                Tech_Panel.add(Date_Panel);
                Tech_Panel.add(Label_6);
                Label_6.setText("Please enter the actual arrival date");
                Tech_Panel.add(Date_Panel2);
                Tech_Panel.add(Label_7);
                Label_7.setText("Please enter the arrival airport");
                Tech_Panel.add(Text_7);
                Tech_Panel.add(Label_8);
                Label_8.setText("Please enter the departure airport");
                Tech_Panel.add(Text_8);
                Tech_Panel.add(Label_9);
                Label_9.setText("Please enter the pilot id");
                Tech_Panel.add(Text_9);
                Tech_Panel.add(Label_10);
                Label_10.setText("Please enter the plane id");
                Tech_Panel.add(Text_10);
                Enter_button.removeAll();
                AddFlight(esql);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        AddPilot_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the First name");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the Last name");
                Tech_Panel.add(Text_2);
                Tech_Panel.add(Label_3);
                Label_3.setText("Please enter the nationality");
                Tech_Panel.add(Text_3);
                Enter_button.removeAll();
                AddPilot(esql);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });

        BookFlight_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.removeAll();
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the customer id");
                Tech_Panel.add(Text_1);
                Tech_Panel.add(Label_2);
                Label_2.setText("Please enter the flight id");
                Tech_Panel.add(Text_2);
                Enter_button.removeAll();
                BookFlight(esql);
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        ListNumberOfAvailableSeats_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
            	
                Tech_Panel.setVisible(false); 
                Output_Label.setText("");
                Tech_Panel.removeAll();
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Tech_Panel.add(Label_1);
                Label_1.setText("Please enter the flight number: ");
                Tech_Panel.add(Text_1);
                Text_1.setText("");
                Enter_button.removeAll();
                ListNumberOfAvailableSeats(esql); 
                Tech_Panel.add(Enter_button);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        ListsTotalNumberOfRepairsPerPlane_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  

                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                
                Tech_Panel.removeAll();
                Enter_button.removeAll();
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                ListsTotalNumberOfRepairsPerPlane(esql);
                JScrollPane scroll2 = new JScrollPane(Output_Label);
                Tech_Panel.add(scroll2);
                Tech_Panel.setVisible(true);
            }  
        });
        FindPassengersCountWithStatus_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
            	
                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                Tech_Panel.removeAll();
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
                Enter_button.removeAll();
                FindPassengersCountWithStatus(esql);
                Tech_Panel.add(Output_Label);
                Tech_Panel.setVisible(true);
            }  
        });
        ListTotalNumberOfRepairsPerYear_button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                 
                Tech_Panel.setVisible(false);
                Output_Label.setText("");
                Tech_Panel.removeAll();
                Tech_Panel.setLayout(new BoxLayout(Tech_Panel,BoxLayout.Y_AXIS));
             
                Tech_Panel.add(Output_Label);
              
                Enter_button.removeAll();
                ListTotalNumberOfRepairsPerYear(esql);
                Tech_Panel.setVisible(true);
            }  
        });
        Manu_panel.add(AddPlane_button);
        Manu_panel.add(AddFlight_button);
        Manu_panel.add(AddPilot_button);
        Manu_panel.add(AddTechnician_button);
        Manu_panel.add(BookFlight_button);
        Manu_panel.add(ListNumberOfAvailableSeats_button);
        Manu_panel.add(ListsTotalNumberOfRepairsPerPlane_button);
        Manu_panel.add(ListTotalNumberOfRepairsPerYear_button);
        Manu_panel.add(FindPassengersCountWithStatus_button);
        frame.getContentPane().add(BorderLayout.NORTH, Manu_label);
        frame.getContentPane().add(BorderLayout.CENTER,Manu_panel);
        //frame.getContentPane().add(BorderLayout.SOUTH,Tech_Panel);
        Manu_panel.add(Tech_Panel);
        frame.setVisible(true);
       //------------------GUI---------------------------------------
			while(true){}
			//String create_seq_1 = "CREATE SEQUENCE plane_id_seq  START WITH 67";
			//int rowCount = esql.executeQuery(create_seq_1);
/*
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
			}*/
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

	public static void AddPlane(DBproject esql) {//1_good.
      	 Text_1.setText("");
      	 Text_2.setText("");
      	 Text_3.setText("");
      	 Text_4.setText("");
        Enter_button.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){ 
            	try{
            		Output_Label.setText("");
        			//System.out.print("\tEnter make: $");			
        			//String make = in.readLine();
        			String make = Text_1.getText(); 
        			//System.out.print("\tEnter model: $");			
        			//String model = in.readLine();
        			String model = Text_2.getText();
        			//System.out.print("\tEnter age: $");			
        			//String age = in.readLine();
        			String age = Text_3.getText();
        			//System.out.print("\tEnter seats: $");			
        			//String seats = in.readLine();
        			String seats = Text_4.getText();
        			
        			
        			String query;
        			query = "SELECT setval('plane_id_seq', (SELECT MAX(id) FROM Plane));";
        			Output_Label.setText(esql.executeQueryAndReturnResult(query).get(0).get(0));

        			query = String.format("INSERT INTO Plane VALUES (nextval('plane_id_seq'), '%s', '%s', %s, '%s');", make, model, age, seats);
        			
        			esql.executeUpdate(query);
        			
        		}catch(Exception q){
        			System.err.println(q.getMessage());
        		}
            	 Text_1.setText("");
            	 Text_2.setText("");
            	 Text_3.setText("");
            	 Text_4.setText("");
            	 }});
	}

	public static void AddPilot(DBproject esql) {//2_good
      	 Text_1.setText("");
      	 Text_2.setText("");
      	 Text_3.setText("");
        Enter_button.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent q){ 
            	try{
                	Output_Label.setText("");		
        			String first = Text_1.getText();		
        			String last = Text_2.getText();		
        			String nationality = Text_3.getText();
        			String full = first +" "+ last;
        			String query;
        			query = "SELECT setval('pilot_id_seq', (SELECT MAX(id) FROM Pilot));";
        			String pilotid = esql.executeQueryAndReturnResult(query).get(0).get(0);
        			pilotid = "Pilot id: "+pilotid;
        			query = String.format("INSERT INTO Pilot (id, fullname, nationality ) VALUES (nextval('pilot_id_seq'), '%s', '%s');",  full, nationality);

        			
        			esql.executeUpdate(query);
        			 
        		  	Output_Label.setText(pilotid);
        		}catch(Exception e){
        			System.err.println(e.getMessage());
        		}
            	 Text_1.setText("");
            	 Text_2.setText("");
            	 Text_3.setText("");
            	 }});
	
	}

	public static void AddFlight(DBproject esql) {//3
     	 Text_1.setText("");
       	 Text_2.setText("");
       	 Text_3.setText("");
       	 Text_4.setText("");
       	 day_Text.setText("");
       	 day_Text2.setText("");
       	 month_Text.setText("");
       	 month_Text2.setText("");
       	 year_Text.setText("");
       	 year_Text2.setText("");
       	 Text_7.setText("");
       	 Text_8.setText("");
       	 Text_9.setText("");
       	 Text_10.setText("");
		// Given a pilot, plane and flight, adds a flight in the DB
        Enter_button.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent q){ 
            	Output_Label.setText("");
            	try{

        			String cost = Text_1.getText();
        			String num_sold = Text_2.getText();
        			String num_stops = Text_3.getText();

                    String actual_depart_date = day_Text.getText()+ "-"+month_Text.getText()+"-"+year_Text.getText();

                    String actual_arrival_date = day_Text2.getText()+ "-"+month_Text2.getText()+"-"+year_Text2.getText();

                    String arrival_airport = Text_7.getText();

                    String departure_airport = Text_8.getText();
			
        			String pilotid = Text_9.getText();
        			
        			String plane = Text_10.getText();
        			
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
            	 Text_1.setText("");
            	 Text_2.setText("");
            	 Text_3.setText("");
            	 Text_4.setText("");
            	 day_Text.setText("");
            	 day_Text2.setText("");
            	 month_Text.setText("");
            	 month_Text2.setText("");
            	 year_Text.setText("");
            	 year_Text2.setText("");
            	 Text_7.setText("");
            	 Text_8.setText("");
            	 Text_9.setText("");
            	 Text_10.setText("");
            	 }});
	}

	public static void AddTechnician(DBproject esql) {//4_good
      	 Text_1.setText("");
      	 Text_2.setText("");
        Enter_button.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent q){ 

            	Output_Label.setText("");
        		try{		
        			String first = Text_1.getText();	
        			String last = Text_2.getText();
        			String full = first +" "+last;
        			String query;
        			query = "SELECT setval('tech_id_seq', (SELECT MAX(id) FROM Technician));";
                                String techid = esql.executeQueryAndReturnResult(query).get(0).get(0);

        			query = String.format("INSERT INTO Technician (id, full_name) VALUES (nextval('tech_id_seq'), '%s');",  full);
        			
        			esql.executeUpdate(query);
        			Output_Label.setText(Output_Label.getText()+"\n"+ "Tech id:"+ techid);
        		}catch(Exception e){
        			System.err.println(e.getMessage());
        		}
            	 Text_1.setText("");
            	 Text_2.setText("");

            	 }});
	}

	public static void BookFlight(DBproject esql) {//5_good
		// Given a customer and a flight that he/she wants to book, add a reservation to the DB
   	 Text_1.setText("");
   	 Text_2.setText("");
        Enter_button.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent q){ 
            	Output_Label.setText("");
            	try{
        			String cid = Text_1.getText();
        		
        			String fid = Text_2.getText();

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
        			}
        			Output_Label.setText(Output_Label.getText()+"\n"+ "Rnum: "+ esql.getCurrSeqVal("rnum_seq"));
        		}catch(Exception e){
        			System.err.println(e.getMessage());
        		}
            	 Text_1.setText("");
            	 Text_2.setText("");
            	 }});
		
	}

	public static void ListNumberOfAvailableSeats(DBproject esql) {//6
		Text_1.setText("");
		// For flight number and date, find the number of availalbe seats (i.e. total plane capacity minus booked seats )
		Enter_button.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){ 
            	Output_Label.setText("");
            		try{
            			String fid = Text_1.getText();
            			
            			String query = String.format("SELECT (SELECT P.seats FROM Plane P WHERE P.id IN (SELECT F.plane_id FROM FlightInfo F WHERE F.flight_id =%s)) - (SELECT F1.num_sold FROM Flight F1 WHERE fnum =  %s) AS \"Availalbe seats\";", fid, fid);
            			System.out.print(query);
            			int rowCount = esql.executeQueryAndPrintResult(query);
            			//Output_Label.setText("total tow(s): " + rowCount);
            		}catch(Exception q){
            			System.err.println(q.getMessage());
            		}
            	 Text_1.setText("");
            	 }});
	}

	public static void ListsTotalNumberOfRepairsPerPlane(DBproject esql) {//7
		// Count number of repairs per planes and list them in descending order
            	Output_Label.setText("");
        		try{
                    String query = "SELECT R.plane_id, COUNT(R.rid) FROM Repairs R GROUP BY R.plane_id ORDER BY Count(R.rid) DESC;";

                    int rowCount = esql.executeQueryAndPrintResult(query);
                    System.out.println("total tow(s): " + rowCount);
            }catch(Exception e){
                    System.err.println(e.getMessage());
            }
	}

	public static void ListTotalNumberOfRepairsPerYear(DBproject esql) {//8
		// Count repairs per year and list them in ascending order
		 Text_1.setText("");
            	Output_Label.setText("");
        		try{
        			String query = String.format("SELECT  date_part('year', r.repair_date) AS \"year\", COUNT(r.rid) AS \"Repair times\" FROM Repairs r GROUP BY date_part('year', r.repair_date) ORDER BY Count(r.rid);");
        			esql.executeQueryAndPrintResult(query);
        		}catch(Exception e){
        			System.err.println(e.getMessage());
        		}
	}
	
	public static void FindPassengersCountWithStatus(DBproject esql) {//9_good
		// Find how many passengers there are with a status (i.e. W,C,R) and list that number.
		 Text_1.setText("");
            	Output_Label.setText("");
                JButton W_button = new JButton("W");
                JButton C_button = new JButton("C");
                JButton R_button = new JButton("R");
                Label_1.setText("Please enter the flight number:");
                Tech_Panel.add(Label_1);
                Tech_Panel.add(Text_1);
                Text_1.setText("");
                Tech_Panel.add( W_button);
                Tech_Panel.add(C_button);
                Tech_Panel.add(R_button);
                W_button.addActionListener(new ActionListener(){ 
                    public void actionPerformed(ActionEvent q){ 
                    	try{
                    	Output_Label.setText("");
                    	if(Text_1.getText() == "")
                    		Output_Label.setText("Please enter the flight number first");
                    	else{
        				String statusw = String.format("SELECT COUNT(r.rnum) FROM Reservation r WHERE r.status = 'W' AND r.fid = %s;", Text_1.getText());
        				
        				Output_Label.setText("Waitinglist: \n");
        				esql.executeQueryAndPrintResult(statusw);}
                    	}catch(Exception e){
                			System.err.println(e.getMessage());}
                		
                    	 }});
                C_button.addActionListener(new ActionListener(){ 
                    public void actionPerformed(ActionEvent q){ 
                       	try{

                        	Output_Label.setText("");
                        	if(Text_1.getText() == "")
                        		Output_Label.setText("Please enter the flight number first");
                        	else{
            				String statusw = String.format("SELECT COUNT(r.rnum) FROM Reservation r WHERE r.status = 'C' AND r.fid = %s;", Text_1.getText());
            				Output_Label.setText("Conformed: \n");
            				esql.executeQueryAndPrintResult(statusw);}
                        	 }catch(Exception e){
                    			System.err.println(e.getMessage());}
                    	 }});
                R_button.addActionListener(new ActionListener(){ 
                    public void actionPerformed(ActionEvent q){ 
                       	try{

                        	Output_Label.setText("");
                        	if(Text_1.getText() == "")
                        		Output_Label.setText("Please enter the flight number first");
                        	else{
            				String statusw = String.format("SELECT COUNT(r.rnum) FROM Reservation r WHERE r.status = 'R' AND r.fid = %s;", Text_1.getText());
            				Output_Label.setText("Regested: \n");
            				esql.executeQueryAndPrintResult(statusw);}
                        	}catch(Exception e){
                    			System.err.println(e.getMessage());}
                    	 }});
                Tech_Panel.add(Output_Label);
            

	}
}
