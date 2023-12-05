package com.mitocode.mitosales.repo;

import com.mitocode.mitosales.model.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ICategoryRepo extends IGenericRepo<Category, Integer> {
    //DerivedQueries
    //SELECT + FROM Category c WHERE c.name = '';
    List<Category> findByName(String name);
    List<Category> findByNameLikeIgnoreCase(String name);
    //%XYZ% -> findByNameContains
    //%XYZ -> findByNameStartsWith
    //XYZ% -> findByNameEndsWith
    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category> findByEnabledTrue();
    List<Category> findByEnabledFalse();

    //JPQL Java Persistence Query Language
    @Query("FROM Category c WHERE c.name = :name AND c.description LIKE %:description%")
    List<Category> getNameAndDescription(@Param("name") String name,@Param("description") String description);

    @Query("SELECT new com.mitocode.mitosales.model.Category(c.name, c.enabled) FROM Category c WHERE c.name = :name AND c.description LIKE %:description%")
    List<Category> getNameAndDescription2(@Param("name") String name,@Param("description") String description);

    //SQL: NativeQuery
    @Query(value = "SELECT * FROM category c WHERE c.name = :name", nativeQuery = true)
    List<Category> getNameSQL(@Param("name") String name);
    @Modifying
    @Query(value = "UPDATE category SET name = :name", nativeQuery = true)
    Integer updateNames(@Param("name") String name);

}
