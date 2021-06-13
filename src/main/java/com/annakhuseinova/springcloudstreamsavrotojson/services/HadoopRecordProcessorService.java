package com.annakhuseinova.springcloudstreamsavrotojson.services;

import com.annakhuseinova.springcloudstreamsaavro.model.PosInvoice;
import com.annakhuseinova.springcloudstreamsavrotojson.bindings.PosListenerBinding;
import com.annakhuseinova.springcloudstreamsavrotojson.model.HadoopRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableBinding(PosListenerBinding.class)
@RequiredArgsConstructor
public class HadoopRecordProcessorService {

    private final RecordBuilder recordBuilder;

    @StreamListener("hadoop-input-channel")
    @SendTo("hadoop-output-channel")
    public KStream<String, HadoopRecord> process(KStream<String, PosInvoice> input){
        KStream<String, HadoopRecord> hadoopRecordKStream = input.mapValues(recordBuilder::getMaskedInvoice)
                .flatMapValues(recordBuilder::getHadoopRecords);
        hadoopRecordKStream.foreach((key, value)-> log.info(String.format("Hadoop Record:- Key: %s, Value: %s",
                key, value)));
        return hadoopRecordKStream;
    }
}
