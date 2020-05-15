package ru.otus.hw3;


import org.apache.commons.cli.*;
import java.util.logging.Logger;
import ru.otus.hw3.TestRunner;

/**
 * Command line tool for test running
 */
public class ConsoleLauncher {
    public static void main(String[] args) {
        Options cliOptions = new Options();
        cliOptions.addRequiredOption("i", "input-test-class", true, "Name of class which contains tests");

        HelpFormatter help = new HelpFormatter();

        CommandLineParser parser = new DefaultParser();

        try{
            CommandLine cli = parser.parse(cliOptions, args);
            String className = cli.getOptionValue("i");
            new TestRunner(className);
        }
        catch (ParseException e){
            help.printHelp("Runs all tests which present in file passed as -i arg", cliOptions);
        }
    }
}
