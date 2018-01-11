package me.veloxdigitis.bricksj.proxy;

import me.veloxdigitis.bricksj.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProcessRegistry {

    private static ProcessRegistry instance;

    public static ProcessRegistry getInstance() {
        if(instance == null)
            instance = new ProcessRegistry();
        return instance;
    }

    private List<Process> registry = new ArrayList<>();

    public void register(Process p) {
        Logger.info("Registering " + p.toString());
        registry.add(p);
    }

    public void killAll() {
        Logger.info("Killing all processes");
        registry.forEach(Process::destroy);
        registry.clear();
    }

}
