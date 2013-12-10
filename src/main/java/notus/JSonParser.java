/**
* This file is part of Notus.
*
* Notus is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Notus is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Notus.  If not, see <http://www.gnu.org/licenses/>.
 */
package notus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;

import parser.absconparseur.tools.UnsupportedConstraintException;
import parser.instances.InstanceFileParser;

public class JSonParser implements InstanceFileParser {

	File file = null;
	public static Gson gson = new Gson();
	Instance instance = null;
	
	public static final String ITEMS_KEY = "items";
	public static final String BINS_KEY = "bins";

	public static final String NAME_KEY = "name";
	public static final String ARITY_KEY = "arity";
	public static final String COST_KEY = "cost";
	public static final String SIZES_KEY = "sizes";
	
	public JSonParser() {}

	
	@Override
	public File getInstanceFile() {
		return file;
	}
	
	public Item[] getItems() {
		return instance.items;
	}

	public Bin[] getBins() {
		return instance.bins;
	}

	public Dimensions getDimensions() {
		return null;
	}

	@Override
	public void loadInstance(File file) {
		this.file = file;
	}

	@Override
	public void parse(boolean displayInstance) {
		try {
			/*
			instance = gson.fromJson(new FileReader(file),  Instance.class);
			//{"items":[{"name":"MySQL-LB","consume":[{"ram":128}],"arity":2},{"name":"MySQL-backend","consume":[{"ram":512}],"arity":4},{"name":"Keystone-LB","consume":[{"ram":128}],"arity":2},{"name":"Keystone-backend","consume":[{"ram":128}],"arity":3},{"name":"Glance-API-LB","consume":[{"ram":128}],"arity":2},{"name":"Glance-API-backend","consume":[{"ram":128}],"arity":2},{"name":"Glance-Registry-LB","consume":[{"ram":128}],"arity":2},{"name":"Glance-Registry-backend","consume":[{"ram":128}],"arity":2},{"name":"Glance-DB","consume":[],"arity":1},{"name":"Nova-API-LB","consume":[{"ram":128}],"arity":2},{"name":"Nova-API-backend","consume":[{"ram":128}],"arity":2},{"name":"Nova-Scheduler","consume":[{"ram":128}],"arity":2},{"name":"Nova-Conductor","consume":[{"ram":128}],"arity":2},{"name":"Nova-Compute","consume":[{"ram":1024}],"arity":4},{"name":"Nova-DB","consume":[],"arity":1},{"name":"Queue-LB","consume":[{"ram":128}],"arity":2},{"name":"Queue-backend","consume":[{"ram":128}],"arity":3}],"bins":[{"name":"Location_category_1","provide":[{"ram":2048}],"cost":1,"arity":6}]}
			System.out.print(gson.toJson(instance, Instance.class));
			*/
			Map<String, Map> mapInstance = (Map<String, Map>) JSONUtils.deSerializeData(file);
			//System.out.print(JSONUtils.serializeData(map));
			
			instance = new Instance();
			
			List<Map> mapItems = (List<Map>) mapInstance.get(ITEMS_KEY);
			List<Map> mapBins = (List<Map>) mapInstance.get(BINS_KEY);
			
			instance.items = new ItemJSON[mapItems.size()];
			instance.bins = new BinJSON[mapBins.size()];
			
			System.out.println("Get " + ITEMS_KEY);					
			for(int i = 0; i < instance.items.length; i++) {
				 ItemJSON item = new ItemJSON();
				 Map mapItem = mapItems.get(i);

				 item.name = (String) mapItem.get(NAME_KEY);
				 item.arity = (Long) mapItem.get(ARITY_KEY);
				 List<Map> mapSizes = (List<Map>) mapItem.get(SIZES_KEY);
				 
				 item.sizes = new Size[mapSizes.size()];
				 
				 for(int j = 0; j < item.sizes.length; j++) {
					 Size size = new Size();
					 size.dims.putAll((Map<String, Integer>) mapSizes.get(j));
					 item.sizes[j] = size;
					 //System.out.println(size.dims);
				 }
				 
				 instance.items[i] = item;
			}
			
			System.out.println("Get " + BINS_KEY);					
			for(int i = 0; i < instance.bins.length; i++) {
				 BinJSON bin = new BinJSON();
				 Map mapBin = mapBins.get(i);

				 bin.name = (String) mapBin.get(NAME_KEY);
				 bin.arity = (Long) mapBin.get(ARITY_KEY);
				 bin.cost = (Long) mapBin.get(COST_KEY);
				 
				 List<Map> mapSizes = (List<Map>) mapBin.get(SIZES_KEY);
				 
				 bin.sizes = new Size[mapSizes.size()];
				 
				 for(int j = 0; j < bin.sizes.length; j++) {
					 Size size = new Size();
					 size.dims.putAll((Map<String, Integer>) mapSizes.get(j));
					 bin.sizes[j] = size;
					 //System.out.println(size.dims);
				 }
				 
				 instance.bins[i] = bin;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cleanup() {
		file = null;	
		instance = null;	
	}
	
	
	class Instance {
	   ItemJSON[] items;
	   BinJSON[]  bins;
	}

	
	class ItemJSON implements Item {
		
		//{ "name": "MySQL-LB", "consume": [ {"ram", 128} ], "arity": 2 },
	    
		String name;
		Size[] sizes;
		long arity;
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getSize(int dim) {
			return sizes[dim].getDimensionCount();
		}
		
		@Override
		public int getArity() {
			return (int) arity;
		}
	}

	class BinJSON implements Bin {
		 /*
		{
		      "name": "Location_category_1",
		      "provide": [ {"ram", 2048} ],
		      "cost": 1,
		      "arity": 6
		    }
		    */
		String name;
		Size[] sizes;
		long cost;
		long arity;

		@Override
		public String getName() {
			return name;
		}
		@Override
		public int getSize(int dim) {
			return sizes[dim].getDimensionCount();
		}
		@Override
		public int getArity() {
			return (int) arity;
		}
		@Override
		public int getCost() {
			// TODO Auto-generated method stub
			return (int) cost;
		}	
	}
	
	class Size implements Dimensions {
		
		Map<String, Integer> dims = new LinkedHashMap<String, Integer>();
		
		public Size() {}
		
		@Override
		public int getDimensionCount() {
			return dims.size();
		}

		@Override
		public int getIndex(String dim) {
			return dims.get(dim);
		}

		@Override
		public String getName(int dim) {
			//http://stackoverflow.com/questions/3478061/does-javas-linkedhashmap-maintain-the-order-of-keys
			return (String) dims.keySet().toArray()[dim];
		}	
	}
	
	public static void main(String[] args) {
		JSonParser json = new JSonParser();
		json.loadInstance(new File("src/test/resources/veryredundant-highmysqlandnovacompute-wheezy/binpacking.json"));
		json.parse(true);
	}

}

