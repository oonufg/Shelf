package ru.pablo.Domain.Entities;

import ru.pablo.Domain.MediaService.Entities.MediaFile;
import ru.pablo.Domain.MediaService.Persistence.Repositories.MediaFileRepository;

public class Book {
    private Long id;
    private String title;
    private String description;
    private long payloadId;
    private static MediaFileRepository mediaFileRepository;

    static {
        mediaFileRepository = new MediaFileRepository();
    }

    public Book(long id, String title, String description, long payloadId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.payloadId = payloadId;
    }

    public Book(String title, String description, MediaFile mediaFile){
        this.title = title;
        this.description = description;
        this.payloadId = mediaFileRepository.addMediaFile(mediaFile);
    }

    public long getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public MediaFile getPayload(){
        return mediaFileRepository.getMediaFile(payloadId);
    }

    public long getPayloadId(){
        return payloadId;
    }
}