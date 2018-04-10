package com.demo.gametask.model.config;

public class BossRaidConfig {

    private int id;
    private BossConfig bossConfig;
    private String name;
    private long beginTime;
    private long endTime;
    private long period;
    private long announceLength;
    private long length;
    private long showLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BossConfig getBossConfig() {
        return bossConfig;
    }

    public void setBossConfig(BossConfig bossConfig) {
        this.bossConfig = bossConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public long getAnnounceLength() {
        return announceLength;
    }

    public void setAnnounceLength(long announceLength) {
        this.announceLength = announceLength;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getShowLength() {
        return showLength;
    }

    public void setShowLength(long showLength) {
        this.showLength = showLength;
    }
}
