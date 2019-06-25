package io.ankara.ui.vaadin.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.vaadin.easyuploads.ImagePreviewField;
import org.vaadin.spring.annotation.PrototypeScope;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;


@SpringComponent
@PrototypeScope
public class CloudinaryPictureUploader extends VerticalLayout {
    private ImagePreviewField uploader;

    @Value("${com.cloudinary.cloud_name}")
    private String mCloudName;

    @Value("${com.cloudinary.api_key}")
    private String mApiKey;

    @Value("${com.cloudinary.api_secret}")
    private String mApiSecret;

    private String lastUrl;

    public CloudinaryPictureUploader() {
        uploader = new ImagePreviewField();
        addComponent(uploader);
        setExpandRatio(uploader,1);

        uploader.addValueChangeListener(event -> {
            if (event.getValue() == null) return;

            try{
                //TODO transfer this logic to a service
                File file = Files.createTempFile("temp", uploader.getLastFileName()).toFile();

                ImageIO.write(
                        ImageIO.read(
                                new ByteArrayInputStream(uploader.getValue())),
                        "png",
                        file
                );

                Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
                Map response = c.uploader().upload(file, ObjectUtils.emptyMap());
                JSONObject json = new JSONObject(response);
                lastUrl = json.getString("url");
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }

    public String getLastUrl() {
        return lastUrl;
    }
}
