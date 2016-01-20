package model.answer;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.util.DBAccess;

/**
 * The AnswerOption for the Test Question.
 * @author Andrew
 */
public class AnswerOption {
   /**
    * The option id.
    */
   private long optionId;
   /**
    * The question id.
    */
   private long questionId;
   /**
    * The option.
    */
   private String option;
   /**
    * The position.
    */
   private int position;
   /**
    * The database.
    */
   private DBAccess database;

   /**
    * Create an answer option that already exists in the database...
    * 
    * @param optionId
    */
   public AnswerOption(long optionId) {
      this.optionId = optionId;
      this.database = new DBAccess();

      try {
         populateFromDatabase();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Insert a new AnswerOption into the database and return an object for it
    * 
    * @param optionId
    * @param questionId
    * @param option
    * @param position
    * @return
    */
   public static AnswerOption newAnswerOption(long questionId, String option, int position) {
      DBAccess database = new DBAccess();
      AnswerOption ret = null;

      try {
         database.insert("AnswerOptions", "(questionId, option, matchingPosition)" + "VALUES (" + questionId + ", '"
               + option + "', " + position + ")");

         ResultSet res = database.select("AnswerOptions", "MAX(optionId)", "");

         res.next();
         ret = new AnswerOption(res.getLong("MAX(optionId)"));
         database.closeConnection();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return ret;
   }

   /**
    * Using the optionId member, grab all the associated data from the database
    * for the given AnswerOption
    * 
    * @throws Exception
    */
   private void populateFromDatabase() throws Exception {
      ResultSet results = database.select("AnswerOptions", "*", "optionId = " + this.optionId);
      while (results.next()) {
         this.questionId = results.getInt("questionId");
         this.option = results.getString("option");
         this.position = results.getInt("matchingPosition");
      }
      database.closeConnection();
   }

   /**
    * Remove an option from the db
    */
   public void deleteOption() {
      try {
         this.database.delete("AnswerOptions", "optionId = " + this.optionId);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * Change the option text of this AnswerOption and sync it to db
    * 
    * @param newText
    */
   public void setOptionText(String newText) {
      this.option = newText;

      this.database.update("AnswerOptions", "option = '" + option + "'", "optionId = " + this.optionId);
   }

   /**
    * Gets the option text.
    * @return
    */
   public String getOptionText() {
      return option;
   }

   /**
    * Gets the option id.
    * @return
    */
   public long getOptionId() {
      return optionId;
   }

   /**
    * Change the position of this AnswerOption and sync it to db
    * 
    * @param newPos
    */
   public void setPosition(int newPos) {
      this.position = newPos;

      this.database.update("AnswerOptions", "matchingPosition = '" + position + "'", "optionId = " + this.optionId);
   }

   /**
    * Compare by position (for sorting purposes)
    * 
    * @param that
    * @return
    */
   public int compareTo(AnswerOption that) {
      return new Integer(this.position).compareTo(that.position);
   }
}
