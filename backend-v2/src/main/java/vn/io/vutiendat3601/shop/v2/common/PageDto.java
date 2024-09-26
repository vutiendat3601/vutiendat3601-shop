package vn.io.vutiendat3601.shop.v2.common;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public record PageDto<T>(List<T> items, int page, int size, int totalPages, long totalItems) {
  public <R> PageDto<R> map(Function<T, R> mapper) {
    final List<R> items = this.items.stream().map(mapper).toList();
    return new PageDto<>(items, page, size, totalPages, totalItems);
  }

  public static <R> PageDto<R> of(Page<R> page) {
    return new PageDto<>(
        page.getContent(),
        page.getNumber() + 1,
        page.getSize(),
        page.getTotalPages(),
        page.getTotalElements());
  }
}
