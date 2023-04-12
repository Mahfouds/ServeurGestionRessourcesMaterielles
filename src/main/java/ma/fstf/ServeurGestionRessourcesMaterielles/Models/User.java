package ma.fstf.ServeurGestionRessourcesMaterielles.Models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails{
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String login;
    @Column
    private String nom;
    @Column
    private String pass;
    @Column
    private String photo_profile;
    @Column
    private String prenom;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column
    private String telephone;
    /*@OneToMany
    @JoinColumn(name = "technicien_id")
    private List<Constat> constats;*/
   /* @OneToMany
    @JoinColumn(name = "emetteur_id")
    private List<Message> messages_envoyes;*/
  /*  @OneToMany
    @JoinColumn(name = "recepteur_id")
    private List<Message> messages_recus;*/

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
