package com.annakhuseinova.springcloudstreamsavrotojson.bindings;

import com.annakhuseinova.springcloudstreamsaavro.model.PosInvoice;
import com.annakhuseinova.springcloudstreamsavrotojson.model.HadoopRecord;
import com.annakhuseinova.springcloudstreamsavrotojson.model.Notification;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface PosListenerBinding {

    @Input("notification-input-channel")
    KStream<String, PosInvoice> notificationInputStream();

    @Output("notification-out-channel")
    KStream<String, Notification> notificationOutputStream();

    @Input("hadoop-input-channel")
    KStream<String, PosInvoice> hadoopInputStream();

    @Output("hadoop-output-channel")
    KStream<String, HadoopRecord> hadoopOutputStream();
}
