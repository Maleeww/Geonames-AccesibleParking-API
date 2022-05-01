package retrofit.opiniones;

import opiniones.modelo.Opinion;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OpinionesRestClient {
	
	@FormUrlEncoded
	@POST("opiniones")
	Call<Void> create(@Field("url")String url);
	
	@GET("opiniones/{url}")
	Call<Opinion> getByUrl(@Path("url") String url);

	@DELETE("opiniones/{url}")
	Call<Void> removeByUrl(@Path("url") String url);

	@FormUrlEncoded
	@POST("opiniones/{url}")
	Call<Void> valorar(@Path("url") String url, @Field("email") String email,@Field("nota") int nota,@Field("comentario") String comentario);


}
