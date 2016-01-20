package model.test.generation; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.util.DBAccess;

/** A class to represent specific criteria
 that will be used to generate a test
 in advanced test generator.
 *
 * @author Clay Jacobs
 */
public class AutomationRule {

  /**
   * The database for the test tool.
   */  
  private DBAccess database;

  /**
   * The id of this AutomationRule
   */
  private long automationRuleId;
  
  
  /**
   * The id of the AutomationGroup
   */
  private long automationGroupId;
  
  
  /**
   * Name of property in a question object.
   */
  private String questionProperty;
  
  /**
   * Compare operation to perform on a question property.
   */
  private String compareType;
  
  /**
   * The Primary value to compare with for an automation rule. Must
   * be set for each automation rule.
   */
  private String primaryCompareTo;
  
  /**
   * Secondary CompareRules joined with the primary comparison.
   */
  private ArrayList<CompareRule> compareRules;
  
  /**
   * A collection of question numbers (numbered relative 
   * to the automation group's number of questions parameter)
   * within an automation group which the rule must be applied to.
   */
  private ArrayList<Integer> applyOnQuestions;


  /**
   * Creates an automation rule.
   */
  public AutomationRule() {
	  database = new DBAccess();
	  compareRules = new ArrayList<CompareRule>();
	  applyOnQuestions = new ArrayList<Integer>();
  }
  
  /**
	* Returns the id associated with this AutomationRule.
	*
	* @return The id associated with this AutomationRule.
	*/
  public long getId() {
     return automationRuleId;
  }
	
  /**
	* Sets the id associated with this AutomationRule.
	*
	* @param id the id to associate with this AutomationRule.
	*/
  public void setId(long id) {
	  automationRuleId = id;
  
  }
  
  /**
	* Returns the automationGroupId associated with this AutomationRule.
	*
	* @return The automationGroupId associated with this AutomationRule.
	*/
  public long getGroupId() {
     return automationGroupId;
  }
	
  /**
	* Sets the automationGroupId associated with this AutomationRule.
	*
	* @param id the automationGroupId to associate with this AutomationRule.
	*/
  public void setGroupId(long id) {
	  automationGroupId = id;
  
  }
  
  /**
   * Adds a CompareRule to the AutomationRule.
   *
   * @param rule the CompareRule to add to the current AutomationRule
   pre:
   post:
   */
  public void addCompareRule(CompareRule rule) {
	  compareRules.add(rule);
  }
  
  /**
   * Removes the CompareRule at the specified index.
   *
   * @param index the index of the CompareRule to remove from the current AutomationRule.
   pre:
   post:
   */
  public void removeCompareTo(int index) {
	  compareRules.remove(index);
  }
  
  /**
   * Gets the CompareRule at the specified index.
   *
   * @param index the index of the compare rule to get.
   * @return the CompareRule at the specified index.
   pre:
   post:
   */
  public CompareRule getCompareRule(int index) {
	  
	  CompareRule result = compareRules.get(index);
	  return result;
  }
  
  /**
   * Sets the CompareRule at the specified index.
   *
   * @param rule The CompareRule to set to in the current AutomationRule
   * @param index the index of the CompareRule to set.
   pre:
   post:
   */
  public void setCompareRule(CompareRule rule, int index) {
	  
	  compareRules.set(index, rule);
  }

  /**
   * Parses questions as a comma separated list of question numbers
   * and sets applyOnQuestions to the resulting collection.
   * Skips over invalid numbers.
   *
   * @param questions A comma separated list of questions to parse
   pre:
   post:
   */
  public void setApplyTo(String questions) {
	  //TODO: parse this
	  applyOnQuestions.clear();
	  Scanner scanner = new Scanner(questions);
	  while (scanner.hasNextInt()) {
		  int next = scanner.nextInt();
		  applyOnQuestions.add(next);
	  }
  }


