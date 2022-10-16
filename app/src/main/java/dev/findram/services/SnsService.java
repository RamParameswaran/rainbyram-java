package dev.findram.services;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.MessageAttributeValue;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SnsService {
    private final String TOPIC_ARN = System.getenv("SNS_TOPIC_ARN");
    private final AmazonSNS snsClient = AmazonSNSClient
                                        .builder()
                                        .withRegion("eu-west-2")
                                        .build();

    public void publish() {
        // Format today's date as string
        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        // Construct SNS request
        PublishRequest request = new PublishRequest(
                TOPIC_ARN,
                String.format("😮 It's going to rain today %s! Make sure you bring an ☂", dateString)
                );

        // Add MessageAttribute for SMS SenderID
        MessageAttributeValue messageAttributes = new MessageAttributeValue();
        messageAttributes.setDataType("String");
        messageAttributes.setStringValue("RainByRam");
        request.addMessageAttributesEntry("AWS.SNS.SMS.SenderID", messageAttributes);

        // Publish message to SNS topic
        snsClient.publish(request);
        System.out.println("SNS message published!");
    }
}
