package com.czofac.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.czofac.models.SdnEntryModel;



public interface SdnEntryRepository extends MongoRepository<SdnEntryModel, Integer>  {

}
