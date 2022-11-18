package co.com.poli.authservice.constants;

public enum TypeServicesEnum {
    ALIMENTACION("ALIMENTACION"),
    TRANSPORTE("TRANSPORTE"),
    LIMPIEZA("LIMPIEZA"),
    REPARACION("REPARACION"),
    EDUCATIVO("EDUCATIVO"),
    INFORMATICO("INFORMATICO");

    private final String namesServices;
    TypeServicesEnum(String name) {
        namesServices = name;
    }

    public String getNamesServices() {
        return namesServices;
    }
}
