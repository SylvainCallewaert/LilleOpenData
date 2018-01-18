package sylvain.advancedlayoutapp.fr.lilleopendata.model;

public class ListeBibli {

    private String libelle;
    private String adresse;
    private String ville;
    private Double latitude;
    private Double longitude;

    public ListeBibli() {
    }

    public String getLibelle() {
        return libelle;
    }

    public ListeBibli setLibelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public String getAdresse() {
        return adresse;
    }

    public ListeBibli setAdresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public String getVille() {
        return ville;
    }

    public ListeBibli setVille(String ville) {
        this.ville = ville;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public ListeBibli setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public ListeBibli setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }
}
