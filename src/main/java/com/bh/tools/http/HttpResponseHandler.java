package com.bh.tools.http;

import com.bh.tools.http.model.Response;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author JanChao .
 */
public class HttpResponseHandler extends AbstractResponseHandler<Response> {

    private Response response;

    @Override
    public Response handleResponse(org.apache.http.HttpResponse httpResponse) throws IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        response = new Response();
        response.setCode(statusLine.getStatusCode());
        response.setErrorMessage(statusLine.getReasonPhrase());
        return handleEntity(httpResponse.getEntity());
    }

    @Override
    public Response handleEntity(HttpEntity entity) throws IOException {
        if (entity != null) {
            response.setContent(EntityUtils.toString(entity));
        }
        EntityUtils.consume(entity);
        return response;
    }
}
