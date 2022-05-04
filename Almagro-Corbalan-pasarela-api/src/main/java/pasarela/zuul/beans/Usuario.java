package pasarela.zuul.beans;

import org.bson.types.ObjectId;

import pasarela.zuul.seguridad.AvailableRoles;

public class Usuario {

	private ObjectId id;
	private String userId;
	private String email;
	private AvailableRoles rol;
	
	public Usuario() {
		
	}
	
	public String getUserId() {
		return userId;
	}
	public String getEmail() {
		return email;
	}
	public AvailableRoles getRol() {
		return rol;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRol(AvailableRoles rol) {
		this.rol = rol;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	
}
