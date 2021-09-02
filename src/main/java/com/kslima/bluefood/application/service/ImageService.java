package com.kslima.bluefood.application.service;

import com.kslima.bluefood.util.IOUtils;
import com.kslima.bluefood.util.ImageDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${bluefood.files.logotipo}")
    private String logotiposDir;

    @Value("${bluefood.files.comida}")
    private String comidasDir;

    @Value("${bluefood.files.categoria}")
    private String categoriasDir;

    public void uploadLogotipo(MultipartFile multipartFile, String fileName) {
        try {
            IOUtils.copy(multipartFile.getInputStream(), fileName, ImageDirectory.LOGOTIPO.getDirectory());
        } catch (IOException e) {
            throw new ApplicationServiceException(e);
        }
    }

    public byte[] getBytes(String type, String imgName) {
        try {

            String dir;
            if ("logotipo".equals(type)) {
                dir = logotiposDir;

            } else if ("comida".equals(type)) {
                dir = comidasDir;

            } else if ("categoria".equals(type)) {
                dir = categoriasDir;

            } else {
                throw new IllegalAccessException(type + "não é um tipo válido");
            }

            System.out.println("achou " + type + " " + dir);
            return IOUtils.getBytes(Paths.get(dir, imgName));

        } catch (Exception e) {
            throw new ApplicationServiceException(e);
        }
    }

}
