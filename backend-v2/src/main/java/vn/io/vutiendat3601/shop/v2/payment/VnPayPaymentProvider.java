package vn.io.vutiendat3601.shop.v2.payment;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import vn.io.vutiendat3601.shop.v2.util.StringUtils;

@Service
public class VnPayPaymentProvider implements PaymentProvider {

  @Value("${app.payment.vnpay.vnpVersion}")
  private String vnpVersion;

  @Value("${app.payment.vnpay.vnpTmnCode}")
  private String vnpTmnCode;

  @Value("${app.payment.vnpay.vnpCurrCode}")
  private String vnpCurrCode;

  @Value("${app.payment.vnpay.vnpLocale}")
  private String vnpLocale;

  @Value("${app.payment.vnpay.vnpOrderType}")
  private String vnpOrderType;

  @Value("${app.payment.vnpay.vnpBankCode}")
  private String vnpBankCode;

  @Value("${app.payment.vnpay.url}")
  private String url;

  @Value("${app.payment.vnpay.secretKey}")
  private String secretKey;

  private final Map<String, String> vnpDefaultParams = new HashMap<>();

  @PostConstruct
  private void initialize() {
    vnpDefaultParams.put("vnp_Version", vnpVersion);
    vnpDefaultParams.put("vnp_TmnCode", vnpTmnCode);
    vnpDefaultParams.put("vnp_CurrCode", vnpCurrCode);
    vnpDefaultParams.put("vnp_Locale", vnpLocale);
    vnpDefaultParams.put("vnp_OrderType", vnpOrderType);
    // vnpDefaultParams.put("vnp_BankCode", vnpBankCode);
  }

  private String generateVnpSecureHash(String data) {
    return StringUtils.hmacSha512(secretKey, data);
  }

  @Override
  @NonNull
  public String generatePaymentUrl(
      @NonNull BigDecimal amount, @NonNull String paymentRef, @NonNull Map<String, String> params) {
    final Map<String, String> vnpParams = new TreeMap<>(vnpDefaultParams);
    vnpParams.putAll(params);
    vnpParams.put("vnp_TxnRef", paymentRef);
    vnpParams.put(
        "vnp_Amount", String.valueOf(amount.multiply(new BigDecimal(100)).toBigInteger()));

    final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

    vnpParams.forEach(
        (name, value) -> {
          name = URLEncoder.encode(name, StandardCharsets.US_ASCII);
          value = URLEncoder.encode(value, StandardCharsets.US_ASCII);
          builder.queryParam(name, value);
        });
    builder.queryParam("vnp_SecureHash", generateVnpSecureHash(builder.build().getQuery()));
    return builder.build().toUriString();
  }

  /* @Override
  public String generate(@NonNull Order order, @NonNull Map<String, String> params) {
    final ZonedDateTime now = ZonedDateTime.now(DEFAULT_ZONE_ID);

    final Map<String, String> vnpParams = new TreeMap<>(vnpDefaultParams);
    vnpParams.putAll(params);
    vnpParams.put("vnp_TxnRef", StringUtils.makeRandomDigits(8));
    vnpParams.put(
        "vnp_Amount",
        String.valueOf(order.getFinalAmount().multiply(new BigDecimal(100)).toBigInteger()));
    vnpParams.put(
        "vnp_OrderInfo",
        "Thanh toan don hang tai shopsinhvien.io.vn: " + order.getTrackingNumber());
    vnpParams.put("vnp_CreateDate", now.format(DATE_TIME_FORMATTER));
    vnpParams.put("vnp_ExpireDate", now.plusDays(durationDay).format(DATE_TIME_FORMATTER));

    final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

    vnpParams.forEach(
        (name, value) -> {
          name = URLEncoder.encode(name, StandardCharsets.US_ASCII);
          value = URLEncoder.encode(value, StandardCharsets.US_ASCII);
          builder.queryParam(name, value);
        });
    builder.queryParam("vnp_SecureHash", generateVnpSecureHash(builder.build().getQuery()));
    return builder.build().toUriString();
  } */
}
