package application;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Game_objects {
	
	 Group g;protected double y;
	protected int id,total=0;
	
	
	{
		this.id=++total;
		
	}
	
	Game_objects(){
		
		g=new Group();
	}
	
	abstract boolean if_collided(User user);
	
	void come_down(double yy) {
		
		g.setTranslateY(g.getTranslateY()+yy);
		this.y+=yy;
	}
	
	boolean if_passed() {
		
		return y>=700;
	}

}

class Stars extends Game_objects{
	
	 Circle c=new Circle();
	
	
	private boolean taken = false;
	
	Stars(double y){
		
		super();
		this.y=y;
		c.setRadius(10);
		c.setFill(Color.WHITE);
		c.setCenterX(300);
		c.setCenterY(y);
		
		
	}

	@Override
	boolean if_collided( User user) {
		// TODO Auto-generated method stub
		
		if( Shape.intersect(user.get_ball().c,c).getBoundsInLocal().getWidth()!=-1) {
			
			if(!taken)
				user.change_stars(1);
			taken=true;
			
			
		}
		
		
		
		return taken;
	}
	
	@Override
	void come_down(double yy) {
		
		c.setTranslateY(c.getTranslateY()+yy);
		this.y+=yy;
	}
	
	void set_taken(boolean taken) {
		
		this.taken=taken;
	}
	
	boolean get_taken() {
		
		return this.taken;
	}
}

class Color_switching_ball extends Game_objects{
	
	private Arc arc1,arc2,arc3,arc4;
	
	Color_switching_ball(double y){
		
		super();
		this.y=y;
		 arc1=new Arc();
			
			arc1.setCenterX(300.0f);
			arc1.setCenterY(y);
			arc1.setRadiusX(100.0f);
			arc1.setRadiusY(100.0f);
			arc1.setStartAngle(0);
			arc1.setStrokeWidth(10);
			arc1.setStroke(Color.BLUE);
			arc1.setType(ArcType.OPEN);
			arc1.setLength(90);
			arc1.setFill(Color.WHITE);
			
			 arc2=new Arc();
			
			
			arc2.setCenterX(300.0f);
			arc2.setCenterY(y);
			arc2.setRadiusX(100.0f);
			arc2.setRadiusY(100.0f);
			arc2.setStartAngle(90);
			arc2.setStrokeWidth(10);
			arc2.setStroke(Color.RED);
			arc2.setType(ArcType.OPEN);
			arc2.setLength(90);
			arc2.setFill(Color.WHITE);
			
			arc3=new Arc();
			
			
			arc3.setCenterX(300.0f);
			arc3.setCenterY(y);
			arc3.setRadiusX(100.0f);
			arc3.setRadiusY(100.0f);
			arc3.setStartAngle(180);
			arc3.setStrokeWidth(10);
			arc3.setStroke(Color.YELLOW);
			arc3.setType(ArcType.OPEN);
			arc3.setLength(90);
			arc3.setFill(Color.WHITE);
			
			 arc4=new Arc();
			
			
			arc4.setCenterX(300.0f);
			arc4.setCenterY(y);
			arc4.setRadiusX(100.0f);
			arc4.setRadiusY(100.0f);
			arc4.setStartAngle(270);
			arc4.setStrokeWidth(10);
			arc4.setStroke(Color.GREEN);
			arc4.setType(ArcType.OPEN);
			arc4.setLength(90);
			arc4.setFill(Color.WHITE);
			
			arc1.setRadiusX(30.0f);
			arc1.setRadiusY(30.0f);
			arc2.setRadiusX(30.0f);
			arc2.setRadiusY(30.0f);
			arc3.setRadiusX(30.0f);
			arc3.setRadiusY(30.0f);
			arc4.setRadiusX(30.0f);
			arc4.setRadiusY(30.0f);
			
			arc1.setStrokeWidth(0);
			arc1.setStroke(Color.BLUE);
			arc1.setType(ArcType.ROUND);
			//arc.setLength(90);
			arc1.setFill(Color.BLUE);
			
			arc2.setStrokeWidth(0);
			arc2.setStroke(Color.BLUE);
			arc2.setType(ArcType.ROUND);
			//arc2.setLength(90);
			arc2.setFill(Color.RED);
			
			arc4.setStrokeWidth(0);
			arc4.setStroke(Color.BLUE);
			arc4.setType(ArcType.ROUND);
			//arc4.setLength(90);
			arc4.setFill(Color.GREEN);
			
			arc3.setStrokeWidth(0);
			arc3.setStroke(Color.BLUE);
			arc3.setType(ArcType.ROUND);
			//arc3.setLength(90);
			arc3.setFill(Color.YELLOW);
			
			
			g.getChildren().addAll(arc1,arc2,arc3,arc4);
	
		
		
	}

