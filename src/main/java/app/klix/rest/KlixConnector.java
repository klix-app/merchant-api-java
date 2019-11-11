package app.klix.rest;

import app.klix.KlixClient;
import app.klix.encoder.JwsEncoder;
import app.klix.error.KlixException;
import app.klix.request.ApproveOrderRequest;
import app.klix.request.GetOrderRequest;
import app.klix.request.RejectOrderRequest;
import app.klix.response.GetOrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.ToString;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@ToString
public final class KlixConnector {

    private static final String X_KLIX_API_KEY = "X-KLIX-Api-Key";

    private final KlixClient klixClient;

    private final JwsEncoder jwsEncoder;

    public KlixConnector(KlixClient klixClient, JwsEncoder jwsEncoder) {

        this.klixClient = klixClient;
        this.jwsEncoder = jwsEncoder;
    }

    public GetOrderResponse getOrderDetails(GetOrderRequest request) {

        HttpResponse response = getOrderDetailsEntity(request);
        try {
            String rawBody = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = klixClient.getObjectMapper();

            return objectMapper.readValue(rawBody, GetOrderResponse.class);

        } catch (IOException e) {
            throw new KlixException("Failed to deserialize order [" + request.getOrderId() + "] response object", e);
        }

    }

    public HttpResponse rejectOrder(RejectOrderRequest request) {

        String baseUri = klixClient.getEnvironment().getUri();
        String merchantCertificateId = klixClient.getMerchantCertificateId();
        String privateKey = new String(klixClient.getPrivateKey());


        String merchantId;
        String orderId;
        String reasonCode;
        try {
            merchantId = URLEncoder.encode(request.getMerchantId(), "UTF-8");
            orderId = URLEncoder.encode(request.getOrderId(), "UTF-8");
            reasonCode = URLEncoder.encode(request.getReasonCode().name(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new KlixException("Failed to encode", e);
        }

        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(baseUri + "/merchants/" + merchantId + "/orders/" + orderId);

        HttpClient httpClient = klixClient.getHttpClient();
        try {
            String jsonString = new JSONObject()
                    .put("id", orderId)
                    .put("reasonCode", reasonCode)
                    .toString();

            String encodedBody = jwsEncoder.encodeJwsBody(jsonString, merchantCertificateId, privateKey);

            StringEntity input = new StringEntity(encodedBody, ContentType.APPLICATION_JSON);
            httpDelete.setEntity(input);

            return httpClient.execute(httpDelete);

        } catch (IOException | JSONException e) {
            throw new KlixException("Failed to reject order [" + orderId + "]", e);
        }

    }

    public HttpResponse approveOrder(ApproveOrderRequest request) {

        String baseUri = klixClient.getEnvironment().getUri();
        String merchantCertificateId = klixClient.getMerchantCertificateId();
        String privateKey = new String(klixClient.getPrivateKey());


        String merchantId;
        String orderId;
        try {
            merchantId = URLEncoder.encode(request.getMerchantId(), "UTF-8");
            orderId = URLEncoder.encode(request.getOrderId(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new KlixException("Failed to encode", e);
        }

        HttpPut httpPut = new HttpPut(baseUri + "/merchants/" + merchantId + "/orders/" + orderId);

        HttpClient httpClient = klixClient.getHttpClient();
        try {
            GetOrderRequest orderRequest = new GetOrderRequest(this);
            orderRequest.withMerchantId(merchantId);
            orderRequest.withOrderId(orderId);

            String fullOrderDetails = getOrderDetailsRaw(orderRequest);

            String encodedBody = jwsEncoder.encodeJwsBody(fullOrderDetails, merchantCertificateId, privateKey);

            StringEntity input = new StringEntity(encodedBody, ContentType.APPLICATION_JSON);
            httpPut.setEntity(input);

            return httpClient.execute(httpPut);

        } catch (IOException e) {
            throw new KlixException("Failed to approve order [" + orderId + "]", e);
        }

    }

    private HttpResponse getOrderDetailsEntity(GetOrderRequest request) {

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
        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            Preconditions.checkArgument(response.getStatusLine().getStatusCode() == 200);
            return response;

        } catch (IOException e) {
            throw new KlixException("Failed to deserialize order [" + orderId + "] response object", e);
        }

    }

    private String getOrderDetailsRaw(GetOrderRequest request) {

        HttpResponse response = getOrderDetailsEntity(request);
        try {
            return EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            throw new KlixException("Failed to deserialize order [" + request.getOrderId() + "] response object", e);
        }

    }

}
