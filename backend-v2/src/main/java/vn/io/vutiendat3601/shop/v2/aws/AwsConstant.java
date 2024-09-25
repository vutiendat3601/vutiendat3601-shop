package vn.io.vutiendat3601.shop.v2.aws;

import static java.lang.System.getenv;

import java.util.Optional;

public interface AwsConstant {
  String AWS_REGION = Optional.ofNullable(getenv("AWS_REGION")).orElse("ap-southeast-1");
  String AWS_ACCESS_KEY_ID =
      Optional.ofNullable(getenv("AWS_ACCESS_KEY_ID")).orElse("AKIAT24DPKRLAGPVFX5E");
  String AWS_ACCESS_KEY_SECRET =
      Optional.ofNullable(getenv("AWS_ACCESS_KEY_SECRET"))
          .orElse("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");
}
