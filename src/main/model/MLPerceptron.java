package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MLPerceptron {
	
	private double b, a, D;
	private boolean isTrained;
	
	private double[][] v;
	private double[][] w;
	
	private double[] Q;
	private double[] T;
	
	MLPerceptron(double b, double a, int D) {
		this.b = b;
		this.a = a;
		this.D = D / 100.0;
	}
	
	private double randomInit() {
		return Math.random() * 2.0 - 1.0;
	}
	
	private void initPerceptron(int n, int h, int m) {
		int i, j;
		
		v = new double[n][h];
		w = new double[h][m];
		Q = new double[h];
		T = new double[m];
		for(i = 0; i < v.length; i++) {
			for(j = 0; j < v[i].length; j++)
				v[i][j] = randomInit();
		}
		for(i = 0; i < w.length; i++) {
			for(j = 0; j < w[i].length; j++)
				w[i][j] = randomInit();
		}
		for(i = 0; i < Q.length; i++)
			Q[i] = randomInit();
		for(i = 0; i < T.length; i++)
			T[i] = randomInit();
		
	}
	
	private double activationFunc(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}
	
	private void setHiddenLayer(List<Integer> xr, double[] g) {
		int i, j;
		double sum;
		
		for(j = 0; j < g.length; j++) {
			sum = 0.0;
			for(i = 0; i < xr.size(); i++)
				sum += v[i][j] * xr.get(i);
			g[j] = activationFunc(sum + Q[j]);
		}
	}
	
	private void setOutputLayer(double[] g, double[] y) {
		int j, k;
		double sum;
		
		for(k = 0; k < y.length; k++) {
			sum = 0.0;
			for(j = 0; j < g.length; j++)
				sum += w[j][k] * g[j];
			y[k] = activationFunc(sum + T[k]);
		}
	}
	
	void startTraining(List<Symbol> symbols)
			throws MLPerceptronException
	{
		int i, j, k, iter = 0;
		double max;
		
		isTrained = false;
		initPerceptron(symbols.get(0).getImageData().size(), (int)Math.sqrt(symbols.get(0).getImageData().size() / symbols.size()), symbols.size());
		while(!isTrained) {
			List<Double> maxErrors = new ArrayList<Double>();
			for(Symbol symbol : symbols) {
				double[] g = new double[Q.length];
				double[] y = new double[T.length];
				List<Integer> xr = symbol.getImageData();
				List<Integer> yr = new ArrayList<Integer>(Collections.nCopies(symbols.size(), 0));
				yr.set(symbols.indexOf(symbol), 1);
				setHiddenLayer(xr, g);
				setOutputLayer(g, y);
			
				double[] d = new double[y.length];
				max = Math.abs(yr.get(0) - y[0]);
				for(k = 0; k < d.length; k++) {
					d[k] = yr.get(k) - y[k];
					if(max < Math.abs(yr.get(k) - y[k]))
						max = Math.abs(yr.get(k) - y[k]);
				}
				maxErrors.add(max);
				double[] e = new double[g.length];
				for(j = 0; j < e.length; j++) {
					e[j] = 0.0;
					for(k = 0; k < d.length; k++)
						e[j] += d[k] * y[k] * (1 - y[k]) * w[j][k];
				}
				for(j = 0; j < w.length; j++) {
					for(k = 0; k < w[j].length; k++)
						w[j][k] += a * y[k] * (1 - y[k]) * d[k] * g[j];
				}
				for(k = 0; k < T.length; k++)
					T[k] += a * y[k] * (1 - y[k]) * d[k];
				for(i = 0; i < v.length; i++) {
					for(j = 0; j < v[i].length; j++)
						v[i][j] += b * g[j] * (1 - g[j]) * e[j] * xr.get(i);
				}
				for(j = 0; j < Q.length; j++)
					Q[j] += b * g[j] * (1 - g[j]) * e[j];
			}
			isTrained = (Collections.max(maxErrors) < D);
			if(iter++ > 20000 && !isTrained)
				throw new MLPerceptronException("Кол-во итераций на обучение нейронной сети\nпревысило допустимое значение." + 
												"\nНейронная сеть не обучена.");
		}
	}
	
	int define(Symbol symbol)
			throws MLPerceptronException
	{
		if(!isTrained)
			throw new MLPerceptronException("Нейронная сеть не обучена");
		double[] g = new double[Q.length];
		double[] y = new double[T.length];
		List<Integer> xr = symbol.getImageData();
		setHiddenLayer(xr, g);
		setOutputLayer(g, y);
		
		int k, n = 0;
		double max = y[0];
		for(k = 0; k < y.length; k++) {
			if(max < y[k]) {
				max = y[k];
				n = k;
			}
		}
		return n;
	}
	
}
