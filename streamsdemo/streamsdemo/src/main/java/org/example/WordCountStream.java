package org.example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

public class WordCountStream {
//User case word count, words are been count as the introduce uinto the flow,
    public static void main (String [] args){
       Properties props = new Properties();
       props.put(StreamsConfig.APPLICATION_ID_CONFIG,"streams-wordcount");
       props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
       props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
       props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
       props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

       StreamsBuilder builder = new StreamsBuilder();
       KStream<String,String> stream = builder.stream("streams-wordcount-input");
       KGroupedStream<String,String> KGroupedStream = stream.flatMapValues(values-> Arrays.asList(values.toLowerCase().split(" ")))
               .groupBy((key,value)->value);

       KTable<String,Long> countsTable = KGroupedStream.count();
       countsTable.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(),Serdes.Long()));

       Topology topology =builder.build();
       System.out.println(topology.describe());

       KafkaStreams streams = new KafkaStreams(topology,props);
      // streams.cleanUp();
       streams.start();

       Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
                                                //to add a close when the button stop is hitted
    }
}
