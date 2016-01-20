package model.grading;

import model.util.DBAccess;

/**
 * A Grade object for individual answers.
 *
 * @author Clay Jacobs
 */
public abstract class Grade {
   private long gradeId;
   private String comment;
   private double pointsEarned;
   private DBAccess database;
   
   
   /**
    * Returns the id for this grade.
    *
    * @return the id for this grade.
    pre:
    post:
    */
   public abstract long getId();
   
   
   /**
    * Sets the id to use for this grade.
    *
    * @param id the id to use for this grade.
    pre:
    post:
    */
   public abstract void setId(long id);
   
   /**
    * Returns whether a grade has been received
    *
    * @return true if a grade was entered. false otherwise.
    pre:
    post:
    */
   public abstract boolean isGraded();
   
   /**
    * Returns the comments on a user's answer.
    *
    * @return the comments on a user's answer
    pre:
    post:
    */
   public abstract String getComments();
   
   
   /**
    * Sets the comments on a student's answer.
    *
    * @param feedback the comments to use on a student's answer.
    pre:
    post:
    */
   public abstract void setComments(String feedback);
   
   
   /**
    * Returns the points earned on a user's answer.
    *
    * @return the points earned on a user's answer
    pre:
    post:
    */
   public abstract double getPointsEarned();
   
   
   /**
    * Sets the points earned on a student's answer.
    *
    * @param points the points earned to use on a student's answer.
    pre:
    post:
    */
   public abstract void setPointsEarned(double points);


}