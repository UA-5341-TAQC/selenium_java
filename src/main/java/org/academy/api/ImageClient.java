package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.Image.ImageRequest;

public class ImageClient extends BaseClient {

    private static final String ENDPOINT = "/Image";

    public ImageClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public Response createImage(ImageRequest request) {
        return prepareRequest()
                .body(request)
                .post(ENDPOINT);
    }

    public Response getImageByName(String name) {
        return prepareRequest()
                .pathParam("name", name)
                .get(ENDPOINT + "/by-name/{name}");
    }

    public Response getImageById(Long id) {
        return prepareRequest()
                .pathParam("id", id)
                .get(ENDPOINT + "/{id}");
    }

    public Response updateImage(Long id, ImageRequest request) {
        return prepareRequest()
                .pathParam("id", id)
                .body(request)
                .put(ENDPOINT + "/{id}");
    }

    public Response deleteImage(Long id) {
        return prepareRequest()
                .pathParam("id", id)
                .delete(ENDPOINT + "/{id}");
    }
}