package me.veloxdigitis.bricksj.config;

import me.veloxdigitis.bricksj.logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InfoFileLoader {

    public InfoFile load(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        if(lines.size() < 2)
            throw new InfoFileException();
        return new InfoFile(path, lines.get(0), lines.get(1));
    }

    public List<InfoFile> loadAll(Path path, Pattern infoFilePattern) throws IOException {
        return Files.walk(path).
                filter(p -> infoFilePattern.matcher(p.getFileName().toString()).find()).
                map(this::loadOpt).
                flatMap(Optional::stream).
                collect(Collectors.toList());
    }

    private Optional<InfoFile> loadOpt(Path path) {
        try {
            return Optional.of(load(path));
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return Optional.empty();
        }
    }

}
