package com.test.makieiev.webshop.util.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZIPResponseWrapper extends ServletOutputStream {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final GZIPOutputStream gzipOutputStream;
    private boolean closed;
    private final HttpServletResponse response;
    private final ServletOutputStream output;

    public GZIPResponseWrapper(HttpServletResponse response) throws IOException {
        super();
        closed = false;
        this.response = response;
        this.output = response.getOutputStream();
        byteArrayOutputStream = new ByteArrayOutputStream();
        gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
    }

    @Override
    public void close() throws IOException {
        checkClose();
        gzipOutputStream.finish();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        response.addHeader("Content-Length", Integer.toString(bytes.length));
        response.addHeader("Content-Encoding", "gzip");
        output.write(bytes);
        output.flush();
        output.close();
        closed = true;
    }

    @Override
    public void flush() throws IOException {
        checkClose();
        gzipOutputStream.flush();
    }

    public void write(int b) throws IOException {
        checkClose();
        gzipOutputStream.write((byte) b);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        write(bytes, 0, bytes.length);
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        checkClose();
        gzipOutputStream.write(bytes, off, len);
    }

    public boolean closed() {
        return (this.closed);
    }

    private void checkClose() throws IOException {
        if (closed) {
            throw new IOException("Closed output stream");
        }
    }

}