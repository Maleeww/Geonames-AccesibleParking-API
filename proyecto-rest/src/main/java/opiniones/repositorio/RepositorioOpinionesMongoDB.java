package opiniones.repositorio;

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

import opiniones.modelo.Opinion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class RepositorioOpinionesMongoDB implements RepositorioOpiniones {

	
	private MongoCollection<Opinion> opiniones;

	
	public RepositorioOpinionesMongoDB() {
		
		// TODO: cadena de conexión
		
		String uriString = "mongodb+srv://malew:<password>@cluster0.y5vc0.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
		ConnectionString connectionString = new ConnectionString(uriString);

		CodecRegistry pojoCodecRegistry = CodecRegistries
				.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.codecRegistry(codecRegistry).build();

		MongoClient mongoClient = MongoClients.create(clientSettings);
		
		MongoDatabase db = mongoClient.getDatabase("arso");
		
		this.opiniones = db.getCollection("opiniones", Opinion.class);
		
	}

	/** Métodos de apoyo **/

	protected boolean checkDocument(ObjectId id) {

		Opinion encuesta = opiniones.find(Filters.eq("_id", id)).first();
		
		return encuesta != null;
	}
	

	/** fin métodos de apoyo **/

	@Override
	public String add(Opinion opinion) throws RepositorioException {

		try {
			
			ObjectId id = new ObjectId();
			opinion.setId(id);
			
			opiniones.insertOne(opinion);
			
			return id.toString();
			
		} catch (Exception e) {
			throw new RepositorioException("No se ha podido insertar la entidad", e);
		}
	}

	@Override
	public void update(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {
			
		if (! checkDocument(opinion.getId()))
			throw new EntidadNoEncontrada("No existe la entidad con id:" + opinion.getId());
		

		try {
			
			opiniones.replaceOne(Filters.eq("_id", opinion.getId()), opinion);
			
			
		} catch (Exception e) {
			throw new RepositorioException("No se ha podido actualizar la entidad, id:" + opinion.getId(), e);
		}

	}

	@Override
	public void delete(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {
		
		if (! checkDocument(opinion.getId()))
			throw new EntidadNoEncontrada("No existe la entidad con id:" + opinion.getId());
		
		try {
			opiniones.deleteOne(Filters.eq("_id", opinion.getId()));
			
		} catch (Exception e) {
			throw new RepositorioException("No se ha podido borrar la entidad, id:" + opinion.getId(), e);
		}

	}

	@Override
	public Opinion getById(String id) throws RepositorioException, EntidadNoEncontrada {

		Opinion opinion = opiniones.find(Filters.eq("_id", new ObjectId(id))).first();
		
		if (opinion == null)
			throw new EntidadNoEncontrada("No existe la entidad con id:" + id);
		
		return opinion;
	}

	@Override
	public List<Opinion> getAll() throws RepositorioException {
		
		LinkedList<Opinion> allOpiniones = new LinkedList<>();

		opiniones.find().into(allOpiniones);
		
		return allOpiniones;
	}

	@Override
	public List<String> getIds() {
		
		LinkedList<String> allIds = new LinkedList<String>();
		
		opiniones.find().map(e -> e.getId().toString()).into(allIds);
		
		
		return allIds;
	}

	@Override
	public Opinion getByUrl(String urlRecurso) throws EntidadNoEncontrada {
		/*LinkedList<Opinion> lista = new LinkedList<>();
			lista.addAll(getAll());
			for(Opinion o:lista) {
				if (o.getUrlRecurso().equals(urlRecurso)) return o;
			}
		return null;
		*/
		Opinion opinion = opiniones.find(Filters.eq("urlRecurso", urlRecurso)).first();
		
		if (opinion == null)
			throw new EntidadNoEncontrada("No existe la entidad con url:" + urlRecurso);
		
		return opinion; 
		
		 
	}
}