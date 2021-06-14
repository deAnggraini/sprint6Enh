package id.co.bca.pakar.be.oauth2.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class SecUserDto implements UserDetails {
	
	public SecUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public String getUsername() {
        return "javainuse-user";
    }

    @Override
    public String getPassword() {
        return "";
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("User"));
//    }

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
}
