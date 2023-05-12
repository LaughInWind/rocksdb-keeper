package cn.mrray.rocksdbkeeper;

import cn.mrray.rocksdbkeeper.db.DatabaseOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@SpringBootApplication
public class RocksdbKeeperApplication {

    @Value("${rocksdb.channel}")
    private String channel;

    @Autowired
    private DatabaseOperator databaseOperator;

    public static void main(String[] args) {
        SpringApplication.run(RocksdbKeeperApplication.class, args);
    }

    /**
     * 应用启动时初始化RocksDB连接
     * @throws IOException
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() throws IOException {
        databaseOperator.setChannel(channel);
    }

}
