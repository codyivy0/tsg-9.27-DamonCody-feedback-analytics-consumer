package com.example.feedback_analytics_consumer.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeedbackEventListener {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackEventListener.class);

    @KafkaListener(topics = "feedback-submitted", groupId = "feedback-analytics-group")
    public void listen(Map<String, Object> message) {
        try {
            // Extract fields from the message
            String id = (String) message.get("id");
            String memberId = (String) message.get("memberId");
            String providerName = (String) message.get("providerName");
            Integer rating = (Integer) message.get("rating");
            String comment = (String) message.get("comment");
            String submittedAt = (String) message.get("submittedAt");
            // Log structured analytics data
            logger.info("Received feedback (id={}) rating={} provider='{}' member='{}' comment='{}' submittedAt='{}'",
                    id, rating, providerName, memberId, comment, submittedAt);

        } catch (Exception e) {
            logger.error("Error processing feedback event: {}", e.getMessage(), e);
        }
    }
}