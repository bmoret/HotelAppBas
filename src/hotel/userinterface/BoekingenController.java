package hotel.userinterface;

import hotel.model.Boeking;
import hotel.model.Hotel;
import hotel.model.KamerType;
import hotel.model.Klant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

public class BoekingenController {
    public ComboBox<KamerType> boekingKamertype;
    public DatePicker boekingVertrek;
    public DatePicker boekingAankomst;
    public TextField boekingAdres;
    public TextField boekingNaam;
    public Label boekingLabel;

    private Hotel hotel = Hotel.getHotel();
    
    public void initialize() {
        boekingNaam.clear();
        boekingAdres.clear();
        List<KamerType> kamerTypes = hotel.getKamerTypen();
        boekingKamertype.setItems(FXCollections.observableList(kamerTypes));
        boekingKamertype.setValue(kamerTypes.get(1));
        boekingAankomst.setValue(LocalDate.now());
        boekingVertrek.setValue(LocalDate.now().plusDays(7));
    }

    public void reset(ActionEvent actionEvent) {initialize();}

    public void boek(ActionEvent actionEvent) throws Exception {
        if (boekingNaam.getText().equals("") || boekingAdres.getText().equals("")) {
            boekingLabel.setText("Vul alle text velden!");
        } else if (boekingAankomst.getValue().isBefore(LocalDate.now())) {
            boekingLabel.setText("Datum moet na vandaag vallen");
        } else if (boekingVertrek.getValue().isBefore(boekingAankomst.getValue())) {
            boekingLabel.setText("Vertrekdatum moet na de aankomstdatum vallen");
        } else {
            try {
                hotel.voegBoekingToe(boekingAankomst.getValue(), boekingVertrek.getValue(), boekingNaam.getText(), boekingAdres.getText(), boekingKamertype.getValue());
                Button source = (Button)actionEvent.getSource();
                Stage stage = (Stage)source.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                boekingLabel.setText(e.getMessage());
            }
        }
    }

    public void handleKamertypeWijziging(ActionEvent actionEvent) {
    }

    public void handleAankomstWijziging(ActionEvent actionEvent) {
        boekingVertrek.setValue(boekingAankomst.getValue().plusDays(7));
    }
}
