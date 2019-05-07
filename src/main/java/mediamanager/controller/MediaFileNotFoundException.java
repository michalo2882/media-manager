package mediamanager.controller;

class MediaFileNotFoundException extends RuntimeException {
    MediaFileNotFoundException(Long id) {
        super(String.format("MediaFile %d not found", id));
    }
}
