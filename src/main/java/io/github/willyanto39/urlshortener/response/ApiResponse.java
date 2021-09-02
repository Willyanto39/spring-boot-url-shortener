package io.github.willyanto39.urlshortener.response;

public final class ApiResponse<T> {
  private String status;
  private T data;
  private String message;

  public ApiResponse() {}

  private ApiResponse(String status) {
    this.status = status;
  }

  private ApiResponse(String status, T data) {
    this(status);
    this.data = data;
  }

  private ApiResponse(String status, String message) {
    this(status);
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data);
  }

  public static ApiResponse<Void> error(String message) {
    return new ApiResponse<>("error", message);
  }
}
