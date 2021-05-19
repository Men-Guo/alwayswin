package com.example.alwayswin.utils.enumUtil;

public enum ImageType {
    IMG_TYPE_PNG("PNG"),
    IMG_TYPE_JPG("JPG"),
    IMG_TYPE_JPEG("JPEG"),
    IMG_TYPE_GIF("GIF");

    private String type;
    
    ImageType(String type) {
        this.type = type;
    }

    public static boolean contains(String target){
        for(ImageType imageType : ImageType.values()){
            if(imageType.getType().equals(target.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
