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
