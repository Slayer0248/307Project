package view.navigation;

import view.accounts.ProfileUI;
import view.testCreation.AdvancedGeneratorPage1UI;
import view.testbank.TestBankUI;
import view.question.QuestionBankUI;
import view.question.QuestionUI;
import view.questioneditor.QuestionEditorUI;
import view.grading.administration.ProctorTestMainUI;
import view.grading.administration.GradeTestMainUI;
import view.logins.ProfessorLoginUI;
import view.administration.ClassAdministrationUI;

import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.*;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.accounts.User;
import model.test.Test;
import model.test.TestBank;

/**
 * view file for the Main view of the tool
 *
 */
public class ProfessorMainViewUI extends Scene {

	private User user;
	
	private Pane mainPane;
	private ScrollPane mainScrollPane;
	private Pane viewPane;
	
	private Pane navPane;
	private GridPane buttonsView;
	private ArrayList<Button> navButtons;
	private ImageView navCollapser;
	
	private double originalHeight = -1;
	private int numNavActions = 0;
	
	private Button classAdminButton;
	private Button logoutButton;
	
	private ParallelTransition parallelTransition;
	
	
	//private ImageView classAdminButton;
	
	/**
	 * ui constructor
	 * 
	 * @param width
	 * @param height
	 */
	public ProfessorMainViewUI(double width, double height) {
		super(new Pane(), width, height);
		mainPane = (Pane)super.getRoot();
		parallelTransition = new ParallelTransition();
	}
	
	/**
	 * Method to perform the final setup
	 * 
	 * @param fromView
	 * @param userId
	 * @throws Exception
	 */
	public void performFinalSetup(String fromView, long userId) throws Exception {
		mainPane.resizeRelocate(0, 0, 600, 600);
		
		user = new User(userId);
		
		mainScrollPane = new ScrollPane();
		mainScrollPane.resizeRelocate(0, 64, 600, 536);
		
		if (fromView.equals("loginView")) {
			ProfileUI profileView = new ProfileUI();
			/*try {
				FXMLLoader.setRoot()
				profileView = FXMLLoader.load(getClass().getResource("/view/accounts/ProfileUI.fxml"));
			}
			catch (Exception e) {
				e.printStackTrace();
		        System.err.println(e.getClass().getName() + ": " + e.getMessage());
		        System.exit(0);
		    }*/
			
			profileView.setupForUser(userId);
			
			viewPane = (Pane)profileView;
			viewPane.relocate(0, 0);
			mainScrollPane.setContent(viewPane);
		}
		else if(fromView.equals("testCreator")) {
			TestBankUI testBank = new TestBankUI(ProfessorMainViewUI.this, user);
			viewPane = (Pane)testBank;
			viewPane.relocate(0, 0);
			mainScrollPane.setContent(viewPane);
			//setActivePane(new TestBankUI(ProfessorMainViewUI.this));
		}
		else if(fromView.equals("questionEditor")) {
			QuestionBankUI questionBank = new QuestionBankUI(ProfessorMainViewUI.this, user);
			viewPane = (Pane)questionBank;
			viewPane.relocate(0, 0);
			mainScrollPane.setContent(viewPane);
			//setActivePane(new TestBankUI(ProfessorMainViewUI.this));
		}
		
		mainPane.getChildren().add(mainScrollPane);
		
		//viewPane.
		
		initMainView();
		setupEventHandlers();
	}
	
	/**
	 * gets the active userId
	 * @return
	 */
	public long getActiveUserId() {
		//return user.getId();
		return 0;
	}
	
	/**
	 * gets the mainscrollpane
	 * @return
	 */
	public ScrollPane getMainScrollPane() {
		return mainScrollPane;
	}
	
	/**
	 * gets the navCollapser
	 * @return
	 */
	public ImageView getNavCollapser() {
		return navCollapser;
	}
	
	/**
	 * gets the parallelTransition
	 * @return
	 */
	public ParallelTransition getTransition() {
		return parallelTransition;
	}
	
