package vn.io.vutiendat3601.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.io.vutiendat3601.shop.service.BannerService;

@RestController
@RequestMapping("v1/banners")
public class BannerController {
  private BannerService bannerService;

  public BannerController(BannerService bannerService) {
    this.bannerService = bannerService;
  }

  @GetMapping
  public ResponseEntity<?> selectBanners() {
    return ResponseEntity.ok().body(bannerService.selectBanners());
  }
}
