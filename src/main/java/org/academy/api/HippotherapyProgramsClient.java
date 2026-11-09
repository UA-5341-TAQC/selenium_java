package org.academy.api;

import org.academy.models.HippotherapyPrograms.HippotherapyProgramDto;
import org.academy.models.HippotherapyPrograms.PublishedHippotherapyProgram;
import java.util.Arrays;
import java.util.List;

public class HippotherapyProgramsClient extends BaseClient{
    private static final String ENDPOINT = "/HippotherapyPrograms";
    private static final String PUBLISHED_ENDPOINT= ENDPOINT+ "/published";
    private static final String PROGRAM_SLUG_ENDPOINT= ENDPOINT+ "/slug";

    public HippotherapyProgramsClient(String baseApiUrl) {
        super(baseApiUrl);
    }

    public List<PublishedHippotherapyProgram> getPublishedHippoProgram(){
        PublishedHippotherapyProgram[] publishedPrograms = prepareRequest()
                .get(PUBLISHED_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(PublishedHippotherapyProgram[].class);
        return Arrays.asList(publishedPrograms);
    }

    public HippotherapyProgramDto getHippoProgramSlug(String slug){
        return prepareRequest()
                .get(PROGRAM_SLUG_ENDPOINT+ "/"+slug)
                .then()
                .statusCode(200)
                .extract()
                .as(HippotherapyProgramDto.class);
    }

    public HippotherapyProgramDto getHippoProgramId(Long id){
        return prepareRequest()
                .pathParam("id", id)
                .get( ENDPOINT + "/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(HippotherapyProgramDto.class);
    }
}
