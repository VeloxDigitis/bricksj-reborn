package me.veloxdigitis.bricksj.info;

import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.stats.Time;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class SimpleHTMLHistoryInfoParser implements HistoryInfoParser {

    private String HTML;

    public SimpleHTMLHistoryInfoParser() {
        try {
            this.HTML = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("templates/info.html"), "UTF-8");
        } catch (IOException e) {
            Logger.error("Couldn't load HTML template");
            this.HTML = "%title%<br />%winner%";
        }
    }

    @Override
    public String parse(BattleHistory history) {
        Time time = new Time(history);
        PlayersPair players = history.getPlayers();
        return HTML.
                replaceAll("%title%",       history.toString()).
                replaceAll("%winner%",      history.getWinner().toString()).
                replaceAll("%a%",       players.get().toString()).
                replaceAll("%amin%",   time.getMin(players.get()) + "").
                replaceAll("%amax%",   time.getMax(players.get()) + "").
                replaceAll("%aavg%",   ((int)(time.getAverage(players.get()) * 100) / 100.0) + "").
                replaceAll("%b%",       players.getOpponent().toString()).
                replaceAll("%bmin%",   time.getMin(players.getOpponent()) + "").
                replaceAll("%bmax%",   time.getMax(players.getOpponent()) + "").
                replaceAll("%bavg%",   ((int)(time.getAverage(players.getOpponent()) * 100) / 100.0) + "");
    }
}
