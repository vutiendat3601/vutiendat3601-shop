package vn.io.vutiendat3601.shop.v2.aws;

import static java.lang.System.getenv;

public interface AwsConstant {
  String AWS_REGION = getenv("AWS_REGION");
  String AWS_ACCESS_KEY_ID = getenv("AWS_ACCESS_KEY_ID");
  String AWS_ACCESS_KEY_SECRET = getenv("AWS_ACCESS_KEY_SECRET");
}
