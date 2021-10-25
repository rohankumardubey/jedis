package redis.clients.jedis.params;


public class GeoSearchstoreParam extends GeoSearchParam {
    private final String destination;

    public GeoSearchstoreParam(String dest) {
        this.destination = dest;
    }

    public String getDest() {
        return this.destination;
    }

    public GeoSearchstoreParam storedist() {
        addParam(STOREDIST);
        return this;
    }

    public static GeoSearchstoreParam GeoSearchstoreParam(String dest) { return new GeoSearchstoreParam(dest); }

}
