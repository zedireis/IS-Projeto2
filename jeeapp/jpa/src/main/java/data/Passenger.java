package data;

import javax.persistence.*;
import java.util.List;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int dinheiro;

    @Column(unique=true)
    private String email;

    private String name, password, salt;

    @OneToMany(mappedBy = "passenger")
    private  List<Ticket> tickets;

    private boolean manager;

    public Passenger() {
        this.manager=false;
    }

    public Passenger(String name, String email, String password) {
        this.manager=false;

        this.name = name;
        this.email = email;

        String saltvalue = PassBasedEnc.getSaltvalue(30);
        /* generates an encrypted password. It can be stored in a database.*/
        String encryptedpassword = PassBasedEnc.generateSecurePassword(password, saltvalue);
        this.password = encryptedpassword;
        this.salt = saltvalue;

        this.dinheiro = 0;
    }

    public void addDinheiro(int valor){
        this.dinheiro += valor;
    }

    public int getDinheiro() {
        return dinheiro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String saltvalue = PassBasedEnc.getSaltvalue(30);
        /* generates an encrypted password. It can be stored in a database.*/
        String encryptedpassword = PassBasedEnc.generateSecurePassword(password, saltvalue);
        this.password = encryptedpassword;
        this.salt=saltvalue;
    }

    public String getSalt() {
        return salt;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }


}