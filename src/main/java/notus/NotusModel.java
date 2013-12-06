package notus;

import java.io.File;

import parser.absconparseur.tools.UnsupportedConstraintException;
import parser.instances.AbstractMinimizeModel;
import choco.kernel.model.Model;
import choco.kernel.solver.Configuration;

public class NotusModel extends AbstractMinimizeModel {

	
	private Item[] items;
	public Bin[] bins;
	public Dimensions dimensions;

	protected NotusModel(Configuration settings) {
		super(new JSonParser(), settings);
	}

	
	
	
	@Override
	public void initialize() {
		super.initialize();
		items=null;
		bins=null;
		dimensions=null;		
	}




	@Override
	public void load(File fichier) throws UnsupportedConstraintException {
		super.load(fichier);
		JSonParser parser= (JSonParser) getParser();
		items=parser.getItems();
		bins=parser.getBins();
		dimensions=parser.getDimensions();
	}



	@Override
	protected Object makeSolutionChart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model buildModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
