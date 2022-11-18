package co.com.poli.authservice.constants;

public enum CommuneList {
    Popular("POPULAR"),
    Santa_Cruz("SANTA CRUZ"),
    Manrique("MANRIQUE"),
    Aranjuez("ARANJUEZ"),
    Castilla("CASTILLA"),
    Doce_de_Octubre("DOCE DE OCTUBRE"),
    Robledo("ROBLEDO"),
    Villa_Hermosa("VILLA HERMOSA"),
    Buenos_Aires("BUENOS AIRES"),
    La_Candelaria("LA CANDELARIA"),
    Laureles_Estadio("LAURERES-ESTADIO"),
    La_América("LA AMERICA"),
    San_Javier("SAN JAVIER"),
    El_Poblado("EL POBLADO"),
    Guayabal("GUAYABAL"),
    Belén("BELEN");

    private final String namesCommune;
    CommuneList(String name) {
        namesCommune = name;
    }

    public String getNamesCommune() {
        return namesCommune;
    }

}
