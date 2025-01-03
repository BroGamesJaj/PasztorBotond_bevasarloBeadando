package hu.petrik.pasztorbotond_bevasarlobeadando;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitApiService {
    // GET all users
    @GET("4JMugO/termekek")
    Call<List<Termekek>> getAllTermek();

    // GET user by ID
    @GET("4JMugO/termekek/{id}")
    Call<Termekek> getTermekById(@Path("id") int id);

    // POST (create a new user)
    @POST("4JMugO/termekek")
    Call<Termekek> createTermek(@Body Termekek people);

    // PUT (update a user)
    @PUT("4JMugO/termekek/{id}")
    Call<Termekek> updateTermek(@Path("id") int id, @Body Termekek people);

    // DELETE (delete a user by ID)
    @DELETE("4JMugO/termekek/{id}")
    Call<Termekek> deleteTermek(@Path("id") int id);
}
