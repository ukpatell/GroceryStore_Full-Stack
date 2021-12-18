package com.umang.storewebsite.config;

import com.umang.storewebsite.entity.Product;
import com.umang.storewebsite.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager1){
        entityManager = entityManager1;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Disable methods for certain auth
        HttpMethod[] unSupportedActions = {HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.POST};

        // Disable for Product : PUT, POST, DELETE (ONLY GET for VIEW)
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)));

        // Disable for Product Category : PUT, POST, DELETE (ONLY GET for VIEW)
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)));
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        // Internal Helper Method
        exposeIDs(config);
    }

    private void exposeIDs(RepositoryRestConfiguration config) {
        // Expose Ids

        // List of all Entity Classes from Manager
        Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();

        // Array of entity types
        List<Class> entityClasses = new ArrayList<>();

        // Get all Entity Types
        for(EntityType tempEntity: entityTypes){
            entityClasses.add(tempEntity.getJavaType());
        }

        // Expose Entity
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
