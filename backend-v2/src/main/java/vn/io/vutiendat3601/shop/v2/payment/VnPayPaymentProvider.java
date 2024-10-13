package vn.io.vutiendat3601.shop.v2.payment;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
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
          name = URLEncoder.encode(name, StandardCharsets.UTF_8);
          value = URLEncoder.encode(value, StandardCharsets.UTF_8);
          builder.queryParam(name, value);
        });
    builder.queryParam("vnp_SecureHash", generateVnpSecureHash(builder.build().getQuery()));
    return builder.build().toUriString();
  }

  public Map<String, String> validateUrl(@NonNull String url) {
    // Decode url
    final String decUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);

    // Get Url with raw params
    final UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(decUrl).build();

    // Prepare params to hash
    StringBuilder sb = new StringBuilder();
    final MultiValueMap<String, String> multiValueParams = uriComponents.getQueryParams();
    final String secureHash = multiValueParams.getFirst("vnp_SecureHash");
    if (Objects.nonNull(secureHash)) {
      uriComponents.getQueryParams().keySet().stream()
          .filter(name -> !name.equals("vnp_SecureHash"))
          .forEach(
              name -> {
                String value =
                    URLEncoder.encode(multiValueParams.getFirst(name), StandardCharsets.UTF_8);
                name = URLEncoder.encode(name, StandardCharsets.UTF_8);
                sb.append("%s=%s&".formatted(name, value));
              });
      String hash = null;
      if (sb.length() > 0) {
        final String query = sb.substring(0, sb.length() - 1);
        hash = StringUtils.hmacSha512(secretKey, query);
      }
      if (secureHash.equals(hash)) {
        final Map<String, String> params = new HashMap<>();
        multiValueParams
            .keySet()
            .forEach(name -> params.put(name, multiValueParams.getFirst(name)));
        return params;
      }
    }
    throw new ConflictException("Invalid VNPay Url");
  }
}
