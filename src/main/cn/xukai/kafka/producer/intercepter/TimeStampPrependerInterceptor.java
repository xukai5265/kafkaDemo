package cn.xukai.kafka.producer.intercepter;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Created by kaixu on 2017/11/30.
 * 生产者拦截器
 * 目的： 在发送消息之前对消息进行处理，在消息体的头部，添加时间戳信息
 */
public class TimeStampPrependerInterceptor implements ProducerInterceptor{
    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        return new ProducerRecord(
                record.topic(), record.partition(), record.timestamp(), record.key(), System.currentTimeMillis() + "," + record.value().toString());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
