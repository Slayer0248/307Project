package model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Allows other model classes to access the database.
 *
 * @author Clay Jacobs
 */
public class DBAccess {

   /**
    * A connection to the database file.
    */
   private Connection dbConnection;
   private Statement statement;

   /**
    * Creates a db access.
    */
   public DBAccess() {
   }

   /**
    * This function will attempt a select query on a database table and return
    * the results. This function will throw an exception if the connection fails
    * or the query is bad.
    *
    * @param table
    *           The name of the database table to select from
    * @param columns
    *           the columns to select in the given table
    * @param whereClause
    *           any conditions to narrow the select ResultSet
    * @return the ResultSet object for the given query 
    pre: 
    post:
    */
   public ResultSet select(String table, String columns, String whereClause) throws Exception {
      ResultSet selectedData = null;

      try {
         this.connectToDB();
         
         String query = "SELECT " + columns + " FROM " + table;

         if (whereClause.length() > 0) {
            query = query + " WHERE " + whereClause;
         }
         query = query + ";";
         System.out.println("select: " + query);
         selectedData = this.statement.executeQuery(query);

      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());

         System.exit(1);
      }
      //DO NOT DISCONNECT FROM DB... user must call closeConnection when they are done reading

      return selectedData;
   }

   /**
    * This function will attempt to update a row in a database table. This
    * function will throw an exception if the connection fails or the query is
    * bad.
    *
    * @param table
    *           The name of the database table where we want to update a row
    * @param updateClause
    *           the updates to perform on the columns
    * @param whereClause
    *           any conditions to narrow the set of rows to update 
    pre: 
    post:
    */
   public void update(String table, String updateClause, String whereClause) {

      try {
         this.connectToDB();
         
         String query = "UPDATE " + table + " SET " + updateClause;

         if (whereClause.length() > 0) {
            query = query + " WHERE " + whereClause;
         }
         query = query + ";";

         System.out.println("update: " + query);
         this.statement.executeUpdate(query);
         this.dbConnection.commit();
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      } finally {
         this.cleanupFromDB();
      }

   }

   /**
    * This function will attempt to insert a row in a database table. This
    * function will throw an exception if the connection fails or the query is
    * bad.
    *
    * @param table
    *           The name of the database table where we want to insert a row
    * @param insertClause
    *           the insertion to perform 
    * @throws SQLException
   pre: 
   post:
    */
   public void insert(String table, String insertClause) throws SQLException {
      try {
         this.connectToDB();
         
         String query = "INSERT INTO " + table + " " + insertClause + ";";

         System.out.println("insert: " + query);
         this.statement.executeUpdate(query);
         this.dbConnection.commit();
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(1);
      } finally {
         this.cleanupFromDB();
      }

   }

   /**
    * This function will attempt to delete a row in a database table. This
    * function will throw an exception if the connection fails or the query is
    * bad.
    *
    * @param table
    *           The name of the database table where we want to delete a row
    * @param whereClause
    *           any conditions to narrow the set of rows to delete 
    * @throws SQLException
   pre: 
   post:
    */
   public void delete(String table, String whereClause) throws SQLException {
      try {
         this.connectToDB();
         
         String query = "DELETE from " + table + " WHERE " + whereClause + ";";

         System.out.println("delete: " + query);
         this.statement.executeUpdate(query);
         this.dbConnection.commit();
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(1);
      } finally {
         this.cleanupFromDB();
      }

   }

   /**
    * This function will attempt to delete all rows in a database table. This
    * function will throw an exception if the connection fails or the query is
    * bad.
    *
    * @param table
    *           The name of the database table where we want to delete all rows
    * @throws SQLException
    pre: 
    post:
    */
   public void deleteAll(String table) throws SQLException {
      try {
         this.connectToDB();
         
         String query = "DELETE from " + table + ";";

         System.out.println("delete all: " + query);
         this.statement.executeUpdate(query);
         this.dbConnection.commit();
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(1);
      } finally {
         this.cleanupFromDB();
      }
   }

   /**
    * This function returns the row count for a specific query or negative 1 if
    * the query failed.
    *
    * @param table
    *           The name of the database table where we want to delete all rows
    pre: 
    post:
    */
   public int countAll(String table, String whereClause) throws Exception {
      ResultSet selectedData = null;
      int count = 0;

      try {
         this.connectToDB();
         
         String query = "SELECT * FROM " + table;

         if (whereClause.length() > 0) {
            query = query + " WHERE " + whereClause;
         }
         query = query + ";";
         System.out.println("count: " + query);
         selectedData = this.statement.executeQuery(query);
         while (selectedData.next()) {
            count++;
         }
         closeConnection();
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(1);
      } finally {
         this.cleanupFromDB();
      }

      return count;
   }

   /**
    * Closes the database. Must be called after a select statement
    */
   public void closeConnection() {
      this.cleanupFromDB();
      System.out.println("Closed connection.");
   }

   /**
    * Returns whether there is an id that isn't being used in the current table
    */
   public boolean isOpenIdFor(String table, String idColumn) {
      boolean isOpenId = false;
      long id = 0;
      int countWithID = -1;
      try {
         countWithID = countAll(table, idColumn + " = " + id);
         System.out.println("count with id = " + countWithID);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      if (countWithID == 0) {
         isOpenId = true;
      } else if (countWithID > 0) {
         id++;
      }
      while (id != 0 && !isOpenId) {
         try {
            countWithID = countAll(table, idColumn + " = " + id);
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         if (countWithID == 0) {
            isOpenId = true;
         } else if (countWithID > 0) {
            id++;
         }
      }

      return isOpenId;
   }

   /**
    * Gets an id that isn't being used in the current table
    */
   public long getOpenIdFor(String table, String idColumn) {
      boolean isOpenId = false;
      long id = 0;
      int countWithID = -1;
      try {
         countWithID = countAll(table, idColumn + " = " + id);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      if (countWithID == 0) {
         isOpenId = true;
      } else if (countWithID > 0) {
         id++;
      }
      while (id != 0 && !isOpenId) {
         try {
            countWithID = countAll(table, idColumn + " = " + id);
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         if (countWithID == 0) {
            isOpenId = true;
         } else if (countWithID > 0) {
            id++;
         }
      }

      return id;
   }
   
   /**
    * Opens a collection to the database and initializes a statement for us
    * to use
    * 
    * @throws SQLException
    */
   private void connectToDB() throws
         SQLException {
      try {
         Class.forName("org.sqlite.JDBC");
      } catch (ClassNotFoundException e) {
         System.out.println("unable to find JDBC libraries");
         e.printStackTrace();
         System.exit(1);
      }
      
      String dbPath = this.getClass().getResource("JeetTest.db").getPath();
      
      int success = 0;
      try {
    	  this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" +  "JeetTest.db");
    	  System.out.println("open the filesystem");
    	  success++;
      }
      catch (Exception e) {
  	     //e.printStackTrace();
      }
      
      if (success == 0) {
         try {
            this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" + "JeetTest.db");
    	  System.out.println("open the database in the jar");
         }
         catch (Exception e) {
    	     e.printStackTrace();
         }
      }
      this.dbConnection.setAutoCommit(false);
      
      this.statement = dbConnection.createStatement();
   }
   
   /**
    * Closes out of any connectins to the database that may exist
    */
   private void cleanupFromDB() {
      try {
         if (this.statement != null)
            this.statement.close();
         this.statement = null;
         if (this.dbConnection != null)
            this.dbConnection.close();
         this.dbConnection = null;
      }
      catch (Exception e) {
         System.out.println("Could not clean up after database operation");
         e.printStackTrace();
         System.exit(1);
      }
   }
}
