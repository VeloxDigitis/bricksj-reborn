package me.veloxdigitis.bricksj.info;

import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.stats.Time;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SimpleHTMLHistoryInfoParser implements HistoryInfoParser {

    private String HTML;

    public SimpleHTMLHistoryInfoParser() {
        try {
            this.HTML = new String(Files.readAllBytes(new File(getClass().getResource("/templates/info.html").getFile()).toPath()));
        } catch (IOException e) {
            Logger.error("Couldn't load HTML template");
            this.HTML = "%title%<br />%winner%";
        }
    }

    @Override
    public String parse(BattleHistory history) {
        Time time = new Time(history);
        return HTML.
                replaceAll("%title%",       history.toString()).
                replaceAll("%winner%",      history.getWinner().toString()).
                replaceAll("%winnermin%",   time.getMin(history.getWinner()) + "").
                replaceAll("%winnermax%",   time.getMax(history.getWinner()) + "").
                replaceAll("%winneravg%",   ((int)(time.getAverage(history.getWinner()) * 100) / 100.0) + "");
    }
}
