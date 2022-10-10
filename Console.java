package com.mathias;

import java.util.Scanner;

public class Console {
    private String prompt;

    public void consolePrintLn(String prompt){

        System.out.println(prompt);
    }
    public void consolePrint(String prompt){

        System.out.print(prompt);
    }
    public String consoleInput(String prompt){
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.next();
    }
}
