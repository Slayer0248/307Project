package model.accounts;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;

import org.apache.commons.net.util.Base64;

import model.util.DBAccess;

/**
 * Represents a user object.
 *
 * @author Clay Jacobs
 */
public class User {

  /**
   * The database for the test tool.
   */
  private DBAccess database;

  /**
   * Id for the current user.
   */
  private long userId;

  /**
   * Type of user. Either Student or Professor
   */
   private String type;
   
  /**
   * Whether the user can proctor a test or not.
   */
   private boolean canProctor;

  /**
   * First name for student log in.
   */
   private String firstName;
   
  /**
   * First name for student log in.
   */
   private String lastName;
   
  /**
   * Username for logging in.
   */
   private String username;
   
  /**
   * Password for logging in.
   */
   private String password;
   
  /**
   * Profile picture for user.
   */
   private byte[] profilePic;
   
   
   /**
    * Creates a User.
    */
   public User() {
   
   }
   
   /**
    * Creates a User.
    * @param id
    */
   public User(long id) {
	   
      ResultSet results = null;
      try {
    	 database = new DBAccess();
         results = database.select("Users", "*", "userID = " + id);
			
		 while (results.next()) {
			setId(results.getLong("userID"));
		    setType(results.getString("type"));
			setCanProctor(results.getBoolean("isProctor"));
			setFirstName(results.getString("firstName"));
			setLastName(results.getString("lastName"));
			setUsername(results.getString("orgUsername"));
			setPassword(results.getString("password"));
		    if (results.getString("profilePicFile").length() > 0) {
			   setProfilePic(results.getString("profilePicFile"));
		    }
		    //System.out.println("We got this s**t username: " + curUser.getUsername());
		  }
		  database.closeConnection();
			
	   	
      } catch (Exception e) {
	     // TODO Auto-generated catch block
		 e.printStackTrace();
	  }
   }
   
   /**
	* Returns the id associated with this user.
	*
	* @return The id associated with this user.
	*/
   public long getId() {
       return userId;
   }
	
   /**
	* Sets the id associated with this user.
	*
	* @param id the id to associate with this user.
	*/
   public void setId(long id) {
      userId = id;
   }
   
  /**
   * @return the type of the current user, either "Student" or "Professor"
   */
   public String getType() {
      return type;
   }
   
  /**
   * @return whether the current user can proctor a test.
   */
   public boolean getCanProctor() {
       return canProctor;
   }

  /**
   * @return The current user's first name.
   */
   public String getFirstName() {
      return firstName;
   }
   
  /**
   * @return The current user's last name.
   */
   public String getLastName() {
      return lastName;
   }
   
  /**
   * @return The current user's username.
   */
   public String getUsername() {
       return username;
   }
   
  /**
   * @return The current user's password.
   */
   public String getPassword() {
      return password;
   }
   
  /**
   * @return The current user's profile picture.
   */
   public byte[] getProfilePic() {
      return profilePic;
   }
   
   /**
    * @return The current user's profile picture.
    */
   /*public String getProfilePicName() {
       return profilePic.getName();
   }*/
   
  /**
   * Sets the type of user, either student or professor.
   *
   * @param userType the type to use for the current user, either "Student" or "Professor"
   */
   public void setType(String userType) {
      type = userType;
   }
   
  /**
   * Sets whether the user can proctor a test.
   *
   * @param proctoring whether the current user can proctor a test or not.
   */
   public void setCanProctor(boolean proctoring) {
       canProctor = proctoring;
   }

  /**
   * Sets the user's first name.
   *
   * @param firstname the first name to use for the current user
   *
   pre: firstname != null;
   post: firstName.equals(firstname);
   */
   public void setFirstName(String firstname) {
      firstName = firstname;
   }
   
  /**
   * Sets the user's last name.
   *
   * @param lastname the last name to use for the current user
   */
   public void setLastName(String lastname) {
       lastName = lastname;
   }
   
  /**
   * Sets the user's username.
   *
   * @param usersName the username to use for the current user
   */
   public void setUsername(String usersName) {
       username = usersName;
   }
   
  /**
   * Sets the user's password.
   *
   * @param newPassword the password to use for the current user
   */
   public void setPassword(String newPassword) {
      password = newPassword;
   }
   
  /**
   * Sets the user's profile picture.
   *
   * @param newProfilePic the image file to use as a profile pic for the current user
   */
   public void setProfilePic(String newProfilePic) {
       System.out.println("in model.accounts.User.setProfilePic");
       profilePic = Base64.decodeBase64(newProfilePic);
       
       //Path
       //FileOutputStream imageOutFile = new FileOutputStream("");
   }

   /**
    * Gets the apps directory path.
    * @return
    */
   private String getAppDirPath() {
		String className = getClass().getName();
		String resourceName = className.replace('.', '/') + ".class";
		ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader==null) {
		   classLoader = ClassLoader.getSystemClassLoader();
		}
		URL url = classLoader.getResource(resourceName);
		File currentFile = new File(url.getPath());
		File appDirectory = currentFile.getParentFile();
		return appDirectory.getAbsolutePath();
	}
}
