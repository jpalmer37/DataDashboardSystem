package httpTest;
import java.util.ArrayList;

/**
 * @author Shoumik Shill
 * 
 * Analysis Strategy for Analysis D:
 *  - 2-series
 *  - AG.LND.FRST.ZS VS EN.CLC.GHGR.MT.CE
 *  - Forest area (% of land area) in Canada from 1990 to 2009
 *    VS
 *    Average of GHG net emissions/removals by LUCF (Mt of CO2 equivalent) in Canada from 1990 to 2009
 *  - In other words, 
 *    
 *  - Line Graph
 *    - Line 1: Forest area from 1990 to 2009
 *    - Line 2: Average of Greenhouse gas net emissions/removals by land-use, change and forestry in Canada
 *              from 1990 to 2009
 *              (Horizontal line as it is one value)
 *              
 */

public class AnalysisD extends AnalysisStrategy
{
	
	public AnalysisD() {
		
		this.numOfSeries = 2;
		this.analysisID = "Forest area (% of land area) vs Average GHG net emissions/removals by LUCF";
		
		this.dataNames = new String[2];
		this.dataNames[0] = "Average Forest area";
		this.dataNames[1] = "Average GHG net emissions/removal";
		
		this.axisNames = new String[1];
		this.axisNames[0] = "Year";
	}
	
	
	/** Do Analysis Method
	* To be called from Computational Server class
	* Core method of the project that, based on the user selection:
	* 1. Gets unprocessed data from Reader class
	* 
	* @param Selection
	* 
	*/
	public void doAnalysis(Selection selection) {
		
		this.processedData = new ArrayList[2];
		this.years = new ArrayList[2];
		
		Data[] dataSet = this.retrieveData(selection);
		
		// dataSet[1] = the GHG emissions/removals data
		ArrayList<Double> GHGData = dataSet[1].getFirst(); // get values
		// Array List of the averages for these GHG data lines
		ArrayList<Double> avgGHG = new ArrayList<Double>();
		
		for (int i = 0; i < GHGData.size(); i++) {
			avgGHG.add(this.getAverage(GHGData).get(0));
		}
		
		this.processedData[0] = dataSet[0].getFirst(); // Unchanged values of Forest Area
		this.processedData[1] = avgGHG; // Data for single horizontal line that is the avg GHG emissions/removals
		
		// Set years
		this.years[0] = dataSet[0].getSecond();
		this.years[1] = dataSet[1].getSecond();
		
	} // End Do Analysis
	
	
	/**
	 * Retrieve Data
	 * To be called from Do Analysis to get the data to be processed
	 * @return Data from World Bank
	 */
	public Data[] retrieveData(Selection selection) 
	{
		
		// Initialize Data Array to be returned
		Data[] seriesArray = new Data[2];
		
		// Create a reader and retrieve data based on selection and Analysis
		Reader reader = new Reader();
		seriesArray[0] = reader.retrieveData(selection, "AG.LND.FRST.ZS");
		seriesArray[1] = reader.retrieveData(selection, "EN.CLC.GHGR.MT.CE");
		
		// Return data
		return seriesArray;
		
	} // End Retrieve Data
	
} // End Analysis D Class
