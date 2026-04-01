package connectroomies.security;

import connectroomies.model.entities.Usuario;
import connectroomies.model.enums.EstadoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {


    private final Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	 List<GrantedAuthority> authorities = usuario.getRoles().stream()
    	            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNombre()))
    	            .collect(Collectors.toList());

    	    //System.out.println("ROLES DEL USUARIO: " + authorities);

    	    return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return EstadoUsuario.ACTIVO.equals(usuario.getEstado());
    }
}
