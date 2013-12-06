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

import cli.AbstractBenchmarkCmd;

import parser.instances.AbstractInstanceModel;
import parser.instances.BasicSettings;

public class NotusCmd extends AbstractBenchmarkCmd {

	public NotusCmd() {
		super(new BasicSettings());
	}

	@Override
	public boolean execute(File file) {
		instance.solveFile(file);
		return instance.getStatus().isValidWithCSP();
	}

	@Override
	protected AbstractInstanceModel createInstance() {
		return new NotusModel(settings);
	}

	public static void main(String[] args) {
		final NotusCmd cmd = new NotusCmd();
		if(args.length==0) {cmd.help();}
		else {cmd.doMain(args);}
	}
}
