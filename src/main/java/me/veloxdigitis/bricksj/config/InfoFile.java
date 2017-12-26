package me.veloxdigitis.bricksj.config;

import java.nio.file.Path;

public class InfoFile {

    private final Path filePath;

    private final String runCommand;
    private final String playerName;

    public InfoFile(Path filePath, String runCommand, String playerName) {
        this.filePath = filePath;
        this.runCommand = runCommand;
        this.playerName = playerName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getRunCommand() {
        return runCommand;
    }

    public String getPlayerName() {
        return playerName;
    }

}
