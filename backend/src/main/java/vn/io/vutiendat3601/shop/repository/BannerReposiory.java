package vn.io.vutiendat3601.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.io.vutiendat3601.shop.entity.Banner;

@Repository
public interface BannerReposiory extends JpaRepository<Banner, Long> {
}
