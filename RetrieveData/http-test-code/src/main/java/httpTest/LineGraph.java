package httpTest;

import java.util.ArrayList;

import javax.swing.JPanel;

public class LineGraph extends Viewer
{
	LineGraph()
	{
		super(ViewerType.LINEGRAPH);
	}

	@Override
	protected void display(ArrayList<Double>[] data, ArrayList<Integer>[]  years, String[] types, JPanel plotArea, String[] units, String analysisID) 
	{
		
	}

}