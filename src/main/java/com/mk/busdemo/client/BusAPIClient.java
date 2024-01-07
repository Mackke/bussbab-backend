package com.mk.busdemo.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.mk.busdemo.client.response.JourneyResponse;
import com.mk.busdemo.client.response.StopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Basic Client for calling the trafiklab API. Only needed Routes are implemented with the parameters they require
 * <p>
 * Since the API is really slow due to a huge amount of data, we gotta set the "Accept-Encoding" Header to gzip, deflate
 * like they described in their Docs. That way the data is delivered compressed.
 * <p>
 * Unfortunately tho, that is quite fishy, and it sometimes doesn't return the zipped data.
 * <p>
 * Also, the gzip header only work when requesting the Data as XML and NOT Json. That's why XmlMapper is needed.
 **/
@Component
public class BusAPIClient {
    private static final Logger LOG = LoggerFactory.getLogger(BusAPIClient.class);

    private static final String API_MODEL_BUS_STOP = "stop";
    private static final String API_MODEL_JOURNEY = "JourneyPatternPointOnLine";
    private static final String API_TRANSPORT_MODE = "BUS";

    private final HttpClient client;
    private final XmlMapper xmlMapper;

    @Value("${busApi.baseUrl}")
    private String busApiBaseUrl;

    @Value("${busApi.key}")
    private String busApiKey;

    public BusAPIClient() {
        this.client = createHttpClient();
        this.xmlMapper = createXmlMapper();
    }

    /**
     * Get all Journeys for a bus
     *
     * @return JourneyResponse
     */
    public JourneyResponse getJourneys() {
        var request = newGETRequest(API_MODEL_JOURNEY);
        return execRequest(request, JourneyResponse.class);
    }

    /**
     * Get all Bus Stops
     * @return BusStopResponse
     */
    public StopResponse getBusStops() {
        var request = newGETRequest(API_MODEL_BUS_STOP);
        return execRequest(request, StopResponse.class);
    }


    private HttpClient createHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    private XmlMapper createXmlMapper() {
        var mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * Helper Methods for executing a HttpRequest and return the body with given Response type
     * <p>
     * Not optimal yet since other status codes are not handled etc.
     *
     * @param req          the httpRequest
     * @param responseType the response type
     * @param <T>          The Class of the response body
     * @return An Object of given type with response data
     */
    private <T> T execRequest(HttpRequest req, Class<T> responseType) {
        try {
            HttpResponse<InputStream> response = client.send(req, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() == 200) {
                boolean isGzipped = "gzip".equalsIgnoreCase(response.headers().firstValue("Content-Encoding").orElse(""));

                String result;
                InputStream bodyStream = response.body();

                if (isGzipped) {
                    LOG.info("[BusApiClient] Response is gzipped");
                    bodyStream = new GZIPInputStream(bodyStream);
                }

                try (var in = bodyStream) {
                    result = new String(in.readAllBytes());
                }

                return Optional.ofNullable(xmlMapper.readTree(result))
                        .map(root -> root.path("ResponseData"))
                        .map(responseData -> parseNode(responseData, responseType))
                        .orElse(null);
            }
            LOG.warn("[BusApiClient] Unhandled response code returned from request");
        } catch (IOException | InterruptedException e) {
            LOG.error("[BusApiClient] Error during Request execution", e);
        }

        return null;
    }

    private URI buildRequestURI(String model) {
        return URI.create(String.format("%s?model=%s&DefaultTransportModeCode=%s&key=%s",
                busApiBaseUrl, model, API_TRANSPORT_MODE, busApiKey));
    }

    private HttpRequest newGETRequest(String model) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(buildRequestURI(model))
                .header("Accept-Encoding", "gzip, deflate")
                .build();
    }

    private <T> T parseNode(JsonNode node, Class<T> responseType) {
        try {
            return xmlMapper.treeToValue(node, responseType);
        } catch (JsonProcessingException e) {
            LOG.error("[BusApiClient] Error during parsing of response body", e);
            return null;
        }
    }
}