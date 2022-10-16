package dev.findram.services;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.MessageAttributeValue;

public class SnsService {
    private final String TOPIC_ARN = System.getenv("SNS_TOPIC_ARN");
    private final AmazonSNS snsClient = AmazonSNSClient
                                        .builder()
                                        .withRegion("eu-west-2")
                                        .build();

    public void publish(String message) {
        // Construct SNS request
        PublishRequest request = new PublishRequest(TOPIC_ARN, message);

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
