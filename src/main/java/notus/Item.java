package notus;

import gnu.trove.TObjectIntHashMap;


public interface Item {
	
	
	String getName();
	int getSize(String dim);
	int getSize(int dim);
	int getArity();
	
}

