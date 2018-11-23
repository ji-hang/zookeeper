package com.example.demo.run;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.config.Config;
import com.example.demo.exception.BusinessException;
import com.example.demo.util.PropertiesUtil;
import com.example.demo.util.ZkClient;

@Component
public class Run implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String name = Config.ROOT + PropertiesUtil.getProperty("server.name");
		ZooKeeper client = ZkClient.getInstance();
		Stat stat = client.exists(name, null);
		if(stat != null) {
			throw new BusinessException("服务名【" + PropertiesUtil.getProperty("server.name") + "】已存在... ");
		}
		client.create(name, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}

}
