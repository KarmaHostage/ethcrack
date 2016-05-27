package com.karmahostage.ethcrack.cli;

import com.karmahostage.ethcrack.ThreadedDecryption;
import com.karmahostage.ethcrack.domain.EthKeystore;
import com.karmahostage.ethcrack.parsing.EthKeystoreJsonParser;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class CommandInterpreter {

    private ExecutorService executorService;

    public CommandInterpreter() {
        this.executorService = Executors.newFixedThreadPool(16);
    }

    public void interpret(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = getOptions();
        try {
            CommandLine cmd = parser.parse(options, args);
            interpretValidCommand(cmd);
        } catch (ParseException e) {
            System.out.println(options.toString());
        } catch (RuntimeException exc) {
            System.out.println("error: " + exc.getMessage());
        }
    }

    private void interpretValidCommand(CommandLine cmd) {
        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ethcrack", getOptions());
        } else {
            File wallet = interpetWallet(cmd);
            EthKeystore ethKeystore = new EthKeystoreJsonParser()
                    .parse(wallet);

            Optional<String> password = interpetPassword(cmd);
            if (password.isPresent()) {
                process(ethKeystore, password.get());
            } else {
                File file = interpretWordlist(cmd);
                System.out.println("going to try recovering with the wordlist");
                try (Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()))) {
                    lines.forEachOrdered(line -> process(ethKeystore, line));
                } catch (IOException e) {
                    System.out.println("unable to read wordlist");
                }
            }
            this.executorService.shutdown();
        }
    }

    private void process(EthKeystore ethKeystore, String line) {
        this.executorService.execute(
                new ThreadedDecryption(ethKeystore, line)
        );
    }

    private Optional<String> interpetPassword(CommandLine cmd) {
        if (cmd.hasOption("p")) {
            return Optional.of(cmd.getOptionValue("p"));
        } else {
            return Optional.empty();
        }
    }

    private File interpetWallet(CommandLine cmd) {
        String wallet;
        if (cmd.hasOption("w")) {
            wallet = cmd.getOptionValue("w");
            System.out.println(String.format("using different wallet file %s", wallet));
        } else {
            wallet = "wallet.json";
            System.out.println("using default wallet file ./wallet.json");
        }

        File file = new File(wallet);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("%s was not found", wallet));
        } else {
            return file;
        }
    }

    private File interpretWordlist(CommandLine cmd) {
        String wordlist;
        if (cmd.hasOption("f")) {
            wordlist = cmd.getOptionValue("f");
            System.out.println(String.format("using different wordlist file %s", wordlist));
        } else {
            wordlist = "wordlist.txt";
            System.out.println("using default wordlist file ./wordlist.txt");
        }

        File file = new File(wordlist);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("%s was not found", wordlist));
        } else {
            return file;
        }
    }

    private Options getOptions() {

        Option help = Option.builder("h")
                .desc("print this help")
                .longOpt("help")
                .build();

        Option wordlist = Option.builder("f")
                .argName("default: wordlist.txt")
                .longOpt("wordlist")
                .hasArg()
                .desc("use a wordlist to recover your password")
                .build();

        Option password = Option.builder("p")
                .longOpt("password")
                .hasArg()
                .desc("test specific password")
                .build();

        Option wallet = Option.builder("w")
                .longOpt("wallet")
                .argName("default: wallet.json")
                .hasArg()
                .desc("mist wallet file")
                .build();

        return new Options()
                .addOption(help)
                .addOption(wordlist)
                .addOption(password)
                .addOption(wallet);
    }

}
