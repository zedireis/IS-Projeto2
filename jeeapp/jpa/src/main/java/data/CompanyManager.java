package data;

import javax.persistence.*;
import java.util.List;

@Entity
public class CompanyManager extends Passenger{

    public CompanyManager() {
        super();
        this.setManager(true);
    }

    public CompanyManager(String name, String email, String password) {
        super(name, email, password);
        this.setManager(true);
    }

}