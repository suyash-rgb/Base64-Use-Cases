package com.suyash.Base64UseCases.services.usecase5;

import com.suyash.Base64UseCases.models.usecase5.StoredAssets;
import com.suyash.Base64UseCases.repository.usecase5.StoredAssetsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AssetStorageService {

    private final StoredAssetsRepository repository;

    public AssetStorageService(StoredAssetsRepository repository) {
        this.repository = repository;
    }

    public StoredAssets store(MultipartFile file, String overrideName) throws IOException {

        //check if file empty
        if(file.isEmpty()){
            throw new IllegalArgumentException("File is empty");
        }

        //check if file is too large
        if(file.getSize()>5_000_000){
            throw new IllegalArgumentException("File too large(>5MBs)");
        }

        //check if mime type is allowed
        String mime = file.getContentType();
        if(!isAllowedMime(mime)){
            throw new IllegalArgumentException("Unsupported type: "+mime);
        }

        String fileName = (overrideName!=null && !overrideName.isBlank())
                ? overrideName
                : FilenameUtils.getName(file.getOriginalFilename());

        String base64 = Base64.getEncoder().encodeToString(file.getBytes());

        StoredAssets asset = new StoredAssets();
        asset.setFilename(fileName);
        asset.setMimeType(mime);
        asset.setBase64Data(base64);
        asset.setCreatedAt(Instant.now());

        return repository.save(asset);
    }

    public List<StoredAssets> listAllAssets() {
        return repository.findAll();
    }

    public Optional<StoredAssets> get(String id) {
       return repository.findById(id);
    }

    public String toDataUri(StoredAssets asset){
        return "data: "+ asset.getMimeType()+ ";base64," +asset.getBase64Data();
    }

    public byte[] decode(StoredAssets asset){
        return Base64.getDecoder().decode(asset.getBase64Data());
    }

    private boolean isAllowedMime(String mime) {
        return mime!=null && (mime.equals("application/pdf")
                              || mime.equals("image/png")
                              || mime.equals("image/jpeg")
                              || mime.equals("image/svg+xml"));
    }
}
