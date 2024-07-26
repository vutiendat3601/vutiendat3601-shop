package vn.io.vutiendat3601.shop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import vn.io.vutiendat3601.shop.entity.State;

@CrossOrigin
@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, Integer> {
  List<State> findByCountryCode(@Param("code") String code);
}
