import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KmeansImpl {

	private double[][] _data;
	private int[] _label;
	private int[] _withLabel;
	private double[][] _centroids;
	private int _nrows, _ndims;
	private int _numClusters;
	
	public static void main(String[] args) {
		String filePath = "/Users/janmejayasahoo/Documents/workspace/ML_Assignment6/src/data_input.csv";
		KmeansImpl KM = new KmeansImpl(filePath, null);
		KM.clustering(14, null);

	}
	
	public double[][] getCentroids() {
		return _centroids;
	}

	public int[] getLabel() {
		return _label;
	}

	public int nrows() {
		return _nrows;
	}

	public KmeansImpl(String fileName, String labelname) {
		BufferedReader reader;
		String line = " ";
		try {
			reader = new BufferedReader(new FileReader(fileName));
			_nrows = 1;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				_ndims = values.length;
				_nrows++;

			}

			reader.close();
			System.out.println(_nrows + " " + _ndims);
			_data = new double[_nrows][];
			for (int i = 0; i < _nrows; i++)
				_data[i] = new double[_ndims];
			reader = new BufferedReader(new FileReader(fileName));
			int nrow = 0;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				double[] dv = new double[values.length];
				for (int i = 0; i < values.length; i++) {
					dv[i] = Double.parseDouble(values[i]);
				}
				_data[nrow] = dv;
				nrow++;
			}
			reader.close();

			if (labelname != null) {
				reader = new BufferedReader(new FileReader(labelname));
				_withLabel = new int[_nrows];
				int c = 0;
				while ((line = reader.readLine()) != null) {

				}
				reader.close();
				
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

	}

	public void clustering(int numClusters, double[][] centroids) {
		List[] a1 = null;
		_numClusters = numClusters;
		if (centroids != null)
			_centroids = centroids;
		else {

			_centroids = new double[_numClusters][];
			ArrayList idx = new ArrayList();
			for (int i = 0; i < numClusters; i++) {
				int c;
				do {
					c = (int) (Math.random() * _nrows);
				} while (idx.contains(c));
				idx.add(c);

				_centroids[i] = new double[_ndims];
				for (int j = 0; j < _ndims; j++)
					_centroids[i][j] = _data[c][j];
			}
			

		}
		double[][] c1 = _centroids;
		int round = 0;
		while (round < 5) {

			a1 = new ArrayList[numClusters];
			for (int i = 0; i < numClusters; i++) {
				a1[i] = new ArrayList();
			}
			_centroids = c1;

			_label = new int[_nrows];
			for (int i = 0; i < _nrows; i++) {
				_label[i] = closest(_data[i]);
				a1[closest(_data[i])].add(i + 1);
			}

			c1 = updateCentroids();
			round++;

		}

		
		for (int i = 0; i < a1.length; i++) {
			List l1 = a1[i];
			Iterator i1 = l1.iterator();
			System.out.println("cluster number"+ (i + 1));
			System.out.println("Points :");
			while (i1.hasNext()) {

				System.out.print(i1.next());
				System.out.print(",");
			}
			System.out.println();
		}

		double sum = 0;
		for (int i = 0; i < a1.length; i++) {

			List l2 = a1[i];
			Iterator i1 = l2.iterator();
			while (i1.hasNext()) {
				int i3 = (int) i1.next();
				double s = dist1(_data[i3 - 1], _centroids[i]);
				sum += s;
			}
		}
		System.out.println("SSE");
		System.out.println(sum);
	}

	private double[][] updateCentroids() {

		double[][] newc = new double[_numClusters][];
		int[] counts = new int[_numClusters];

		for (int i = 0; i < _numClusters; i++) {
			counts[i] = 0;
			newc[i] = new double[_ndims];
			for (int j = 0; j < _ndims; j++)
				newc[i][j] = 0;
		}

		for (int i = 0; i < _nrows; i++) {
			int cn = _label[i];
			for (int j = 0; j < _ndims; j++) {
				newc[cn][j] += _data[i][j];
			}
			counts[cn]++;
		}

		for (int i = 0; i < _numClusters; i++) {
			for (int j = 0; j < _ndims; j++) {
				newc[i][j] /= counts[i];
			}
		}

		return newc;
	}

	private int closest(double[] v) {
		double mindist = dist(v, _centroids[0]);
		int label = 0;
		for (int i = 1; i < _numClusters; i++) {
			double t = dist(v, _centroids[i]);
			if (mindist > t) {
				mindist = t;
				label = i;
			}
		}
		return label;
	}

	private double dist(double[] v1, double[] v2) {
		double sum = 0;
		for (int i = 0; i < _ndims; i++) {
			double d = v1[i] - v2[i];
			sum += d * d;
		}
		return Math.sqrt(sum);
	}

	private double dist1(double[] v1, double[] v2) {
		double sum = 0;
		for (int i = 0; i < _ndims; i++) {
			double d = v1[i] - v2[i];
			sum += d * d;
		}
		return sum;
	}


}
