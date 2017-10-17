package com.czofac.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.czofac.models.TokenModel;

public interface TokenRepository extends MongoRepository<TokenModel, String> {

}
