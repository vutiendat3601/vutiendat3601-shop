package vn.io.vutiendat3601.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.io.vutiendat3601.shop.entity.Banner;
import vn.io.vutiendat3601.shop.repository.BannerReposiory;

@Service
public class BannerService {
  private BannerReposiory bannerReposiory;
  
  public BannerService(BannerReposiory bannerReposiory) {
    this.bannerReposiory = bannerReposiory;
  }

  public List<Banner> selectBanners() {
    return bannerReposiory.findAll();
  }
}
