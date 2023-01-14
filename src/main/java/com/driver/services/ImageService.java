package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository2;
    @Autowired
    BlogRepository blogRepository;

    public Image createAndReturn(Blog blog, String description, String dimensions){
        //create an image based on given parameters and add it to the imageList of given blog
        Image image=new Image(description,dimensions);
        List<Image> imageList=blog.getImageList();
        if(imageList==null){
            imageList=new ArrayList<>();
        }
        imageList.add(image);
        blog.setImageList(imageList);
        imageRepository2.save(image);
        blogRepository.save(blog);
        return image;
    }

    public void deleteImage(int id){
        if(imageRepository2.findById(id).get()!=null){
            imageRepository2.deleteById(id);
        }
    }

    public Image findById(int id) {
        if(imageRepository2.findById(id).get()!=null){
            return imageRepository2.findById(id).get();
        }
        return null;
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        //In case the image is null, return 0
        if(imageRepository2.findById(image.getId())==null){
            return 0;
        }
        int imgMax=Integer.parseInt(image.getDimensions().split("X")[0]);
        int imgMin=Integer.parseInt(image.getDimensions().split("X")[1]);
        int scrMax=Integer.parseInt(screenDimensions.split("X")[0]);
        int scrMin=Integer.parseInt(screenDimensions.split("X")[1]);

        return (int)(scrMax*scrMin/imgMax*imgMin);
    }
}
