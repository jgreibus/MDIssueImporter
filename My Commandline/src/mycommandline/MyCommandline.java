package mycommandline;

import com.nomagic.magicdraw.commandline.CommandLine;

public class MyCommandline extends CommandLine
{
	public static void main(String[] args)
	{
		new MyCommandline().launch(args);
	}

	@Override
	protected byte execute()
	{
		System.out.println("Executing my command line...");
		return 0;
	}
}
