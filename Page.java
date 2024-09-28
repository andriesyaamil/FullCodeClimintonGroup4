package main;
import javafx.scene.layout.BorderPane;

public abstract class Page extends BorderPane {
	

	int idCounter =0;

	
	
	public Page() {
		init();
		setLayout();
		setAction();
		}
	
	
	public abstract void setAction();
	
	public abstract void setLayout();

	public abstract void init();
	
	
	
	
	
	
}
