package com.snicesoft.freefir.utils;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
/**
 * 自定义主鍵生成策略
 * @author zrk  
 * @date 2015年9月28日 上午10:50:14
 */
public class IdGenerator implements IdentifierGenerator {

	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		return IdWorker.getInstance().nextId();
	}

}
