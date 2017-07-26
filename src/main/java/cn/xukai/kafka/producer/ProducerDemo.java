package cn.xukai.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * Created by kaixu on 2017/7/3.
 * kafka 生产者
 *
 * 需要在 kafka/config/server.properties 配置文件中添加
 * host.name=192.168.107.128
 * advertised.host.name=192.168.107.128
 * 192.168.107.128 为kafka 所在机器的ip地址
 *
 * 启动 zk
 * bin/zookeeper-server-start.sh config/zookeeper.properties
 * 启动 kafka server
 * bin/kafka-server-start.sh config/server.properties
 *
 * 参考链接： http://orchome.com/303
 */
public class ProducerDemo {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.107.128:9092");
        /*
          ack ： 0 ： topic leader 分区接收到 该消息立即返回成功
                 1 ： topic leader 分区持久化后 返回成功
            -1/ all： topic leader 分区及副本 持久化后 返回成功
         */
        props.put("acks", "all");
        props.put("retries", 0);        // 重试次数
        props.put("batch.size", 16384); // 默认为0 ，表示接收到消息立即发送。16k 为每个分区缓存消息，一旦满了就打包将消息批量发出。
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100; i++){
            ProducerRecord record= new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i));
//            Future<RecordMetadata> future = producer.send(record);
//            future.get();  //阻塞   直到请求完毕后才返回。
//            future.get(10000,SECONDS); // 阻塞 指定超时时间

            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e != null)
                        e.printStackTrace();
                    System.out.println("The offset of the record we just sent is: " + recordMetadata.offset());
                }
            });
        }

        producer.close();
    }
}
