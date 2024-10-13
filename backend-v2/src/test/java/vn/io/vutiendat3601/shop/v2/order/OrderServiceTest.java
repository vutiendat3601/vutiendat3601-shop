// package vn.io.vutiendat3601.shop.v2.order;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.math.BigDecimal;
// import java.util.List;
// import java.util.Optional;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import vn.io.vutiendat3601.shop.v2.address.Address;
// import vn.io.vutiendat3601.shop.v2.address.AddressDao;
// import vn.io.vutiendat3601.shop.v2.address.District;
// import vn.io.vutiendat3601.shop.v2.address.Province;
// import vn.io.vutiendat3601.shop.v2.address.Ward;
// import vn.io.vutiendat3601.shop.v2.auth.AuthContext;
// import vn.io.vutiendat3601.shop.v2.auth.UserAuthentication;
// import vn.io.vutiendat3601.shop.v2.coupon.Coupon;
// import vn.io.vutiendat3601.shop.v2.coupon.CouponObjectType;
// import vn.io.vutiendat3601.shop.v2.coupon.CouponType;
// import vn.io.vutiendat3601.shop.v2.customer.Customer;
// import vn.io.vutiendat3601.shop.v2.customer.CustomerDao;
// import vn.io.vutiendat3601.shop.v2.fee.ProductCouponApplier;
// import vn.io.vutiendat3601.shop.v2.fee.ShippingFeeCalculator;
// import vn.io.vutiendat3601.shop.v2.fee.VatCaclator;
// import vn.io.vutiendat3601.shop.v2.product.Category;
// import vn.io.vutiendat3601.shop.v2.product.Product;
// import vn.io.vutiendat3601.shop.v2.product.ProductDao;
// import vn.io.vutiendat3601.shop.v2.user.User;

// @ExtendWith(MockitoExtension.class)
// public class OrderServiceTest {
//   @Mock private OrderService theTest;

//   @Mock private AuthContext authContext;

//   @Mock private AddressDao addrDao;

//   @Mock private OrderDao orderDao;

//   @Mock private CustomerDao customerDao;

//   @Mock private ProductDao productDao;

//   @Mock private ProductCouponApplier productCouponApplier;

//   @Mock private ShippingFeeCalculator shippingFeeCalculator;

//   @Mock private VatCaclator vatCaclator;

//   private final OrderDtoMapper orderDtoMapper = new OrderDtoMapper();

//   private static final User USER =
//       User.builder()
//           .username("vutiendat3601")
//           .hashedPassword(
//               "$2a$12$TWnHv5oKlQ/eBCs70mBOduygxYZqle/15HQCMYta95at34YdD1bIW") // mypassword1234$12Dn
//           .authorities(List.of())
//           .build();
//   private static final Customer CUSTOMER =
//       Customer.builder()
//           .id(1L)
//           .code("b1b2c525-c9f5-44f7-b442-d5e68fb0a1ed")
//           .name("Dat Vu")
//           .user(USER)
//           .build();
//   private static final Province PROVINCE = Province.builder().id(1L).name("Tỉnh Sơn Tây").build();

//   private static final District DISTRICT =
//       District.builder().id(1L).name("Huyện Trà Bằng").province(PROVINCE).build();

//   private static final Ward WARD =
//       Ward.builder().id(1L).name("Trà Xuân").district(DISTRICT).build();

//   private static final Address ADDRESS =
//       Address.builder()
//           .id(1L)
//           .code("954756a8-ba85-4e73-bfb0-cb872493e8c3")
//           .street("1 Nguyễn Huệ")
//           .customer(CUSTOMER)
//           .ward(WARD)
//           .build();

//   // private final AddressDetail ADDRESS_DETAIL =
//   //     AddressDetail.builder()
//   //         .id(ADDRESS.getId())
//   //         .code(ADDRESS.getCode())
//   //         .provinceId(PROVINCE.getId())
//   //         .provinceName(PROVINCE.getName())
//   //         .districtId(DISTRICT.getId())
//   //         .districtName(DISTRICT.getName())
//   //         .street(ADDRESS.getStreet())
//   //         .build();

//   private final UserAuthentication USER_AUTH =
//       new UserAuthentication(1L, "vutiendat3601", "Dat Vu", true, CUSTOMER.getCode());

//   private final Category CATEGORY =
//       Category.builder().id(1L).code("DIEN_TU").name("Đồ điện tử").slug("dien-tu").build();

//   private final Coupon COUPON_CATEGORY =
//       Coupon.builder()
//           .code("GIAM50%")
//           .name("Giảm 50% cho mặt hàng Điện tử")
//           .category(CATEGORY)
//           .maxAmount(new BigDecimal(200_000D))
//           .type(CouponType.AMOUNT)
//           .objectType(CouponObjectType.CATEGORY)
//           .build();

//   private final Product PRODUCT =
//       Product.builder()
//           .id(1L)
//           .productNo("20240812sfD4r5Snm")
//           .slug("tai-nghe-khong-day-airpod")
//           .unitListedPrice(new BigDecimal(7000000D))
//           .unitPrice(new BigDecimal(5000000))
//           .isActive(true)
//           .unitsInStock(10)
//           .category(CATEGORY)
//           .build();
//   private final Product PRODUCT1 =
//       Product.builder()
//           .id(1L)
//           .productNo("20220124vfD3r5Szy")
//           .slug("tai-nghe-khong-day-airpod")
//           .unitListedPrice(new BigDecimal(7000000D))
//           .unitPrice(new BigDecimal(5000000))
//           .isActive(true)
//           .unitsInStock(10)
//           .category(CATEGORY)
//           .build();

//   private final CreateOrderRequest CREATE_ORDER_REQ =
//       new CreateOrderRequest(
//           ADDRESS.getCode(),
//           "FREESHIP15K",
//           List.of(
//               new CreateOrderItemDto("20240812sfD4r5Snm", 2, COUPON_CATEGORY.getCode()),
//               new CreateOrderItemDto("20220124vfD3r5Szy", 1, null)));

//   @BeforeEach
//   void setUp() {
//     theTest =
//         new OrderService(
//             authContext,
//             addrDao,
//             orderDao,
//             customerDao,
//             productDao,
//             productCouponApplier,
//             shippingFeeCalculator,
//             vatCaclator,
//             orderDtoMapper);
//   }

//   @Test
//   void canCreateOrder() {
//     // Given
//     // final BigDecimal expectedTotalProductAmount = CREATE_ORDER_REQ.items().forEach(null);
//     when(authContext.getUser()).thenReturn(USER_AUTH);
//     when(customerDao.selectByCode(CUSTOMER.getCode())).thenReturn(Optional.of(CUSTOMER));
//     when(addrDao.selectByCodeAndCustomerCode(ADDRESS.getCode(), CUSTOMER.getCode()))
//         .thenReturn(Optional.of(ADDRESS));
//     when(productDao.selectByProductNoAndIsActiveTrue(PRODUCT.getProductNo()))
//         .thenReturn(Optional.of(PRODUCT));
//     when(productDao.selectByProductNoAndIsActiveTrue(PRODUCT1.getProductNo()))
//         .thenReturn(Optional.of(PRODUCT1));

//     // When
//     theTest.createOrder(CREATE_ORDER_REQ);

//     // Then
//     final ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
//     verify(orderDao).insert(orderCaptor.capture());
//     final Order actual = orderCaptor.getValue();
//     assertEquals(2, actual.getNumberOfProducts());
//     assertEquals(ADDRESS, actual.getShippingAddress());
//     assertEquals(OrderStatus.PENDING, actual.getStatus());
//   }
// }
