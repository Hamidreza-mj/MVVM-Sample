package hlv.app.mvvmsample.repo.remote.event;

import hlv.app.mvvmsample.repo.remote.Status;

public class ResponseEvent<T> {

    private Status status;
    private T result;
    private String message;

    private int perPage;
    private int totalPages;
    private int currentPage;

    public ResponseEvent<T> response(Status status, T result, String message) {
        this.status = status;
        this.result = result;
        this.message = message;
        return this;
    }

    public ResponseEvent<T> loadingResponse() {
        return response(Status.LOADING, null, "Loading...");
    }

    public ResponseEvent<T> successResponse(T result) {
        return response(Status.SUCCESS, result, "Success.");
    }

    public ResponseEvent<T> failureResponse(String message) {
        return response(Status.FAILURE, null, message);
    }

    public ResponseEvent<T> networkErrorResponse() {
        return response(Status.NETWORK_ERROR, null, "Network Error!");
    }

    public void setPageMeta(int currentPage, int perPage, int totalPages) {
        this.perPage = perPage;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isLastPage() {
        return currentPage == totalPages;
    }

}
