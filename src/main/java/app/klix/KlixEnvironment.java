package app.klix;

public enum KlixEnvironment {

    STAGING("https://api.stage.klix.app"),
    PRODUCTION("https://api.klix.app");

    private final String uri;

    KlixEnvironment(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

}
