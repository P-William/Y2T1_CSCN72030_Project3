package com.group3;

import com.group3.objects.Logs;
import com.group3.objects.Sensor;

import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    private String logFilePath;
    private BufferedWriter writeToFile;
    private List<Logs> logEntry;
    private String errorCatch;


    public String operatorLogs(String filePath){

        System.out.println("peepee");

        return "success";
    }

    public Logs writeLogs(Logs logging){

        return logging;

    }

    private List<Logs> formatLogs(String action, LocalDateTime timestamp, List<Sensor> sensorList) {

        List<Logs> logList = new ArrayList<>();

        return logList;
    }

    private String logError(String error){

        return "poopoo";
    }

}