	/**
	 * returns whether this is collapsed
	 * @return
	 */
	public boolean isCollapsed() {
		if (originalHeight != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * sets the active pane
	 * @param newPane
	 * @return
	 */
	public QuestionBankUI setActivePane(Pane newPane) {
		viewPane = (Pane)newPane;
		viewPane.relocate(0, 0);
		mainScrollPane.setContent(viewPane);
		
		if(viewPane instanceof ClassAdministrationUI) {
			classAdminButton.setText("Profile");
		}
		else {
			
			classAdminButton.setText("Classes");
		}
		return null;
	}
	
	/**
	 * initializes the main view
	 */
	private void initMainView() {
		navPane = new Pane();
		navPane.resizeRelocate(0, 0, getWidth(), 85);
		buttonsView = new GridPane();
		buttonsView.resizeRelocate(0, 0, getWidth(), 64);
		navButtons = new ArrayList<Button>();
	
		/*setup question bank button*/
		Button questionBankButton = new Button("Question Bank");
		questionBankButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
					setActivePane(new QuestionBankUI(ProfessorMainViewUI.this,user));
				//System.out.println("Show Question Bank UI.");
			}
		});
		buttonsView.add(questionBankButton, 0, 0);
		navButtons.add(questionBankButton);
		
		/*setup test bank button*/
		Button testBankButton = new Button("Test Bank");
		testBankButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setActivePane(new TestBankUI(ProfessorMainViewUI.this, user));
				System.out.println("Show Test Bank UI.");
			}
		});
		buttonsView.add(testBankButton, 1, 0);
		navButtons.add(testBankButton);
		
		/*setup proctor tests button*/
		Button proctorTestsButton = new Button("Proctor Tests");
		proctorTestsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setActivePane(new ProctorTestMainUI(ProfessorMainViewUI.this, user));
				//System.out.println("Show Proctor Tests UI.");
			}
		});
		buttonsView.add(proctorTestsButton, 2, 0);
		navButtons.add(proctorTestsButton);
	
		/*setup grades button*/
		Button gradesViewButton = new Button("Grades");
		gradesViewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setActivePane(new GradeTestMainUI(ProfessorMainViewUI.this, user));
			}
		});
		buttonsView.add(gradesViewButton, 3, 0);
		navButtons.add(gradesViewButton);
		
		
		//final setup for the nav bar buttons
		buttonsView.setGridLinesVisible(true);
		for (int i=0; i<navButtons.size(); i++) {
			Button curButton = navButtons.get(i);
			curButton.setPrefSize(150, 64);
			curButton.setTextFill(Color.WHITE);
			//curButton.setStyle("-fx-background-color: linear-gradient(#FF9900, #FFAD33); -fx-font-size: 14px;");
			//curButton.setStyle("-fx-background-color: linear-gradient(#FF9900, #FFC266); -fx-font-size: 14px;");
			curButton.setStyle("-fx-background-color: linear-gradient(#E68A00,#FFAD33); -fx-font-size: 17px; ");
			//curButton.setFont(value);
			
			
			/*DropShadow dropShadow = new DropShadow();
			dropShadow.setRadius(5.0);
			dropShadow.setOffsetX(3.0);
			dropShadow.setOffsetY(3.0);
			dropShadow.setColor(Color.color(0.4, 0.5, 0.5));*/
			//curButton.setEffect(dropShadow);
		}
		
		/*add the button to expand or contract the main view*/
		Image image = new Image("view/navigation/images/MainViewContract.png");
		navCollapser = new ImageView();
		navCollapser.setImage(image);
		navCollapser.resizeRelocate(275, 39, 50, 50);
		navCollapser.setPreserveRatio(true);
		navCollapser.setSmooth(true);
		navCollapser.setCache(true);
		
		/*TODO: Add event handler to this later*/
		
		
		/*add the link to the accounts page to the main view*/
		/*Image image2 = new Image("view/navigation/images/AccountsButton.png");
		classAdminButton = new ImageView();
		classAdminButton.setImage(image2);
		classAdminButton.setFitWidth(60);
		classAdminButton.setFitHeight(60);
		classAdminButton.resizeRelocate(510, 510, 60, 60);*/ /*looks even enough*/
		/*classAdminButton.setPreserveRatio(true);
		classAdminButton.setSmooth(true);
		classAdminButton.setCache(true);*/
		classAdminButton = new Button("Classes");
		classAdminButton.setPrefSize(80, 21);
		classAdminButton.relocate(0, 64);
		classAdminButton.setTextFill(Color.WHITE);
		classAdminButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				//Stage testStage = (Stage)(getWindow());
				int done = 0;
				if(!(viewPane instanceof ClassAdministrationUI)) {
				   setActivePane(new ClassAdministrationUI(ProfessorMainViewUI.this, user));
				   done++;
				   System.out.println("Show Class Administration UI.");
				}
				else if (done == 0) {
					ProfileUI profileView = new ProfileUI();
					profileView.setupForUser(user.getId());
					
					viewPane = (Pane)profileView;
					viewPane.relocate(0, 0);
					mainScrollPane.setContent(viewPane);
					classAdminButton.setText("Classes");
					System.out.println("Show profile UI.");
				}
				
			}
		});
		classAdminButton.setStyle("-fx-background-color: linear-gradient(#E68A00,#FFAD33); -fx-font-size: 12px; ");
		
		logoutButton = new Button("Logout");
		logoutButton.setPrefSize(80, 21);
		logoutButton.relocate(520, 64);
		logoutButton.setTextFill(Color.WHITE);
		logoutButton.setStyle("-fx-background-color: linear-gradient(#E68A00,#FFAD33); -fx-font-size: 12px; ");
		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Logout");
				Stage testStage = (Stage)(getWindow());
				ProfessorLoginUI loginPane = new ProfessorLoginUI("Professor");
				Scene loginScene = new Scene(loginPane, 600, 600);
				testStage.setScene(loginScene);
			}
		});
		/*TODO: Add event handler to this later*/
		
		/*Rectangle2D viewportRect = new Rectangle2D(60, 60, 530, 530);
		accountsImageView.setViewport(viewportRect);*/
		/*classAdminButton = new ToggleButton("", accountsImageView);
		classAdminButton.resizeRelocate(500, 500, 60, 60);*/
		//classAdminButton.setStyle("-fx-graphic: url('view/AccountsButton.png');");
		//classAdminButton.setStyle("-fx-focus-color: transparent;");
		
		navPane.getChildren().add(navCollapser);
		navPane.getChildren().add(buttonsView);
		navPane.getChildren().add(logoutButton);
		
		
		
		mainPane.getChildren().add(navPane);
		mainPane.getChildren().add(classAdminButton);
	
	}
	
	/**
	 * sets up Event handlers
	 */
	private void setupEventHandlers() {
		//Show Class Administration UI.
		//MouseEvent.
		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Logout");
			}
		});
		
		navCollapser.addEventHandler(MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//navCollapser.setDisable(true);
				int done = 0;
				//System.out.println("Scroll Pane y:"+  mainScrollPane.getBoundsInParent().getMinY());
				if (mainScrollPane.getBoundsInParent().getMinY() == 64.0 && e.getClickCount() == 1) {
					done++;
					System.out.println("Collapse navigation bar");
					TranslateTransition translateTransition =
				            new TranslateTransition(Duration.millis(100), navPane);
					translateTransition.setFromY(0);
					translateTransition.setToY(-64);
					translateTransition.setCycleCount(1);
					
					/*FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), buttonsView);
					fadeTransition.setFromValue(1.0);
					fadeTransition.setByValue(0.1);
					fadeTransition.setFromValue(0.1);
					fadeTransition.setCycleCount(Timeline.INDEFINITE);*/
					
					TranslateTransition translateTransition2 =
				            new TranslateTransition(Duration.millis(100), mainScrollPane);
					translateTransition2.setFromY(64);
					translateTransition2.setToY(0);
					translateTransition2.setCycleCount(1);
					
					TranslateTransition translateTransition3 =
				            new TranslateTransition(Duration.millis(100), classAdminButton);
					translateTransition3.setFromX(0);
					translateTransition3.setToX(-80);
					translateTransition3.setCycleCount(1);
					
					
					parallelTransition = new ParallelTransition();
					parallelTransition.getChildren().addAll(
							translateTransition,
							translateTransition2,
							translateTransition3
					);
					parallelTransition.setCycleCount(1);
					parallelTransition.play();
					
					mainScrollPane.resizeRelocate(0, 0, 600, 600);
					if (viewPane.getPrefHeight() < 600) {
						originalHeight = viewPane.getPrefHeight();
						viewPane.setPrefHeight(600);
						
						if (viewPane instanceof QuestionBankUI) {
							QuestionBankUI qbui = (QuestionBankUI)viewPane;
							qbui.getScrollbar().setPrefHeight(557);
						}
						else if (viewPane instanceof TestBankUI) {
							TestBankUI tbui = (TestBankUI)viewPane;
							tbui.getScrollbar().setPrefHeight(557);
						}
					}
					
					Image image = new Image("view/navigation/images/MainViewExpand.png");
					navCollapser.setImage(image);
					navCollapser.setPreserveRatio(true);
					navCollapser.setSmooth(true);
					navCollapser.setCache(true);
					
					
					
					//mainScrollPane.resizeRelocate(600, 600);
				}
				else if (done == 0 && e.getClickCount() == 1) {
					System.out.println("Expand navigation bar");
					if (originalHeight > 0) {
						//originalHeight = viewPane.getPrefHeight();
						viewPane.setPrefHeight(originalHeight);
						//mainScrollPane.resizeRelocate(0, 0, 600, 536);
						//mainScrollPane.setPrefHeight(536);
						//System.out.println("Scroll Pane y:"+  mainScrollPane.getLayoutY());
					}
					
					
					TranslateTransition translateTransition =
				            new TranslateTransition(Duration.millis(100), navPane);
					translateTransition.setFromY(-64);
					translateTransition.setToY(0);
					translateTransition.setCycleCount(1);
					
					/*FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), buttonsView);
					fadeTransition.setFromValue(0.1);
					fadeTransition.setByValue(0.1);
					fadeTransition.setFromValue(1.0);
					fadeTransition.setCycleCount(Timeline.INDEFINITE);*/
					
					TranslateTransition translateTransition2 =
				            new TranslateTransition(Duration.millis(100), mainScrollPane);
					translateTransition2.setFromY(0);
					translateTransition2.setToY(64);
					translateTransition2.setCycleCount(1);
					
					TranslateTransition translateTransition3 =
				            new TranslateTransition(Duration.millis(100), classAdminButton);
					translateTransition3.setFromX(-80);
					translateTransition3.setToX(0);
					translateTransition3.setCycleCount(1);
					
					/*TranslateTransition translateTransition4 =
				            new TranslateTransition(Duration.millis(100), logoutButton);
					translateTransition4.setFromY(0);
					translateTransition4.setToY(64);
					translateTransition4.setCycleCount(1);*/
					
					parallelTransition = new ParallelTransition();
					parallelTransition.getChildren().addAll(
							translateTransition,
							translateTransition2,
							translateTransition3
					);
					parallelTransition.setCycleCount(1);
					parallelTransition.play();
					
					mainScrollPane.resizeRelocate(0, 0, 600, 536);
					
					Image image = new Image("view/navigation/images/MainViewContract.png");
					navCollapser.setImage(image);
					navCollapser.setPreserveRatio(true);
					navCollapser.setSmooth(true);
					navCollapser.setCache(true);
					
					if (viewPane instanceof QuestionBankUI) {
						QuestionBankUI qbui = (QuestionBankUI)viewPane;
						qbui.getScrollbar().setPrefHeight(493);
					}
					else if (viewPane instanceof TestBankUI) {
						TestBankUI tbui = (TestBankUI)viewPane;
						tbui.getScrollbar().setPrefHeight(493);
					}
				}
				
				//navCollapser.setDisable(false);
			}
		});
	}
	
}
