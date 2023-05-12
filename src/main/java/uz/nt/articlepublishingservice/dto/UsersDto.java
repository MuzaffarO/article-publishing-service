package uz.nt.articlepublishingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.nt.articlepublishingservice.security.UserRoles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authorities",
        "role", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"},allowSetters = true)
public class UsersDto implements UserDetails {
    private Integer id;
    @NotNull(message = "Username cannot be null!")
    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @NotNull(message = "Password cannot be null!")
    @NotBlank(message = "Password cannot be empty!")
    private String password;
    @NotNull(message = "Password cannot be null!")
    @NotBlank(message = "Password cannot be empty!")
    @Email(message = "Email must be valid!")
    private String email;
    private String bio;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRoles.valueOf(role)
                .getAuthorities().stream()
                .map(a->new SimpleGrantedAuthority(a))
                .toList();
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
