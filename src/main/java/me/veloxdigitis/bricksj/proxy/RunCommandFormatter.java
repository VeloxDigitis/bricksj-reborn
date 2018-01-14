package me.veloxdigitis.bricksj.proxy;

import java.util.Arrays;

public class RunCommandFormatter {

    public static String[] format(String path, String runCommand) {
        return Arrays.stream(runCommand.split("\\s+")).
                map(a -> {
                    if(a.endsWith(".exe"))
                        return path + "\\" + a;
                    return a;
                }).
                map(a -> a.replaceAll("\\\\", "/")).
                toArray(String[]::new);
    }

}
