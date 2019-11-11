package app.klix.rest;


import net.jcip.annotations.NotThreadSafe;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import java.net.URI;

@NotThreadSafe
public
class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    private static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return METHOD_NAME;
    }

    HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteWithBody() {
        super();
    }
}