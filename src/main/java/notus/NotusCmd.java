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
