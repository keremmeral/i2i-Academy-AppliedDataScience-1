package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class StudentProducer {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        Student student = new Student("Kerem", 22, "Economics");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(student);

        ProducerRecord<String, String> record =
                new ProducerRecord<>("student-topic", json);

        producer.send(record);

        System.out.println("Sent -> " + json);

        producer.close();
    }
}