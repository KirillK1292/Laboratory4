package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.model.MLPerceptronException;
import main.model.Model;
import main.model.ModelException;
import main.view.View;

public class Controller {
	
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		this.view.addDrawPanelMouseMotionListener(new DrawPanelMouseMotionListener());
		this.view.addNoiseSliderChangeListener(new NoiseSliderChangeListener());
		this.view.addClearDrawPanelButtonActionListener(new ClearDrawPanelButtonActionListener());
		this.view.addDefineSymbolButtonActionListener(new DefineSymbolButtonActionListener());
		this.view.addTrainingButtonActionListener(new TrainingButtonActionListener());
		this.view.addSymbolsComboBoxItemListener(new SymbolsComboBoxItemListener());
		this.view.addNewSymbolTextFieldKeyListener(new NewSymbolTextFieldKeyListener());
	}
	
	private class DrawPanelMouseMotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent mouseevent) {
			view.setNoiseValue(0);
			view.draw(mouseevent.getX(), mouseevent.getY());
			view.setDefineSymbolButtonEnabled(true);
		}

		@Override
		public void mouseMoved(MouseEvent mouseevent) {
		}
		
	}
	
	private class NoiseSliderChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent changeevent) {
			view.addNoiseRemoveNoise(view.getNoiseValue());
		}
		
	}
	
	private class ClearDrawPanelButtonActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setNoiseValue(0);
			view.clearDrawPanel();
			view.setDefineSymbolButtonEnabled(false);
			view.setNewSymbolText("");
			view.setNewSymbolTextFieldEnabled(false);
		}
		
	}
	
	private class DefineSymbolButtonActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				view.setNewSymbolTextFieldEnabled(false);
				String symbolName = model.defineSymbol(view.getSymbolImage());
				int result = view.showQuestionMessage(symbolName);
				if(result == 0)
					view.addTrueDefine();
				if(result == 1)
					view.addFalseDefine();
				if(result == 2)
					view.setNewSymbolTextFieldEnabled(true);
			} catch (MLPerceptronException ex) {
				view.showErrorMessage(ex.getMessage());
			}
		}
		
	}
	
	private class TrainingButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				view.setNoiseValue(0);
				view.clearDrawPanel();
				view.setDefineSymbolButtonEnabled(false);
				view.setToolPanelVisible(false);
				view.setNewSymbolText("");
				view.setNewSymbolTextFieldEnabled(false);
				view.clearImagePanel();
			
				double b = Double.valueOf(view.getBetaValue());
				double a = Double.valueOf(view.getAlphaValue());
				int D = Integer.valueOf(view.getMaxErrorValue());
				model.trainNeuralNetwork(b, a, D);
			
				view.removeAllSymbols();
				int n = model.getSymbolsCount();
				for(int i = 0; i < n; i++)
					view.addSymbol(model.getSymbolName(i));
				view.setToolPanelVisible(true);
			} catch(NumberFormatException ex) {
				view.showErrorMessage(ex.getMessage());
			} catch (MLPerceptronException ex) {
				view.showErrorMessage(ex.getMessage());
			}
		}
		
	}
	
	private class SymbolsComboBoxItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(view.getSelectedSymbolIndex() >= 0)
				view.setSymbolImage(model.getSymbolImage(view.getSelectedSymbolIndex()));
		}
		
	}
	
	private class NewSymbolTextFieldKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && !view.getNewSymbolText().isEmpty()) {
				try {
					view.setNoiseValue(0);
					model.addSymbol(view.getNewSymbolText(), view.getSymbolImage());
					view.addSymbol(view.getNewSymbolText());
					view.setNewSymbolText("");
					view.setNewSymbolTextFieldEnabled(false);
				} catch (ModelException ex) {
					view.showErrorMessage(ex.getMessage());
				} catch (MLPerceptronException ex) {
					view.showErrorMessage(ex.getMessage());
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {			
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		
	}
	
}
