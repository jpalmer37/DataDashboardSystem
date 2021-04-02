package httpTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
/*
 * ISSUES WE NEED TO RESOLVE: 
 * 1. How do we find out how many TimeSeriesCollections we will need? We will need 1 per UNIQUE unit, for example if units = {kg, kg, g} (for a 3-series graph), we need to create 2 TimeSeriesCollection objects
 */
public class ScatterPlot extends Viewer
{
	ScatterPlot()
	{
		super(ViewerType.SCATTERPLOT);
	}
	
	@Override
	protected void display(ArrayList<Double>[] data, ArrayList<Integer>[] years, String[] types, JPanel plotArea, String[] units, String analysisID) 
	{
		// Variable Declaration
		int numTS = data.length;
		// create TimeSeries for each series in data
		TimeSeries[] seriesArray = new TimeSeries[numTS];
		for(int i = 0; i < numTS; i++)
		{
			
			// Find out number of years for that series
			int numYears = years[i].size();
			// Create a new TimeSeries using the series name
			seriesArray[i] = new TimeSeries(types[i]);
			// For every year in the specified series, add the Year and its corresponding data value to that series
			for(int j = 0; j < numYears; j++)
			{
				seriesArray[i].add(new Year(years[i].get(j)), data[i].get(j));
			}
		}
		
		// group TimeSeries in TimeSeriesCollections by units
		TimeSeriesCollection[] dataSet = new TimeSeriesCollection[numofTSC];
		// create XYPlot 
		XYPlot plot = new XYPlot();
		// create XYItemRenderer array
		XYItemRenderer[] itemRenArray = new XYItemRenderer[numofTSC];
		// create XYItemRenderer for EACH TimeSeriesCollection
		// set the data sets and renderers for the plot
		for(int k = 0; k < numOfTSC; k++) 
		{
			// create XYLineAndShapeRenderer for that TimeSeriesCollection
			itemRenArray[k] = new XYLineAndShapeRenderer(false, true);
			// set the Dataset for that TimeSeriesCollection
			plot.setDataset(k, dataSet[k]);
			// set the XYItemRenderer for that TimeSeriesCollection
			plot.setRenderer(k, itemRenArray[k]);
			// set Range axis (at most 2) [provide names]
			plot.setRangeAxis(k, new NumberAxis(TSCUnits[k]));
			// set the datasets to their corresponding y-axis
			plot.mapDatasetToRangeAxis(k, k);
		}
		
		// set Domain axis (only 1 for Years)
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		
		// create JFreeChart with Title, Font and plot
		JFreeChart scatterChart = new JFreeChart(analysisID,
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
		// create ChartPanel using JFreeChart and set dimensions, border and background
		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
	
		// add ChartPanel to JPanel plotArea
		plotArea.add(chartPanel);
	}
	
}