package com.test.makieiev.webshop.util.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class GZIPHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private final HttpServletResponse origResponse;
    private ServletOutputStream stream = null;
    private PrintWriter writer = null;

    public GZIPHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }

    private ServletOutputStream createOutputStream() throws IOException {
        return (new GZIPResponseWrapper(origResponse));
    }

    public void finishResponse() throws IOException {
        if (Objects.nonNull(writer)) {
            writer.close();
        } else if (Objects.nonNull(stream)) {
            stream.close();
        }
    }

    public void flushBuffer() throws IOException {
        stream.flush();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        checkWriter();
        if (Objects.isNull(stream)) {
            stream = createOutputStream();
        }
        return (stream);
    }

    public PrintWriter getWriter() throws IOException {
        if (Objects.nonNull(writer)) {
            return (writer);
        }
        checkStream();
        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8));
        return (writer);
    }

    public void setContentLength(int length) {
    }

    private void checkStream() {
        if (Objects.nonNull(stream)) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }
    }

    private void checkWriter() {
        if (Objects.nonNull(writer)) {
            throw new IllegalStateException("getWriter() has already been called!");
        }
    }

}