  /**
   * Sets propertyName to the name of the property in a Question
   * object which the rule acts on.
   *
   * @param propertyName the property in a Question to compare with compareTo.
   pre:
   post:
   */
  public void setQuestionProperty(String propertyName) {
	  questionProperty = propertyName;
	  
  }
  
  /**
   * Sets comparison to the type of comparison operation 
   * to perform on the specified property.
   *
   * @param comparison the type of comparison operation 
   * to perform on the specified property for the current AutomationRule.
   pre:
   post:
   */
  public void setCompareType(String comparison) {
	  compareType = comparison;
  }
  
  /**
   * Sets compareTo to the primary value that is compared with
   * specified question property's value.
   *
   * @param compareTo the primary value that is compared with
   * specified question property's value for the current AutomationRule
   pre:
   post:
   */
  public void setPrimaryCompareTo(String compareTo) {
	  primaryCompareTo = compareTo;
  }
  
  /**
   * Sets the List of CompareRules
   *
   * @param rules the List<CompareRule> to use in the current AutomationRule.
   pre:
   post:
   */
  public void setCompareRules(ArrayList<CompareRule> rules) {
	  compareRules = rules;
  }
  

  /**
   * @return The name of the property in a Question
   * object which the current AutomationRule acts on.
   pre:
   post:
   */
  public String getQuestionProperty() {
	  return questionProperty;
  }
  
  /**
   * @return The type of comparison operation 
   * to perform on the specified property.
   pre:
   post: 
   */
  public String getCompareType() {
	  return compareType;
  }
  
  /**
   * @return The primary value that is compared with
   * specified question property's value.
   pre:
   post:
   */
  public String getPrimaryCompareTo() {
	  return primaryCompareTo;
  }
  
  
  /**
   * @return the List<CompareRule> from the current AutomationRule
   pre:
   post:
   */
  public ArrayList<CompareRule> getCompareRules() {
	  return compareRules;
  }
  
  /**
   * @return the list of AutomationGroup relative indices of questions
   * which the current AutomationRule must be applied to.
   pre:
   post:
   */
  public int[] getApplyTo() {
	  Integer values[] = new Integer[applyOnQuestions.size()];
	  int result[] = new int[applyOnQuestions.size()]; 
	  applyOnQuestions.toArray(values);
	  
	  for (int i=0; i<values.length; i++) {
		  result[i] = values[i].intValue();
	  }
	  
	  return result;
  }
  
  /**
   * Adds the rule to the database.
   */
  public void addToDatabase() {
	  try {
	     database.insert("AutomationRules(automationRuleId, automationGroupId, questionProperty, compareType, primaryCompareTo)", 
	    		 "VALUES("+getId() + ", " + getGroupId() + ", '" + getQuestionProperty()+ "', '" + getCompareType()+ "', '" + getPrimaryCompareTo() +"')");
	     for (int i=0; i<compareRules.size(); i++) {
			 if (!compareRules.get(i).isInDatabase()) {
				 compareRules.get(i).addToDatabase();
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
  public void deleteInDatabase() {
	  try {
		  for (int i=0; i<compareRules.size(); i++) {
		      if (compareRules.get(i).isInDatabase()) {
				 compareRules.get(i).deleteInDatabase();
			  }
		 }
		 database.delete("AutomationRules", "automationRuleId = " + getId());
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
		 if (database.countAll("AutomationRules", "automationRuleId = " + getId()) >0) {
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
	  database.update("AutomationRules",
			  "automationRuleId=" + getId() + ", automationGroupId =" + getGroupId() + ", questionProperty = '" + getQuestionProperty() + "', compareType = '" + getCompareType() + "', primaryCompareTo = '" + getPrimaryCompareTo() + "'",
			  "automationRuleId=" + getId());
	  for (int i=0; i<compareRules.size(); i++) {
		 if (compareRules.get(i).isInDatabase()) {
		    compareRules.get(i).updateInDatabase();
		 }
		 else {
			compareRules.get(i).addToDatabase();
		 }
	 }
  }
  
  
}
