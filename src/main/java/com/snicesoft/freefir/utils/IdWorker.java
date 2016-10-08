package com.snicesoft.freefir.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 *  来自于twitter项目 
 *  <a href="https://github.com/twitter/snowflake">snowflake</a>的id产生方案，全局唯一，时间有序 
 *  
 * @author zrk  
 * @date 2015年12月3日 下午4:16:04
 */
public class IdWorker{
//    private final static Logger logger = LoggerFactory.getLogger(IdWorker.class);
    //机器标识位数
	@Value("${base.workerId}")
    private static long workerId;
    //机器ID最大值
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 1023,1111111111,10位
    
    private final long snsEpoch = 1288834974657L;// 起始标记点，作为基准
    private long sequence = 0L;// 0，并发控制
    private final long workerIdBits = 5L;// 只允许workid的范围为：0-1023
    private final long sequenceBits = 10L;// sequence值控制在0-4095

    private final long workerIdShift = sequenceBits;
    private final long timestampLeftShift =  sequenceBits + workerIdBits ;
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;// 4095,111111111111,12位

    private long lastTimestamp = -1L;
    
    private static IdWorker idWorker;
    
    public static synchronized IdWorker getInstance(){
    	if(idWorker == null)
    		idWorker = new IdWorker(workerId>=0?workerId:1);
		return idWorker;
    }
    
    public IdWorker(long workerId ) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {// workid < 1024[10位：2的10次方]
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }
    public synchronized Long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {// 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
            //System.out.println("lastTimeStamp:" + lastTimestamp);
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
            }
        }
        else {
            this.sequence = 0;
        }
        this.lastTimestamp = timestamp;
        return timestamp - this.snsEpoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
    }

    /**
     * 	等待下一个毫秒的到来 
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     * 
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

	public long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(long workerId) {
		IdWorker.workerId = workerId;
	}
	
  
    
}
