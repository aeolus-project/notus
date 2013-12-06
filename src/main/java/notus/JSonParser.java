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
import parser.instances.InstanceFileParser;

public class JSonParser implements InstanceFileParser {

	public JSonParser() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public File getInstanceFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Item[] getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public Bin[] getBins() {
		// TODO Auto-generated method stub
		return null;
	}

	public Dimensions getDimensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadInstance(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parse(boolean displayInstance)
			throws UnsupportedConstraintException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

}
