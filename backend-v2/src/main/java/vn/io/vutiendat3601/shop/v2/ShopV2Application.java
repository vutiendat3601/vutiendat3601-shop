package vn.io.vutiendat3601.shop.v2;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import vn.io.vutiendat3601.shop.v2.address.Address;
import vn.io.vutiendat3601.shop.v2.address.AddressRepository;
import vn.io.vutiendat3601.shop.v2.address.WardRepository;
import vn.io.vutiendat3601.shop.v2.coupon.Coupon;
import vn.io.vutiendat3601.shop.v2.coupon.CouponObjectType;
import vn.io.vutiendat3601.shop.v2.coupon.CouponRepository;
import vn.io.vutiendat3601.shop.v2.coupon.CouponType;
import vn.io.vutiendat3601.shop.v2.customer.Customer;
import vn.io.vutiendat3601.shop.v2.customer.CustomerRepository;
import vn.io.vutiendat3601.shop.v2.product.CategoryRepository;
import vn.io.vutiendat3601.shop.v2.user.User;
import vn.io.vutiendat3601.shop.v2.user.UserRepository;

@RequiredArgsConstructor
@EnableAsync
@SpringBootApplication
public class ShopV2Application {
  public static void main(String[] args) {
    SpringApplication.run(ShopV2Application.class, args);
  }

  private final UserRepository userRepo;
  private final CustomerRepository customerRepo;
  private final AddressRepository addrRepo;
  private final WardRepository wardRepo;
  private final CouponRepository couponRepo;
  private final CategoryRepository categoryRepo;

  @EventListener(ApplicationReadyEvent.class)
  public void mockData() {
    final User USER =
        userRepo
            .findByUsername("vutiendat3601")
            .orElseGet(
                () ->
                    userRepo.save(
                        User.builder()
                            .username("vutiendat3601")
                            .email("vutien.dat.3601@gmail.com")
                            .hashedPassword(
                                "$2b$10$Gf1a5iLyJ40tdevJKMlxw.SjCFODArUG/2dmbt5PwY6kH3l0ppWfK") // $2024Vutiendat3601
                            .authorities(List.of())
                            .isVerified(true)
                            .phone("08988831235")
                            .build()));
    final String customerCode = "e26714e2-c695-4574-a3d7-4762c8b33faa";
    final Customer CUSTOMER =
        customerRepo
            .findByCode(customerCode)
            .orElseGet(
                () ->
                    customerRepo.save(
                        Customer.builder()
                            .name("Dat Vu")
                            .code(customerCode)
                            .user(USER)
                            .phones(List.of())
                            .build()));
    final String addrCode = "903089e9-6b54-45ce-b64c-3a59a59cdd1d";
    addrRepo
        .findByCodeAndCustomerCode(addrCode, customerCode)
        .orElseGet(
            () ->
                addrRepo.save(
                    Address.builder()
                        .customer(CUSTOMER)
                        .code(addrCode)
                        .ward(wardRepo.findById(26740L).get())
                        .street("1 Nguyễn Huệ")
                        .build()));

    final String couponCode = "GiangSinh2024";
    couponRepo
        .findByCode(couponCode)
        .orElseGet(
            () ->
                couponRepo.save(
                    Coupon.builder()
                        .code(couponCode)
                        .name("Giảm giá 50K cho sản phẩm sách dịp giáng sinh")
                        .maxAmount(new BigDecimal(50_000))
                        .type(CouponType.AMOUNT)
                        .expiredAt(ZonedDateTime.now(ZoneOffset.UTC).plusDays(30))
                        .objectType(CouponObjectType.CATEGORY)
                        .category(categoryRepo.findById(2L).get())
                        .quantity(200)
                        .build()));
  }

  @EventListener(ApplicationReadyEvent.class)
  public void mockAdminData() {
    final User USER =
        userRepo
            .findByUsername("admin")
            .orElseGet(
                () ->
                    userRepo.save(
                        User.builder()
                            .username("admin")
                            .email("admin@gmail.com")
                            .hashedPassword(
                                "$2b$10$Gf1a5iLyJ40tdevJKMlxw.SjCFODArUG/2dmbt5PwY6kH3l0ppWfK") // $2024Vutiendat3601
                            .authorities(List.of("ADMIN"))
                            .isVerified(true)
                            .phone("08923118712")
                            .build()));
    final String customerCode = "4a3702a0-a5a9-43cd-a2cd-31d2e7c6a5b0";
    customerRepo
        .findByCode(customerCode)
        .orElseGet(
            () ->
                customerRepo.save(
                    Customer.builder()
                        .name("ADMIN")
                        .code(customerCode)
                        .user(USER)
                        .phones(List.of())
                        .build()));
  }
}
