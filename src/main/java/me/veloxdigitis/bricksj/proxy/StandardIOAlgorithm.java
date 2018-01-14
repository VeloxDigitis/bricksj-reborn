package me.veloxdigitis.bricksj.proxy;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import me.veloxdigitis.bricksj.logger.Logger;
import org.junit.rules.Timeout;

import java.io.*;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class StandardIOAlgorithm implements Algorithm {

    private final Path rootPath;
    private final String[] runCommand;

    private Process process;
    private BufferedReader reader;
    private PrintWriter writer;

    private final TimeLimiter timeLimiter = SimpleTimeLimiter.create(Executors.newSingleThreadExecutor());

    public StandardIOAlgorithm(Path rootPath, String runCommand) {
        this.rootPath = rootPath;
        this.runCommand = RunCommandFormatter.format(rootPath.toAbsolutePath().toString(), runCommand);
    }

    @Override
    public void run() {
        try {
            Logger.info("Starting " + getName());
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(runCommand);
            processBuilder.directory(rootPath.toFile());
            this.process = processBuilder.start();
            ProcessRegistry.getInstance().register(process);
            this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            this.writer = new PrintWriter(process.getOutputStream(), true);
        } catch (IOException e) {
            Logger.error("Couldn't start process");
        }
    }

    @Override
    public void terminate() {
        try {
            Logger.info("Killing " + getName());

            process.destroy();
            writer.close();
            reader.close();

            Logger.info(getName() + " successfully killed");
        } catch (NullPointerException | IOException e) {
            Logger.error("Couldn't communicate with algorithm");
        } catch (NoSuchMethodError e) {
            Logger.info("Please install Java 9");
        }
    }

    public void send(String message) {
        try {
            Logger.info(String.format("%s <- %s", getName(), message));
            writer.println(message);
        } catch (NullPointerException e) {
            Logger.error("Couldn't communicate with algorithm");
        }
    }

    public String read() throws IOException, TimeoutException {
        try {
            String result = timeLimiter.callWithTimeout(reader::readLine, 2, TimeUnit.SECONDS);
            Logger.info(String.format("%s -> %s", getName(), result.toLowerCase()));
            return result.toLowerCase();
        } catch (NullPointerException | InterruptedException | ExecutionException e) {
            throw new IOException("Can't read");
        }
    }

}
