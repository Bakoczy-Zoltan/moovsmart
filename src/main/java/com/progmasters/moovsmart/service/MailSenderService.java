package com.progmasters.moovsmart.service;


import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.repository.TokenStorageRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import com.progmasters.moovsmart.security.TokenStorage;
import com.progmasters.moovsmart.util.DataBaseSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MailSenderService {

    private final Logger logger = LoggerFactory.getLogger(MailSenderService.class);
    private JavaMailSender javaMailSender;
    private UserRepository userRepository;
    private TokenStorageRepository tokenStorageRepository;

    private DataBaseSaver dataBaseSaver;

    @Value("${spring.mail.url}")
    private String actualUrl;

    @Value("${spring.mail.username}")
    private String mailSenderAddress;

    @Autowired
    public MailSenderService(JavaMailSender javaMailSender,
                             UserRepository userRepository,
                             TokenStorageRepository tokenStorage) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.tokenStorageRepository = tokenStorage;
    }

    public void sendMailByTokenRegistration(String userName, String mail) {
        String newToken = UUID.randomUUID().toString();
        Optional<UserProperty> user = this.userRepository.findUserPropertiesByMail(mail);
        if (user.isPresent()) {
            UserProperty newUser = user.get();
            TokenStorage validToken = new TokenStorage(newToken, newUser, LocalDateTime.now());
            this.tokenStorageRepository.save(validToken);

            try {
                SimpleMailMessage message = MakeMailMessage(userName, mail, newToken);
                javaMailSender.send(message);

                String logInfo = "Mail send to: #" + mail;
                logger.info(logInfo);
            } catch (Exception e) {
                String logInfo = "Mail sending rejected to address of: " + mail;
                logger.debug(logInfo);
            }
        } else {
            String logInfo = "Mail sending rejected to address of: " + mail;
            logger.debug(logInfo);
        }
    }

    private SimpleMailMessage MakeMailMessage(String userName, String mail, String newToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link = this.actualUrl +"signin/" + newToken;

        message.setSubject("Regisztráció megerősítése");
        message.setFrom(mailSenderAddress);
        message.setTo(mail);
        message.setText("Kedves " + userName + "!\n" +
                "Kérlek kattints az alábbi linkre a regisztrációhoz!" + "\n" + link
        );
        return message;
    }

    public UserProperty getUserByToken(String token){
        UserProperty user = null;
        Optional<TokenStorage> tempToken = this.tokenStorageRepository.findByActiveToken(token);
        if(tempToken.isPresent()){
             user = tempToken.get().getTokenUser();
        }
        return user;
    }

    @Scheduled(fixedRate = 2000000)
    public void checkDate(){
        if(LocalTime.now().isAfter(LocalTime.parse("22:00")) &&
        LocalTime.now().isBefore(LocalTime.parse("22:45"))){
            try {
                saveDB();
            }catch (SQLException | IOException | ClassNotFoundException f) {
                this.logger.warn(f.getMessage());
                this.logger.warn("Database saving failed");
            }
            this.logger.info("Database saved");
        }

    }

    public void saveDB() throws SQLException, IOException, ClassNotFoundException {
        this.dataBaseSaver = new DataBaseSaver();
        dataBaseSaver.getMysqlExportService().export();

        File file = dataBaseSaver.getMysqlExportService().getGeneratedZipFile();
        makeCopyOfFile(file);
    }

    private void makeCopyOfFile (File file){
        try (FileInputStream input = new FileInputStream(file.getPath());
             FileOutputStream output = new FileOutputStream(
                     "C:\\Users\\zolta\\IdeaProjects\\mainProject\\angular-moovsmart\\src\\main\\resources\\db_saver\\" + file.getName())) {
            int byteSource;
            while ((byteSource = input.read()) != -1) {
                output.write(byteSource);
            }
            this.logger.info("copy of " + file.getName() + ": done");
        } catch (Exception e) {
            this.logger.warn("Invalid path or file or target");
        }
    }
}
