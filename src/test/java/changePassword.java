import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class changePassword {

    public String authorizationToken;
    public String baseURI="https://dev.bes-learning.com:3005/api/v1";

    public String userEmail ="Thor@mailinator.com";
    public String userPassword = "zkbzraSptSpc";

    public String oldPassword = userPassword;
    public String newPassword = "Test@123";
    public String confirmNewPassword = "Test@123";

    @Test(priority = 1)
    @Feature("Positive Scenario")
    public void LoginUser_POST(){
        String body = "{\"email\":\"" + userEmail + "\",\"password\":\"" + userPassword + "\"}";

        System.out.println(body);
        Response response=given()
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/auth/login");

        if (response.getStatusCode() == 200) {
            // Parse the response JSON string into a JsonObject
            JsonPath jsonPath = response.jsonPath();
            authorizationToken = jsonPath.get("data.auth.accessToken");
        }
        System.out.println(authorizationToken);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dependsOnMethods = {"LoginUser_POST"}, priority = 3)
    @Feature("Positive Scenario")
    public void ChangePassword_POST(){
        String body="{\"oldPassword\":\"" + oldPassword + "\",\"newPassword\":\"" + newPassword + "\",\"confirmNewPassword\":\"" + confirmNewPassword + "\"}";
//        String authorizationToken= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OTksImp0aSI6IjA1Zzk4YmNlYTE5MzFiNzIyMTdnOTdkYmU4MTZhZWMwNzQ4ZDhiNDQiLCJpYXQiOjE3MTk4MzY5MTQsImV4cCI6MTc1MTM5NDUxNH0.0eWtDz4DUOilb-NQn_f7fcvOwffLxyNr47mq8t3YUIA";

        System.out.println(body);
        System.out.println(authorizationToken);
        Response response=given()
                .header("Authorization","Bearer "+ authorizationToken)
                .contentType("application/json")
                .body(body)
                .post(baseURI+"/auth/change-password");

        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
