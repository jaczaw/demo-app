package pl.demo.demoapp.model;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Manager {

    private String nameManager;
    private String contactInfo;

}
