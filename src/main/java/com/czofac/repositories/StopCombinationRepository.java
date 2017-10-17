package com.czofac.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.czofac.models.StopCombinationModel;



public interface StopCombinationRepository extends MongoRepository<StopCombinationModel, Integer> {

}
