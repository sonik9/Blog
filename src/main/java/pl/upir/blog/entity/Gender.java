package pl.upir.blog.entity;

/**
 * Created by Vitalii on 12.08.2015.
 */
public enum Gender {
    male("Мужской"),female("Женский");

    private final String rus;

    Gender(String rus) {
        this.rus=rus;
    }

    public String getValue(){
        return name();
    }


    private   String getRus() {
        return rus;
    }

    public String rus(){
        return getRus();
    }
}
