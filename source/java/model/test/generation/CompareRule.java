package model.test.generation;

import java.sql.SQLException;

import model.util.DBAccess;

/** This class represents a compound boolean expression
  for adding to an automation rule.
 *
 * @author Clay Jacobs
 */
public class CompareRule {

  /**
   * The database for the test tool.
   */  
  private DBAccess database;
   
  /**
   * The id of this AutomationRule
   */
  private long compareRuleId;
  
  /**
   * The id of this AutomationRule
   */
  private long automationRuleId;
  
  
  /**
   * The id of the AutomationGroup
   */
  private long automationGroupId;
  
  /**
   * Compare operation to perform with an automation rule.
   * Either AND or OR.
   *
   */
  private String joinType;
  
  /**
   * The value to compare with for an automation rule.
   */
  private String compareTo;
 
  
  /**
   * Creates a compare rule.
   */
  public CompareRule() {
	  database = new DBAccess();
  }
  
  /**
	* Returns the id associated with this CompareRule.
	*
	* @return The id associated with this CompareRule.
	*/
  public long getId() {
     return compareRuleId;
  }
	
  /**
	* Sets the id associated with this CompareRule.
	*
	* @param id the id to associate with this CompareRule.
	*/
  public void setId(long id) {
	  compareRuleId = id;
  
  }
  
  /**
	* Returns the automationRuleId associated with this CompareRule.
	*
	* @return The automationRuleId associated with this CompareRule.
	*/
  public long getRuleId() {
     return automationRuleId;
  }
	
  /**
	* Sets the automationRuleId associated with this CompareRule.
	*
	* @param id the automationRuleId to associate with this CompareRule.
	*/
  public void setRuleId(long id) {
	  automationRuleId = id;
  
  }
  
  /**
	* Returns the automationGroupId associated with this CompareRule.
	*
	* @return The automationGroupId associated with this CompareRule.
	*/
  public long getGroupId() {
     return automationGroupId;
  }
	
  /**
	* Sets the automationGroupId associated with this CompareRule.
	*
	* @param id the automationGroupId to associate with this CompareRule.
	*/
  public void setGroupId(long id) {
	  automationGroupId = id;
  
  }
  
  /**
   * Sets join to the type of comparison operation 
   * to perform in the boolean expression.
   *
   *
   * @param join The boolean operator to use, either "AND" or "OR"; 
   pre:
   post:
   */
  public void setJoinType(String join) {
	  joinType = join;
  }
  
  /**
   * Sets the value that is compared with a
   * question property's value.
   *
   * @param compareTo The String to compare with a
   * question property's value.
   pre:
   post:
   */
  public void setCompareTo(String compareTo) {
	  this.compareTo = compareTo;
  }
  
  /**
   * @return The type of comparison operation 
   * to perform in the boolean expression as a String.
   pre:
   post:
   */
  public String getJoinType() {
	  return joinType;
  }
  
  /**
   * @return The String to compare to a question property's value.
   pre:
   post:
   */
  public String getCompareTo() {
	  return compareTo;
  }
  
  /**
   * Adds rule to database.
   */
  public void addToDatabase() {
	try {
		database.insert("CompareRules(compareRuleId, automationRuleId, automationGroupId, joinType, compareTo)", 
				"VALUES("+ getId() + "," + getRuleId() + ", " + getGroupId() + ", '" + getJoinType() + "', '" + getCompareTo()+"')");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  /**
   * Deletes rule from database.
   */
  public void deleteInDatabase() {
	  try {
		 database.delete("CompareRules", "compareRuleId = " + getId());
	  } catch (SQLException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
	  }
  }
  
  /**
   * Returns if it is in the database.
   * @return
   */
  public boolean isInDatabase() {
	  boolean isInDatabase = false;
	  try {
		 if (database.countAll("CompareRules", "compareRuleId = " + getId()) >0) {
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
	  database.update("CompareRules",
			  "compareRuleId = " + getId() + ",automationRuleId=" + getRuleId() + ", automationGroupId =" + getGroupId() + ", joinType = '" + getJoinType() + "', compareTo = '" + getCompareTo() + "'",
			  "compareRuleId = " + getId());
  }
}