	@Override
	boolean if_collided( User user) {
		// TODO Auto-generated method stub
		
		
		if( Shape.intersect(user.get_ball().c,arc1).getBoundsInLocal().getWidth()!=-1) {
			user.get_ball().c.setFill(arc1.getFill());
			user.set_ballcolor("Blue");
			
		}
		
		
	
	
	if( Shape.intersect(user.get_ball().c,arc2).getBoundsInLocal().getWidth()!=-1) {
		user.get_ball().c.setFill(arc2.getFill());
		user.set_ballcolor("Red");
		
	}
	
	if( Shape.intersect(user.get_ball().c,arc3).getBoundsInLocal().getWidth()!=-1) {
		user.get_ball().c.setFill(arc3.getFill());
		user.set_ballcolor("Yellow");
		
	}
	
	if( Shape.intersect(user.get_ball().c,arc4).getBoundsInLocal().getWidth()!=-1) {
		user.get_ball().c.setFill(arc4.getFill());
		user.set_ballcolor("Green");
		
	}
	
	return true;
	
	}
	
	
}

interface Collision{
	
	public boolean collided(User user);
}


class Obstacles extends Game_objects implements Collision{

	@Override
	public boolean collided(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean if_collided( User user) {
		// TODO Auto-generated method stub
		
		collided(user);
		return false;
	}
	
	
}

class Circle_obs extends Obstacles{
	
	private Arc arc1,arc2,arc3,arc4;
	
	Circle_obs(double y){
		this.y=y;
		 arc1=new Arc();
			
			arc1.setCenterX(300.0f);
			arc1.setCenterY(y);
			arc1.setRadiusX(100.0f);
			arc1.setRadiusY(100.0f);
			arc1.setStartAngle(0);
			arc1.setStrokeWidth(10);
			arc1.setStroke(Color.BLUE);
			arc1.setType(ArcType.OPEN);
			arc1.setLength(90);
			arc1.setFill(Color.WHITE);
			
			 arc2=new Arc();
			
			
			arc2.setCenterX(300.0f);
			arc2.setCenterY(y);
			arc2.setRadiusX(100.0f);
			arc2.setRadiusY(100.0f);
			arc2.setStartAngle(90);
			arc2.setStrokeWidth(10);
			arc2.setStroke(Color.RED);
			arc2.setType(ArcType.OPEN);
			arc2.setLength(90);
			arc2.setFill(Color.WHITE);
			
			arc3=new Arc();
			
			
			arc3.setCenterX(300.0f);
			arc3.setCenterY(y);
			arc3.setRadiusX(100.0f);
			arc3.setRadiusY(100.0f);
			arc3.setStartAngle(180);
			arc3.setStrokeWidth(10);
			arc3.setStroke(Color.YELLOW);
			arc3.setType(ArcType.OPEN);
			arc3.setLength(90);
			arc3.setFill(Color.WHITE);
			
			 arc4=new Arc();
			
			
			arc4.setCenterX(300.0f);
			arc4.setCenterY(y);
			arc4.setRadiusX(100.0f);
			arc4.setRadiusY(100.0f);
			arc4.setStartAngle(270);
			arc4.setStrokeWidth(10);
			arc4.setStroke(Color.GREEN);
			arc4.setType(ArcType.OPEN);
			arc4.setLength(90);
			arc4.setFill(Color.WHITE);
			
			
			g.getChildren().addAll(arc1,arc2,arc3,arc4);
	
	}
	
