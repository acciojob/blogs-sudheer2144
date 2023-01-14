package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository2;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        //create an image based on given parameters and add it to the imageList of given blog
        Image image=new Image(description,dimensions);
        List<Image> imageList=blog.getImageList();
        imageList.add(image);
        blog.setImageList(imageList);
        return image;
    }

    public void deleteImage(int id){
        if(imageRepository2.existsById(id)){
            imageRepository2.deleteById(id);
        }
    }

    public Image findById(int id) {
        if(imageRepository2.existsById(id)){
            return imageRepository2.findById(id).get();
        }
        return null;
    }

    public int countImagesInScreen(int id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        if(imageRepository2.findById(id)==null){
            return 0;
        }
        Image image=imageRepository2.findById(id).get();
        int imgMax=Integer.parseInt(image.getDimensions().split("X")[0]);
        int imgMin=Integer.parseInt(image.getDimensions().split("X")[1]);
        int scrMax=Integer.parseInt(screenDimensions.split("X")[0]);
        int scrMin=Integer.parseInt(screenDimensions.split("X")[1]);

        return (int)(scrMax*scrMin/imgMax*imgMin);
    }
}
