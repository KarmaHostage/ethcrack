package com.karmahostage.ethcrack;

import com.karmahostage.ethcrack.cli.CommandInterpreter;

public class Ethcrack {

    public static void main(String[] args) {
        new CommandInterpreter().interpret(args);
    }

}
