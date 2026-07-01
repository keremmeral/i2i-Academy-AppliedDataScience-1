package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class StudentConsumer {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "student-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("student-topic"));

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Waiting for messages...");

        while (true) {
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofSeconds(1));

            for (ConsumerRecord<String, String> record : records) {
                Student student = mapper.readValue(record.value(), Student.class);

                System.out.println("Received Student");
                System.out.println("----------------");
                System.out.println("Name       : " + student.getName());
                System.out.println("Age        : " + student.getAge());
                System.out.println("Department : " + student.getDepartment());
                System.out.println();
            }
        }
    }
}