package main.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import com.jidesoft.swing.JideLabel;

public class View extends JFrame {
	
	private JLabel drawPanelLabel, symbolsComboBoxLabel, newSymbolTextFieldLabel, alphaTextFieldLabel, betaTextFieldLabel, maxErrorTextFieldLabel;
	private JideLabel noiseSliderLabel;
	private DrawPanel drawPanel;
	private JButton trainingButton, clearDrawPanelButton, defineSymbolButton;
	private JSlider noiseSlider;
	private JPanel toolPanel;
	private JComboBox symbolsComboBox;
	private JTextField newSymbolTextField, alphaTextField, betaTextField;
	private JSpinner maxErrorSpinner;
	private ImagePanel symbolImagePanel;
	private JLabel trueLabel, falseLabel;
	
	public View(String title) {
		super(title);
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(411, 337);
		setResizable(false);
		setLayout(null);
		
		toolPanel = new JPanel();
		toolPanel.setBounds(115, 12, 277, 286);
		toolPanel.setVisible(false);
		toolPanel.setLayout(null);
		toolPanel.setBorder(BorderFactory.createEtchedBorder(0));
		
		drawPanelLabel = new JLabel("Напишите символ");
		drawPanelLabel.setBounds(6, 6, 128, 15);
		
		drawPanel = new DrawPanel(220, 220);
		drawPanel.setLocation(6, 24);
		
		noiseSliderLabel = new JideLabel("Уровень шума");
		noiseSliderLabel.setOrientation(JideLabel.VERTICAL);
		noiseSliderLabel.setBounds(258, 95, 15, 82);
		
		noiseSlider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		noiseSlider.setBounds(232, 24, 25, 220);
		noiseSlider.setBorder(BorderFactory.createEtchedBorder(0));
		
		clearDrawPanelButton = new JButton("Очистить");
		clearDrawPanelButton.setBounds(6, 250, 106, 23);
		
		defineSymbolButton = new JButton("Определить");
		defineSymbolButton.setBounds(120, 250, 106, 23);
		defineSymbolButton.setEnabled(false);
		
		toolPanel.add(drawPanelLabel);
		toolPanel.add(drawPanel);
		toolPanel.add(noiseSliderLabel);
		toolPanel.add(noiseSlider);
		toolPanel.add(clearDrawPanelButton);
		toolPanel.add(defineSymbolButton);
		
		betaTextFieldLabel = new JLabel("b = ");
		betaTextFieldLabel.setBounds(30, 12, 22, 13);
		
		betaTextField = new JTextField("0.9");
		betaTextField.setBounds(50, 12, 60, 20);
		
		alphaTextFieldLabel = new JLabel("a = ");
		alphaTextFieldLabel.setBounds(30, 38, 22, 13);
		
		alphaTextField = new JTextField("1.2");
		alphaTextField.setBounds(50, 38, 60, 20);
		
		maxErrorTextFieldLabel = new JLabel("D = ");
		maxErrorTextFieldLabel.setBounds(30, 67, 22, 13);
		
		maxErrorSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		maxErrorSpinner.setBounds(50, 64, 60, 20);
		
		trainingButton = new JButton("Обучить");
		trainingButton.setBounds(12, 90, 97, 23);
		
		symbolsComboBoxLabel = new JLabel("Набор символов");
		symbolsComboBoxLabel.setBounds(12, 130, 97, 13);
		
		symbolsComboBox = new JComboBox();
		symbolsComboBox.setBounds(12, 146, 97, 21);
		
		newSymbolTextFieldLabel = new JLabel("Новый символ");
		newSymbolTextFieldLabel.setBounds(12, 170, 86, 13);
		
		newSymbolTextField = new JTextField();
		newSymbolTextField.setBounds(12, 186, 97, 20);
		newSymbolTextField.setEnabled(false);
		
		symbolImagePanel = new ImagePanel();
		symbolImagePanel.setBounds(53, 212, drawPanel.getWidth() / 4, drawPanel.getHeight() / 4);
		symbolImagePanel.setBorder(BorderFactory.createEtchedBorder(1));
		
		JPanel statisticsPanel = new JPanel();
		statisticsPanel.setBounds(12, 265, 97, 35);
		statisticsPanel.setLayout(null);
		statisticsPanel.setFont(new Font("Tahoma", 0, 3));
		statisticsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(0), "Статистика", 0, 0, new Font("Tahoma", 1, 10)));
		trueLabel = new JLabel("0");
		trueLabel.setBounds(25, 15, 15, 10);
		trueLabel.setForeground(Color.GREEN);
		falseLabel = new JLabel("0");
		falseLabel.setBounds(65, 15, 15, 10);
		falseLabel.setForeground(Color.RED);
		statisticsPanel.add(trueLabel);
		statisticsPanel.add(falseLabel);
		
		
		this.add(toolPanel);
		this.add(betaTextFieldLabel);
		this.add(betaTextField);
		this.add(alphaTextFieldLabel);
		this.add(alphaTextField);
		this.add(maxErrorTextFieldLabel);
		this.add(maxErrorSpinner);
		this.add(trainingButton);
		this.add(symbolsComboBoxLabel);
		this.add(symbolsComboBox);
		this.add(newSymbolTextFieldLabel);
		this.add(newSymbolTextField);
		this.add(symbolImagePanel);
		this.add(statisticsPanel);
	}
	
	public void addDrawPanelMouseMotionListener(MouseMotionListener listener) {
		drawPanel.addMouseMotionListener(listener);
	}
	
	public void addNoiseSliderChangeListener(ChangeListener listener) {
		noiseSlider.addChangeListener(listener);
	}
	
	public void addClearDrawPanelButtonActionListener(ActionListener listener) {
		clearDrawPanelButton.addActionListener(listener);
	}
	
	public void addDefineSymbolButtonActionListener(ActionListener listener) {
		defineSymbolButton.addActionListener(listener);
	}
	
	public void addTrainingButtonActionListener(ActionListener listener) {
		trainingButton.addActionListener(listener);
	}
	
	public void addSymbolsComboBoxItemListener(ItemListener listener) {
		symbolsComboBox.addItemListener(listener);
	}
	
	public void addNewSymbolTextFieldKeyListener(KeyListener listener) {
		newSymbolTextField.addKeyListener(listener);
	}
	
	public void draw(int x, int y) {
		drawPanel.draw(x, y);
	}
	
	public BufferedImage getSymbolImage() {
		return drawPanel.getSymbolImage();
	}
	
	public void clearDrawPanel() {
		drawPanel.clearDrawPanel();
	}
	
	public void addNoiseRemoveNoise(int value) {
		drawPanel.addNoiseRemoveNoise(value);
	}
	
	public int getNoiseValue() {
		return noiseSlider.getValue();
	}
	
	public void setNoiseValue(int value) {
		noiseSlider.setValue(value);
	}
	
	public void addSymbol(String symbol) {
		symbolsComboBox.addItem(symbol);
	}
	
	public void removeAllSymbols() {
		symbolsComboBox.removeAllItems();
	}
	
	public int getSelectedSymbolIndex() {
		return symbolsComboBox.getSelectedIndex();
	}
	
	public String getNewSymbolText() {
		return newSymbolTextField.getText();
	}
	
	public void setNewSymbolText(String text) {
		newSymbolTextField.setText(text);
	}
	
	public void setSymbolImage(BufferedImage image) {
		symbolImagePanel.setImage(image);
	}
	
	public void clearImagePanel() {
		symbolImagePanel.clearImagePanel();
	}
	
	public void setDefineSymbolButtonEnabled(boolean enabled) {
		defineSymbolButton.setEnabled(enabled);
	}
	
	public void setToolPanelVisible(boolean visible) {
		toolPanel.setVisible(visible);
	}
	
	public void setNewSymbolTextFieldEnabled(boolean enabled) {
		newSymbolTextField.setEnabled(enabled);
	}
	
	public String getBetaValue() {
		return betaTextField.getText();
	}
	
	public String getAlphaValue() {
		return alphaTextField.getText();
	}
	
	public String getMaxErrorValue() {
		return maxErrorSpinner.getValue().toString();
	}
	
	public void addTrueDefine() {
		trueLabel.setText(String.valueOf(Integer.valueOf(trueLabel.getText()) + 1));
	}
	
	public void addFalseDefine() {
		falseLabel.setText(String.valueOf(Integer.valueOf(falseLabel.getText()) + 1));
	}
	
	public int showQuestionMessage(String message) {
		Object[] options = new Object[]{"Да", "Нет", "<html>Нет, но добавить<br>в набор символов"};
		return JOptionPane.showOptionDialog(this, "На изображении символ " + message + "?", "Результат", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
	}
	
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
	}
	
}
