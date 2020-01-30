package com.progmasters.moovsmart.util;

import com.smattme.MysqlExportService;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class DataBaseSaver {

    private Properties properties;
    public static final String DIR_OF_DB = "C:/Users/zolta/IdeaProjects/mainProject/angular-moovsmart/src/main/resources/db_saver";

    private MysqlExportService mysqlExportService;


    public DataBaseSaver() throws IOException {
        this.properties = new Properties();
        this.configMySqlSaver();
    }

    public void configMySqlSaver() {
        this.properties.setProperty(MysqlExportService.DB_NAME,"moovsmart");
        this.properties.setProperty(MysqlExportService.DB_USERNAME,"root");
        this.properties.setProperty(MysqlExportService.DB_PASSWORD,"test1234");

        this.properties.setProperty(MysqlExportService.TEMP_DIR, new File("Moovsmart_saved_db").getPath());
        this.properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");

        this.properties.setProperty(MysqlExportService.EMAIL_HOST, "smtp.gmail.com");
        this.properties.setProperty(MysqlExportService.EMAIL_PORT, "587");
        this.properties.setProperty(MysqlExportService.EMAIL_USERNAME, "moovsmartproject@gmail.com");
        this.properties.setProperty(MysqlExportService.EMAIL_PASSWORD, "Moov2002");
        this.properties.setProperty(MysqlExportService.EMAIL_FROM, "moovsmartproject@gmail.com");
        this.properties.setProperty(MysqlExportService.EMAIL_TO, "ugyintezes72@gmail.com");

        this.mysqlExportService = new MysqlExportService(this.properties);
    }

    public MysqlExportService getMysqlExportService(){
        return mysqlExportService;
    }
}
