package apitestestcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TC2_PostRequest {

    @Test(priority = 1)
    public void tc_getAllvideoGames() {
        given()
                .when()
                .get("http://localhost:8080/app/videogames")
                .then()
                .statusCode(200);

    }

        @Test(priority=2)
        public void tc_PostRequest() {

            HashMap data = new HashMap();

                data.put("id","201");
                        data.put("name", "supermario");
                        data.put("releaseDate", "2021-08-25T15:06:10.562Z");
                        data.put("reviewScore", 500);
                        data.put("category","bowling");
                        data.put("rating", "300");

            Response result =   given()
                                .contentType("application/json")
                                .body(data)
                                .when()
                                .post("http://localhost:8080/app/videogames")
                                .then()
                                .statusCode(200)
                                .log().body()
                                .extract().response();
                    String jsonString = result.asString();
            Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);

        }
        @Test(priority =3 )
        public void get_VideoGame() {
            given()
                    .when()
                    .get("http://localhost:8080/app/videogames/201")
                    .then()
                    .statusCode(200)
                    .log().body()
                    .body("videoGame.id",equalTo("201"))
                    .body("videoGame.name",equalTo("supermario"));



        }
       @Test(priority = 4)
        public void update_videoGame() {
            HashMap data = new HashMap();

            data.put("id","201");
            data.put("name", "pacman");
            data.put("releaseDate", "2021-08-25T15:06:10.562Z");
            data.put("reviewScore", 600);
            data.put("category","running");
            data.put("rating", "300");

            given()
                    .contentType("application/json")
                    .body(data)
                    .when()
                    .put("http://localhost:8080/app/videogames/201")
                    .then()
                    .statusCode(200)
                    .log().body()
                    .body("videoGame.id",equalTo("201"))
                    .body("videoGame.name",equalTo("pacman"));


        }
@Test(priority = 5)
        public void delete_VideoGame() throws InterruptedException {
            Response res=
            given()
                    .when()
                    .delete("http://localhost:8080/app/videogames/201")
                    .then()
                    .statusCode(200)
                    .log().body()
                    .extract().response();
            Thread.sleep(3000);
            String jsonString = res.asString();
            Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
        }
}
