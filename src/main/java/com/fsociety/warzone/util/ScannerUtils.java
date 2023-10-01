package com.fsociety.warzone.util;

import java.util.Scanner;

public class ScannerUtils implements AutoCloseable {
    private final Scanner scanner;

    public ScannerUtils() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * 
     * @param prompt
     * @param emptyInputMessage
     * @return
     */
    public String inputString(
            final String prompt,
            final String emptyInputMessage) {
        return inputString(prompt, emptyInputMessage, false, null);
    }

    public String inputString(
            final String prompt,
            final String emptyInputMessage,
            final boolean isInputSenstive,
            final String sensitiveFieldPrompt) {
        String cred = null;
        if (!isInputSenstive) {
            System.out.print(prompt);
            cred = scanner.nextLine();
        } else {
            cred = new String(System.console().readPassword(sensitiveFieldPrompt));
        }
        if (StringUtils.isBlank(cred)) {
            System.out.print(emptyInputMessage);
            inputString(prompt, emptyInputMessage);
        }
        return cred;
    }

    public int fetchInt(
            final String message,
            final String... options) {
        UserInstructionUtils.promptUser(message, options);
        final int intVal = scanner.nextInt();
        scanner.nextLine();
        return intVal;
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}