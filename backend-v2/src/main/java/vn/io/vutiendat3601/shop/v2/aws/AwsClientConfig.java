package vn.io.vutiendat3601.shop.v2.aws;

import static vn.io.vutiendat3601.shop.v2.aws.AwsConstant.AWS_ACCESS_KEY_ID;
import static vn.io.vutiendat3601.shop.v2.aws.AwsConstant.AWS_ACCESS_KEY_SECRET;
import static vn.io.vutiendat3601.shop.v2.aws.AwsConstant.AWS_REGION;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.pinpoint.PinpointClient;

@Configuration
public class AwsClientConfig {
  private final AwsBasicCredentials credentials =
      AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_ACCESS_KEY_SECRET);
  private final Region region = Region.of(AWS_REGION);

  @Bean
  PinpointClient pinpointClient() {
    return PinpointClient.builder()
        .region(region)
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }
}
