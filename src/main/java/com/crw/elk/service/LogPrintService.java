package com.crw.elk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class LogPrintService {

    private static final Logger log = LoggerFactory.getLogger(LogPrintService.class);

    public void printLog() {
        log.info("LogPrintService print log, uuid:{}", UUID.randomUUID().toString());
    }

}
