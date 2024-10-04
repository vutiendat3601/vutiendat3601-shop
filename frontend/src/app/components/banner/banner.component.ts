import { Component, OnInit, OnDestroy } from '@angular/core';
import { Banner } from '../../models/banner';
import { BannerService } from '../../services/banner.service';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css'],
})
export class BannerComponent implements OnInit, OnDestroy {
  banners: Banner[] = [];
  currentSlideIndex = 0;
  slideInterval: any;

  constructor(private bannerService: BannerService) {}

  ngOnInit(): void {
    this.bannerService.getBanners().subscribe((data) => {
      this.banners = data;
      // this.startAutoSlide();
    });
  }

  nextSlide() {
    if (this.banners.length > 0) {
      this.currentSlideIndex = (this.currentSlideIndex + 1) % this.banners.length;
    }
  }

  prevSlide() {
    if (this.banners.length > 0) {
      this.currentSlideIndex =
        (this.currentSlideIndex - 1 + this.banners.length) % this.banners.length;
    }
  }

  startAutoSlide() {
    if (this.banners.length > 0) {
      this.slideInterval = setInterval(() => {
        this.nextSlide();
      }, 3000);
    }
  }

  ngOnDestroy() {
    if (this.slideInterval) {
      clearInterval(this.slideInterval);
    }
  }
}
