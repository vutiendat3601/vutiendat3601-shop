package vn.io.vutiendat3601.shop.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import vn.io.vutiendat3601.shop.entity.Country;
import vn.io.vutiendat3601.shop.entity.Product;
import vn.io.vutiendat3601.shop.entity.ProductCategory;
import vn.io.vutiendat3601.shop.entity.State;

@RequiredArgsConstructor
@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {
  private final HttpMethod[] DENIED_METHODS = {PUT, POST, DELETE};
  private final Class<?>[] UNMODIFIED_DOMAIN_TYPES = {
    Product.class, ProductCategory.class, State.class, Country.class
  };
  private final EntityManager entityManager;

  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config, CorsRegistry cors) {
    for (Class<?> domainType : UNMODIFIED_DOMAIN_TYPES) {
      config
          .getExposureConfiguration()
          .forDomainType(domainType)
          .withItemExposure((metadata, httpMethods) -> httpMethods.disable(DENIED_METHODS))
          .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(DENIED_METHODS));
      exposeIds(config);
    }
  }

  private void exposeIds(RepositoryRestConfiguration config) {
    Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
    List<Class<?>> entityClasses = new ArrayList<>();
    for (EntityType<?> tempEntityType : entities) {
      entityClasses.add(tempEntityType.getJavaType());
    }
    Class<?>[] domainTypes = entityClasses.toArray(new Class[entityClasses.size()]);
    config.exposeIdsFor(domainTypes);
  }
}
