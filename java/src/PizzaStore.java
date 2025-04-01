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
import java.lang.Math;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class PizzaStore {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of PizzaStore
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public PizzaStore(String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end PizzaStore

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
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
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
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
      stmt.close();
      return rowCount;
   }//end executeQuery

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
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and saves the data returned by the query.
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
       // creates a statement object
       Statement stmt = this._connection.createStatement ();

       // issues the query instruction
       ResultSet rs = stmt.executeQuery (query);

       int rowCount = 0;

       // iterates through the result set and count nuber of results.
       while (rs.next()){
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
	if (rs.next())
		return rs.getInt(1);
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
            "Usage: " +
            "java [-classpath <classpath>] " +
            PizzaStore.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if

      Greeting();
      PizzaStore esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the PizzaStore object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new PizzaStore (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statement
 
            System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("1. Create user");
            System.out.println("2. Log in");
            System.out.println("9. < EXIT");
            String authorisedUser = null;
            switch (readChoice()){
               case 1: CreateUser(esql); break;
               case 2: authorisedUser = LogIn(esql); break;
               case 9: keepon = false; break;
               default : System.out.println("Unrecognized choice!"); break;
            }//end switch
            if (authorisedUser != null) {
              boolean usermenu = true;
              while(usermenu) {
            
            /*
            System.out.println("For Debugging");
            System.out.println("\n===== DEBUG: Current FoodOrder Table =====");
            esql.executeQueryAndPrintResult("SELECT * FROM FoodOrder");
            System.out.println("\n===== DEBUG: Current ItemsInOrder Table =====");
            esql.executeQueryAndPrintResult("SELECT * FROM ItemsInOrder");
            System.out.println("\n===== DEBUG: Current Items Table =====");
            esql.executeQueryAndPrintResult("SELECT * FROM Items");
            System.out.println("\n===== DEBUG: Current Users Table =====");
            esql.executeQueryAndPrintResult("SELECT * FROM Users");
            */
            


                System.out.println("MAIN MENU");
                System.out.println("---------");
                System.out.println("1. View Profile");
                System.out.println("2. Update Profile");
                System.out.println("3. View Menu");
                System.out.println("4. Place Order"); //make sure user specifies which store
                System.out.println("5. View Full Order ID History");
                System.out.println("6. View Past 5 Order IDs");
                System.out.println("7. View Order Information"); //user should specify orderID and then be able to see detailed information about the order
                System.out.println("8. View Stores"); 

                //**the following functionalities should only be able to be used by drivers & managers**
                System.out.println("9. Update Order Status");

                //**the following functionalities should ony be able to be used by managers**
                System.out.println("10. Update Menu");
                System.out.println("11. Update User");

                System.out.println(".........................");
                System.out.println("20. Log out");
                switch (readChoice()){
                    case 1: viewProfile(esql, authorisedUser); break;
                   case 2: updateProfile(esql, authorisedUser); break;
                   case 3: viewMenu(esql); break;
                   case 4: placeOrder(esql, authorisedUser); break;
                   case 5: viewAllOrders(esql, authorisedUser); break;
                   case 6: viewRecentOrders(esql, authorisedUser); break;
                   case 7: viewOrderInfo(esql, authorisedUser); break;
                   case 8: viewStores(esql); break;
                   case 9: updateOrderStatus(esql, authorisedUser); break;
                   case 10: updateMenu(esql, authorisedUser); break;
                   case 11: updateUser(esql, authorisedUser); break;
                   case 20: usermenu = false; break;
                   default : System.out.println("Unrecognized choice!"); break;
                }
              }
            }
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main

   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
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

   /*
    * Creates a new user
    **/
   public static void CreateUser(PizzaStore esql) {
    try {

      System.out.print("If You Wish To Exit, Type 'Exit' Anytime. \n");
      System.out.print("Choose Your User Name: ");
      String login = in.readLine().trim().toLowerCase();
      if (login.equalsIgnoreCase("exit")) 
        {
            System.out.println("Account Creation Cancelled.");
            return;
        }

      // Since User Name Is A Primary Key, We Must Make Sure Its Unique
      String checkUserQuery = String.format("SELECT * FROM Users WHERE login = '%s'", login);
      int userExists = esql.executeQuery(checkUserQuery);

      if (userExists > 0) 
      {
         System.out.println("User Already Exists. Please Choose A Different Username.");
         return;
      }

      String password;
        while (true) {
            System.out.print("Choose A Password: ");
            String password1 = in.readLine();

         if (password1.equalsIgnoreCase("exit")) 
         {
            System.out.println("Account Creation Cancelled.");
            return;
         }

            System.out.print("Confirm Your Password: ");
            String password2 = in.readLine();

            if (password2.equalsIgnoreCase("exit")) 
         {
            System.out.println("Account Creation Cancelled.");
            return;
         }

            if (password1.equals(password2)) {
                password = password1; 
                break;
            } else {
                System.out.println("Passwords Do Not Match! Please Try Again.");
            }
        }

      String phoneNum;
      while (true) {
          System.out.print("Enter Your Phone Number (Ex. 1234567890): ");
          phoneNum = in.readLine().trim();

          if (phoneNum.equalsIgnoreCase("exit")) 
         {
            System.out.println("Account Creation Cancelled.");
            return;
         }

         // Ensure Phone Number Is Valid
    if (phoneNum.matches("\\d{10}"))
      {
         break; 
      }  

   else 
      {
          System.out.println("Invalid Phone Number! Please Enter Exactly 10 Digits.");
      }
   }

      // Default Roles
      String role = "customer";  
      String favoriteItems = "NULL";

      // Creating String To Insert User Into DataBase
      String insertUserQuery = String.format
      (
         "INSERT INTO Users (login, password, role, favoriteItems, phoneNum) " +
         "VALUES ('%s', '%s', '%s', %s, '%s')", 
         login, password, role, favoriteItems, phoneNum
      );

      // Inserting User Into DataBase
      esql.executeUpdate(insertUserQuery);
      System.out.println("Account Successfully Created. Please Log In.");

    } 
      catch (Exception e) 
    {
         System.err.println("Error creating user: " + e.getMessage());
      }
   }



   /*
    * Check log in credentials for an existing user
    * @return User login or null is the user does not exist
    **/
   public static String LogIn(PizzaStore esql)
   {
    try {
      System.out.print("If You Wish To Exit, Type 'exit' Anytime. \n");
        System.out.print("Enter Your Username: ");
        String login = in.readLine().trim().toLowerCase();

          if (login.equalsIgnoreCase("exit")) 
        {
            System.out.println("Account Creation Cancelled.");
            return null;
        }

        System.out.print("Enter Your Password: ");
        String password = in.readLine();

          if (password.equalsIgnoreCase("exit")) 
        {
            System.out.println("Account Creation Cancelled.");
            return null;
        }

        // Check If Username & Password Matching
        String query = String.format
        (
            "SELECT * FROM Users WHERE login = '%s' AND password = '%s'", login, password
        );
        int userExists = esql.executeQuery(query);

        if (userExists > 0) 
        {
            System.out.println("Login Successful! Hello, " + login);
            // Returns Session
            return login; 
        } 
        else 
        {
            System.out.println("Invalid Username Or Password. Please Try Again.");
            return null;
        }
      } 
         catch (Exception e)
      {
        System.err.println("Error Logging In: " + e.getMessage());
        return null;
    }

   }//end

// Rest of the functions definition go in here

    public static void viewProfile(PizzaStore esql, String authorisedUser) 
   {
      try {
        System.out.println("------------");
        System.out.println(authorisedUser + "'s Profile Data:");
        System.out.println("------------");
        String query = String.format("SELECT u.favoriteItems, u.phoneNum FROM Users u WHERE u.login = '%s'", authorisedUser);
        esql.executeQueryAndPrintResult(query);
      }
      catch (Exception e) 
      {
        System.err.println("Error Rretrieving Profile: " + e.getMessage());
      }
   }

   public static void updateProfile(PizzaStore esql, String authorisedUser) {
      try {
        System.out.println("------------");
        System.out.println("Profile Options:");
        System.out.println("------------");
        System.out.println("1. Update Favorite Item");
        System.out.println("2. Update Phone Number");
        System.out.println("3. Change Password");;
        System.out.println("4. Go Back");

        int choice = readChoice(); 

        switch (choice) {
            case 1:
                String updateQuery;
                String query;
               // Load the items from the CSV file, manually - since the way queries are immediately printed
               // could be loaded with a query, but not done here for simplicity

               // current favorite item
               System.out.println("Your current favorite item:");
               query = String.format("SELECT favoriteItems FROM Users WHERE login = '%s'", authorisedUser);
               esql.executeQueryAndPrintResult(query);

               //  items to choose from
               System.out.println("-------------------------------");
               System.out.println("Available items to choose from:");
               System.out.println("-------------------------------");

               String itemQuery = "SELECT itemName FROM Items";
               esql.executeQueryAndPrintResult(itemQuery);

               System.out.println("-------------------------------");
               System.out.println("Enter New Favorite Item:");

               String userItemChoice = in.readLine().trim();
               String checkUserQuery = String.format("SELECT * FROM Items WHERE itemName = '%s'", userItemChoice);
               int itemExists = esql.executeQuery(checkUserQuery);

               while(itemExists == 0) 
               {
                  // // sql query failed, so reprint menu
                  // System.out.println("Available items to choose from:");
                  // esql.executeQueryAndPrintResult(itemQuery);

                  // Error handling, output error message and get new user input
                  System.out.println("Error: Item Doesnt Exists. Choose a Different One.");
                  userItemChoice = in.readLine().trim();

                  // execute sql query again
                  checkUserQuery = String.format("SELECT * FROM Items WHERE itemName = '%s'", userItemChoice);
                  itemExists = esql.executeQuery(checkUserQuery);
               }

               
               // update the favorite item in the database`
               updateQuery = String.format("UPDATE Users SET favoriteItems = '%s' WHERE login = '%s'", userItemChoice, authorisedUser);
               esql.executeUpdate(updateQuery);

               System.out.println("Favorite item updated successfully to: " + userItemChoice);

                break; 
            case 2:
               // current phone number
               query = String.format("SELECT phoneNum FROM Users WHERE login = '%s'", authorisedUser);
               esql.executeQueryAndPrintResult(query);

               // new phone number
               System.out.println("Enter Your New Phone Number:");
               String newPhoneNumber = in.readLine();

               // update the phone number
               updateQuery = String.format("UPDATE Users SET phoneNum = '%s' WHERE login = '%s'", newPhoneNumber, authorisedUser);
               esql.executeUpdate(updateQuery);

               System.out.println("Phone number updated successfully to: " + newPhoneNumber);
                
                break;
            case 3:
 
               System.out.println("Enter your new password:");
               String newPassword = in.readLine();

               // updating the password in the database
               String updatePasswordQuery = String.format("UPDATE Users SET password = '%s' WHERE login = '%s'", newPassword, authorisedUser);
               esql.executeUpdate(updatePasswordQuery);

               System.out.println("Password updated successfully.");
                 
                break;
            case 4:
                System.out.println("Going back");
                return; 
            default:
                System.out.println("Unrecognized choice!");
                return;
        }
      } 
      catch (Exception e) 
      {
        System.err.println("Error retrieving menu: " + e.getMessage());
      }
   }
   public static void viewMenu(PizzaStore esql)
   {
    try {
        System.out.println("------------");
        System.out.println("Browse Menu:");
        System.out.println("------------");
        System.out.println("1. View All Items");
        System.out.println("2. Filter By Type (Drinks, Entree, or Sides)");
        System.out.println("3. Filter By Price (Items Below Certain Price)");
        System.out.println("4. Sort By Price (Low To High)");
        System.out.println("5. Sort By Price (High To Low)");
        System.out.println("9. Go Back");

        int choice = readChoice();

        String query = "SELECT * FROM Items"; 

        switch (choice) {
            case 1:
                break; 
            case 2:
               String type = "";
               while (true) {
                  System.out.println("Select the type of item to filter by:");
                  System.out.println("1. Entree");
                  System.out.println("2. Sides");
                  System.out.println("3. Drinks");
                  System.out.print("Enter the number corresponding to your choice: ");

                  String input = in.readLine().trim();

                  if (input.equals("1")) {
                     System.out.println("You Selected Entree");
                     type = "entree";
                     query =  String.format("SELECT * FROM Items WHERE typeOfItem = '%s'", type);
                     break;
                  } else if (input.equals("2")) {
                     System.out.println("You Selected Sides");
                     type = "sides";
                     query =  String.format("SELECT * FROM Items WHERE typeOfItem = '%s'", type);
                     break;
                  } else if (input.equals("3")) {
                     System.out.println("You Selected Drinks");
                     type = "drinks";
                     query =  String.format("SELECT * FROM Items WHERE typeOfItem = '%s'", type);
                     break;
                  } else {
                     System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                  }
               }
                break;
            case 3:
                System.out.print("Enter The Maximum Price: ");
                double maxPrice = Double.parseDouble(in.readLine());
                query = String.format("SELECT * FROM Items WHERE price <= %.2f", maxPrice);
                break;
            case 4:
                query = "SELECT * FROM Items ORDER BY price ASC";
                break;
            case 5:
                query = "SELECT * FROM Items ORDER BY price DESC";
                break;
            case 9:
                return; 
            default:
                System.out.println("Invalid Choice! Returning To Menu.");
                return;
        }

        esql.executeQueryAndPrintResult(query);
      } 
      catch (Exception e) 
      {
        System.err.println("Error retrieving menu: " + e.getMessage());
      }
   }

   public static void placeOrder(PizzaStore esql, String authorisedUser) 
   {
      try{
      

      // Displays All Stores To Choose From
      System.out.println("Available Stores:");
      String query = "SELECT * FROM Store";
      esql.executeQueryAndPrintResult(query);

      System.out.print("Enter the Store ID you want to order from: ");
      String storeID = in.readLine().trim();

      // Makes Sure Store Exists
      String checkStoreQuery = String.format("SELECT * FROM Store WHERE storeID = '%s'", storeID);
        if (esql.executeQuery(checkStoreQuery) == 0) 
        {
            System.out.println("Invalid Store ID! Order cancelled.");
            return;
         }

      // List All Items In The Store
      List<String> itemNames = new ArrayList<>();
      List<Integer> quantities = new ArrayList<>();
      double totalPrice = 0.0; 

      while (true) 
      {

         System.out.println("\nAvailable Items in Store " + storeID + ":");
         String itemsQuery = "SELECT itemName, price FROM Items"; 
         esql.executeQueryAndPrintResult(itemsQuery);


         System.out.print("Enter Done Anytime To Finish Ordering: \n");
         System.out.print("Enter Item Name: ");
         String itemName = in.readLine().trim();


         if (itemName.equalsIgnoreCase("done")) 
         {
            break;
         }
            // Check If Item Exists
            String checkItemQuery = String.format("SELECT price FROM Items WHERE itemName = '%s'", itemName);
            List<List<String>> itemResult = esql.executeQueryAndReturnResult(checkItemQuery);

            if (itemResult.isEmpty()) 
            {
                System.out.println("Item Not Found!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity;
            try {
                quantity = Integer.parseInt(in.readLine().trim());
                if (quantity <= 0) 
                {
                    System.out.println("Quantity Must Be Greater Than Zero.");
                    continue;
                }
               } 
               catch (NumberFormatException e) 
               {
                System.out.println("Invalid quantity! Please enter a number.");
                continue;
            }

            // Store Items And Quantity
            itemNames.add(itemName);
            quantities.add(quantity);

            // Calculate Total Price
            double itemPrice = Double.parseDouble(itemResult.get(0).get(0));
            totalPrice += itemPrice * quantity;
        }

        if (itemNames.isEmpty()) 
        {
            System.out.println("No Items Selected. Order Cancelled.");
            return;
        }

      // Get Last Order ID, Increase Or Set Value To Make New OrderID
      String orderIDQuery = "SELECT MAX(orderID) FROM FoodOrder";
      List<List<String>> orderIDResult = esql.executeQueryAndReturnResult(orderIDQuery);

       int newOrderID;
        if (orderIDResult.isEmpty() || orderIDResult.get(0).get(0) == null) 
        {
            newOrderID = 10000; 
        } else 
        {
            newOrderID = Integer.parseInt(orderIDResult.get(0).get(0)) + 1;
        }

      String insertOrderQuery = String.format(
            "INSERT INTO FoodOrder (orderID, login, storeID, totalPrice, orderStatus, orderTimestamp) " +
            "VALUES (%d, '%s', '%s', %.2f, 'Pending', NOW())",
            newOrderID, authorisedUser, storeID, totalPrice
        );
        esql.executeUpdate(insertOrderQuery);

        // Insert into ItemsInOrder Table
        for (int i = 0; i < itemNames.size(); i++) {
            String insertItemQuery = String.format(
                "INSERT INTO ItemsInOrder (orderID, itemName, quantity) VALUES (%d, '%s', %d)",
                newOrderID, itemNames.get(i), quantities.get(i)
            );
            esql.executeUpdate(insertItemQuery);
        }

        System.out.println("Order placed successfully!");
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));

      /*
       System.out.println("\n===== DEBUG: Current FoodOrder Table =====");
        esql.executeQueryAndPrintResult("SELECT * FROM FoodOrder");

        System.out.println("\n===== DEBUG: Current ItemsInOrder Table =====");
        esql.executeQueryAndPrintResult("SELECT * FROM ItemsInOrder");
      */

      } catch (Exception e) {
        System.err.println("Error placing order: " + e.getMessage());
    }
}


   public static void viewAllOrders(PizzaStore esql, String authorisedUser) {
      try {
         // checking if user is customer
         String roleQuery = String.format("SELECT login FROM Users WHERE role = 'customer' AND login = '%s'", authorisedUser);

         int isCustomer = esql.executeQuery(roleQuery);

         String orderQuery;
         if (isCustomer > 0) {
            // customers can only see their own orders
            orderQuery = String.format("SELECT orderID, orderTimestamp, totalPrice, orderStatus FROM FoodOrder WHERE login = '%s'", authorisedUser);
         } else {
            // managers and drivers can view all orders
            orderQuery = "SELECT * FROM FoodOrder ORDER BY orderTimestamp DESC";
         }
         esql.executeQueryAndPrintResult(orderQuery);
      } catch (Exception e) {
         System.err.println("Error retrieving order details: " + e.getMessage());
      }
   }
   public static void viewRecentOrders(PizzaStore esql, String authorisedUser) {
      try {
         // 5 most recent orders of the current authorized user
         String query =  String.format("SELECT OrderID as Recent_OrderIds FROM foodorder WHERE login = '%s' ORDER BY orderTimestamp DESC LIMIT 5", authorisedUser);
         esql.executeQueryAndPrintResult(query);
      }
      catch (Exception e) 
      {
        System.err.println("Error retrieving orders: " + e.getMessage());
      }
   }
   public static void viewOrderInfo(PizzaStore esql, String authorisedUser) {
      try {
         // get the user to enter an order ID
        System.out.print("Enter the Order ID: ");
        int orderID = Integer.parseInt(in.readLine());

        // check if the user is a customer
        String roleQuery = String.format("SELECT login FROM Users WHERE role = 'customer' AND login = '%s'", authorisedUser);
        int isCustomer = esql.executeQuery(roleQuery);
        String orderQuery;
        if (isCustomer > 0) {
            // customers can only see their own orders
            orderQuery = String.format("SELECT * FROM FoodOrder WHERE orderID = %d AND login = '%s'", orderID, authorisedUser );
        } else {
            // managers and drivers can view any order
            orderQuery = String.format("SELECT * FROM FoodOrder WHERE orderID = %d", orderID );
        }

        // Print order details
        esql.executeQueryAndPrintResult(orderQuery);

    } catch (Exception e) {
        System.err.println("Error retrieving order details: " + e.getMessage());
    }
   }

   public static void viewStores(PizzaStore esql) 
   {
      try {
         String query = "SELECT * FROM Store";
         esql.executeQueryAndPrintResult(query);
      } catch (Exception e) {
         System.err.println("Error Retrieving Stores: " + e.getMessage());
      }
   }

  public static void updateOrderStatus(PizzaStore esql, String authorisedUser) {
    try {
       
      String roleQuery = String.format(
            "SELECT login FROM Users WHERE role = 'manager' AND login = '%s'", 
            authorisedUser
        );

         String roleQuery1 = String.format(
            "SELECT login FROM Users WHERE role = 'driver' AND login = '%s'", 
            authorisedUser
        );


        int isManager = esql.executeQuery(roleQuery);
        
        int isDriver = esql.executeQuery(roleQuery1); 

        if (isManager <0 && isDriver > 0) {
            System.out.println("Unauthorized: Only managers can update menu items.");
            return;
        }


        System.out.println("\n===== Available Orders =====");
        String allOrdersQuery = "SELECT orderID, login, storeID, totalPrice, orderStatus, orderTimestamp FROM FoodOrder";
        esql.executeQueryAndPrintResult(allOrdersQuery);

        System.out.print("\nEnter the Order ID you want to update: ");
        int orderID = Integer.parseInt(in.readLine().trim());


        String checkOrderQuery = String.format("SELECT * FROM FoodOrder WHERE orderID = %d", orderID);
        if (esql.executeQuery(checkOrderQuery) == 0) 
        {
            System.out.println("Error: Order ID Not Found.");
            return;
        }

        System.out.println("\n===== Order Status Options =====");
        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Out for Delivery");
        System.out.println("4. Completed");
        System.out.println("5. Canceled");

        String newStatus;
        while (true) {
            System.out.print("\nEnter the number corresponding to the new order status: ");
            String choice = in.readLine().trim();

            switch (choice) {
                case "1":
                    newStatus = "Pending";
                    break;
                case "2":
                    newStatus = "In Progress";
                    break;
                case "3":
                    newStatus = "Out for Delivery";
                    break;
                case "4":
                    newStatus = "Completed";
                    break;
                case "5":
                    newStatus = "Canceled";
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
                    continue;
            }
            break;
        }

        // Step 6: Update the order status in the database
        String updateQuery = String.format("UPDATE FoodOrder SET orderStatus = '%s' WHERE orderID = %d", newStatus, orderID);
        esql.executeUpdate(updateQuery);

        // Step 7: Display the updated order for confirmation
        System.out.println("\nOrder status updated successfully!");
        String updatedOrderQuery = String.format("SELECT * FROM FoodOrder WHERE orderID = %d", orderID);
        esql.executeQueryAndPrintResult(updatedOrderQuery);

    } catch (Exception e) {
        System.err.println("Error updating order status: " + e.getMessage());
    }
}





   public static void updateMenu(PizzaStore esql, String authorisedUser) {
      try {
        // Check if the user is a manager
        String roleQuery = String.format(
            "SELECT login FROM Users WHERE role = 'manager' AND login = '%s'", 
            authorisedUser
        );

        int isManager = esql.executeQuery(roleQuery);

        if (isManager < 0) {
            System.out.println("Unauthorized: Only managers can update menu items.");
            return;
        }

        // Read item name to update
        System.out.print("Enter the name of the item you want to update: ");
        String itemName = in.readLine();
        
        String query = String.format("SELECT * FROM Items WHERE itemName = '%s'", itemName);
        esql.executeQueryAndPrintResult(query);

        while (true) {
            System.out.println("Select what you want to update:");
            System.out.println("1. Ingredients");
            System.out.println("2. Type of Item");
            System.out.println("3. Price");
            System.out.println("4. Description");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            String input = in.readLine().trim();
            String updateQuery = "";

            switch (input) {
                case "1":
                    System.out.print("Enter new ingredients: ");
                    String newIngredients = in.readLine().trim();
                    updateQuery = String.format("UPDATE Items SET ingredients = '%s' WHERE itemName = '%s'", newIngredients, itemName);
                    break;

                case "2":
                    while (true) {
                        System.out.println("Select the new type of item:");
                        System.out.println("1. Entree");
                        System.out.println("2. Sides");
                        System.out.println("3. Drinks");
                        System.out.print("Enter your choice: ");

                        String typeInput = in.readLine().trim();
                        switch (typeInput) {
                            case "1":
                                updateQuery = String.format("UPDATE Items SET typeOfItem = 'entree' WHERE itemName = '%s'", itemName);
                                break;
                            case "2":
                                updateQuery = String.format("UPDATE Items SET typeOfItem = 'sides' WHERE itemName = '%s'", itemName);
                                break;
                            case "3":
                                updateQuery = String.format("UPDATE Items SET typeOfItem = 'drinks' WHERE itemName = '%s'", itemName);
                                break;
                            default:
                                System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                                continue;
                        }
                        break;
                    }
                    break;

                case "3":
                    System.out.print("Enter new price: ");
                    String newPrice = in.readLine();
                    updateQuery = String.format("UPDATE Items SET price = %s WHERE itemName = '%s'", newPrice, itemName);
                    break;

                case "4":
                    System.out.print("Enter new description: ");
                    String newDescription = in.readLine();
                    updateQuery = String.format( "UPDATE Items SET description = '%s' WHERE itemName = '%s'", 
                        newDescription, itemName
                    );
                    break;

                case "9":
                    System.out.println("Exiting update menu...");
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
                    continue;
            }

            // Execute the update query
            esql.executeUpdate(updateQuery);
            query = String.format("SELECT * FROM Items WHERE itemName = '%s'", itemName);
            esql.executeQueryAndPrintResult(query);
            System.out.println("Item updated successfully.");
        }
      } catch (Exception e) {
         System.err.println("Error Retrieving Stores: " + e.getMessage());
      }
   }
   
   public static void updateUser(PizzaStore esql,  String authorisedUser) 
   {
       try {
        // Check if the user is a manager
        String roleQuery = String.format(
            "SELECT login FROM Users WHERE role = 'manager' AND login = '%s'", 
            authorisedUser
        );

        
        int isManager = esql.executeQuery(roleQuery); 
        

        if (isManager < 0) {
            System.out.println("Unauthorized: Only managers can update menu items.");
            return;
        }

      System.out.println("------------");
      System.out.println("User Management:");
      System.out.println("------------");
      System.out.println("1. Update a User’s Login");
      System.out.println("2. Update a User’s Role");
      System.out.println("3. Go Back");
      int choice = readChoice();

      switch (choice) {
      case 1:
         // Update User Login
         System.out.print("Enter the Username of the User You Want to Modify: ");
         String targetUser = in.readLine().trim().toLowerCase();

         /*
          String checkUserQuery1 = String.format("SELECT * FROM Users WHERE login = '%s'", newLogin);
         int checkTargetUser = esql.executeQuery(checkUserQuery1);
         if (checkTargetUser == 0) 
         {
         System.out.println("Error: User Doesnt Exisit.");
         return;
         }
         */

         System.out.print("Enter the New Username: ");
         String newLogin = in.readLine().trim().toLowerCase();

         String checkUserQuery = String.format("SELECT * FROM Users WHERE login = '%s'", newLogin);
         int userExists = esql.executeQuery(checkUserQuery);
         if (userExists > 0) 
         {
         System.out.println("Error: Username Already Exists. Choose a Different One.");
         return;
         }

         //Update login in database
         String updateLoginQuery = String.format("UPDATE Users SET login = '%s' WHERE login = '%s'", newLogin, targetUser);
         esql.executeUpdate(updateLoginQuery);
         System.out.println("User Login Successfully Updated!");
          break;

         case 2:
         // Update User Role
         System.out.print("Enter the Username of the User You Want to Modify: ");
         String userToModify = in.readLine().trim().toLowerCase();
         System.out.println("Available Roles: Customer, Driver, Manager");
         System.out.print("Enter the New Role: ");
         String newRole = in.readLine().trim().toLowerCase();

         if (!newRole.equals("customer") && !newRole.equals("driver") && !newRole.equals("manager")) 
         {
            System.out.println("Invalid Role! Please Enter 'customer', 'driver', or 'manager'.");
            return;
         }

         String updateRoleQuery = String.format("UPDATE Users SET role = '%s' WHERE login = '%s'", newRole, userToModify);
         esql.executeUpdate(updateRoleQuery);
         System.out.println("User Role Successfully Updated!");
         break;

         case 3:
         System.out.println("Returning to Main Menu.");
         return;

         default:
         System.out.println("Invalid Choice! Returning to Main Menu.");
         return;
        }

    } catch (Exception e) {
        System.err.println("Error Updating User: " + e.getMessage());
    }
} //end PizzaStore
}