package pasarela.zuul.repositorio;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import pasarela.zuul.beans.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class RepositorioUsuariosMongoDB implements RepositorioUsuarios {

	
	private MongoCollection<Usuario> usuarios;

	
	public RepositorioUsuariosMongoDB() {
		
		// TODO: cadena de conexión
		
		String uriString = "mongodb+srv://malew:arso@cluster0.y5vc0.mongodb.net/arso?retryWrites=true&w=majority";
		ConnectionString connectionString = new ConnectionString("mongodb+srv://malew:arso@cluster0.y5vc0.mongodb.net/arso?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .build();

		MongoClient mongoClient = MongoClients.create(settings);
		// Pojo Codec Registry para que no de error "couldnt find codec for class"
		CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoDatabase database = mongoClient.getDatabase("arso").withCodecRegistry(pojoCodecRegistry);  
		//MongoDatabase database = mongoClient.getDatabase("arso");
		
		this.usuarios = database.getCollection("usuarios", Usuario.class);
		
	}

	/** Métodos de apoyo **/

	protected boolean checkDocument(ObjectId id) {

		Usuario usuario = usuarios.find(Filters.eq("_id", id)).first();
		
		return usuario != null;
	}
	

	/** fin métodos de apoyo **/

	@Override
	public String add(Usuario usuario) throws RepositorioException {

		try {
			
			ObjectId id = new ObjectId();
			usuario.setId(id);
			
			usuarios.insertOne(usuario);
			
			return id.toString();
			
		} catch (Exception e) {
			throw new RepositorioException("No se ha podido insertar la entidad", e);
		}
	}

	@Override
	public void update(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
			
		if (! checkDocument(usuario.getId()))
			throw new EntidadNoEncontrada("No existe la entidad con id:" + usuario.getId());
		

		try {
			
			usuarios.replaceOne(Filters.eq("_id", usuario.getId()), usuario);
			
			
		} catch (Exception e) {
			throw new RepositorioException("No se ha podido actualizar la entidad, id:" + usuario.getId(), e);
		}

	}

	@Override
	public void delete(Usuario usuario) throws RepositorioException, EntidadNoEncontrada {
		
		if (! checkDocument(usuario.getId()))
			throw new EntidadNoEncontrada("No existe la entidad con id:" + usuario.getId());
		
		try {
			usuarios.deleteOne(Filters.eq("_id", usuario.getId()));
			
		} catch (Exception e) {
			throw new RepositorioException("No se ha podido borrar la entidad, id:" + usuario.getId(), e);
		}

	}

	@Override
	public Usuario getById(String id) throws RepositorioException, EntidadNoEncontrada {

		Usuario usuario = usuarios.find(Filters.eq("_id", new ObjectId(id))).first();
		
		if (usuario == null)
			throw new EntidadNoEncontrada("No existe la entidad con id:" + id);
		
		return usuario;
	}

	@Override
	public List<Usuario> getAll() throws RepositorioException {
		
		LinkedList<Usuario> allUsuarios = new LinkedList<>();

		usuarios.find().into(allUsuarios);
		
		return allUsuarios;
	}

	@Override
	public List<String> getIds() {
		
		LinkedList<String> allIds = new LinkedList<String>();
		
		usuarios.find().map(e -> e.getId().toString()).into(allIds);
		
		
		return allIds;
	}


	@Override
	public Usuario getByUserId(String userId) throws EntidadNoEncontrada {
		Usuario usuario = usuarios.find(Filters.eq("userId", userId)).first();
		
		if (usuario == null)
			throw new EntidadNoEncontrada("No existe la entidad con url:" + userId);
		
		return usuario; 
		
		 
	}
}