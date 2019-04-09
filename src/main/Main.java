package main;

import main.controller.Controller;
import main.model.Model;
import main.view.View;

public class Main {
	
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View("Laboratory 4");
		Controller controller = new Controller(model, view);
		view.setVisible(true);
	}

}
