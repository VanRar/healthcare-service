package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;

class MedicalServiceImplTest {

    @Test
    void checkBloodPressure() {

        HealthInfo healthInfo = Mockito.mock(HealthInfo.class);
        Mockito.when(healthInfo.getBloodPressure())
                .thenReturn(new BloodPressure(120, 80));

        PatientInfo patientInfo = Mockito.mock(PatientInfo.class);
        Mockito.when(patientInfo.getHealthInfo())
                .thenReturn(healthInfo);
        Mockito.when(patientInfo.getId())
                .thenReturn("123456789");

        PatientInfoFileRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123456789"))
                .thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("123456789", new BloodPressure(100, 80));
        Mockito.verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 123456789, need help", argumentCaptor.getValue());

    }

    @Test
    void checkBloodPressureNorm() {

        HealthInfo healthInfo = Mockito.mock(HealthInfo.class);
        Mockito.when(healthInfo.getBloodPressure())
                .thenReturn(new BloodPressure(120, 80));

        PatientInfo patientInfo = Mockito.mock(PatientInfo.class);
        Mockito.when(patientInfo.getHealthInfo())
                .thenReturn(healthInfo);
        Mockito.when(patientInfo.getId())
                .thenReturn("123456789");

        PatientInfoFileRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123456789"))
                .thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        //Проверим при нормальных показателях
        medicalService.checkBloodPressure("123456789", new BloodPressure(120, 80));
        Mockito.verify(alertService, Mockito.never()).send("Warning, patient with id: 123456789, need help");

    }

    @Test
    void checkTemperature() {

        HealthInfo healthInfo = Mockito.mock(HealthInfo.class);
        Mockito.when(healthInfo.getNormalTemperature())
                .thenReturn(new BigDecimal("36.6"));

        PatientInfo patientInfo = Mockito.mock(PatientInfo.class);
        Mockito.when(patientInfo.getHealthInfo())
                .thenReturn(healthInfo);
        Mockito.when(patientInfo.getId())
                .thenReturn("123456789");

        PatientInfoFileRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123456789"))
                .thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        medicalService.checkTemperature("123456789", new BigDecimal("35"));
        Mockito.verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 123456789, need help", argumentCaptor.getValue());

    }
    @Test
    void checkTemperatureNormal2() {

        HealthInfo healthInfo = Mockito.mock(HealthInfo.class);
        Mockito.when(healthInfo.getNormalTemperature())
                .thenReturn(new BigDecimal("36.6"));

        PatientInfo patientInfo = Mockito.mock(PatientInfo.class);
        Mockito.when(patientInfo.getHealthInfo())
                .thenReturn(healthInfo);
        Mockito.when(patientInfo.getId())
                .thenReturn("123456789");

        PatientInfoFileRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123456789"))
                .thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        //Хотя это можно было и здесь сделать: Проверим при нормальных показателях
        medicalService.checkTemperature("123456789", new BigDecimal("36.6"));
        Mockito.verify(alertService, Mockito.never()).send("Warning, patient with id: 123456789, need help");

    }

    @Test
    void checkTemperatureIncreased() {

        HealthInfo healthInfo = Mockito.mock(HealthInfo.class);
        Mockito.when(healthInfo.getNormalTemperature())
                .thenReturn(new BigDecimal("36.6"));

        PatientInfo patientInfo = Mockito.mock(PatientInfo.class);
        Mockito.when(patientInfo.getHealthInfo())
                .thenReturn(healthInfo);
        Mockito.when(patientInfo.getId())
                .thenReturn("123456789");

        PatientInfoFileRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123456789"))
                .thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        //При повышении, но сервис судя по всему это не парит
        medicalService.checkTemperature("123456789", new BigDecimal("40"));
        Mockito.verify(alertService, Mockito.never()).send("Warning, patient with id: 123456789, need help");
    }
}