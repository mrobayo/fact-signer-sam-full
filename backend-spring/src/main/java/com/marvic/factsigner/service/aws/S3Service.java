package com.marvic.factsigner.service.aws;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import com.marvic.factsigner.exception.APIException;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFicheros;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

@Component
public class S3Service {

    private S3Client s3Client;

    public S3Service(Region awsRegion) {
        init(awsRegion);
    }

    public void init(Region awsRegion) {
        this.s3Client = S3Client.builder()
                .region(awsRegion)
                //.credentialsProvider(Credentials) //(ProfileCredentialsProvider.create("default"))
                .build();
    }

    public String saveObject(String bucketName, String keyName, String stringObject) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                //.acl("public-read")
                .contentType("application/xml")
                .build();
        //ObjectMetadata objectMetadata = new ObjectMetadata();
        //objectMetadata.setContentType("test/csv");

        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromString(stringObject));

        if (response.sdkHttpResponse().isSuccessful()) {
            return getURL(bucketName, keyName);
        }
        throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR,
                response.sdkHttpResponse().statusText().orElse("Fail uploading XML"));
    }

    public String saveObject(String bucketName, String keyName, Document document) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            UtilidadFicheros.writeXML(document, out);

            RequestBody body = RequestBody.fromBytes(out.toByteArray());
            return saveObject(bucketName, keyName, body);
        } catch(Exception exception) {
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    public String saveObject(String bucketName, String keyName, RequestBody body) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                //.acl("public-read")
                .contentType("application/xml")
                .build();
        //ObjectMetadata objectMetadata = new ObjectMetadata();
        //objectMetadata.setContentType("test/csv");

        PutObjectResponse response = s3Client.putObject(request, body);

        if (response.sdkHttpResponse().isSuccessful()) {
            return getURL(bucketName, keyName);
        }
        throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR,
                response.sdkHttpResponse().statusText().orElse("Fail uploading XML"));
    }

    private String getURL(String bucketName, String keyName) {
        try {
            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            URL url = s3Client.utilities().getUrl(request);
            return url.toString();

        } catch (S3Exception e) {
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.awsErrorDetails().errorMessage());
        }
    }




}
