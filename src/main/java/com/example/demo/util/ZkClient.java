package com.example.demo.util;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.config.Config;

import lombok.Data;

@Data
public class ZkClient implements Watcher {

	private static Logger logger = LoggerFactory.getLogger(ZkClient.class);

	private ZooKeeper zooKeeper;

	private String host;

	private CountDownLatch countDownLatch = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			countDownLatch.countDown();
		}
	}

	public ZkClient(String host) {
		super();
		this.host = host;
	}

	/**
	 * 调用后获得连接好的Zookeeper
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static ZooKeeper getInstance() throws IOException, InterruptedException {
		ZkClient client = new ZkClient(Config.ZK_HOST);
		return client.connect();
	}

	/**
	 * 连接zookeeper
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private ZooKeeper connect() throws IOException, InterruptedException {
		this.zooKeeper = new ZooKeeper(this.host, 2000, this);
		if (!countDownLatch.await(5000, TimeUnit.MILLISECONDS)) {
			zooKeeper.close();
			throw new InterruptedException("连接超时");
		}
		logger.info("zookeeper connect success!");
		return zooKeeper;
	}
	

	public static void main(String[] args) throws IOException, InterruptedException  {
		ZooKeeper client = ZkClient.getInstance();
		client.close();
	}

}
