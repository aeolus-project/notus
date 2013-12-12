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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import parser.instances.InstanceFileParser;

public class JSonParser implements InstanceFileParser {

	class Instance {
	   ItemObject[] items = null;
	   BinObject[]  bins = null;
	   DescriptionSizes descrDims = null;
	}
	
	File file = null;
	//public static Gson gson = new Gson();
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
		return instance.descrDims;
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
			
			List<Map<String, Object>> mapItems = (List<Map<String, Object>>) mapInstance.get(ITEMS_KEY);
			List<Map<String, Object>> mapBins = (List<Map<String, Object>>) mapInstance.get(BINS_KEY);
			
			instance.items = new ItemObject[mapItems.size()];
			instance.bins = new BinObject[mapBins.size()];
			
			boolean descrDone = false;
			instance.descrDims = new DescriptionSizes();
			
			//System.out.println("Get " + ITEMS_KEY);					
			for(int i = 0; i < instance.items.length; i++) {
				 ItemObject item = new ItemObject();
				 Map<String, Object> mapItem = (Map<String, Object>) mapItems.get(i);

				 item.name = (String) mapItem.get(NAME_KEY);
				 item.arity = ((Long) mapItem.get(ARITY_KEY)).intValue();
				 List<Map<String, Object> > mapSizes = (List<Map<String, Object> >) mapItem.get(SIZES_KEY);
				 
				 item.sizes = new int[mapSizes.size()];
				 
				 if(instance.descrDims.dims == null) {
					 instance.descrDims.dims = new String[item.sizes.length];
				 }
				 
				 for(int j = 0; j < item.sizes.length; j++) {
					 
					 String key = (String) mapSizes.get(j).keySet().iterator().next();
					 if(!descrDone) {
						 for(int k = 0; k < j; k++) {
							if(key.equals(instance.descrDims.dims[k])) {
								throw new Exception("Problem : 2 dimensions have the same name");
							}				
						 }
						 instance.descrDims.dims[j] = key;
					 }
					 item.sizes[j] = ((Long) mapSizes.get(j).get(key)).intValue();
					 //System.out.println(size.dims);
				 }
				 
				 descrDone = true;
				 instance.items[i] = item;
			}
			
			//System.out.println("Get " + BINS_KEY);					
			for(int i = 0; i < instance.bins.length; i++) {
				 BinObject bin = new BinObject();
				 Map<String, Object> mapBin = (Map<String, Object>) mapBins.get(i);

				 bin.name = (String) mapBin.get(NAME_KEY);
				 bin.arity = ((Long) mapBin.get(ARITY_KEY)).intValue();
				 bin.cost = ((Long) mapBin.get(COST_KEY)).intValue();
				 
				 List<Map<String, Object> > mapSizes = (List<Map<String, Object> >) mapBin.get(SIZES_KEY);
				 
				 bin.sizes = new int[mapSizes.size()];
				 
				 for(int j = 0; j < bin.sizes.length; j++) {
					 
					 String key = (String) mapSizes.get(j).keySet().iterator().next();
					 bin.sizes[j] = ((Long) mapSizes.get(j).get(key)).intValue();
					 //System.out.println(size.dims);
				 }
				 
				 instance.bins[i] = bin;
			}
			
			System.out.println("Print Description Dimensions");					
			System.out.println(instance.descrDims);
			
			System.out.println("Print " + ITEMS_KEY);					
			System.out.println(Arrays.asList(instance.items));

			System.out.println("Print " + BINS_KEY);					
			//System.out.println(Arrays.asList(instance.bins));
			
			
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

	class ItemObject implements Item {
		
		//{ "name": "MySQL-LB", "consume": [ {"ram", 128} ], "arity": 2 },
	    
		String name = null;
		int[] sizes = null;
		int arity = -1;
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getSize(int dim) {
			return sizes[dim];
		}
		
		@Override
		public int getArity() {
			return arity;
		}
		
		@Override
		public String toString() {
			return  "name : " + name + "\n"
					+ "sizes : " + Arrays.toString(sizes) + "\n"
					+ "arity : " + arity + "\n";
		}
	}

	class BinObject implements Bin {
		 /*
		{
		      "name": "Location_category_1",
		      "provide": [ {"ram", 2048} ],
		      "cost": 1,
		      "arity": 6
		    }
		    */
		String name = null;
		int[] sizes = null;
		int cost = -1;
		int arity = -1;

		@Override
		public String getName() {
			return name;
		}
		@Override
		public int getSize(int dim) {
			return sizes[dim];
		}
		@Override
		public int getArity() {
			return arity;
		}
		@Override
		public int getCost() {
			return cost;
		}
		
		@Override
		public String toString() {
			return  "name : " + name + "\n"
					+ "sizes : " + Arrays.toString(sizes) + "\n"
					+ "arity : " + arity + "\n"
					+ "cost : " + cost + "\n";
		}
	}
	
	class DescriptionSizes implements Dimensions {
		
		String[] dims = null;
		
		@Override
		public int getDimensionCount() {
			return dims.length;
		}

		@Override
		public int getIndex(String dim) {
			int index = -1;
			for(int i = 0; i < dims.length; i++) {
				if(dims[i].equals(dim)) {
					index = i;
					break;
				}				
			}
			return index;
		}

		@Override
		public String getName(int dim) {
			return dims[dim];
		}	
		
		@Override
		public String toString() {
			return Arrays.toString(dims);			
		}
	}
	
	public static void main(String[] args) {
		JSonParser json = new JSonParser();
		json.loadInstance(new File("src/test/resources/veryredundant-highmysqlandnovacompute-wheezy/binpacking.json"));
		json.parse(true);
	}

}

