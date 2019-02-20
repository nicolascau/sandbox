package fr.elasticsearch.data;

public enum EMetaData {
    TITRE("titre"),
    CONTENU("description"),
    AUTEUR("auteur"),
    MOT_CLE("motCle");

    private String name;

    EMetaData(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
