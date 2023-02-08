package com.codesquad.autobid.image.repository;

import org.springframework.data.repository.CrudRepository;

import com.codesquad.autobid.image.domain.Image;


public interface ImageRepository extends CrudRepository<Image, Long> {

}
