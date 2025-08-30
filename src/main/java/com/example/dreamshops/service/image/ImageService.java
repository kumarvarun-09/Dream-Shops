package com.example.dreamshops.service.image;

import com.example.dreamshops.dto.ImageDTO;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Image;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.image.ImageRepository;
import com.example.dreamshops.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final String IMAGE_DOWNLOAD_PATH = "/api/v1/images/image/download/";

    @Override
    public Image getImageById(Long id) {
        return imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public List<ImageDTO> saveImage(Long productId, List<MultipartFile> files) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for productId : " + productId));

        List<ImageDTO> savedImageDTOs = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                image.setDownloadUrl(IMAGE_DOWNLOAD_PATH);
                // Saving image into db
                Image savedImage = imageRepository.save(image);
                // Updating downloadURL
                savedImage.setDownloadUrl(IMAGE_DOWNLOAD_PATH + savedImage.getId());
                // Updating image
                imageRepository.save(savedImage);
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilename(), image.getDownloadUrl());
                savedImageDTOs.add(imageDTO);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDTOs;
    }

    @Override
    public Image updateImage(Long imageId, MultipartFile file) {
        Image existingImage = getImageById(imageId);
        // If image is present, we'll get the image
        // Otherwise exception will be thrown
        try {
            existingImage.setFilename(file.getOriginalFilename());
            existingImage.setFileType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            return imageRepository.save(existingImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId)
                .ifPresentOrElse(imageRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Image not found");
                        }
                );
    }

    @Override
    public List<Image> getAllImagesForProduct(Long productId) {
        return imageRepository.findByProduct(productId);
    }
}
