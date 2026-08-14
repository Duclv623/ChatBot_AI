package com.jurisai.util;

public class SnowflakeIdGenUtils {
    private final long workerId;
    private final long datacenterId;

    private final long sequenceBits = 4L;           // Giảm xuống 4 bit
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;

    private final long maxWorkerId = ~(-1L << workerIdBits);
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = ~(-1L << sequenceBits);

    private final long twepoch = 1754332800000L;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenUtils(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0)
            throw new IllegalArgumentException("Worker ID out of range");
        if (datacenterId > maxDatacenterId || datacenterId < 0)
            throw new IllegalArgumentException("Datacenter ID out of range");
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp)
            throw new RuntimeException("Clock moved backwards");

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long id = ((timestamp - twepoch) << timestampShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;

        // Kiểm tra xem có vượt quá MAX_SAFE_INTEGER không
        if (id > 9007199254740991L) {
            throw new RuntimeException("Generated ID exceeds JavaScript safe integer limit");
        }

        return id;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