	@Override
	public boolean collided(User user) {
		
		if( Shape.intersect(user.get_ball().c,arc1).getBoundsInLocal().getWidth()!=-1) {
			
			if(arc1.getStroke()!=user.get_ball().c.getFill())
			
				return true;
		
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,arc2).getBoundsInLocal().getWidth()!=-1) {
			
			if(arc2.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,arc3).getBoundsInLocal().getWidth()!=-1) {
			
			if(arc3.getStroke()!=user.get_ball().c.getFill())
			
				return true;
				
			
		}
		
		if( Shape.intersect(user.get_ball().c,arc4).getBoundsInLocal().getWidth()!=-1) {
			
			if(arc4.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
			
		}
		
		return false;

	}
}

class Windmill_obs extends Obstacles{
	
	private Line l1,l2,l3,l4;
	
	public Windmill_obs(double y) {
		this.y=y;
		l1=new Line(350,y,350,y+100);
		l2=new Line(350,y,350,y-100);
		l3=new Line(450,y,350,y);
		l4=new Line(350,y,250,y);
		
		l1.setStrokeWidth(8);
		l2.setStrokeWidth(8);
		l3.setStrokeWidth(8);
		l4.setStrokeWidth(8);
		
		l1.setStroke(Color.RED);
		l2.setStroke(Color.BLUE);
		l3.setStroke(Color.YELLOW);
		l4.setStroke(Color.GREEN);
		
	
		
		g.getChildren().addAll(l1,l2,l3,l4);
	}

	
	@Override
	public boolean collided(User user) {
		
		if( Shape.intersect(user.get_ball().c,l1).getBoundsInLocal().getWidth()!=-1) {
			
			if(l1.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,l2).getBoundsInLocal().getWidth()!=-1) {
			
			if(l2.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,l3).getBoundsInLocal().getWidth()!=-1) {
			
			if(l3.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,l4).getBoundsInLocal().getWidth()!=-1) {
			
			if(l4.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		return false;
	}
}

class Rectangle_obs extends Obstacles{
	
	private Line l1,l2,l3,l4;
	
	public Rectangle_obs(double y) {
		this.y=y;
		l1=new Line(450,y-100,450,y+100);
		l2=new Line(250,y-100,250,y+100);
		l3=new Line(250,y+100,450,y+100);
		l4=new Line(250,y-100,450,y-100);
		
		l1.setStrokeWidth(8);
		l2.setStrokeWidth(8);
		l3.setStrokeWidth(8);
		l4.setStrokeWidth(8);
		
		l1.setStroke(Color.RED);
		l2.setStroke(Color.BLUE);
		l3.setStroke(Color.YELLOW);
		l4.setStroke(Color.GREEN);
		
	
		
		g.getChildren().addAll(l1,l2,l3,l4);
	}
	
	@Override
	public boolean collided(User user) {
		
		if( Shape.intersect(user.get_ball().c,l1).getBoundsInLocal().getWidth()!=-1) {
			
			if(l1.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,l2).getBoundsInLocal().getWidth()!=-1) {
			
			if(l2.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,l3).getBoundsInLocal().getWidth()!=-1) {
			
			if(l3.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
			
		}
		
		if( Shape.intersect(user.get_ball().c,l4).getBoundsInLocal().getWidth()!=-1) {
			
			if(l4.getStroke()!=user.get_ball().c.getFill())
			
				return true;
			
			
		}
		
		return false;
	}
}

class Square_obs extends Obstacles{
	
	 Rectangle s=new Rectangle();
	
	public Square_obs(double y,long x) {
		this.y=y;
		s.setY(y);
		s.setX(0);
		s.setHeight(100);
		s.setWidth(100);
		
		if(x==0)
			s.setFill(Color.RED);
		
		else if(x==1)
			s.setFill(Color.BLUE);
		
		else if(x==2)
			s.setFill(Color.GREEN);
		
		else s.setFill(Color.YELLOW);
		
	}

	
	@Override
	public boolean collided(User user) {
		
		if( Shape.intersect(user.get_ball().c,s).getBoundsInLocal().getWidth()!=-1) {
			
			if(s.getFill()!=user.get_ball().c.getFill())
			
				return true;

			
		}
		
		return false;
		
	}
	
	@Override
	void come_down(double yy) {
		
		s.setTranslateY(s.getTranslateY()+yy);
		this.y+=yy;
	}
}
