package Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 208-it-01 on 28.03.2018.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;


    public Result(Boolean error, String message) {
        this.error = error;
        this.message = message;

    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}