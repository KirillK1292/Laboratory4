package main.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Model {
	
	private List<Symbol> symbols;
	private MLPerceptron neuralNetwork;
	
	public Model() {
		symbols = new ArrayList<Symbol>();
		for(char s = 1040; s < 1045; s++)//1072
			symbols.add(new Symbol(String.valueOf(s)));
	}
	
	public void trainNeuralNetwork(double b, double a, int D)
			throws MLPerceptronException
	{
		neuralNetwork = new MLPerceptron(b, a, D);
		neuralNetwork.startTraining(symbols);
	}
	
	public String defineSymbol(BufferedImage image)
			throws MLPerceptronException
	{
		Symbol symbol = new Symbol("", image);
		int n = neuralNetwork.define(symbol);
		return symbols.get(n).getName();
	}
	
	public int getSymbolsCount() {
		return symbols.size();
	}
	
	public void addSymbol(String name, BufferedImage image)
			throws ModelException, MLPerceptronException
	{
		Symbol symbol = new Symbol(name, image);
		if(symbols.contains(symbol))
			throw new ModelException("Символ " + name + " уже существует в наборе.");
		symbols.add(symbol);
		neuralNetwork.startTraining(symbols);
	}
	
	public String getSymbolName(int i) {
		return symbols.get(i).getName();
	}
	
	public BufferedImage getSymbolImage(int i) {
		return symbols.get(i).getImage();
	}
	
}
