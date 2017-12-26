package me.veloxdigitis.bricksj.proxy;

import me.veloxdigitis.bricksj.logger.Logger;

import java.io.*;
import java.nio.file.Path;

public abstract class StandardIOAlgorithm implements Algorithm {

    private final Path rootPath;
    private final String runCommand;

    private Process process;
    private BufferedReader reader;
    private BufferedWriter writer;

    public StandardIOAlgorithm(Path rootPath, String runCommand) {
        this.rootPath = rootPath;
        this.runCommand = runCommand;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", runCommand);
            processBuilder.directory(rootPath.toFile());
            this.process = processBuilder.start();
            this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        } catch (IOException e) {
            Logger.error("Couldn't start process");
        }
    }

    @Override
    public void terminate() {
        try {
            Runtime.getRuntime().exec("taskkill /F /pid " + process.pid());
            process.destroy();
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        try {
            Logger.info("Sending " + message + " to " + getName());
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            Logger.error("Couldn't communicate with algorithm");
        }
    }

    public String read() {

        try {
            return reader.readLine().toLowerCase();
        } catch (IOException e) {
            Logger.error("Couldn't communicate with algorithm");
            return "";
        }
    }

}
