package com.demo.myremedytestapp.galleryfunction;

import com.google.gson.annotations.SerializedName;

public class ResponseImageUpload {
    @SerializedName("response")
    ResponseUpload response;

    public ResponseUpload getResponse() {
        return response;
    }

    public void setResponse(ResponseUpload response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseImageUpload{" +
                "response=" + response +
                '}';
    }

    public class ResponseUpload {
        @SerializedName("is_error")
        Integer is_error;
        @SerializedName("message")
        String message;
        @SerializedName("image_url")
        String image_url;

        public Integer getIs_error() {
            return is_error;
        }

        public void setIs_error(Integer is_error) {
            this.is_error = is_error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        @Override
        public String toString() {
            return "ResponseUpload{" +
                    "is_error=" + is_error +
                    ", message='" + message + '\'' +
                    ", image_url='" + image_url + '\'' +
                    '}';
        }
    }
}
