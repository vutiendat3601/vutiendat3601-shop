package vn.io.vutiendat3601.shop.v2.notification;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.pinpoint.PinpointClient;
import software.amazon.awssdk.services.pinpoint.model.AddressConfiguration;
import software.amazon.awssdk.services.pinpoint.model.ChannelType;
import software.amazon.awssdk.services.pinpoint.model.MessageRequest;
import software.amazon.awssdk.services.pinpoint.model.MessageResponse;
import software.amazon.awssdk.services.pinpoint.model.SMSMessage;
import software.amazon.awssdk.services.pinpoint.model.SendMessagesRequest;
import software.amazon.awssdk.services.pinpoint.model.SendMessagesResponse;

@RequiredArgsConstructor
@Service
@Async
public class SmsAsyncService {
  // private final PinpointClient pinpointClient;

  // public void sendSms(String toPhone, String senderId, String message) {
  //    // Define the SMS message request
  //       Map<String, AddressConfiguration> addressMap = new HashMap<>();
  //       addressMap.put(toPhone, AddressConfiguration.builder()
  //               .channelType(ChannelType.SMS)
  //               .build());

  //       // SMS Message details
  //       SMSMessage smsMessage = SMSMessage.builder()
  //               .body(message)
  //               .messageType("TRANSACTIONAL") // Use TRANSACTIONAL for OTP messages
  //               .senderId(senderId) // Optional sender ID
  //               .build();

  //       // Create the message request
  //       MessageRequest messageRequest = MessageRequest.builder()
  //               .addresses(addressMap)
  //               .messageConfiguration(smsMessage)
  //               .build();

  //       // Create the SendMessages request
  //       SendMessagesRequest sendMessagesRequest = SendMessagesRequest.builder()
  //               .applicationId("YOUR_PINPOINT_APP_ID") // Your Pinpoint App ID
  //               .messageRequest(messageRequest)
  //               .build();

  //       // Send the message
  //       SendMessagesResponse result = pinpoint.sendMessages(sendMessagesRequest);

  //       // Print response details
  //       MessageResponse messageResponse = result.messageResponse();
  //       System.out.println("Message sent! " + messageResponse);
  // }
}
