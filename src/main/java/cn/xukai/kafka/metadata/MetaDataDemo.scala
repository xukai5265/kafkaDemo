package cn.xukai.kafka.metadata

import kafka.api.TopicMetadataRequest._
import kafka.api.{TopicMetadataRequest, TopicMetadataResponse}
import kafka.consumer.SimpleConsumer

/**
  * Created by kaixu on 2017/7/31.
  * kafka metadata 协议
  * 作用： client 发送metadata协议到broker，broker处理返回分区信息，其中包括：
  *       1. 该主题的一共有几个分区
  *       2. Leader分区所在的broker地址及端口？
          3. 每个broker的地址及端口是多少？
  * https://www.iteblog.com/archives/2215.html
  */
object MetaDataDemo {
  def main(args: Array[String]): Unit = {
    val consumer = new SimpleConsumer("192.168.107.128",9092,50,1024*4,DefaultClientId)
    val req: TopicMetadataRequest = new TopicMetadataRequest(CurrentVersion,0,DefaultClientId,List("test"))
    val resp: TopicMetadataResponse = consumer.send(req)
    println("Broker Infos:")
    println(resp.brokers.mkString("\n\t"))
    val metadata = resp.topicsMetadata
    metadata.foreach { topicMetadata =>
      val partitionsMetadata = topicMetadata.partitionsMetadata
      partitionsMetadata.foreach { partitionMetadata =>
        println(s"partitionId=${partitionMetadata.partitionId}\n\tleader=${partitionMetadata.leader}" +
          s"\n\tisr=${partitionMetadata.isr}\n\treplicas=${partitionMetadata.replicas}")
      }
    }
  }
}
