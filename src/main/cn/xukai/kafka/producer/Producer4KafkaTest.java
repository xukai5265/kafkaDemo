package cn.xukai.kafka.producer;

/**
 * Created by kaixu on 2017/11/29.
 */
public class Producer4KafkaTest {
    public static void main(String[] args) {
        Producer4Kafka producer4Kafka = new Producer4Kafka("xk-3",false);
        producer4Kafka.start();
    }
}
