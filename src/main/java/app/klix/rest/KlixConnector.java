package app.klix.rest;

import app.klix.GetOrderRequest;
import app.klix.GetOrderResponse;
import app.klix.KlixClient;
import app.klix.error.KlixException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class KlixConnector {

    public static final String X_KLIX_API_KEY = "X-KLIX-Api-Key";

    private final KlixClient klixClient;

    public KlixConnector(KlixClient klixClient) {
        this.klixClient = klixClient;
    }

    public GetOrderResponse getOrderDetails(GetOrderRequest request) {
        String baseUri = klixClient.getEnvironment().getUri();

        String merchantId;
        String orderId;
        try {
            merchantId = URLEncoder.encode(request.getMerchantId(), "UTF-8");
            orderId = URLEncoder.encode(request.getOrderId(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new KlixException("Failed to encode", e);
        }

        HttpGet httpGet = new HttpGet(baseUri + "/merchants/" + merchantId + "/orders/" + orderId);
        httpGet.addHeader(X_KLIX_API_KEY, klixClient.getApiKey());

        HttpClient httpClient = klixClient.getHttpClient();
        try {
            httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return
    }

}
