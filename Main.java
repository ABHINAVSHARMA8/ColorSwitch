package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;


public class Main extends Application  implements Serializable{
	
	//double velocity=0;
	
	transient Stage stage;transient Scene scene;transient AnchorPane groot;
	
	String ballcolor="Blue";int no_of_stars;
	
	int high_score;
	int level=1;
	long travel=0;
	
	
	transient int loadgame_ok=-1;
	
	transient User user=new User(this);
	// Starting page, Fxml.fxml
	
	
	transient Objects<Circle_obs> cobs=new Objects<>();
	transient Objects<Windmill_obs> wobs=new Objects<>();
	transient Objects<Rectangle_obs> robs=new Objects<>();
	transient Objects<Square_obs> sobs=new Objects<>();
	transient Objects<Color_switching_ball> csb=new Objects<>();
	transient Objects<Stars> stars=new Objects<>();
		
	double bally=0;
	ArrayList<Double> cobsy=new ArrayList<Double>();
	ArrayList<Double> csby=new ArrayList<Double>();
	ArrayList<Double> wobsy=new ArrayList<Double>();
	ArrayList<Double> robsy=new ArrayList<Double>();
	ArrayList<Double> sobsy=new ArrayList<Double>();
	ArrayList<Double> starsy=new ArrayList<Double>();
	ArrayList<Long> sobs_color=new ArrayList<Long>();
	ArrayList<Boolean> stars_taken=new ArrayList<Boolean>();
		
		
	
	
	@FXML
	transient private Circle c1_fxml=new Circle();
	
	
	
	@FXML
	transient private Circle c2_fxml=new Circle();
	
	@FXML
	transient private Circle c3_fxml=new Circle();
	
	@FXML
	transient private Circle c1_loading=new Circle();
	
	
	
	@FXML
	transient private Circle c2_loading=new Circle();
	
	@FXML
	transient private Circle c3_loading=new Circle();
	
	@FXML
	transient private Label hslbl=new Label();
	
	
	//transient  Circle ball=new Circle();
	
	transient Button btn=new Button();
	 
	 transient Label lbl=new Label();
	 transient Label errormsg=new Label();
	 transient Label level_lbl=new Label();
	 transient Button pausebtn=new Button();
	 transient Button restartbtn=new Button();
	 transient Button exitbtn=new Button();
	 transient Button usestars=new Button();
	 transient Button savebtn =new Button();
	 boolean pause=false;
	 
	 
	 	public void high_score_serialise() throws FileNotFoundException,IOException{
	 		
			ObjectOutputStream out=null;
			out=new ObjectOutputStream(new FileOutputStream("hs.txt"));
			out.writeObject(this);
			out.close();
	 		
	 	}
	 	
	 	public void high_score_deserialise() throws FileNotFoundException,IOException,ClassNotFoundException{
	 		
			
			ObjectInputStream in=null;Main orgy=null;;
			if(!new File("hs.txt").exists())
				return;

			
				try {
					
					in=new ObjectInputStream(new FileInputStream("hs.txt"));
					
					orgy=(Main)in.readObject();
				}
				
				catch(FileNotFoundException gg) {
					
					System.out.println(gg);
				}
				
				if(orgy!=null)
			
				high_score=orgy.high_score;
			
				else System.out.println("deserilaisation for high score throws an error");

			in.close();
			
			
	 		
	 	}
	 
		public void serialise() throws FileNotFoundException,IOException{
			
			boolean done=true;int count=0;
			
			ObjectOutputStream out=null;
			
			while(done) {
			
				try {
					
					if(new File("out"+count+".txt").exists()) {
						count++;
					}
					
					if(!new File("out"+count+".txt").exists() && count<5) {
						
						out=new ObjectOutputStream(new FileOutputStream("out"+count+".txt"));
						out.writeObject(this);
						done=false;
						
					}
					

					if(count==5)
						done=false;
					

				}
				
				catch(FileNotFoundException ff) {
					
					//out.writeObject(this);
					//done=false;
					
					System.out.println(ff);
				}
			
			}
			
			if(count==5) {
				
				System.out.println("Save limit reached");
				errormsg.setText("Save limit Reached");
			}
			
			out.close();
			
		}
		
		public  Main deserialise() throws FileNotFoundException,IOException,ClassNotFoundException{
			
			ObjectInputStream in=null;
			Main org=null;

			
				try {
					
					in=new ObjectInputStream(new FileInputStream("out"+loadgame_ok+".txt"));
					
					org=(Main)in.readObject();
				}
				
				catch(FileNotFoundException gg) {
					
					System.out.println(gg);
				}
			
			
			

			in.close();
			return org;
			
			
		}
		
		
	
