package com.suyash.Base64UseCases.repository.usecase5;

import com.suyash.Base64UseCases.models.usecase5.StoredAssets;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface StoredAssetsRepository extends MongoRepository<StoredAssets, String> {

    List<StoredAssets> findByMimeType(String mimeType);
}
