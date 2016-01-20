package model.test.generation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.question.Question;
import model.util.DBAccess;

/** This class is used to generate a group of
 *questions based on the selected automation
 *rules.
 *
 * @author Clay Jacobs
 */
public class AutomationGroup {

  /**
   * The database for the test tool.
   */  
  private DBAccess database;

  /**
   * The id of this AutomationGroup
   */
  private long automationGroupId;
  
  /**
   * The id of the User who owns this AutomationGroup
   */
  private long ownerId;

  /**
   * Number of questions to generate with the AutomationGroup.
   */
  private int questionsToGenerate;

  /**
   * List of AutomationRules sorted in the order they will be applied.
   */
  private ArrayList<AutomationRule> generationRules;
  
  
  /**
   * Creates an automation group.
   */
  public AutomationGroup() {
	 database = new DBAccess();
	 generationRules = new ArrayList<AutomationRule>();
     System.out.println("in model.test.generation.AutomationGroup.setProfilePic");
  }
  
  /**
	* Returns the id associated with this AutomationGroup.
	*
	* @return The id associated with this AutomationGroup.
	*/
  public long getId() {
     return automationGroupId;
  }
	
  /**
	* Sets the id associated with this AutomationGroup.
	*
	* @param id the id to associate with this AutomationGroup.
	*/
  public void setId(long id) {
	  automationGroupId = id;
  
  }
  
  /**
	* Returns the ownerid associated with this AutomationGroup.
	*
	* @return The ownerid associated with this AutomationGroup.
	*/
  public long getOwnerId() {
     return ownerId;
  }
	
  /**
	* Sets the ownerid associated with this AutomationGroup.
	*
	* @param id the ownerid to associate with this AutomationGroup.
	*/
  public void setOwnerId(long id) {
	  ownerId = id;
  
  }
  
  
  /**
   * Evaluates an automation group.
   *
   *
   * @return A List<Question> of the questions that meets the requirements
   * of the AutomationRules.
   */
  public List<Question> evaluate() {
     return null;
  }
  
  /**
   * Adds an AutomationRule to the end of generationRules.
   *
   *
   * @param rule the AutomationRule to add
   */
  public void addAutomationRule(AutomationRule rule) {
	  generationRules.add(rule);
  }
  
  /**
   * Removes the AutomationRule at the specified index in generationRules.
   * 
   * @param index the position of the AutomationRule to remove
   */
  public void removeAutomationRule(int index) {
	  generationRules.remove(index);
  }
  
  /**
   * Gets the AutomationRule at the specified index.
   *
   *
   * @param index the position of the AutomationRule we need to return.
   * @return The AutomationRule at the specified index.
   */
  public AutomationRule getAutomationRule(int index) {
     return generationRules.get(index);
  }
  
  /**
   * Sets the AutomationRule at the specified index.
   *
   *
   * @param rule the new AutomationRule to assign.
   * @param index the position of the AutomationRule we to set.
   */
  public void setAutomationRule(AutomationRule rule, int index) {
	  generationRules.set(index, rule);
  }


  /**
   * Sets the list of AutomationRules
   *
   * @param rules the List<AutomationRule> to use to set up an AutomationGroup
   */
  public void setAutomationRules(ArrayList<AutomationRule> rules) {
	  generationRules = rules;
  }
  
  
  /**
   * Returns the List of AutomationRules.
   *
   * 
   * @return The List<AutomationRule> contained in an AutomationGroup
   */
  public ArrayList<AutomationRule> getAutomationRules() {
     return generationRules;
  }
  
  /**
   * Returns the number of questions to generate with the AutomationGroup.
   *
   * 
   * @return The number of questions the AutomationGroup will create
   */
  public int getNumberOfQuestions() {
     return questionsToGenerate;
  }
  
  /**
   * Sets the number of questions to generate with the AutomationGroup
   * to numQuestions.
   *
   * 
   * @param numQuestions the new number of questions the AutomationGroup 
   */
  public void setNumberOfQuestions(int numQuestions) {
	  questionsToGenerate = numQuestions;
  }

  /**
   * Adds the automation group to the database.
   */
  public void addToDatabase() {
    try {
		database.insert("AutomationGroups(automationGroupId, ownerId, totalQuestions)", 
				  "VALUES("+this.getId() + ", " + this.getOwnerId() + ", " + this.getNumberOfQuestions()+")");
		for (int i=0; i<generationRules.size(); i++) {
			if (!generationRules.get(i).isInDatabase()) {
			   generationRules.get(i).addToDatabase();
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
  
  /**
   * Deletes from the database.
   */
  public void deleteFromDatabase() {
	try {
		for (int i=0; i<generationRules.size(); i++) {
			if (generationRules.get(i).isInDatabase()) {
			   generationRules.get(i).deleteInDatabase();
			}
		}
		database.delete("AutomationGroups", "automationGroupId = " + this.getId());
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  /**
   * Returns if it is in the database.s
   * @return
   */
  public boolean isInDatabase() {
	  boolean isInDatabase = false;
	  try {
		 if (database.countAll("AutomationGroups", "automationGroupId = " + this.getId()) >0) {
			isInDatabase = true;
		 }
	  } catch (Exception e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  }
	  return isInDatabase;
  }
  
  /**
   * Updates the database.
   */
  public void updateInDatabase() {
	  database.update("AutomationGroups",
			  "automationGroupId =" + getId() + ", ownerId = '" + getOwnerId() + "', totalQuestions = '" + getNumberOfQuestions() + "'",
			  "automationGroupId=" + getId());
	  for (int i=0; i<generationRules.size(); i++) {
		  if (generationRules.get(i).isInDatabase()) {
			  generationRules.get(i).updateInDatabase();
	      }
		  else {
			  generationRules.get(i).addToDatabase();
		  }
	 }
  }
  
}

