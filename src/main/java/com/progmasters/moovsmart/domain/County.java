package com.progmasters.moovsmart.domain;

public enum County {
    BACS_KISKUN("Bács-Kiskun"), BARANYA("Baranya"), BEKES("Békés"),
    BORSOD_ABAUJ_ZEMPLEN("Borsod-Abaúj-Zemplén"), CSONGRAD("Csongrád"),
    FEJER("Fejér"), GYOR_MOSON_SOPRON("Győr-Moson-Sopron"),
    HAJDU_BIHAR("Hajdú-Bihar"), HEVES("Heves"), JASZ_NAGYKUN_SZOLNOK("Jász-Nagykun-Szolnok"),
    KOMAROM_ESZTERGOM("Komárom-Esztergom"),NOGRAD("Nógrád"), PEST("Pest"),
    SOMOGY("Somogy"), SZABOLCS_SZATMAR_BEREG("Szabolcs-Szatmár-Bereg"),
    TOLNA("Tolna"), VAS("Vas"), VESZPREM("Veszprém"), ZALA("Zala");

    private String displayName;

    County(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
