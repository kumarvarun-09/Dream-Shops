package com.example.dreamshops.service.image;

import com.example.dreamshops.dto.ImageDTO;
import com.example.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);

    List<ImageDTO> saveImage(Long productId, List<MultipartFile> file);

    Image updteImage(Long imageId, MultipartFile file);

    void deleteImage(Long imageId);

    List<Image> getAllImagesForProduct(Long productId);
}
