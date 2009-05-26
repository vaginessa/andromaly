
package andromaly.main.Business.DataContainers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dt.fe.MonitoredData;


public class DataCollection extends ArrayList<MonitoredDataWrapper> { 
    
    public DataCollection(){
    	super();
    }

    public DataCollection(List<MonitoredData> data){
    	super();
    	for(MonitoredData feature : data){
    		this.add(new MonitoredDataWrapper(feature));
    	}
    }
    
	public double compare(DataCollection other){
    	// returns an average of all data compare
    	// we may want to add different weights for the data in the future
        double ans = 0;
        double numOfFeatures = 0;
        double weight;
        for(int i=0; i<this.size(); i++){
            MonitoredDataWrapper data1 = this.get(i);
            MonitoredDataWrapper data2 = other.get(i);
            weight = data1.getWeight();
            ans += weight * (data1.compare(data2));
            if (weight!=0){
            	numOfFeatures++;
            }
        }
        ans = (ans / numOfFeatures);
        return ans;
    }

    public void aggregate(DataCollection other){
        for(int i=0; i<this.size(); i++){
            MonitoredDataWrapper data1 = this.get(i);
            MonitoredDataWrapper data2 = other.get(i);
            data1.aggregate(data2);
        }
    }

    public boolean AllEmpty(){
        for (MonitoredDataWrapper data: this){
            if (!data.isEmpty()){
                return false;
            }
        }
        return true;
    }
    
	public String toString(){
		String res = "";
		for (MonitoredDataWrapper md : this){
			res += "\t" + md.conciseToString() + "\n";
		}
		return res;
	}
	
	public String getValuesString(){
		String res = "{";
		for (MonitoredDataWrapper md : this){
			res += md.getValue() + ",\t";
		}
		return res + "}";
	}
	
	public void writeToOutput(java.io.Writer osw) throws IOException{
		osw.write("<DataCollection>\n");
		for (MonitoredDataWrapper mdw : this){
			mdw.writeToOutput(osw);
		}
		osw.write("</DataCollection>\n");
		osw.flush();
	}
	
}


