package pasarela.zuul.seguridad;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import pasarela.zuul.beans.Usuario;
import pasarela.zuul.repositorio.FactoriaRepositorioUsuarios;
import pasarela.zuul.repositorio.RepositorioUsuarios;
import pasarela.zuul.repositorio.RepositorioUsuariosMongoDB;
import repositorio.EntidadNoEncontrada;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		// A partir del usuario identificado con OAuth2, intenta obtener la información del usuario en el sistema
		
		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		
		System.out.println(usuario); // depuración
		
		String login = usuario.getAttribute("login");
		
		// En GitHub es necesario que el usuario autorice que se publique su email.
		
		System.out.println(login);
		
		Map<String, String> datosUsuario;
		try {
			datosUsuario = fetchUserInfo(login);
		} catch (EntidadNoEncontrada e) {
			datosUsuario = null;
			e.printStackTrace();
		}
		
		// Si el usuario está registrado en el sistema, construye el token JWT con la información
		
		if (datosUsuario != null) {
			String jwt = JwtUtils.createJWT(authentication.getName(), datosUsuario);
			
			String responseBody = Utils.jwtResponse(jwt);

			// escribe la cabecera con la autorización
			response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + jwt);

			// para depuración y pruebas, lo muestra por la salida
			response.getOutputStream().write(responseBody.getBytes());

		} else {
			
			Utils.writeResponseJSON(response, HttpServletResponse.SC_UNAUTHORIZED, Utils.buildLoginErrorJSON(authentication.getName()));			
		}
	}

	
	private Map<String, String> fetchUserInfo(String userId) throws EntidadNoEncontrada {
		
		// TODO: Recuperar la información de un servicio del sistema
		
		Map<String, String> datosUsuario = new HashMap<String, String>();
		RepositorioUsuarios repo = FactoriaRepositorioUsuarios.getRepositorio();
		Usuario usuario = repo.getByUserId(userId);
		System.out.println("---------- Usuario: "+usuario.getUserId()+ " --- Rol: " +usuario.getRol().toString() + " --- Email: "+usuario.getEmail());
		datosUsuario.put("usuario", usuario.getUserId());
		datosUsuario.put("email", usuario.getEmail());
		datosUsuario.put("rol", usuario.getRol().toString());
		//datosUsuario.put("email", "marcos@um.es");
		//datosUsuario.put("rol", AvailableRoles.PROFESOR.toString());
		
		return datosUsuario;
	}

		
}