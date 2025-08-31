package com.example.dreamshops.dto;

import com.example.dreamshops.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;
    private String imageName;
    private String downloadURL;

    public ImageDTO(Image i) {
        this.id = i.getId();
        this.imageName = i.getFilename();
        this.downloadURL = i.getDownloadUrl();
    }
}
