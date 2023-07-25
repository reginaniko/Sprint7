
public class OrderListRequest {
    private Integer courierId;
    //    private List<String> nearestStation;
    private String nearestStation;
    private Integer limit;
    private Integer page;

    public OrderListRequest(Integer courierId, String nearestStation, Integer limit, Integer page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    public OrderListRequest( String nearestStation, Integer limit, Integer page) {
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    public OrderListRequest(){}



    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getNearestStation() {
        return nearestStation;
    }

    public void setNearestStation(String nearestStation) {
        this.nearestStation = nearestStation;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
