package com.mty.property.common.support.snowflake;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mty
 * @since 2022/09/02 09:31
 **/
public class SnowflakeIdGenerator {
    // 开始时间戳（2017-01-01）
    @Getter
    @Setter
    private long twepoch = 1483200000000L;

    private static final long CUSTOM_ID_BITS = 12L;

    // 业务id所占的位数
    private final long customIdBits;

    private static final long SEQ_BITS = 12L;

    // 序列在id中占的位数
    private final long sequenceBits;

    // 毫秒内序列（0~4096）
    private long sequence = 0;

    // 上次生成ID的时间戳
    private long lastTimestamp = 0;

    private final long customId;

    public SnowflakeIdGenerator(long workerId) {
        this(workerId, CUSTOM_ID_BITS, SEQ_BITS);
    }

    public SnowflakeIdGenerator(long workerId, long customIdBits, long sequenceBits) {
        this.customIdBits = customIdBits;
        this.sequenceBits = sequenceBits;
        this.customId = workerId & (~(-1L << sequenceBits));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp == lastTimestamp) {
            /**
             * 生成序列的掩码
             */
            sequence = (sequence + 1) & (~(1L << sequenceBits));
            if (sequence == 0) {
                timestamp = tilNextMillis();
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        /**
         * 组成64位
         */
        return ((timestamp - twepoch) << (sequenceBits + customId))
                | (customId << (sequenceBits)) | sequence;


    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private long tilNextMillis() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

}