	@Override
	public void start(Stage stage) throws Exception {
		
		this.stage=stage;
		
		Parent  root= FXMLLoader.load(getClass().getResource("Fxml.fxml"));
		//Parent  root2= FXMLLoader.load(getClass().getResource("Menu.fxml"));
		
		 scene = new Scene(root);
		//scene.getStylesheets().add("application.css");
		
		stage.setScene(scene);
		
		stage.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	public void initialize() throws FileNotFoundException, IOException,ClassNotFoundException  {
		// TODO Auto-generated method stub
		
		 
	     //   img_fxml.setImage(new Image(new FileInputStream("/home/alabhinav/Documents/Java Code/Test/src/application/colorswitch_img.png")));
	       high_score_deserialise();
	        rotate_circles();
	        
	        hslbl.setText("The High Score is "+high_score);
	        
	        Animation_loading an_loading=new Animation_loading();
	        Animation_play an_play=new Animation_play();
	         an_loading.start();
	         
	        
	 		
	 		an_play.start();
	        
	        
	        
	        
	       
	}
	
	public void call_Main_menu(ActionEvent e) throws Exception{
		
		Parent root=FXMLLoader.<Parent>load(getClass().getResource("Menu.fxml"));
		//img_Menu.setImage(new Image(new FileInputStream("/home/alabhinav/Documents/Java Code/PROJECT/src/application/backgroundmenu.jpg")));
		 scene=new Scene(root);
		
		 stage=(Stage)((Node)e.getSource()).getScene().getWindow();

		
		stage.setScene(scene);
	
		stage.show();
	}
	
	
	public void exit(ActionEvent e) throws Exception{
		
		
		System.exit(0);
	}
	
	private void rotate(Circle c,boolean reverse,int angle,int duration) {
		
		RotateTransition r=new RotateTransition(Duration.seconds(duration),c);
		
		r.setAutoReverse(reverse);
		r.setByAngle(angle);
		r.setDelay(Duration.seconds(0));
		r.setRate(3);
		r.setCycleCount(18);
		r.play();
	}
	
	private void rotate_circles() {
		
		rotate(c1_fxml,true,360,10);
		rotate(c2_fxml,true,180,18);
		rotate(c3_fxml,true,145,24);
		
	} // end of Fxml.fxml
	
	//start of Menu.fxml
	
	
	
	public void play(ActionEvent e) throws Exception{
		
		Parent root=FXMLLoader.load(getClass().getResource("Loading.fxml"));
		
		 scene=new Scene(root);
		
		 stage=(Stage)((Node)e.getSource()).getScene().getWindow();
		
		//Animation an=new Animation();
		//an.start();

		stage.setScene(scene);
		
		
		stage.show();
		
		
	}
	
	public void view_high_score(ActionEvent e) throws Exception{
		
		Parent root=FXMLLoader.load(getClass().getResource("View_High_Score.fxml"));
		
		 scene=new Scene(root);
		
		 stage=(Stage)((Node)e.getSource()).getScene().getWindow();

		stage.setScene(scene);
		
		
		stage.show();
	}
	
	
	
	public void load(ActionEvent e) throws Exception{
		
		Parent root=FXMLLoader.load(getClass().getResource("loadgame.fxml"));
		
		 scene=new Scene(root);
		
		 stage=(Stage)((Node)e.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		
		stage.show();
		
	}
	
	public void back_Menu(ActionEvent e) throws Exception{
		
		Parent root=FXMLLoader.load(getClass().getResource("Fxml.fxml"));
		
		 scene=new Scene(root);
		
		 stage=(Stage)((Node)e.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		
		stage.show();
	}

	
	// loading starts
	
	public void click_to_play(ActionEvent e) throws Exception{
		
		
		
	//	Parent root=FXMLLoader.load(getClass().getResource("Play.fxml"));
		
		 groot=new AnchorPane();int which=0;long last=0;
		
		/*for(int i=0;i<10;i++) {
			
			csb.add(new Color_switching_ball(-1000*(i+1)));
		}
		
		for(int i=0;i<10;i++) {
			
			wobs.add(new Windmill_obs(-700*(i+1)));
		}
		
		for(int i=0;i<10;i++) {
			
			sobs.add(new Square_obs(-1500*(i+1),i%4));
		}
		
		for(int i=0;i<10;i++) {
			
			robs.add(new Rectangle_obs(-1100*(i+1)));
		}
		
		
		for(int i=0;i<10;i++) {
			
			cobs.add(new Circle_obs(-400*(i+1)));
		}*/
		 
		 	cobs=new Objects<>();
			wobs=new Objects<>();
			robs=new Objects<>();
			sobs=new Objects<>();
			csb=new Objects<>();
			stars=new Objects<>();
				
		
			cobsy=new ArrayList<Double>();
			csby=new ArrayList<Double>();
			wobsy=new ArrayList<Double>();
			robsy=new ArrayList<Double>();
			sobsy=new ArrayList<Double>();
			starsy=new ArrayList<Double>();
			sobs_color=new ArrayList<Long>();
			stars_taken=new ArrayList<Boolean>();
		
		for(long i=0;i<60;i++) {
			
			last-=400;
			//System.out.println(last);
			
			if(which%6==0) {
				
				csb.add(new Color_switching_ball(last));
				csby.add((double)last);
			}
			
			else if(which%6==1) {
				
				wobs.add(new Windmill_obs(last));
				wobsy.add((double)last);
				
			}
			
			else if(which%6==2) {
				sobs.add(new Square_obs(last,(i%4)));
				sobsy.add((double)last);
				sobs_color.add(i%4);
				
			}
			
			else if(which%6==3) {
				
				robs.add(new Rectangle_obs(last));
				robsy.add((double)last);
			}
			
			else if(which%6==4){
				
				cobs.add(new Circle_obs(last));
				cobsy.add((double)last);
			}
			
			else {
				
				stars.add(new Stars(last));
				starsy.add((double)last);
				stars_taken.add(false);
			}
			
			which++;
		}
		
		for(int i=0;i<cobs.size();i++) {
			
			groot.getChildren().add(cobs.get(i).g);
		}
		
		for(int i=0;i<sobs.size();i++) {
			
			groot.getChildren().add(sobs.get(i).s);
		}

		
		for(int i=0;i<robs.size();i++) {
			
			groot.getChildren().add(robs.get(i).g);
		}
		
		for(int i=0;i<csb.size();i++) {
			
			groot.getChildren().add(csb.get(i).g);
		}
		
		for(int i=0;i<wobs.size();i++) {
			
			groot.getChildren().add(wobs.get(i).g);
		}
		
		for(int i=0;i<stars.size();i++) {
			
			groot.getChildren().add(stars.get(i).c);
		}

		
		
		user.setball(300,"Blue");
		
		groot.getChildren().add(user.get_ball().c);
		
		lbl.setMinSize(30,30);
		lbl.setLayoutX(500);
		lbl.setLayoutY(20);
		lbl.setFont(Font.font(15));
		lbl.setTextFill(Color.FLORALWHITE);
		lbl.setText(Integer.toString(user.get_stars()));
		errormsg.setMinSize(30,30);
		errormsg.setLayoutX(400);
		errormsg.setLayoutY(100);
		errormsg.setFont(Font.font(15));
		errormsg.setTextFill(Color.FLORALWHITE);
		
		level_lbl.setMinSize(30,30);
		level_lbl.setLayoutX(500);
		level_lbl.setLayoutY(50);
		level_lbl.setFont(Font.font(15));
		level_lbl.setTextFill(Color.FLORALWHITE);
		level_lbl.setText("Level: "+Integer.toString(level));
		
		btn.setText("Jump");
		btn.setFont(Font.font(10));
		pausebtn.setText("Pause");
		pausebtn.setFont(Font.font(10));
		pausebtn.setLayoutX(0);
		pausebtn.setLayoutY(500);
		restartbtn.setText("Restart");
		restartbtn.setFont(Font.font(10));
		restartbtn.setLayoutX(0);
		restartbtn.setLayoutY(450);
		exitbtn.setText("Exit");
		exitbtn.setFont(Font.font(10));
		exitbtn.setLayoutX(0);
		exitbtn.setLayoutY(400);
		usestars.setText("Use 2 stars");
		usestars.setFont(Font.font(10));
		usestars.setLayoutX(0);
		usestars.setLayoutY(350);
		savebtn.setText("Save");
		savebtn.setFont(Font.font(10));
		savebtn.setLayoutX(0);
		savebtn.setLayoutY(300);
		groot.getChildren().add(btn);
		groot.getChildren().add(lbl);
		groot.getChildren().add(pausebtn);
		groot.getChildren().add(restartbtn);
		groot.getChildren().add(exitbtn);
		groot.getChildren().add(savebtn);
		groot.getChildren().add(errormsg);
		groot.getChildren().add(level_lbl);
		
		groot.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));
		
		
		 scene=new Scene(groot,600,600,Color.BLACK);
		 
		
		
		
		stage=new Stage();
		stage.setScene(scene);
		stage.show();
		
		
	}
	
//	public void copy(Objects a,Objects b) {
//		
//		for(int i=0;i<b.size();i++)
//			a.add(b.get(i));
//	}
	
	public void play_load_game(ActionEvent e) throws IOException,ClassNotFoundException,FileNotFoundException  {
		
		if(loadgame_ok==-1)
			return;
		Main org=deserialise();
		
		user.set_ballcolor(org.ballcolor);
		
		travel=org.travel;
		pause=org.pause;
		level=org.level;
		no_of_stars=org.no_of_stars;
		user.set_stars(org.no_of_stars);
		
		
		 groot=new AnchorPane();int which=0;long last=0;
			

		 	cobs=new Objects<>();
			wobs=new Objects<>();
			robs=new Objects<>();
			sobs=new Objects<>();
			csb=new Objects<>();
			stars=new Objects<>();
			

				
		
			cobsy=new ArrayList<Double>();
			csby=new ArrayList<Double>();
			wobsy=new ArrayList<Double>();
			robsy=new ArrayList<Double>();
			sobsy=new ArrayList<Double>();
			sobs_color=new ArrayList<Long>();
			starsy=new ArrayList<Double>();
			stars_taken=new ArrayList<Boolean>();
			
			for(int i=0;i<org.cobsy.size();i++)
			{
				cobs.add(new Circle_obs(org.cobsy.get(i)));
				cobsy.add(org.cobsy.get(i));
			}
			
			for(int i=0;i<org.wobsy.size();i++)
			{
				wobs.add(new Windmill_obs(org.wobsy.get(i)));
				wobsy.add(org.wobsy.get(i));
			}
			
			for(int i=0;i<org.robsy.size();i++)
			{
				robs.add(new Rectangle_obs(org.robsy.get(i)));
				robsy.add(org.robsy.get(i));
			}
			
			for(int i=0;i<org.sobsy.size();i++)
			{
				sobs.add(new Square_obs(org.sobsy.get(i),org.sobs_color.get(i)));
				sobsy.add(org.sobsy.get(i));
				sobs_color.add(org.sobs_color.get(i));
			}
			
			for(int i=0;i<org.csby.size();i++)
			{
				csb.add(new Color_switching_ball(org.csby.get(i)));
				csby.add(org.csby.get(i));
			}
			
			for(int i=0;i<org.starsy.size();i++)
			{
				stars.add(new Stars(org.starsy.get(i)));
				starsy.add(org.starsy.get(i));
				stars.get(i).set_taken(org.stars_taken.get(i));
				stars_taken.add(org.stars_taken.get(i));
			}
		
		/*for(long i=0;i<60;i++) {
			
			last-=400;
			//System.out.println(last);
			
			if(which%6==0) {
				
				csb.add(new Color_switching_ball(last));
				csby.add((double)last);
			}
			
			else if(which%6==1) {
				
				wobs.add(new Windmill_obs(last));
				wobsy.add((double)last);
				
			}
			
			else if(which%6==2) {
				sobs.add(new Square_obs(last,(i%4)));
				sobsy.add((double)last);
				
				
			}
			
			else if(which%6==3) {
				
				robs.add(new Rectangle_obs(last));
				robsy.add((double)last);
			}
			
			else if(which%6==4){
				
				cobs.add(new Circle_obs(last));
				cobsy.add((double)last);
			}
			
			else {
				
				stars.add(new Stars(last));
				starsy.add((double)last);
			}
			
			which++;
		}*/
		
		for(int i=0;i<cobs.size();i++) {
			
			groot.getChildren().add(cobs.get(i).g);
		}
		
		for(int i=0;i<sobs.size();i++) {
			
			groot.getChildren().add(sobs.get(i).s);
		}

		
		for(int i=0;i<robs.size();i++) {
			
			groot.getChildren().add(robs.get(i).g);
		}
		
		for(int i=0;i<csb.size();i++) {
			
			groot.getChildren().add(csb.get(i).g);
		}
		
		for(int i=0;i<wobs.size();i++) {
			
			groot.getChildren().add(wobs.get(i).g);
		}
		
		for(int i=0;i<stars.size();i++) {
			
			groot.getChildren().add(stars.get(i).c);
		}

		
		
		user.setball(org.bally,org.ballcolor);

		
		groot.getChildren().add(user.get_ball().c);
		
		lbl.setMinSize(30,30);
		lbl.setLayoutX(500);
		lbl.setLayoutY(20);
		lbl.setFont(Font.font(15));
		lbl.setTextFill(Color.FLORALWHITE);
		lbl.setText(Integer.toString(org.no_of_stars));
		errormsg.setMinSize(30,30);
		errormsg.setLayoutX(400);
		errormsg.setLayoutY(100);
		errormsg.setFont(Font.font(15));
		errormsg.setTextFill(Color.FLORALWHITE);
		
		level_lbl.setMinSize(30,30);
		level_lbl.setLayoutX(500);
		level_lbl.setLayoutY(50);
		level_lbl.setFont(Font.font(15));
		level_lbl.setTextFill(Color.FLORALWHITE);
		level_lbl.setText("Level: "+Integer.toString(org.level));
		
		btn.setText("Jump");
		btn.setFont(Font.font(10));
		pausebtn.setText("Pause");
		pausebtn.setFont(Font.font(10));
		pausebtn.setLayoutX(0);
		pausebtn.setLayoutY(500);
		restartbtn.setText("Restart");
		restartbtn.setFont(Font.font(10));
		restartbtn.setLayoutX(0);
		restartbtn.setLayoutY(450);
		exitbtn.setText("Exit");
		exitbtn.setFont(Font.font(10));
		exitbtn.setLayoutX(0);
		exitbtn.setLayoutY(400);
		usestars.setText("Use 2 stars");
		usestars.setFont(Font.font(10));
		usestars.setLayoutX(0);
		usestars.setLayoutY(350);
		savebtn.setText("Save");
		savebtn.setFont(Font.font(10));
		savebtn.setLayoutX(0);
		savebtn.setLayoutY(300);
		groot.getChildren().add(btn);
		groot.getChildren().add(lbl);
		groot.getChildren().add(pausebtn);
		groot.getChildren().add(restartbtn);
		groot.getChildren().add(exitbtn);
		groot.getChildren().add(savebtn);
		groot.getChildren().add(errormsg);
		groot.getChildren().add(level_lbl);
		
		groot.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));
		
		
		Scene scene=new Scene(groot,600,600,Color.BLACK);
		 
		
		
		
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
		
		
		
	}
	
	public void loadgame1(ActionEvent e) throws Exception{
		
		if(new File("out0.txt").exists())
			loadgame_ok=0;
	}
	
	public void loadgame2(ActionEvent e) throws Exception{
		
		if(new File("out1.txt").exists())
			loadgame_ok=1;
	}
	
	public void loadgame3(ActionEvent e) throws Exception{
		
		if(new File("out2.txt").exists())
			loadgame_ok=2;
	}
	
	public void loadgame4(ActionEvent e) throws Exception{
		
		if(new File("out3.txt").exists())
			loadgame_ok=3;
	}
	
	public void loadgame5(ActionEvent e) throws Exception{
		
		if(new File("out4.txt").exists())
			loadgame_ok=4;
	}
	
	public void rotate(Group g,double x) {
		
		g.setRotate(g.getRotate()+x);
	}
	
	public void pause(ActionEvent e) throws Exception{
		
		
		pause=true;
		 Parent proot=FXMLLoader.load(getClass().getResource("Pause.fxml"));
		
		
		scene.setRoot(proot);
		
		 stage=(Stage)((Node)e.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	
	
	
	class Animation_loading extends AnimationTimer{
		
		private long time=0;private boolean alternate=false;private int number=1;

		@Override
		public void handle(long t1) {
			// TODO Auto-generated method stub
			
			long t2=t1-time;
			
			if(t2>15e7) {
				
				
				
				time=t1;
				
				if(alternate) {
				
					if(number==1) {
						c1_loading.setCenterY(c1_loading.getCenterY()-40);
						number=2;
					}
					
					else if(number==2) {
						
						c2_loading.setCenterY(c2_loading.getCenterY()-40);
						number=3;
					}
					
					else {
						c3_loading.setCenterY(c3_loading.getCenterY()-40);
						number=4;
					}
						
					
					if(number==4) {
					alternate=false;
					number=1;
					}
				
				}
				
				else {

					if(number==1) {
						c1_loading.setCenterY(c1_loading.getCenterY()+40);
						number=2;
					}
					
					else if(number==2) {
						
						c2_loading.setCenterY(c2_loading.getCenterY()+40);
						number=3;
					}
					
					else {
						c3_loading.setCenterY(c3_loading.getCenterY()+40);
						number=4;
					}
					
					if(number==4) {
						alternate=true;
						number=1;
					}
					
				}
				
				//System.out.println(off);
			}
			
		
		}
		
		
	
	}
	

	
	class Animation_play extends AnimationTimer{
		
		 boolean reverse =false; // for squares
		 
		@Override
		public void handle(long arg0) {
			// TODO Auto-generated method stub
			
			//System.out.println(user.get_ballcolor());
			level_lbl.setText("Level: "+Integer.toString(level));
			
			if(!pause) {
				
				restartbtn.setOnAction(e->{
					
					try {
						restart(e);
					}
					
					catch(Exception f) {
						
						System.out.println(f);
					}
				});
				
				exitbtn.setOnAction(e->{
					
					try {
						high_score_serialise();
						}
					
						catch(Exception hh) {
							System.out.println(hh);
						}
					
					try {
						exit_pause(e);
					}
					
					catch(Exception f) {
						
						System.out.println(f);
					}
				});
				
			
				pausebtn.setOnAction(e->{
					
					pause=!pause;
					
					if(pause)
						pausebtn.setText("Play");
					
					else pausebtn.setText("Pause");
				});
				
				savebtn.setOnAction(e->{
					
					try {
					save(e);
					}
					
					catch(Exception ff) {
						
						System.out.println("Error in savebtn "+ff);
					}
				});
				
				bally=user.getCenterY();
				
			//	System.out.println(ball.getFill());
				ballcolor=user.get_ballcolor().substring(0, user.get_ballcolor().length());
				/*if(ball.getFill()==Color.BLUE)
					ballcolor="Blue";
				if(ball.getFill()==Color.RED)
					ballcolor="Red";
				if(ball.getFill()==Color.YELLOW)
					ballcolor="Yellow";
				else ballcolor="Green";*/
				
				no_of_stars=user.get_stars();
				
				if(user.get_stars()>high_score)
					high_score=user.get_stars();
				
				lbl.setText(Integer.toString(user.get_stars()));
				for(int i=0;i<cobs.size();i++) {
					
					rotate(cobs.get(i).g,level);
				}
				
				for(int i=0;i<wobs.size();i++) {
				
				rotate(wobs.get(i).g,level);
				}
				
				for(int i=0;i<robs.size();i++) {
					
					rotate(robs.get(i).g,level);
				}
				
	
				for(int i=0;i<csb.size();i++) {
					
					rotate(csb.get(i).g,level);
				}
				
				for(int i=0;i<sobs.size();i++) {
					
					if(!reverse)
						sobs.get(i).s.setX(sobs.get(i).s.getX()+level);
					
					else sobs.get(i).s.setX(sobs.get(i).s.getX()-level);
					
					if(sobs.get(i).s.getX()>=600)
						reverse=true;
					
					if(sobs.get(i).s.getX()<=0)
						reverse=false;
					
					//System.out.println(sobs.get(i).s.getX()+" "+sobs.get(i).s.getTranslateX());
					
				}
		
				
				user.move_down(1+level);
				
				
				btn.setOnAction(e ->{
					
					user.move_up(40+10*level);
					
					
				});
				//System.out.println(travel+" "+level);
				if(user.getCenterY()<=50)
				{	travel++;
					
					user.setCenterY(400);
					//System.out.println(ball.getCenterY()+" "+scene.getHeight()/2);
					
					for(int i=0;i<cobs.size();i++) {
						cobs.get(i).come_down(400);
						cobsy.set(i,cobsy.get(i)+400);
					}
					
					
					
					for(int i=0;i<csb.size();i++) {
						csb.get(i).come_down(400);
						csby.set(i,csby.get(i)+400);
					}
					
					for(int i=0;i<wobs.size();i++) {
						wobs.get(i).come_down(400);
						wobsy.set(i,wobsy.get(i)+400);
					}
						
					for(int i=0;i<robs.size();i++) {
						robs.get(i).come_down(400);
						robsy.set(i,robsy.get(i)+400);
					}
					
					for(int i=0;i<sobs.size();i++) {
						sobs.get(i).come_down(400);
						sobsy.set(i,sobsy.get(i)+400);
					}
					
					for(int i=0;i<stars.size();i++) {
						stars.get(i).come_down(400);
						starsy.set(i,starsy.get(i)+400);
					}
				}
				
				for(int i=0;i<cobs.size();i++) {
					
					if(cobs.get(i).collided(user))
						
					{	
					
						groot.getChildren().add(usestars);
						
						pause=true;
						
						usestars.setOnAction(e->{
							
							if(user.get_stars()>=2) {
								
								user.change_stars(-2);
								user.setCenterY(-300);
								pause=false;
								groot.getChildren().remove(usestars);
							}
							
						});
						
					}
				}
					
				
				
				
				for(int i=0;i<robs.size();i++) {
					
					
					
					if(robs.get(i).collided(user))
					{	
						
						
						groot.getChildren().add(usestars);
						
						pause=true;
						
						usestars.setOnAction(e->{
							
							if(user.get_stars()>=2) {
								
								user.change_stars(-2);
								user.setCenterY(-300);
								pause=false;
								groot.getChildren().remove(usestars);
							}
							
						});
						
					}
						
				}
				
				for(int i=0;i<wobs.size();i++) {
					
					
					
					if(wobs.get(i).collided(user))
					{	
						
						
						groot.getChildren().add(usestars);
						
						pause=true;
						
						usestars.setOnAction(e->{
							
							if(user.get_stars()>=2) {
								
								user.change_stars(-2);
								user.setCenterY(-200);
								pause=false;
								groot.getChildren().remove(usestars);
							}
							
						});
						
					}
						
				}
				
				for(int i=0;i<sobs.size();i++) {
					
					
					
					if(sobs.get(i).collided(user))
					{	
						
						
						groot.getChildren().add(usestars);
						
						pause=true;
						
						usestars.setOnAction(e->{
							
							if(user.get_stars()>=2) {
								
								user.change_stars(-2);
								user.setCenterY(-200);
								pause=false;
								groot.getChildren().remove(usestars);
							}
							
						});
						
					}
						
				}
				
				for(int i=0;i<csb.size();i++) {
					
		
					csb.get(i).if_collided(user);
						
				}
				
				for(int i=0;i<stars.size();i++) {
					
					
					stars.get(i).if_collided(user);
					stars_taken.set(i,stars.get(i).get_taken());
						
				}
				
				for(int i=0;i<cobs.size();i++) {
					//System.out.println(i+" "+cobs.size());
				if(cobs.get(i).if_passed()) {
					
					groot.getChildren().remove(cobs.get(i));
					cobs.remove(i);
					cobsy.remove(i);
					
				}
			}
				
			
			Iterator it=stars_taken.iterator();
			
			while(it.hasNext()) {
				
				boolean x=(boolean)it.next();
			}
			
			for(int i=0;i<robs.size();i++) {
				
				
				
				if(robs.get(i).if_passed()) {
					groot.getChildren().remove(robs.get(i));
					robs.remove(i);
					robsy.remove(i);
				}
			}
			
			for(int i=0;i<wobs.size();i++) {
				
				
				
				if(wobs.get(i).if_passed()) {
					
					groot.getChildren().remove(wobs.get(i));
					wobs.remove(i);
					wobsy.remove(i);
				}
					
			}
			
			for(int i=0;i<sobs.size();i++) {
				
				
				
				if(sobs.get(i).if_passed()) {
					groot.getChildren().remove(sobs.get(i));
					sobs.remove(i);
					sobsy.remove(i);
					sobs_color.remove(i);
				}
			}
			
			for(int i=0;i<csb.size();i++) {
				
	
				if(csb.get(i).if_passed()) {
					groot.getChildren().remove(csb.get(i));
					csb.remove(i);
					csby.remove(i);
				}
					
			}
			
			for(int i=0;i<stars.size();i++) {
				
				
				if(stars.get(i).if_passed()) {
					groot.getChildren().remove(stars.get(i));
					stars.remove(i);
					starsy.remove(i);
					stars_taken.remove(i);
				}
					
			}
				
				if(travel>=67) {
					travel=0;
					int last=0;int which=0;
					for(long i=0;i<60;i++) {
						
						last-=400;
						//System.out.println(last);
						
						if(which%6==0) {
							
							csb.add(new Color_switching_ball(last));
							csby.add((double)last);
						}
						
						else if(which%6==1) {
							
							wobs.add(new Windmill_obs(last));
							wobsy.add((double)last);
							
						}
						
						else if(which%6==2) {
							sobs.add(new Square_obs(last,i%4));
							sobsy.add((double)last);
							sobs_color.add((Long)i%4);
							
						}
						
						else if(which%6==3) {
							
							robs.add(new Rectangle_obs(last));
							robsy.add((double)last);
						}
						
						else if(which%6==4){
							
							cobs.add(new Circle_obs(last));
							cobsy.add((double)last);
						}
						
						else {
							
							stars.add(new Stars(last));
							starsy.add((double)last);
							stars_taken.add(false);
						}
						
						which++;
					}
					
					for(int i=0;i<cobs.size();i++) {
						
						groot.getChildren().add(cobs.get(i).g);
					}
					
					for(int i=0;i<sobs.size();i++) {
						
						groot.getChildren().add(sobs.get(i).s);
					}
	
					
					for(int i=0;i<robs.size();i++) {
						
						groot.getChildren().add(robs.get(i).g);
					}
					
					for(int i=0;i<csb.size();i++) {
						
						groot.getChildren().add(csb.get(i).g);
					}
					
					for(int i=0;i<wobs.size();i++) {
						
						groot.getChildren().add(wobs.get(i).g);
					}
					
					for(int i=0;i<stars.size();i++) {
						
						groot.getChildren().add(stars.get(i).c);
					}
	
					level++;
				}
			
			}
		}
			
			
			
			
	}
	
	//end of Play.fxml
	
	//start of Pause.fxml
	
	public void resume(ActionEvent e)throws Exception {
		
		pause=false;
		
		AnchorPane proot=new AnchorPane();
		
		Scene scene=new Scene(proot);
			
		
		
		Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void restart(ActionEvent e)throws Exception {
		
		
		
		Parent root=FXMLLoader.load(getClass().getResource("Loading.fxml"));
		
		 scene=new Scene(root);
		
		 stage=(Stage)((Node)e.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		
		stage.show();
	}
	
	
	public void save(ActionEvent e)throws Exception {
		
		serialise();
		
	}
	
	public void exit_pause(ActionEvent e) throws Exception{
		
		Parent root=FXMLLoader.load(getClass().getResource("Fxml.fxml"));
		
		 Scene scene=new Scene(root);
		
		 Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		//stage.centerOnScreen();
		
		stage.show();
	}
	
	


	
	
	public static void main(String[] args) {
		
		launch(args);
	}
}

class Ball{
	
	 Circle c;
	
	 Ball(){
		c=new Circle();
		c.setCenterX(300);
		
		c.setRadius(10);
		

	}
	
	double getCenterY() {
		return c.getCenterY();
	}
	
	
	void move_down(double y) {
		
		c.setCenterY(c.getCenterY()+y);
	}
	
	void move_up(double y) {
		
		c.setCenterY(c.getCenterY()-y);
	}
	
	void setCenterY(double y) {
		
		c.setCenterY(c.getCenterY()+y);
	}
}

class User{
	
	Main game; private int stars;
	
	private String ballcolor="";
	
	private Ball ball;
	
	 User(Main game){
		
		this.game=game;
		this.stars=0;
		this.ballcolor="Blue";
		this.ball=new Ball();
	}
	
	Main get_game() {
		
		return this.game;
	}
	
	void set_ballcolor(String bc) {
		
		this.ballcolor=bc.substring(0,bc.length());
	}
	
	String get_ballcolor() {
		
		return this.ballcolor;
	}
	
	void setball(double y,String s) {
		
		ball.c.setCenterY(y);
		if(s.equals("Red"))
			ball.c.setFill(Color.RED);
		
		else if(s.equals("Blue"))
			ball.c.setFill(Color.BLUE);
		
		else if(s.equals("Yellow"))
			ball.c.setFill(Color.YELLOW);
		
		else ball.c.setFill(Color.GREEN);
		
	}
	
	Ball get_ball() {
		
		return this.ball;
	}
	
	double getCenterY() {
		
		
		return ball.getCenterY();
	}
	
	void move_down(double y) {
		
		ball.move_down(y);
	}
	
	void move_up(double y) {
		
		ball.move_up(y);
	}
	
	void setCenterY(double y) {
		
		ball.setCenterY(y);
	}
	
	int get_stars() {
		
		return stars;
	}
	
	void change_stars(int x) {
		
		this.stars+=x;
	}
	void set_stars(int x) {
		
		this.stars=x;
	}
	
}


class Objects<T>{
	
	private ArrayList<T> list;int size;
	
	Objects(){
		
		list=new ArrayList<T>();
		size=0;
	}
	
	void add(T o) {
		
		list.add(o);
		size++;
	}
	
	T get(int i) {
		
		return list.get(i);
	}
	
	int size() {
		
		return size;
	}
	
	void remove(int i) {
		
		list.remove(i);
		size--;
	}
}

