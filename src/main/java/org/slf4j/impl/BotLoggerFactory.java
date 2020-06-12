package org.slf4j.impl;

import org.slf4j.ILoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BotLoggerFactory implements ILoggerFactory
{
    private final ConcurrentMap<String, BotLogger> loggerMap = new ConcurrentHashMap<>();
    private final List<String> logList = new ArrayList<>();

    public BotLogger getLogger(String name)
    {
        BotLogger logger = loggerMap.get(name);
        if (logger != null) {
            return logger;
        } else {
            BotLogger newInstance = new BotLogger(name, this);
            BotLogger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }

    protected void log(String log)
    {
        logList.add(log);
    }

    public void save() throws IOException
    {
        File folder = new File("logs");
        if(!folder.exists()) folder.mkdir();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("logs/"+BotLogger.simpleDate.format(new Date()).replace(":", "-")+".log"), "UTF-8"));

        for(String str : logList) {
            writer.write(str);
            writer.newLine();
        }

        writer.flush();
    }
}