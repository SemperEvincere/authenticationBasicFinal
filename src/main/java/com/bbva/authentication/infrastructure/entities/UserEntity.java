package com.bbva.authentication.infrastructure.entities;

import com.bbva.authentication.domain.models.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Getter
@Setter
public class UserEntity implements UserDetails {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	private String salt;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<UserRole> roles;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@Builder.Default
	private LocalDateTime lastPasswordChange = LocalDateTime.now();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
		            .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
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
