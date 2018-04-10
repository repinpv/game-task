package com.demo.gametask.utils;

import com.demo.gametask.model.config.BossConfig;
import com.demo.gametask.model.config.BossRaidConfig;

public class ConfigUtils {

    public static final BossConfig BOSS_CONFIG;
    public static final BossRaidConfig BOSS_RAID_CONFIG;

    static {
        BOSS_CONFIG = new BossConfig();
        BOSS_CONFIG.setId(1);
        BOSS_CONFIG.setName("Hell Cat");
        BOSS_CONFIG.setImage("");
        BOSS_CONFIG.setHealthPoints(1000000);
        BOSS_CONFIG.setMassAttackDamage(15);
        BOSS_CONFIG.setTopAttackDamage(30);
        BOSS_CONFIG.setSingleAttackDamage(50);
        BOSS_CONFIG.setMassAttackInterval(60000);
        BOSS_CONFIG.setTopAttackInterval(120000);
        BOSS_CONFIG.setSingleAttackInterval(1000);
        BOSS_CONFIG.setMassAttackDelay(45000);
        BOSS_CONFIG.setTopAttackDelay(90000);
        BOSS_CONFIG.setSingleAttackDelay(1000);

        BOSS_RAID_CONFIG = new BossRaidConfig();
        BOSS_RAID_CONFIG.setId(1);
        BOSS_RAID_CONFIG.setBossConfig(BOSS_CONFIG);
        BOSS_RAID_CONFIG.setName("Hell Cat's Hunt!!!");
        BOSS_RAID_CONFIG.setBeginTime(0); // from 1970
        BOSS_RAID_CONFIG.setEndTime(Long.MAX_VALUE);
        BOSS_RAID_CONFIG.setPeriod(20 * 60000);
        BOSS_RAID_CONFIG.setAnnounceLength(3 * 60000);
        BOSS_RAID_CONFIG.setLength(10 * 60000);
        BOSS_RAID_CONFIG.setShowLength(4 * 60000);
    }
}
