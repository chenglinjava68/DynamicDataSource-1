package com.foundation.dynamic.dao;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author fqh
 *
 */
public class DataSourceSwitcher {
	
	private ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	private AtomicInteger index = new AtomicInteger(0);
	
	private List<String> slaves;

	public void setDataSource(String dataSource) {
		contextHolder.set(dataSource);
	}

	public void setMaster() {
		clearDataSource();
		setDataSource("master");
	}

	public void setSlave() {
		clearDataSource();
		if (index.get() >= slaves.size()) {
			index.set(0);
		}
		setDataSource(slaves.get(index.get()));
		index.addAndGet(1);
	}

	public String getDataSource() {
		return (String) contextHolder.get();
	}

	public void clearDataSource() {
		contextHolder.remove();
	}

	public List<String> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<String> slaves) {
		this.slaves = slaves;
	}
}
